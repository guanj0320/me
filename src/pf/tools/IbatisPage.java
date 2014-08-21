/**
 * @(#)IbatisPage.java			
 * @update		10/03/09
 */
package pf.tools;

import java.util.List;
/**
 * 类作用：继承AbstractPage类，实现Pageable接口，从而有效的对查询出的数据集做处理，
 * 		如获得数据结果，获得查询总数，获得每页记录数等
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class IbatisPage extends AbstractPage implements Pageable {
	/**
	 * 作用：生成Pageable对象，其中每个对象可以中包含所需的关于记录的信息，如获得数据结果，获得查询总数，获得每页记录数等
	 * 
	 * @param	list					查询出来的记录集
	 * @param	record					总共的记录数
	 * */
	public IbatisPage(List list, int record) throws PageException,Exception {
		this(list,record,1,Pageable.DEFAULT_PAGESIZE);
	}
	/**
	 * 作用：生成Pageable对象，其中每个对象可以中包含所需的关于记录的信息，如获得数据结果，获得查询总数，获得每页记录数等
	 * 
	 * @param	list					查询出来的记录集
	 * @param	record					总共的记录数
	 * @param	currentPage				当前的页数
	 * */
	public IbatisPage(List list, int record,int currentPage) throws PageException,Exception {
		this(list,record,currentPage,Pageable.DEFAULT_PAGESIZE);
	}
	/**
	 * 作用：生成Pageable对象，其中每个对象可以中包含所需的关于记录的信息，如获得数据结果，获得查询总数，获得每页记录数等
	 * 
	 * @param	list					查询出来的记录集
	 * @param	record					总共的记录数
	 * @param	currentPage				当前的页数
	 * @param	pageSize				每张页面的记录数
	 * */
	public IbatisPage(List list, int record, int currentPage, int pageSize) throws PageException,Exception {
		super(currentPage, pageSize);
		this.result = list;
		this.count = record;
        init();
	}

	@Override
	protected void init() throws PageException, Exception {
		checkPage(this.getCurrentPage());
        int fromIndex = (this.getCurrentPage() - 1) * this.getPageSize();
        int toIndex = Math.min(fromIndex + this.getPageSize(), this.count);

        this.firstRow = fromIndex+1;
        this.lastRow = toIndex;
	}

}
