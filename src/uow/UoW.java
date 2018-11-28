package uow;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.dsrg.soenea.domain.DomainObject;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.MetaDomainObject;
import org.dsrg.soenea.domain.MetaMapper;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.interf.IDomainObject;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.dsrg.soenea.uow.MapperFactory;
import org.dsrg.soenea.uow.MissingMappingException;

import data.ConnectionManager;

public class UoW {
	
	protected Set<DomainObject<?>> registeredNewObjects = new HashSet<DomainObject<?>>();
	protected Set<DomainObject<?>> registeredDirtyObjects = new HashSet<DomainObject<?>>();
	protected Set<DomainObject<?>> registeredRemovedObjects = new HashSet<DomainObject<?>>();
	protected Set<DomainObject<?>> registeredCleanObjects = new HashSet<DomainObject<?>>();

	protected Set<DomainObject<?>> deletedObjects = new HashSet<DomainObject<?>>();
	
	public static final int CASCADE_NONE = -1;
	
	protected int cascadeDepth = CASCADE_NONE;
	protected Set<DomainObject<?>> pendingNewObjects;
	protected Set<DomainObject<?>> pendingDirtyObjects;
	protected Set<DomainObject<?>> pendingRemovedObjects;
	protected Set<DomainObject<?>> pendingCleanObjects;
	
	private static ThreadLocal<UoW> current = new ThreadLocal<UoW>() {
		protected UoW initialValue() {
			return new UoW();
		};
	};
	
	static {
		ThreadLocalTracker.registerThreadLocal(current);
	}
	
	private static MapperFactory myFactory;
	/**
	 * Questionable Synchronization...
	 * @param f
	 */
	public static void initMapperFactory(MapperFactory f) {
		MyMapper = new MetaMapper();
		MyMapper.init(f);
		myFactory = f;
	}
	
	
	public static <IDField, DO extends DomainObject<IDField>> boolean hasMappingForClass(Class<DO> key) {
		return myFactory.contains(key);
	}
	
	protected static MetaMapper MyMapper;
	
	public <IDField> boolean hasDomainObject(DomainObject<IDField> d) throws ObjectRemovedException {
		return hasObject(d);
	}
	
	/**
	 * This checks if the UoW is storing a Domain Object that corresponds to the passed
	 * MetaDomainObject representation passed.
	 * 
	 * @param d
	 * @return
	 * @throws ObjectRemovedException
	 */
	public boolean hasDomainObjectRepresentedByMeta(MetaDomainObject<?, ?> d) throws ObjectRemovedException {
		return hasObject(d);
	}
	
	private boolean hasObject(Object d) throws ObjectRemovedException {
		Collection<DomainObject<?>> R = isCascading()?pendingRemovedObjects:registeredRemovedObjects;
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		Collection<DomainObject<?>> N = isCascading()?pendingNewObjects:registeredNewObjects;
		Collection<DomainObject<?>> C = isCascading()?pendingCleanObjects:registeredCleanObjects;
		if(R.contains(d)) {
			DomainObject<?> removedObject = null;
			for(DomainObject<?> myDO:R)
				if(myDO.equals(d)) {
					removedObject = myDO;
					throw new ObjectRemovedException(removedObject);
				}
		} 
		if(deletedObjects.contains(d)) {
			DomainObject<?> removedObject = null;
			for(DomainObject<?> myDO:deletedObjects)
				if(myDO.equals(d)) {
					removedObject = myDO;
					throw new ObjectRemovedException(removedObject);
				}			
		}
		return C.contains(d) || N.contains(d) || D.contains(d);
	}
	
	@SuppressWarnings("unchecked")
	public <IDField, DO extends DomainObject<IDField>> DO getObject(MetaDomainObject<IDField, DO> md) {
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		Collection<DomainObject<?>> N = isCascading()?pendingNewObjects:registeredNewObjects;
		Collection<DomainObject<?>> C = isCascading()?pendingCleanObjects:registeredCleanObjects;
		for(DomainObject<?> myDO:N)
			if(myDO.equals(md))
				return (DO)myDO;
		for(DomainObject<?> myDO:D)
			if(myDO.equals(md))
				return (DO)myDO;
		for(DomainObject<?> myDO:C)
			if(myDO.equals(md))
				return (DO)myDO;
		return null;
	}
	
	private boolean isCascading() {
		return cascadeDepth != CASCADE_NONE;
	}
	private void incrementCascade() {
		if (cascadeDepth == CASCADE_NONE) {
			cascadeDepth = 0;
			pendingDirtyObjects = new HashSet<DomainObject<?>>(registeredDirtyObjects);
			pendingCleanObjects = new HashSet<DomainObject<?>>(registeredCleanObjects);
			pendingRemovedObjects = new HashSet<DomainObject<?>>(registeredRemovedObjects);
			pendingNewObjects = new HashSet<DomainObject<?>>(registeredNewObjects);
		}
		++cascadeDepth;
	}
	private void decrementCascade(boolean commit) {
		--cascadeDepth;
		if (cascadeDepth == 0) {
			cascadeDepth = CASCADE_NONE;
			
			if (commit) {
    			registeredDirtyObjects = pendingDirtyObjects;
    			registeredCleanObjects = pendingCleanObjects;
    			registeredRemovedObjects = pendingRemovedObjects;
    			registeredNewObjects = pendingNewObjects;
			}
			
			pendingDirtyObjects = null;
			pendingCleanObjects = null;
			pendingRemovedObjects = null;
			pendingNewObjects = null;
		}
	}
	
	public <IDField> boolean isNew(IDomainObject<IDField> d) throws MissingMappingException{
		Collection<DomainObject<?>> N = cascadeDepth==CASCADE_NONE?registeredNewObjects:pendingNewObjects;
		return contains(N, d);
	}

	public <IDField> void registerNewWithoutValidation(IDomainObject<IDField> d) throws MissingMappingException, MapperException{
		
	}
	public <IDField> void registerNew(IDomainObject<IDField> d) throws MissingMappingException, MapperException{
		DomainObject<IDField> actualDO = getInnerDomainObject(d);
		checkIfClassIsRegistered((IDomainObject<?>)actualDO);

		if (isCascading()) {
			if (pendingNewObjects.contains(actualDO))
				return;
		} else {
			if (registeredNewObjects.contains(actualDO))
				return;
		}
		
		incrementCascade();
		
		Collection<DomainObject<?>> R = isCascading()?pendingRemovedObjects:registeredRemovedObjects;
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		Collection<DomainObject<?>> N = isCascading()?pendingNewObjects:registeredNewObjects;
		Collection<DomainObject<?>> C = isCascading()?pendingCleanObjects:registeredCleanObjects;
		
		D.remove(actualDO);
		C.remove(actualDO);
		R.remove(actualDO);
		N.add(actualDO);
		
		boolean valid = false;
		try {
			MyMapper.getMapper(actualDO).validateInsert(actualDO);
			MyMapper.getMapper(actualDO).cascadeInsert(actualDO);
			valid = true;
		} catch (InstantiationException e) {
			throw new MapperException(e);
		} catch (IllegalAccessException e) {
			throw new MapperException(e);
		} finally {
    		decrementCascade(valid);
		}
	}

	public <IDField> boolean isDirty(IDomainObject<IDField> d) throws MissingMappingException{
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		return contains(D, d);
	}
	public <IDField> void registerDirty(IDomainObject<IDField> d) throws MissingMappingException, MapperException{
		DomainObject<IDField> actualDO = getInnerDomainObject(d);
		checkIfClassIsRegistered((IDomainObject<?>)actualDO);
		
		incrementCascade();
		
		Collection<DomainObject<?>> R = isCascading()?pendingRemovedObjects:registeredRemovedObjects;
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		Collection<DomainObject<?>> N = isCascading()?pendingNewObjects:registeredNewObjects;
		Collection<DomainObject<?>> C = isCascading()?pendingCleanObjects:registeredCleanObjects;
		
		if(!contains(D, actualDO) && !contains(N, actualDO)) {
			C.remove(actualDO);
			R.remove(actualDO);
			D.add(actualDO);
		}
		
		boolean valid = false;
		try {
			MyMapper.getMapper(actualDO).validateUpdate(actualDO);
			MyMapper.getMapper(actualDO).cascadeUpdate(actualDO);
			valid = true;
		} catch (InstantiationException e) {
			throw new MapperException(e);
		} catch (IllegalAccessException e) {
			throw new MapperException(e);
		} finally {
    		decrementCascade(valid);
		}
	}
	
	public <IDField> boolean isRemoved(IDomainObject<IDField> d) throws MissingMappingException{
		Collection<DomainObject<?>> R = isCascading()?pendingRemovedObjects:registeredRemovedObjects;
		return contains(R, d);
	}
	public <IDField> void registerRemoved(IDomainObject<IDField> d) throws MissingMappingException, MapperException{
		DomainObject<IDField> actualDO = getInnerDomainObject(d);
		checkIfClassIsRegistered((IDomainObject<?>)actualDO);
		
		incrementCascade();
		
		Collection<DomainObject<?>> R = isCascading()?pendingRemovedObjects:registeredRemovedObjects;
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		Collection<DomainObject<?>> N = isCascading()?pendingNewObjects:registeredNewObjects;
		Collection<DomainObject<?>> C = isCascading()?pendingCleanObjects:registeredCleanObjects;
		
		if(!N.remove(actualDO)) {
			C.remove(actualDO);
			D.remove(actualDO);
			R.add(actualDO);
		}
		
		boolean valid = false;
		try {
			MyMapper.getMapper(actualDO).validateDelete(actualDO);
			MyMapper.getMapper(actualDO).cascadeDelete(actualDO);
			valid = true;
		} catch (InstantiationException e) {
			throw new MapperException(e);
		} catch (IllegalAccessException e) {
			throw new MapperException(e);
		} finally {
    		decrementCascade(valid);
		}
	}
	
	public <IDField> void registerClean(IDomainObject<IDField> d) throws MissingMappingException{
		checkIfClassIsRegistered((IDomainObject<?>)d);
		
		DomainObject<IDField> actualDO = getInnerDomainObject(d);
		
		Collection<DomainObject<?>> R = isCascading()?pendingRemovedObjects:registeredRemovedObjects;
		Collection<DomainObject<?>> D = isCascading()?pendingDirtyObjects:registeredDirtyObjects;
		Collection<DomainObject<?>> N = isCascading()?pendingNewObjects:registeredNewObjects;
		Collection<DomainObject<?>> C = isCascading()?pendingCleanObjects:registeredCleanObjects;
		
		N.remove(actualDO);
		D.remove(actualDO);
		R.remove(actualDO);
		C.add(actualDO);
	}
	
	
	public void commit() throws MapperException, InstantiationException, IllegalAccessException, SQLException {
		try {
			ConnectionManager.getConnection().setAutoCommit(false);
			insertNew();
			updateDirty();
			deleteRemoved();
			ConnectionManager.getConnection().commit();
			
			//Now we need to clean up the registered values and set aside the deleted values (#201, #202)
			registeredCleanObjects.addAll(registeredDirtyObjects);
			registeredCleanObjects.addAll(registeredNewObjects);
			deletedObjects.addAll(registeredRemovedObjects);
			registeredDirtyObjects.clear();
			registeredNewObjects.clear();
			registeredRemovedObjects.clear();
			
		} catch(MapperException e) {
			DbRegistry.getDbConnection().rollback();
			throw e;
		}
	}
	
	protected void insertNew() throws MapperException, InstantiationException, IllegalAccessException {
		for (DomainObject<?> d: registeredNewObjects) {
			MyMapper.insert(d);
		}
	}
	
	protected void updateDirty() throws MapperException, InstantiationException, IllegalAccessException {
		for (DomainObject<?> d: registeredDirtyObjects) {
			MyMapper.update(d);
		}
	}
	
	protected void deleteRemoved() throws MapperException, InstantiationException, IllegalAccessException {
		for (DomainObject<?> d: registeredRemovedObjects) {
			MyMapper.delete(d);
		}
	}

	public static void newCurrent() {
		setCurrent(new UoW());
	}
	public static void setCurrent(UoW uow) {
		current.set(uow);
	}
	public static UoW getCurrent() {
		return current.get();
	}
	
	public static boolean isUsingUoW() {
		return current.get() != null;
	}
	
	@SuppressWarnings("unchecked")
	public <IDField> DomainObject<IDField> getInnerDomainObject(IDomainObject<IDField> d) {
		if (d instanceof DomainObjectProxy<?, ?>) {
			return ((DomainObjectProxy<IDField, ?>) d).getInnerObject();
		} else
			return (DomainObject<IDField>)d;
	}
	
	/**
	 * Determines if the DO is in the collection without causing it to unfold, if it happens to be a proxy
	 */
	private boolean contains(Collection<DomainObject<?>> collection, IDomainObject<?> d) {
		for (DomainObject<?> obj : collection) {
			if (obj.getId().equals(d.getId())) {
				if (obj.getClass().equals(d.getClass()))
					return true;
				if (d instanceof DomainObjectProxy<?, ?>) {
					if (obj.getClass().equals(((DomainObjectProxy<?,?>)d).getInnerClass()))
						return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	private void checkIfClassIsRegistered(IDomainObject d) throws MissingMappingException{
		if(d instanceof DomainObjectProxy) _checkIfClassIsRegistered((DomainObjectProxy)d);
		else _checkIfClassIsRegistered((DomainObject)d);
	}
	
	@SuppressWarnings("rawtypes")
	private void _checkIfClassIsRegistered(DomainObjectProxy d) throws MissingMappingException{
		_checkIfClassIsRegistered(d.getInnerObject());		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void _checkIfClassIsRegistered(DomainObject d) throws MissingMappingException{
		if(!hasMappingForClass(d.getClass())) throw new MissingMappingException("Class " + d.getClass().getCanonicalName() + " has not been registed with UoW.");

	}

}
