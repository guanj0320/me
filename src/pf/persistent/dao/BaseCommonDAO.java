package pf.persistent.dao;

import java.util.List;
import java.util.Map;

public interface BaseCommonDAO {
	/**
	 * 公共查询列表数据
	 * @param sqlMapId sqlMap的id
	 * @param condition 条件map
	 * @param offset 起始记录数
	 * @param limit 记录条数,-1表示不分页查询
	 * @return List
	 * @throws Exception
	 */
	public List queryAll(String sqlMapId, Map condition, int offset, int limit) throws Exception;
	/**
	 * 公共查询列表条目数
	 * @param sqlMapId sqlMap的id
	 * @param condition 条件map
	 * @return List
	 * @throws Exception
	 */
	public int queryCount(String sqlMapId, Map condition) throws Exception;
	/**
	 * 公共查询一个对象
	 * @param sqlMapId sqlMap的id
	 * @param condition 条件map
	 * @return Map
	 * @throws Exception
	 */
	public Map queryOne(String sqlMapId, Map condition) throws Exception;
	/**
	 * 插入数据
	 * @param sqlMapId sqlMap的id
	 * @param condition 条件map
	 * @throws Exception
	 */
	public void insert(String sqlMapId, Map condition) throws Exception;
	/**
	 * 更新数据
	 * @param sqlMapId sqlMap的id
	 * @param condition 条件map
	 * @throws Exception
	 */
	public void update(String sqlMapId, Map condition) throws Exception;
	/**
	 * 删除数据
	 * @param sqlMapId sqlMap的id
	 * @param condition 条件map
	 * @throws Exception
	 */
	public void delete(String sqlMapId, Map condition) throws Exception;
}
