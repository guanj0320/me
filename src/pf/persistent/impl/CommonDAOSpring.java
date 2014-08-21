package pf.persistent.impl;

import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.support.JdbcDaoSupport;

import pf.persistent.CommonDAO;


public class CommonDAOSpring extends JdbcDaoSupport implements CommonDAO { 

	public boolean exec(String sql, Object[] params) throws RuntimeException, Exception {
		getJdbcTemplate().update(sql,params);
		return true;
	}

	public List query(String sql, Object[] params) throws RuntimeException, Exception {
		return getJdbcTemplate().queryForList(sql,params);
	}
	
	public Map queryOne(String sql, Object[] params) throws RuntimeException, Exception {
		return getJdbcTemplate().queryForMap(sql,params);
	}

	public int queryCount(String sql, Object[] params) throws RuntimeException, Exception {
		return getJdbcTemplate().queryForInt(sql,params);
	}
}
