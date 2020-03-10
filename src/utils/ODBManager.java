package utils;

import entities.Point;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ODBManager {

    private EntityManagerFactory emf;
    private EntityManager em;
    private String db;

    public ODBManager(String db) {
        this.db = db;
    }

    public void connect() {
        emf = Persistence.createEntityManagerFactory("$objectdb/db/" + db + ".odb");
        em = emf.createEntityManager();
    }

    public void disconnect() {
        em.close();
        emf.close();
    }

    public EntityManager getEm() {
        return em;
    }
    
//    private void store(int n) {
//        em.getTransaction().begin();
//        for (int i = 0; i < n; i++) {
//            Point p = new Point(i, i);
//            em.persist(p);
//        }
//        em.getTransaction().commit();
//    }
    
    public Object findByPrimaryKey(Object o, Object pk) {
        o = em.find(o.getClass(), pk);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return o;
    }

//    private static List<Object> listAll(Object o){
//        TypedQuery<Object> query = em.createQuery("SELECT p FROM " p", (Class<Object>) o.getClass());
//        List<Object> results = query.getResultList();
//        return results;
//    }
}
