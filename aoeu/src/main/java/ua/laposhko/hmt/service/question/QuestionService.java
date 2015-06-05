package ua.laposhko.hmt.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.dao.generic.GenericHibernateDaoImpl;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service("questionService")
@Transactional
public class QuestionService extends GenericManagerImpl<Question, GenericHibernateDao<Question>> implements IQuestionService{

    @Autowired
    public QuestionService(@Qualifier("genericHibernateDaoImp") GenericHibernateDaoImpl<Question> genericHibernateDao){
        genericHibernateDao.setClass(Question.class);
        super.setDao(genericHibernateDao);
    }

    @Override
    public List<Question> findByUserId(long userId) {
        List<Question> all = super.findAll();
        List<Question> result = new ArrayList<>();
        for (Question question : all) {
            if (question != null && question.getUser() != null && question.getUser().getId() == userId) {
                result.add(question);
            }
        }
        return result;
    }

    @Override
    public List<Question> findByCity(long cityId) {
        List<Question> all = findAll();
        List<Question> result = new ArrayList<>();
        for (Question question : all) {
            if(question.getCity() != null && question.getCity().getId() == cityId){
                result.add(question);
            }
        }

        return result;
    }

}
