package ua.laposhko.hmt.service.answer;

import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.service.generic.GenericManager;

import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
public interface IAnswerService extends GenericManager<Answer>{
    List<Answer> findByQuestionId(int id);
}
