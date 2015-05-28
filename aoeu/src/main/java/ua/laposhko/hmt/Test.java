package ua.laposhko.hmt;

import java.util.List;

import ua.laposhko.hmt.config.Log4JConfig;
import ua.laposhko.hmt.dao.CountryDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Country;
import ua.laposhko.hmt.entity.Place;


/**
 * @author Sergey Laposhko
 */
public class Test {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Test.class);

    public static void main(String[] args) throws NoSuchEntityException {
        DAOFactory factory = DAOFactory.getIntsatnce();
    }

}
