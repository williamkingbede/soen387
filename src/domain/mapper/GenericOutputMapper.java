package domain.mapper;

import org.dsrg.soenea.domain.DomainObject;
import org.dsrg.soenea.domain.MapperException;

/**
 * The GenericOutputMapper provides an abstract superclass for all Output Mappers. 
 * @author Stuart Thiel
 *
 * @param <MappedObject>
 */
public abstract class GenericOutputMapper<IDField, MappedObject extends DomainObject<IDField>> implements IOutputMapper<IDField, MappedObject> {
	public abstract void update(MappedObject d) throws MapperException;
	public abstract void delete(MappedObject d) throws MapperException;
	public void cascadeInsert(MappedObject d) throws MapperException {
		
	}
	public void cascadeUpdate(MappedObject d) throws MapperException {
		
	}
	public void cascadeDelete(MappedObject d) throws MapperException {
		
	}
	public void validateInsert(MappedObject d) throws MapperException {
		
	}
	public void validateUpdate(MappedObject d) throws MapperException {
		
	}
	public void validateDelete(MappedObject d) throws MapperException {
		
	}
	
}