package dao;

import accounts.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserDao {
    private Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public UserDataSet get(Long id) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("id", id)).uniqueResult();
    }

    public UserDataSet getByLogin(String login) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public void add(UserDataSet userDataSet) {
        session.save(userDataSet);
    }
}
