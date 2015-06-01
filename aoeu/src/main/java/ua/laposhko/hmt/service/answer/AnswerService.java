package ua.laposhko.hmt.service.answer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.laposhko.hmt.dao.AnswerDAO;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.dao.generic.GenericHibernateDaoImpl;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service("answerService")
public class AnswerService extends GenericManagerImpl<Answer, GenericHibernateDao<Answer>> implements IAnswerService {

    private SessionFactory sessionFactory;

    @Autowired
    public AnswerService(@Qualifier("genericHibernateDaoImp") GenericHibernateDaoImpl<Answer> genericHibernateDao){
        genericHibernateDao.setClass(Answer.class);
        super.setDao(genericHibernateDao);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Answer> findByQuestionId(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("FROM Answer WHERE question.id=?").setLong(0, id).list();
    }
}
