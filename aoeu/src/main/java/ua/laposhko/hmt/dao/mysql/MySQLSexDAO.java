package ua.laposhko.hmt.dao.mysql;

import java.util.List;

import ua.laposhko.hmt.dao.SexDAO;
import ua.laposhko.hmt.entity.Sex;

/**
 * @author Sergey Laposhko
 *
 */
public class MySQLSexDAO extends SexDAO {

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.SexDAO#findAllSexes()
     */
    @Override
    public List<Sex> findAllSexes() {
	return MySQLDAOFactory.findlAllEntities(Sex.class);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.SexDAO#findSexById(long)
     */
    @Override
    public Sex findSexById(long sexId) {
	return (Sex) MySQLDAOFactory.findEntityById(Sex.class, sexId);
    }

}
