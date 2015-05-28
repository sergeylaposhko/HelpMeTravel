package ua.laposhko.hmt.service.user;

import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.generic.GenericManager;

/**
 * Created by Vadym_Vlasenko on 26.05.2015.
 */
public interface IUserService<T> extends GenericManager<T> {

    User findByLogin(String login);

}
