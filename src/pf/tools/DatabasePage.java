package pf.tools;

import java.util.*;

import pf.persistent.CommonDAO;
import pf.persistent.impl.CommonDAOImpl;



public class DatabasePage extends AbstractPage implements Pageable{
	
	private String sql;
	private Object[] params;

	
	/**
	* 通过一个结果列表来初始化
	* @param sql 传入一个sql语句，例如：select * from emp where ename like 'andy' order by deptno
	* @param params sql语句使用的参数，例如：select * from emp where ename like ? order by deptno，params里面就是问号所代表的值
	* @param conn 数据库连接
	* @throws PageException
	*/
	public DatabasePage(String sql,Object[] params) throws PageException,Exception {
		this(sql,params, 1, Pageable.DEFAULT_PAGESIZE);
	}
	
	/**
	* 通过一个结果列表来初始化，指定当前页
	* @param sql 传入一个sql语句，例如：select * from emp where ename like 'andy' order by deptno
	* @param params sql语句使用的参数，例如：select * from emp where ename like ? order by deptno，params里面就是问号所代表的值
	* @param conn 数据库连接
	* @param currentPage
	* @throws PageException
	*/
	public DatabasePage(String sql, Object[] params, int currentPage) throws PageException,Exception {
		this(sql,params, currentPage, Pageable.DEFAULT_PAGESIZE);
	}
	
	/**
	* 通过一个结果列表来初始化，指定当前页和页大小
	* @param sql 传入一个sql语句，例如：select * from emp where ename like 'andy' order by deptno
	* @param params sql语句使用的参数，例如：select * from emp where ename like ? order by deptno，params里面就是问号所代表的值
	* @param conn 数据库连接
	* @param currentPage
	* @param pageSize
	* @throws PageException
	*/
	public DatabasePage(String sql, Object[] params, int currentPage, int pageSize) throws PageException,Exception {
		super(currentPage, pageSize);
//		这里为了达到fail-fast，直接抛出异常，不是必须的
//		if(list==null) throw new NullPointerException(); 
		this.sql = sql;
		this.params = params;
		init();
	}
	
	protected void init() throws PageException,Exception {
		String countSql = "select count(*) ICOUNT from (" + sql + ")";
		CommonDAO c = new CommonDAOImpl();
		List list = c.query(countSql,params);
		Hashtable ht = null;
		int i = 0;
		for(Iterator iter = list.iterator();iter.hasNext();) {
			ht = (Hashtable)iter.next();
			i = Integer.parseInt(ht.get("ICOUNT").toString());	
		}
		this.count = i;
		
		checkPage(this.getCurrentPage());
		
		int fromIndex = (this.getCurrentPage() - 1) * this.getPageSize();
		int toIndex = Math.min(fromIndex + this.getPageSize(), this.count);
		
//		以下的语句支持order by
		String resultSql = "select * from (select rownum as PageSortID , TEMP_EXPPAGEQUERY.*  from (" + sql + ")" + " TEMP_EXPPAGEQUERY) where " + "PageSortID > " + fromIndex + " and PageSortID<=" + toIndex;
//		String resultSql = "select * from ( select row_.*, rownum rownum_ from (" + sql + ") row_ where rownum <= " + toIndex + ") where rownum_ > " + fromIndex;
		
		this.result = c.query(resultSql,params);
	}
}
