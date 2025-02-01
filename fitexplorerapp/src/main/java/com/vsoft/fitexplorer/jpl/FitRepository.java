package com.vsoft.fitexplorer.jpl;

import com.vsoft.fitexplorer.Roles;
import com.vsoft.fitexplorer.UserProfile;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.jpl.entity.UserData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class FitRepository {

    private static final Logger hibernateLogger = LoggerFactory.getLogger("org.hibernate");

    @Autowired
    UserProfile userProfile;

    @Autowired
    private Environment environment;


    @Transactional
    public FitUnit save(FitUnit fitUnit) {

        Session session = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();
        if (sessionFactory instanceof SessionFactoryImplementor) {
            SessionFactoryImplementor sessionFactoryImpl = (SessionFactoryImplementor) sessionFactory;

            // Access advanced configuration options

            Integer batchSize = sessionFactoryImpl.getSessionFactoryOptions().getJdbcBatchSize();
            //System.out.println("JDBC Batch Size: " + batchSize);

            String batchSizeInt = environment.getProperty("hibernate.jdbc.batch_size");
            //System.out.println("Batch Size: " + batchSizeInt);

            String dialectName = sessionFactoryImpl.getJdbcServices().getDialect().getClass().getName();
            //System.out.println("Database Dialect: " + dialectName);
/*
            System.out.println("Order inserts enabled: " + sessionFactoryImpl.getSessionFactoryOptions()
                    .isOrderInsertsEnabled());
            System.out.println("Order updates enabled: " + sessionFactoryImpl.getSessionFactoryOptions()
                    .isOrderUpdatesEnabled());
*/

        } else {
            System.out.println("SessionFactory is not an instance of SessionFactoryImplementor.");
        }


        entityManager.merge(fitUnit);
        return fitUnit;
    }

    @Transactional
    public void save(List<FitUnit> fitUnits) {

        Session session = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();
        if (sessionFactory instanceof SessionFactoryImplementor) {
            SessionFactoryImplementor sessionFactoryImpl = (SessionFactoryImplementor) sessionFactory;

            // Access advanced configuration options

            if (hibernateLogger.isDebugEnabled() || hibernateLogger.isTraceEnabled()) {
                Integer batchSize = sessionFactoryImpl.getSessionFactoryOptions().getJdbcBatchSize();
                System.out.println("JDBC Batch Size: " + batchSize);

                String batchSizeInt = environment.getProperty("hibernate.jdbc.batch_size");
                System.out.println("Batch Size: " + batchSizeInt);

                String dialectName = sessionFactoryImpl.getJdbcServices().getDialect().getClass().getName();
                System.out.println("Database Dialect: " + dialectName);

                System.out.println("Order inserts enabled: " + sessionFactoryImpl.getSessionFactoryOptions()
                        .isOrderInsertsEnabled());
                System.out.println("Order updates enabled: " + sessionFactoryImpl.getSessionFactoryOptions()
                      .isOrderUpdatesEnabled());

            }

        } else {
            System.out.println("SessionFactory is not an instance of SessionFactoryImplementor.");
        }


        for(FitUnit f : fitUnits) {
            entityManager.persist(f);
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Transactional
    public FitActivity save(FitActivity fitActivity) {
        entityManager.persist(fitActivity);
        return fitActivity;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserData loadUser(String username) {
        TypedQuery<UserData> query = entityManager.createQuery(
                "SELECT e FROM UserData e WHERE e.username = :username", UserData.class
        );

        query.setParameter("username", username);

        var result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        } else {
            return query.getResultList().get(0);
        }
    }

    @Transactional
    public UserData save(UserData userData) {
        entityManager.persist(userData);
        return userData;
    }

    @Transactional
    public Map<Pair<Double, Double>, Long> loadHeatMap(UserData userData) {
        List result = entityManager.createNativeQuery("select latitude, longitude, count from heatmap").getResultList();
        Map<Pair<Double, Double>, Long> map = new HashMap<Pair<Double, Double>, Long>();

        for (var r : result) {
            Object[] item = (Object[])r;
            map.put( new ImmutablePair<Double, Double>((Double)item[0], (Double)item[1]), (Long) item[2]);
        }
        return map;
    }

    @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @Transactional
    public List<FitActivity> listFitActivities() {
        TypedQuery<FitActivity> query = entityManager.createQuery(
                "SELECT e FROM FitActivity e order by e.startTime", FitActivity.class
    );



        var result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        } else {
            return query.getResultList();
        }
    }

    @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @Transactional
    public Set<String> listFitActivitiesIDs() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT e.activityId FROM FitActivity e order by e.id", String.class
        );



        var result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        } else {
            return Set.copyOf(query.getResultList());
        }
    }

    @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @Transactional
    public FitActivity listFitActivity(int id) {
        TypedQuery<FitActivity> query = entityManager.createQuery(
                "SELECT e FROM FitActivity e where e.id = :id", FitActivity.class
        );

        query.setParameter("id", id);


        var result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        } else {
            var list = query.getResultList().get(0).getFitUnitList();
            return query.getResultList().get(0);
        }
    }

    @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @Transactional
    public List<FitUnit> listFitActivityUnits(FitActivity fitActivity) {
        TypedQuery<FitUnit> query = entityManager.createQuery(
                "SELECT e FROM FitUnit e where e.fitActivity = :id", FitUnit.class
        );

        query.setParameter("id", fitActivity);


        var result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        } else {
            return query.getResultList();
        }
    }

}
