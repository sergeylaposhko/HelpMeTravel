package ua.laposhko.hmt;

import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;


/**
 * @author Sergey Laposhko
 */
public class Test {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Test.class);

    public static void main(String[] args) throws NoSuchEntityException {
        DAOFactory factory = DAOFactory.getIntsatnce();
    }

}
