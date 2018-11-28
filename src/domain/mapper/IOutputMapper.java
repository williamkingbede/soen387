package domain.mapper;

import org.dsrg.soenea.domain.DomainObject;
import org.dsrg.soenea.domain.MapperException;

public interface IOutputMapper<IDField, MappedObject extends DomainObject<IDField>> {

	public abstract void update(MappedObject d) throws MapperException;

	public abstract void delete(MappedObject d) throws MapperException;

	public abstract void cascadeInsert(MappedObject d) throws MapperException;

	public abstract void cascadeUpdate(MappedObject d) throws MapperException;

	public abstract void cascadeDelete(MappedObject d) throws MapperException;

	public abstract void validateInsert(MappedObject d) throws MapperException;

	public abstract void validateUpdate(MappedObject d) throws MapperException;

	public abstract void validateDelete(MappedObject d) throws MapperException;

}