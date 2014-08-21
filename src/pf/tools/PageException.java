/**
 * @(#)PageException.java			
 * @update		10/03/09
 */
package pf.tools;
/**
 * 类作用：捕获页面中的异常信息
 * 
 * */
public class PageException extends Exception {

    public PageException() {
		super();
	}
    /**
     * 作用：把异常信息传给父类Exception
     * 
     * @param	message				输出异常信息
     * */
	public PageException(String message) {
		super(message);
	}
}
