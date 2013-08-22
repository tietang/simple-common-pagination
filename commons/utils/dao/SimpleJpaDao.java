package commons.utils.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SimpleJpaDao<T, K extends java.io.Serializable> {

	void delete(Object o);

	void saveOrUpdate(Object o);

	void save(Object o);

	T findById(K id);

	Object findUnique(String jpql, Object... values);

	Integer findInt(String jpql, Object... values);

	Long findLong(String jpql, Object... values);

	List<T> findAll();

	List<T> findAll(int startRow, int maxRow);

	List<T> findBySQL(String sql, Object... values);

	List<T> findBySQL(String sql, int startRow, int maxRow, Object... values);

	List<Object[]> findForListBySQL(String sql, Object... values);

	List<Object[]> findForListBySQL(String sql, int startRow, int maxRow, Object... values);

	List<T> findByJPQL(String jpql, Object... values);

	List<T> findByJPQL(String jpql, int startRow, int maxRow, Object... values);

	List<T> find(JpaCallBack action);
	List<T> find(T t);
 
	List<Object[]> findForList(JpaCallBack action);

	Object execute(JpaCallBack action);

	EntityManager getEntityManager();

	void setClass(Class<T> cls);
	
}
