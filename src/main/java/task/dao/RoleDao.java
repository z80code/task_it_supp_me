package task.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import task.dao.models.Role;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleDao {

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Role role) {
        getSession().save(role);
    }

    public void delete(Role role) {
        getSession().delete(role);
    }

    @SuppressWarnings("unchecked")
    public List<Role> getAll() {
        //noinspection JpaQlInspection
        return getSession().createQuery("from Role").list();
    }

    public Role getByName(String name) {
        //noinspection JpaQlInspection
        return (Role) getSession().createQuery(
                "from Role where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    public Role getById(long id) {
        return (Role) getSession().load(Role.class, id);
    }

    public void update(Role role) {
        getSession().update(role);
    }

}
