package pf.persistent.impl;

import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Hashtable;

import pf.persistent.CommonDAO;
import pf.persistent.DBConnection;



public class CommonDAOImpl implements CommonDAO {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public List query(String sql,Object[] params) throws RuntimeException, Exception{
		List list = new ArrayList();
		Map ht = null;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(sql);
			if(null!=params && 0<params.length) {
				this.setParams(ps,params);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				ht = new Hashtable();
				for (int i = 1; i <= columnCount; i++) {
					Object obj = rs.getObject(i);
					if (obj != null) {
						ht.put(rsmd.getColumnName(i).toLowerCase(), obj);
					}
				}
				list.add(ht);
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			DBConnection.closeConnection();
		}
		return list;
	}
	
	public Map queryOne(String sql,Object[] params) throws RuntimeException, Exception{
		Map ht = null;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(sql);
			if(null!=params && 0<params.length) {
				this.setParams(ps,params);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				ht = new Hashtable();
				for (int i = 1; i <= columnCount; i++) {
					Object obj = rs.getObject(i);
					if (obj != null) {
						ht.put(rsmd.getColumnName(i).toLowerCase(), obj);
					}
				}
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			DBConnection.closeConnection();
		}
		return ht;
	}

	public boolean exec(String sql,Object[] params) throws RuntimeException, Exception {
		boolean isSuccess = false;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(sql);
			if(null!=params && 0<params.length) {
				this.setParams(ps,params);
			}
			int i = ps.executeUpdate();
			if (i >= 1)
				isSuccess = true;
		} finally {
			if (ps != null) ps.close();
			DBConnection.closeConnection();
	
		}
		return isSuccess;
	}
	
	public int queryCount(String sql, Object[] params) throws RuntimeException, Exception {
		int ret = 0;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(sql);
			if(null!=params && 0<params.length) {
				this.setParams(ps,params);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ret = rs.getInt(1);
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			DBConnection.closeConnection();
		}
		return ret;
	}

	private void setParams(PreparedStatement pstmt, Object[] params) throws Exception{
		if (null != params) {
			for (int i = 0, paramNum = params.length; i < paramNum; i++) {
				if (null != params[i] && params[i] instanceof java.util.Date) {
					pstmt.setTimestamp(i + 1, new java.sql.Timestamp(((java.util.Date)params[i]).getTime()));
			    } else {
		            pstmt.setObject(i + 1, params[i]);
			    }			    
			 }
		 }
	}
}
