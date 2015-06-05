package ua.laposhko.hmt.service.question;

import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.service.generic.GenericManager;

import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
public interface IQuestionService extends GenericManager<Question> {

    List<Question> findByUserId(long userId);

    List<Question> findByCity(long cityId);
}
