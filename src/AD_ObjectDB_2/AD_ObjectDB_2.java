package AD_ObjectDB_2;

import entities.Point;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class AD_ObjectDB_2 {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/points2.odb");
        EntityManager em = emf.createEntityManager();
        
        //  1) almacenar 10 puntos na base (non 1000 como di o exemplo do titorial)
        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();
        
        //  2) listar todos os puntos (o seu id, e demais atributos)
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }
        
        //  3) amosar os atributos do punto con id=10
        Query q1 = em.createQuery("SELECT p FROM Point p WHERE id=10");
        System.out.println(q1.getSingleResult());
        
        //  4) actualizar o punto de id=10, coordenada y, ao valor que ten mais 2, e dicir se o atributo y do punto de id=10 valia 4, debe pasar a valer 6
        Point p1 = em.find(Point.class, 10);
        em.getTransaction().begin();
        p1.setY(p1.getY() + 2);
        em.getTransaction().commit();
        
        //  5) eliminar punto de id=5
        Point p2 = em.find(Point.class, 5);
        em.getTransaction().begin();
        em.remove(p2);
        em.getTransaction().commit();
        
        //  6) actualizar coordenada y ao valor 1000 para todos os puntos que teñan un valor de y inferior a un valor pasado por parametro (por exemplo facelo para o valor 6)
        TypedQuery<Point> query2 = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results2 = query2.getResultList();
        em.getTransaction().begin();
        for (Point p : results2) {
            if (p.getY() < 6) {
                p.setY(1000);
            }
        }
        em.getTransaction().commit();
        
        //  7) eliminar todos os puntos cuxo valor do atributo y sexa inferior a un valor pasado por parametro ( por exemplo facelo para o valor 3)
        TypedQuery<Point> query3 = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results3 = query3.getResultList();
        em.getTransaction().begin();
        for (Point p : results3) {
            if (p.getY() < 3) {
                em.remove(p);
            }
        }
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
    
}
