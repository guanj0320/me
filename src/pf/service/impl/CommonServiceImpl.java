package pf.service.impl;

import java.util.List;
import java.util.Map;

import pf.persistent.CommonDAO;
import pf.service.CommonService;



public class CommonServiceImpl implements CommonService {
	
	private CommonDAO commonDAOSpring;
	private CommonDAO commonDAO;
	
	public void setCommonDAOSpring(CommonDAO commonDAOSpring) {
		this.commonDAOSpring = commonDAOSpring;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	public List getResult(String sql, Object[] params) throws RuntimeException, Exception{
		return commonDAOSpring.query(sql,params);
	}

	public Map getResultOne(String sql, Object[] params) throws RuntimeException, Exception {
		return commonDAOSpring.queryOne(sql,params);
	}

	public boolean save(String sql, Object[] params) throws RuntimeException, Exception{
		return commonDAOSpring.exec(sql,params);
	}
	
	public List getResultFromDAO(String sql, Object[] params) throws RuntimeException, Exception{
		return commonDAO.query(sql,params);
	}
	
	public Map getResultOneFromDAO(String sql, Object[] params) throws RuntimeException, Exception{
		return commonDAO.queryOne(sql,params);
	}

	public boolean saveFromDAO(String sql, Object[] params) throws RuntimeException, Exception{
		return commonDAO.exec(sql,params);
	}

}
