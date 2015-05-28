package ua.laposhko.hmt.service.question;

import org.springframework.stereotype.Service;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service("questionService")
public class QuestionService extends GenericManagerImpl<Question, GenericHibernateDao<Question>> implements IQuestionService{
}
