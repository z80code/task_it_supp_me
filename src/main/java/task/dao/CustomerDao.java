package task.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import task.dao.models.Customer;

@Repository
@Transactional
public class CustomerDao {
  
  @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
  @Autowired
  private SessionFactory _sessionFactory;
  
  private Session getSession() {
    return _sessionFactory.getCurrentSession();
  }

  public void save(Customer customer) {
    getSession().save(customer);
  }
  
  public void delete(Customer customer) {
    getSession().delete(customer);
  }
  
  @SuppressWarnings("unchecked")
  public List<Customer> getAll() {
    //noinspection JpaQlInspection
    return (List<Customer>)getSession().createQuery("from Customer").list();
  }
  
  public Customer getByEmail(String mail) {
    //noinspection JpaQlInspection
    return (Customer) getSession().createQuery(
        "from Customer where email = :e_mail")
        .setParameter("e_mail", mail)
        .uniqueResult();
  }

  public Customer getById(long id) {

    return (Customer) getSession().get(Customer.class, id);
  }

  public void update(Customer customer) {
    getSession().update(customer);
  }

}
