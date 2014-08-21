package pf.tools;
import java.util.List;

public interface Pageable {
    /**
	 * 获得数据结果
	 * @return
	 */
	List getResult();
	
	/**
	 * 获得查询总数
	 * @return
	 */
	int getCount();
	
	/**
	 * 获得每页记录数
	 * @return
	 */
	int getPageSize();
	
	/**
	 * 获得当前页编号
	 * @return
	 */
	int getCurrentPage();
	
	/**
	 * 获得首页编号
	 * @return
	 */
	int getFirstPage();
	
	/**
	 * 获得前页编号
	 * @return
	 */
	int getPreviousPage();
	
	/**
	 * 获得后页编号
	 * @return
	 */
	int getNextPage();
	
	/**
	 * 获得尾页编号
	 * @return
	 */
	int getLastPage();
	
	/**
	 * 获得总页数
	 * @return
	 */
	int getPages();
	
	/**
	 * 每页默认记录数
	 */
	 
	public final static int DEFAULT_PAGESIZE = 10;

}
