package commons.utils.dao;

import javax.persistence.EntityManager;

public interface JpaCallBack {
	Object doInJpa(EntityManager em);
}
