package pf.tools;
import java.util.List;
public class ListPage extends AbstractPage implements Pageable {

    private List list;
    
	
	/**
	* 通过一个结果列表来初始化
	* @param list
	* @throws PageException
	*/
	public ListPage(List list) throws PageException {
		this(list, 1, Pageable.DEFAULT_PAGESIZE);
	}
	
	/**
	* 通过一个结果列表来初始化，指定当前页
	* @param list
	* @param currentPage
	* @throws PageException
	*/
	public ListPage(List list, int currentPage) throws PageException {
		this(list, currentPage, Pageable.DEFAULT_PAGESIZE);
	}
	
	/**
	* 通过一个结果列表来初始化，指定当前页和页大小
	* @param list
	* @param currentPage
	* @param pageSize
	* @throws PageException
	*/
	public ListPage(List list, int currentPage, int pageSize) throws PageException {
		super(currentPage, pageSize);
//		这里为了达到fail-fast，直接抛出异常，不是必须的
//		if(list==null) throw new NullPointerException(); 
		this.list = list;
		init();
	}
	
	protected void init() throws PageException {
		this.count = list.size();
		checkPage(this.getCurrentPage());
		int fromIndex = (this.getCurrentPage() - 1) * this.getPageSize();
		int toIndex = Math.min(fromIndex + this.getPageSize(), this.count);
		
		this.firstRow = fromIndex+1;
		this.lastRow = toIndex;
		
		this.result = list.subList(fromIndex, toIndex);
	}
}

