package dao;

import java.util.List;

public interface DAO<T, ID> {
	T findById(ID id);
	List<T> findAll();
	T create(T t) ;
	T updateById(ID id, T t);
	boolean delete(T t);
	boolean existsById(ID id);
}
