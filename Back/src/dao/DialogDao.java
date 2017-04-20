package dao;

import dataset.DialogDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DialogDao {
    private Session session;

    public DialogDao(Session session) {
        this.session = session;
    }

    public List<DialogDataSet> getAll() {
        return (List<DialogDataSet>) session.createCriteria(DialogDataSet.class).addOrder(Order.asc("id")).list();
    }

    public void add(DialogDataSet dialog) {
        session.save(dialog);
    }
}
