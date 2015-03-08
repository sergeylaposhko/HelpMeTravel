package ua.laposhko.hmt.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 *
 */
public abstract class AbstractService {

    protected long getId(String idString, String paramName, Logger logger) {
	long res = 0;
	try {
	    res = Long.valueOf(idString);
	} catch (NumberFormatException e) {
	    logger.error("Wrong parameter " + idString);
	    throw new WrongParamException(paramName);
	}
	return res;
    }

    protected <T> List<T> filter(String start, String end, List<T> list) {
	if(start != null && end != null && list != null){
	    try {
		int s = Integer.valueOf(start);
		int e = Integer.valueOf(end);
		if(s < 0 || s > e || s > list.size()) {
		    return new ArrayList<T>();
		}
		if(e > list.size()){
		    return list.subList(s, list.size());
		}
		return list.subList(s, e);
	    } catch (NumberFormatException e) {
		e.printStackTrace();
	    }
	}
	return list;
    }
}
