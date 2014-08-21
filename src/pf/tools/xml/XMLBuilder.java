/**
 * @(#)XMLBuilder.java			
 * @update		10/03/08
 */
package pf.tools.xml;

/**
 * 类作用：创建XMLDocument文档对象
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public final class XMLBuilder {
	
	private XMLBuilder()
    {
    }
	/**
	 * 作用：创建XMLDocument文档对象
	 * 
	 * @return	XMLDocument			返回XMLDocument文档对象
	 * */
    public static XMLDocument buildDocument()
    {
        return buildDocument("");
    }
    /**
     * 作用：创建XMLDocument文档对象
     * 
     * @param	rootName			根节点名称
	 * @return	XMLDocument			返回XMLDocument文档对象
     * */
    public static XMLDocument buildDocument(String rootName)
    {
        XMLHandler xmlHandler = new XMLHandler();
        try
        {
            xmlHandler.create(rootName);
            return xmlHandler;
        }
        catch(XMLException xmlE)
        {
            xmlE.printStackTrace();
        }
        return null;
    }
}
