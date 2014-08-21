/**
 * @(#)XMLDocument.java			
 * @update		10/03/08
 */
package pf.tools.xml;
/**
 * 接口作用：
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public interface XMLDocument {
	public abstract String getVersion();

    public abstract void setEncoding(String s);

    public abstract String getEncoding();

    public abstract void setOutputFormat(boolean flag);

    public abstract void loadFromXML(String s)
        throws XMLException;

    public abstract void loadFromFile(String s)
        throws XMLException;

    public abstract String toXML()
        throws XMLException;

    public abstract void saveToFile(String s)
        throws XMLException;

    public abstract void saveToFile(String s, boolean flag)
        throws XMLException;

    public abstract XMLNode getRoot();

    public abstract XMLNode[] selectNodes(String s);
}
