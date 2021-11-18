import java.lang.reflect.Field;

import org.hibernate.Criteria;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.loader.OuterJoinLoader;
import org.hibernate.loader.criteria.CriteriaLoader;
import org.hibernate.persister.entity.OuterJoinLoadable;

public final class PrintSQLFromCriteria {

	public static final void logSQLFromCriteria(final Criteria criteria)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final CriteriaImpl c = (CriteriaImpl) criteria;
		final SessionImpl s = (SessionImpl) c.getSession();
		final SessionFactoryImplementor factory = s.getSessionFactory();
		final String[] implementors = factory.getImplementors(c.getEntityOrClassName());
		final CriteriaLoader loader = new CriteriaLoader(
				(OuterJoinLoadable) factory.getEntityPersister(implementors[0]), factory, c, implementors[0],
				s.getLoadQueryInfluencers());
		final Field f = OuterJoinLoader.class.getDeclaredField("sql");
		f.setAccessible(true);
		System.out.println((String) f.get(loader));
	}

}
