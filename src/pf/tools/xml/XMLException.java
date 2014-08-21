/**
 * @(#)XMLException.java			
 * @update		10/03/08
 */
package pf.tools.xml;
/**
 * 类作用：捕获XML异常
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class XMLException extends Exception{
	/**
	 * 作用：捕获XMLException异常
	 * */
	public XMLException() {
        super("XML 操作失败！");
    }
	/**
	 * 作用：捕获XMLException异常
	 * 
	 * @param	strMsg		异常信息
	 * */
    public XMLException(String strMsg) {
        super(strMsg);
    }
	/**
	 * 作用：捕获XMLException异常
	 * 
	 * */
    public XMLException(Throwable cause) {
        super(cause);
    }
}
