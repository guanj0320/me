package pf.service;

import java.util.List;
import java.util.Map;

public interface CommonService {
	public List getResult(String sql, Object[] params) throws RuntimeException, Exception;
	public Map getResultOne(String sql, Object[] params) throws RuntimeException, Exception;
	public boolean save(String sql, Object[] params) throws RuntimeException, Exception;
	public List getResultFromDAO(String sql, Object[] params) throws RuntimeException, Exception;
	public Map getResultOneFromDAO(String sql, Object[] params) throws RuntimeException, Exception;
	public boolean saveFromDAO(String sql, Object[] params) throws RuntimeException, Exception;
}
