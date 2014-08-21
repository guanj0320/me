/**
 * @(#)XMLHandler.java			
 * @update		10/03/08
 */
package pf.tools.xml;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
/**
 * 类作用：对xml格式的一系列操作
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public final class XMLHandler implements XMLDocument {
	
	private XMLNode root;
    private String encoding;
    private boolean prettyLayout;
    protected Document FOwnerDoc;

    XMLHandler()
    {
        root = null;
        encoding = "GB2312";
        prettyLayout = true;
        FOwnerDoc = null;
    }
    /**
     * 作用：获取版本信息
     * 
     * */
    public String getVersion()
    {
        return "1.0";
    }
    /**
     * 作用：获取编码格式
     * 
     * */
    public String getEncoding()
    {
        return encoding;
    }
    /**
     * 作用：设置编码信息
     * 
     * */
    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }
    /**
     * 作用：设置输出格式
     * 
     * */
    public void setOutputFormat(boolean prettyLayout)
    {
        this.prettyLayout = prettyLayout;
    }
    /**
     * 作用：从输入流中获取加载xml，采用SAX解析
     * 
     * @param	input		输入流
     * */
    private void loadFromInputStream(InputStream input)
        throws XMLException
    {
        SAXReader sBuilder = new SAXReader(false);
        try
        {
            FOwnerDoc = sBuilder.read(input);
            initDocument();
            input.close();
        }
        catch(Exception ioE)
        {
            throw new XMLException(ioE);
        }
    }
    /**
     * 作用：加载xml格式字符串
     * 
     * @param	strXML			xml格式字符串
     * */
    public void loadFromXML(String strXML)
        throws XMLException
    {
        try
        {
            FOwnerDoc = DocumentHelper.parseText(strXML);
            initDocument();
        }
        catch(DocumentException dE)
        {
            throw new XMLException(dE);
        }
    }
    /**
     * 作用：初始化xml文档
     *
     * */
    private void initDocument()
    {
        root = null;
    }
    /**
     * 作用：创建xml格式文档，添加根节点
     * 
     *@param	rootName			根节点名称
     * */
    public void create(String rootName)
        throws XMLException
    {
        String strXML = "<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>";
        String strRoot = rootName;
        if(strRoot.equals(""))
            strRoot = "Root";
        strXML = strXML + "<" + strRoot + "/>";
        try
        {
            FOwnerDoc = DocumentHelper.parseText(strXML);
        }
        catch(DocumentException dE)
        {
            throw new XMLException(dE);
        }
    }
    /**
     * 作用：加载外部文档
     * 
     * @param	strFileName			外部文档
     * */
    public void loadFromFile(String strFileName)
        throws XMLException
    {
        try
        {
            FileInputStream fInput = new FileInputStream(strFileName);
            loadFromInputStream(fInput);
        }
        catch(Exception e)
        {
            System.err.println("target file=" + strFileName);
            throw new XMLException(e);
        }
    }
    /**
     * 作用：
     * 
     * @param	outter		
     * */
    private void saveToWriter(Writer outter)
        throws XMLException
    {
        try
        {
            OutputFormat format = null;
            if(prettyLayout)
                format = OutputFormat.createPrettyPrint();
            else
                format = OutputFormat.createCompactFormat();
            format.setEncoding(encoding);
            XMLWriter outputter = new XMLWriter(outter, format);
            outputter.write(FOwnerDoc);
            outter.close();
        }
        catch(Exception ioE)
        {
            throw new XMLException(ioE);
        }
    }
    /**
     * 作用：转换为XML格式
     * 	
     * */
    public String toXML()
        throws XMLException
    {
        if(FOwnerDoc != null)
        {
            CharArrayWriter caW = new CharArrayWriter();
            saveToWriter(caW);
            return caW.toString();
        } else
        {
            throw new XMLException("ת��XMLʧ��!");
        }
    }

    public void saveToFile(String strFileName)
        throws XMLException
    {
        saveToFile(strFileName, false);
    }
    /**
     * 作用：将xml保存到文件中
     * 
     * @param	strFileName			文件名称
     * @param	deleted				
     * */
    public void saveToFile(String strFileName, boolean deleted)
        throws XMLException
    {
        File fTemp = new File(strFileName);
        if(!fTemp.exists())
            try
            {
                fTemp.createNewFile();
            }
            catch(IOException ioE)
            {
                throw new XMLException(ioE);
            }
        else
        if(deleted && !fTemp.canWrite())
        {
            fTemp.delete();
            saveToFile(strFileName, false);
            return;
        }
        OutputStreamWriter fWriter = null;
        try
        {
            FileOutputStream fOutStream = new FileOutputStream(strFileName);
            fWriter = new OutputStreamWriter(fOutStream, encoding);
        }
        catch(FileNotFoundException fnfE)
        {
            throw new XMLException(fnfE);
        }
        catch(IOException ioE)
        {
            throw new XMLException(ioE);
        }
        try
        {
            saveToWriter(fWriter);
        }
        catch(XMLException xmlE)
        {
            throw xmlE;
        }
    }
    /**
     * 作用：获取根节点
     * 
     * @return	XMLNode				返回获取的XMLNode节点
     * */
    public XMLNode getRoot()
    {
        if(root == null)
            root = new XMLNodeHandler(this, FOwnerDoc.getRootElement());
        return root;
    }
    /**
     * 
     * 作用：转换为字符串
     * 
     * */
    public String toString()
    {
        String strTemp = "";
        try
        {
            strTemp = toXML();
        }
        catch(XMLException xmlE)
        {
            strTemp = "";
        }
        return strTemp;
    }
    
    public boolean equals(Object parm1)
    {
        return toString().equals(parm1.toString());
    }
    
    public int hashCode()
    {
        return toString().hashCode();
    }
    /**
     * 作用：通过xpath获取XMLNode节点
     * 
     * @param	xpath			需查找的节点
     * @return	XMLNode[]		返回获取的xmlNode
     * */
    public XMLNode[] selectNodes(String xpath)
    {
        List lst = FOwnerDoc.selectNodes(xpath);
        int nCount = lst.size();
        XMLNode rets[] = new XMLNode[nCount];
        for(int i = 0; i < nCount; i++)
        {
            Element el = (Element)lst.get(i);
            rets[i] = new XMLNodeHandler(this, el);
        }

        return rets;
    }

}
