package commons.utils.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import commons.utils.bean.BeanUtils;

@Transactional
public class SimpleJpaDaoImpl<T, K extends java.io.Serializable> implements SimpleJpaDao<T, Serializable> {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private Class<T> cls;
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public SimpleJpaDaoImpl() {
	}

	public SimpleJpaDaoImpl(Class<T> cls) {
		this.cls = cls;
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public Query createQuery(String queryString, Object... values) {
		Query q = this.em.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				q.setParameter(i, values[i]);
			}
		}
		return q;
	}

	public Query createQuery(String queryString, int startRow, int maxRow, Object... values) {
		Query q = this.createQuery(queryString, values);
		q.setFirstResult(startRow);
		q.setMaxResults(maxRow);
		return q;
	}

	
	public void delete(Object o) {
		this.em.remove(this.em.merge(o));
	}

	
	public void save(Object o) {
		this.em.persist(o);
	}

	
	public void saveOrUpdate(Object o) {
		this.em.merge(o);
	}

	
	public Integer findInt(String jpql, Object... values) {
		Query q = this.createQuery(jpql, values);
		Object o = q.getSingleResult();
		Integer value = Integer.valueOf(o.toString());
		return value;
	}

	
	public Long findLong(String jpql, Object... values) {
		Query q = this.createQuery(jpql, values);
		Object o = q.getSingleResult();
		Long value = Long.valueOf(o.toString());
		return value;
	}

	
	public Object findUnique(String jpql, Object... values) {
		Query q = this.createQuery(jpql, values);
		Object o = q.getSingleResult();
		return o;
	}

	
	public T findById(Serializable id) {
		Assert.notNull(cls);
		return (T) this.em.find(cls, id);
	}

	@SuppressWarnings("unchecked")
	
	public List<T> findAll() {
		Assert.notNull(cls);
		String eql = "select o from " + cls.getName() + " o";
		System.out.println(eql);
		Query q = this.em.createQuery(eql);
		return q.getResultList();
	}

	
	public List<T> findAll(int startRow, int maxRow) {
		Assert.notNull(cls);
		String eql = "select o from " + cls.getName() + " o";
		System.out.println(eql);
		Query q = this.em.createQuery(eql);
		q.setFirstResult(startRow);
		q.setMaxResults(maxRow);
		return q.getResultList();
	}

	
	public List<T> findBySQL(String queryString, Object... values) {
		Assert.notNull(cls);
		Query q = this.em.createNativeQuery(queryString, cls);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i + 1, values[i]);
		}
		return q.getResultList();
	}

	
	public List<T> findBySQL(String queryString, int startRow, int maxRow, Object... values) {
		Assert.notNull(cls);
		Query q = this.em.createNativeQuery(queryString, cls);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i, values[i]);
		}
		q.setFirstResult(startRow);
		q.setMaxResults(maxRow);
		return q.getResultList();
	}

	
	public List<T> find(T t) {
		return this.findByExample(t, false).getResultList();
	}

	/**
	 * 附件条件查询
	 * 
	 * @param t
	 * @param where
	 * @return
	 */
	public List<T> find(T t, String where) {
		return this.findByExample(t, false).getResultList();
	}

	private Query findByExample(T t, boolean isSQL) {
		Field[] fs = t.getClass().getDeclaredFields();
		Method[] ms = t.getClass().getDeclaredMethods();
		Map<String, Object> map = new HashMap<String, Object>();
		String where = "";
		List<Object> list = new ArrayList<Object>();
		for (Field f : fs) {
			String fName = f.getName();
			String mName = "get" + fName.substring(0, 1).toUpperCase() + fName.substring(1);
			if (f.getType() == String.class || f.getType() == Short.class || f.getType() == Integer.class || f.getType() == Boolean.class
					|| f.getType() == Character.class || f.getType() == Byte.class || f.getType() == Double.class || f.getType() == Float.class
					|| f.getType() == Long.class || f.getType() == BigDecimal.class || f.getType() == BigInteger.class || f.getType() == Number.class
					|| f.getType() == java.util.Date.class || f.getType() == Date.class
			// f.getType() == short.class ||
			// f.getType() == int.class ||
			// f.getType() == long.class ||
			// f.getType() == float.class ||
			// f.getType() == double.class ||
			// f.getType() == byte.class ||
			// f.getType()== char.class ||
			// f.getType() == boolean.class ||
			) {
				if (f.getType() == boolean.class) {
					String z3 = fName.substring(2, 3);
					if (fName.startsWith("is") && z3.equals(z3.toUpperCase())) {
						mName = fName;
					} else {
						mName = "is" + fName.substring(0, 1).toUpperCase() + fName.substring(1);
					}
				}
				Column a = f.getAnnotation(Column.class);
				String column = (a == null || "".equals(a.name())) ? fName : a.name();
				for (Method m : ms) {
					// System.out.println(mName + "---" + m.getName());
					if (mName.equals(m.getName())) {
						Object value = null;
						try {
							value = m.invoke(t, new Object[] {});
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						if (value != null) {
							if (isSQL) {
								map.put(column, value);
								where += "".equals(where) ? "" : " and ";
								where += column + "= ? ";
							} else {
								map.put(fName, value);
								where += "".equals(where) ? "" : " and  ";
								where += "o." + fName + "= ? ";
							}
							list.add(value);
						}
						// System.out.println(fName + "===" + value);
						break;
					}
				}
			}
		}
		Query q = null;
		String entityName = t.getClass().getName();
		if (isSQL) {
			Table table = t.getClass().getAnnotation(Table.class);
			String tableName = table == null ? entityName : table.name();
			q = this.em.createNativeQuery("select * from " + tableName + " where " + where, t.getClass());
		} else {
			q = this.em.createQuery("select o from " + entityName + " o where " + where);
		}
		int i = 1;
		for (Object o : list) {
			q.setParameter(i, o);
			i++;
		}
		return q;
	}

	
	public Object execute(JpaCallBack action) {
		return action.doInJpa(this.em);
	}

	
	public List<T> find(JpaCallBack action) {
		return (List<T>) action.doInJpa(this.em);
	}

	
	public List<Object[]> findForList(JpaCallBack action) {
		return (List<Object[]>) action.doInJpa(this.em);
	}

	
	public List<T> findByJPQL(String jpql, Object... values) {
		Query q = this.em.createQuery(jpql);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i, values[i]);
		}
		return q.getResultList();
	}

	
	public List<T> findByJPQL(String jpql, int startRow, int maxRow, Object... values) {
		Query q = this.em.createQuery(jpql);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i, values[i]);
		}
		q.setFirstResult(startRow);
		q.setMaxResults(maxRow);
		return q.getResultList();
	}

	
	public List<Object[]> findForListBySQL(String sql, Object... values) {
		Query q = this.em.createNativeQuery(sql);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i, values[i]);
		}
		return q.getResultList();
	}

	
	public List<Object[]> findForListBySQL(String sql, int startRow, int maxRow, Object... values) {
		Assert.notNull(cls);
		Query q = this.em.createNativeQuery(sql, cls);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i, values[i]);
		}
		q.setFirstResult(startRow);
		q.setMaxResults(maxRow);
		return q.getResultList();
	}

	public void setClass(Class<T> cls) {
		this.cls = cls;
	}
}
