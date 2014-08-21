package pf.persistent.dao.impl;

import java.util.List;
import java.util.Map;

import pf.persistent.dao.BaseCommonDAO;
import pf.persistent.dao.BaseDAO;


public class BaseCommonDAOImpl extends BaseDAO implements BaseCommonDAO {

	public void delete(String sqlMapId, Map condition) throws Exception {
		getSqlMapClientTemplate().delete(sqlMapId, condition);
	}

	public void insert(String sqlMapId, Map condition) throws Exception {
		getSqlMapClientTemplate().insert(sqlMapId, condition);
	}

	public List queryAll(String sqlMapId, Map condition, int offset, int limit)
			throws Exception {
		if(limit < 0)
			return getSqlMapClientTemplate().queryForList(sqlMapId, condition);
		if(offset > 0) 
			return getSqlMapClientTemplate().queryForList(sqlMapId, condition, offset, limit);
		return getSqlMapClientTemplate().queryForList(sqlMapId, condition, 0, limit);
	}
	
	public int queryCount(String sqlMapId, Map condition) throws Exception {
		return ((Integer)getSqlMapClientTemplate().queryForObject(sqlMapId, condition)).intValue();
	}

	public Map queryOne(String sqlMapId, Map condition) throws Exception {
		return (Map)getSqlMapClientTemplate().queryForObject(sqlMapId, condition);
	}

	public void update(String sqlMapId, Map condition) throws Exception {
		getSqlMapClientTemplate().update(sqlMapId, condition);
	}

}
