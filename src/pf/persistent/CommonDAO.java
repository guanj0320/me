package pf.persistent;

import java.util.*;

public interface CommonDAO {
	/**
	 * 通用查询
	 * @param sql 查询语句，例如：select * from tbl where a=? and b=?
	 * @param params 查询条件数组 针对查询语句中的问号（“?”）
	 * @return List
	 * @throws Exception
	 */
	List query(String sql, Object[] params) throws RuntimeException, Exception;
	/**
	 * 通用查询
	 * @param sql 查询语句，例如：select * from tbl where a=? and b=?
	 * @param params 查询条件数组 针对查询语句中的问号（“?”）
	 * @return Map 返回一条记录
	 * @throws Exception
	 */
	Map queryOne(String sql, Object[] params) throws RuntimeException, Exception;
	/**
	 * 执行SQL
	 * @param sql 执行语句，例如：insert into tbl(a,b) values(?,?)
	 * @param params 执行语句字段值数组 针对执行语句中的问号（“?”）
	 * @return boolean
	 * @throws Exception
	 */
	boolean exec(String sql, Object[] params) throws RuntimeException, Exception;
	/**
	 * 查询个数
	 * @param sql 查询语句，例如：select count(*) from tbl where a=? and b=?
	 * @param params 查询条件数组 针对查询语句中的问号（“?”）
	 * @return int 返回查询出的个数
	 * @throws RuntimeException
	 * @throws Exception
	 */
	int queryCount(String sql, Object[] params) throws RuntimeException, Exception;
}
