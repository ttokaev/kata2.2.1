package hiber.dao;

import hiber.model.User;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      Query query = sessionFactory.getCurrentSession().createQuery("from User u join fetch u.car c where c.model = :model and c.series = :series", User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      List res = query.getResultList();
      return res.isEmpty() ? null : (User) res.get(0);
   }

}
