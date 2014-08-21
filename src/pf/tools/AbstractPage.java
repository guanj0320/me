/**
 * @(#)AbstractPage.java			
 * @update		10/03/09
 */
package pf.tools;
import java.util.List;
/**
 * 类作用：该类为抽象类，定义了实现类的接口与属性
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public abstract class AbstractPage implements Pageable {
	/**当前页面*/
    private int currentPage;
    /**页面大小，记录个数*/
	private int pageSize;
	/**总共的页面数*/
	private int pages;
	/**总的记录数*/
	protected int count;
	/**查询出的记录*/
	protected List result;
	
	protected int firstRow;
	
	protected int lastRow;
	
	/**
	* 指定当前页　
	* @param currentPage
	* @throws PageException
	*/
	public AbstractPage(int currentPage) {
		this(currentPage, Pageable.DEFAULT_PAGESIZE);
	}
	
	/**
	* 指定当前页和页大小
	* @param currentPage
	* @param pageSize
	* @throws PageException
	*/
	public AbstractPage(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	protected void checkPage(int currentPage) throws PageException {
		if((currentPage < 1) || (currentPage > this.getPages())) {
			this.currentPage = 1;
//			if(this.getPages() != 0) {
//				throw new PageException("页面超出范围:总页数为" + this.getPages() + ",当前页为" + currentPage);
//			}
		}
	}
	
	/**
	* 这个方法被子类重写用来初始化，也就是计算count值和result结果，在子类 的构造函数中调用。
	*/
	abstract protected void init() throws PageException,Exception;
	
	public List getResult() {
		return result;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getFirstRow() {
		return firstRow;
	}
	
	public int getLastRow() {
		return lastRow;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
		
	public int getFirstPage() {
		return 1;
	}
	
	public int getPreviousPage() {
		if(currentPage <= 1) {
			return 1;
		} else {
			return currentPage - 1;
		}
	}
	
	public int getNextPage() {
		if(currentPage >= pages) {
			if(pages == 0) {
				return 1;
			} else {
				return pages;
			}
		} else {
			return currentPage + 1;
		}
	}
	
	public int getLastPage() {
		if(pages <= 0) {
			return 1;
		} else {
			return pages;
		}
	}
	
	public int getPages() {
		if(pages == 0) 
			this.pages = (count + pageSize - 1)/pageSize;
		return pages;
	}
	
}

