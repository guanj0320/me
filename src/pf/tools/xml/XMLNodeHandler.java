/**
 * @(#)XMLNodeHandler.java			
 * @update		10/03/09
 */
package pf.tools.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Element;
/**
 * 类作用：对XMLNode的一系列操作，增加节点，删除节点，合并节点，设置节点属性
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class XMLNodeHandler implements XMLNode {
	
	private Element FTarget;
    private XMLHandler xmlHandler;
    /**
     * 作用：
     * 
     * */
    Element getSourceElement()
    {
        return FTarget;
    }
    /**
     * 作用：
     * 
     * */
    XMLHandler getXMLHandler()
    {
        return xmlHandler;
    }
    /**
     * 作用：
     * 
     * */
    public XMLNodeHandler(XMLHandler xmlHandler, Element elTarget)
    {
        this.xmlHandler = xmlHandler;
        FTarget = elTarget;
    }
    /**
     * 作用：
     * 
     * */
    public XMLDocument getDocument()
    {
        return xmlHandler;
    }
    /**
     * 作用：
     * 
     * */
    public Object getSource()
    {
        return this;
    }
    /**
     * 作用：
     * 
     * */
    public int getChildNodesCount()
    {
        return FTarget.elements().size();
    }
    /**
     * 作用：
     * 
     * */
    public XMLNode getParent()
    {
        Element elParent = FTarget.getParent();
        if(elParent != null)
            return new XMLNodeHandler(xmlHandler, elParent);
        else
            return null;
    }
    /**
     * 作用：通过nIndex来获取对应的节点
     * 
     * @param	nIndex				通过Index指定节点
     * */
    public XMLNode getChildNode(int nIndex)
    {
        if(nIndex >= 0 && nIndex < getChildNodesCount())
        {
            List lst = FTarget.elements();
            Element elChild = (Element)lst.get(nIndex);
            return new XMLNodeHandler(xmlHandler, elChild);
        } else
        {
            return null;
        }
    }
    /**
     * 作用：通过节点名称来获取子节点
     * 
     * @param	nodeName			节点名称
     * */
    public XMLNode getChildNode(String nodeName)
    {
        Element elChild = FTarget.element(nodeName);
        if(elChild != null)
            return new XMLNodeHandler(xmlHandler, elChild);
        else
            return null;
    }
    /**
     * 作用：创建新的子节点
     * 
     * @param	nodeName			需创建的节点名称
     * */
    public XMLNode createChildNode(String nodeName)
    {
        Element elNew = FTarget.addElement(nodeName);
        return new XMLNodeHandler(xmlHandler, elNew);
    }
    /**
     * 作用：添加子节点
     * 
     * @param	nodeName			需创建的节点名称
     * */
    public XMLNode addChildNode(XMLNode newNode)
    {
        XMLNodeHandler xmlNode = (XMLNodeHandler)newNode.getSource();
        Element elNew = xmlNode.getSourceElement().createCopy();
        FTarget.add(elNew);
        return new XMLNodeHandler(xmlHandler, elNew);
    }
    
    public void replace(XMLNode source)
    {
        replace(source, false);
    }
    /**
     * 作用：将原有节点下的所有节点用source节点替换
     * 
     * @param	source			需要替换成的子节点
     * @param	bText			当为true时，表示不使用CDATA，反之使用CDATA
     * */
    public void replace(XMLNode source, boolean bText)
    {
        String attrNames[] = source.getAttributeNames();
        removeAttributes();
        removeChildren();
        try
        {
            for(int i = 0; i < attrNames.length; i++)
                setAttribute(attrNames[i], source.getAttributeValue(attrNames[i]));

        }
        catch(XMLException xmlE)
        {
            return;
        }
        int nCount = source.getChildNodesCount();
        if(nCount == 0)
        {
            String nodeValue = source.getNodeValue();
            if(!nodeValue.equals(""))
                if(bText)
                    setText(nodeValue);
                else
                    setCDATAText(nodeValue);
        } else
        {
            for(int i = 0; i < nCount; i++)
            {
                XMLNode subSource = source.getChildNode(i);
                XMLNode subTarget = createChildNode(subSource.getNodeName());
                subTarget.replace(subSource, bText);
            }

        }
    }
    /**
     * 作用：判断节点中的append属性是否可以附加，如果可以，则作为
     * 平级节点，反之作为子节点
     * 
     * @param	source			需要合并的节点
     * */
    public void merge(XMLNode source)
    {
        int count = source.getChildNodesCount();
        for(int i = 0; i < count; i++)
        {
            XMLNode childNode = source.getChildNode(i);
            String append = childNode.getAttributeValue("append");
            if("true".equalsIgnoreCase(append))
            {
                String nodeName = childNode.getNodeName();
                XMLNode target = getChildNode(nodeName);
                if(target != null)
                    target.merge(childNode);
                else
                    addChildNode(childNode);
            } else
            {
                addChildNode(childNode);
            }
        }

    }
    /**
     * 作用：移除节点下的所有子节点
     * 
     * */
    public void removeChildren()
    {
        FTarget.clearContent();
    }
    /**
     * 作用：移除节点中的子节点
     * 
     * */
    public void removeAttributes()
    {
        String attrNames[] = getAttributeNames();
        try
        {
            for(int i = 0; i < attrNames.length; i++)
                setAttribute(attrNames[i], "");

        }
        catch(XMLException xmlexception) { }
    }
    /**
     * 作用：移除节点
     * 
     * @param	nodeName			需要移除的节点名称
     * */
    public void removeChildNode(String nodeName)
    {
        FTarget.remove(FTarget.element(nodeName));
    }
    /**
     * 作用：移除节点
     * 
     * @param	nodeName			需要移除的节点
     * */
    public void removeChildNode(XMLNode node)
    {
        XMLNodeHandler xmlHandler = (XMLNodeHandler)node.getSource();
        Element elNode = xmlHandler.getSourceElement();
        FTarget.remove(elNode);
    }

    public String getNodeName()
    {
        return FTarget.getName();
    }

    public String getNodeValue()
    {
        return FTarget.getTextTrim();
    }

    public void setText(String nodeValue)
    {
        FTarget.clearContent();
        FTarget.setText(nodeValue);
    }

    public void setCDATAText(String cdataValue)
    {
        FTarget.clearContent();
        FTarget.addCDATA(cdataValue);
    }

    public int getAttributesCount()
    {
        return FTarget.attributeCount();
    }
    /**
     * 作用：获取节点中的属性，属性可能有多个
     * 
     * */
    public String[] getAttributeNames()
    {
        List lst = FTarget.attributes();
        String Ret[] = new String[lst.size()];
        Iterator item = lst.iterator();
        for(int i = 0; item.hasNext(); i++)
        {
            Attribute attr = (Attribute)item.next();
            Ret[i] = attr.getName();
        }

        return Ret;
    }
    /**
     * 作用：获取属性中的值
     * 
     * @param	attrName			属性名称
     * */
    public String getAttributeValue(String attrName)
    {
        Attribute attr = FTarget.attribute(attrName);
        if(attr != null)
        {
            String retValue = attr.getValue();
            if(retValue == null)
                return "";
            else
                return retValue;
        } else
        {
            return "";
        }
    }
    /**
     * 作用：获取属性中的值，当属性不存在时，返回null
     * 
     * @param	attrName			属性名称
     * */
    public String getAttributeValueWithStrict(String attrName)
    {
        Attribute attr = FTarget.attribute(attrName);
        if(attr != null)
        {
            String retValue = attr.getValue();
            return retValue;
        } else
        {
            return null;
        }
    }
    /**
     * 作用：该方法用于设置attrName的值
     * 
     * @param	attrName			属性名称
     * @param	attrValue			设置属性的值
     * */
    public void setAttribute(String attrName, String attrValue)
        throws XMLException
    {
        if(!attrValue.equals(""))
        {
            try
            {
                FTarget.addAttribute(attrName, attrValue);
            }
            catch(Exception lldE)
            {
                throw new XMLException(lldE);
            }
        } else
        {
            Attribute attr = FTarget.attribute(attrName);
            if(attr != null)
                FTarget.remove(attr);
        }
    }
    /**
     * 作用：用于获取xpath路金下的所有XMLNode节点
     * 
     * @param	xpath			xpath路径
     * */
    public XMLNode[] selectNodes(String xpath)
    {
        List lst = FTarget.selectNodes(xpath);
        int nCount = lst.size();
        XMLNode rets[] = new XMLNode[nCount];
        for(int i = 0; i < nCount; i++)
        {
            Element el = (Element)lst.get(i);
            rets[i] = new XMLNodeHandler(xmlHandler, el);
        }

        return rets;
    }
    /**
     * 作用：获取节点为properties的节点，并将其节点属性名作为对象中属性，节点属性名的值作为对象属性的值，传入
     * 
     * @return	Object			把一个节点作为一个对象返回
     * */
    public Object getNodeValueObject()
    {
        int size = getChildNodesCount();
        Object valObject = null;
        if(size == 0)
        {
            valObject = getNodeValue();
        } else
        {
            XMLNode mapNode = getChildNode("properties");
            if(mapNode == null)
            {
                String values[] = new String[size];
                for(int j = 0; j < size; j++)
                    values[j] = getChildNode(j).getNodeValue();

                valObject = values;
            } else
            {
                size = mapNode.getChildNodesCount();
                Properties mapParams = new Properties();
                for(int j = 0; j < size; j++)
                {
                    XMLNode paramNode = mapNode.getChildNode(j);
                    String key = paramNode.getAttributeValue("name");
                    String value = paramNode.getNodeValue();
                    mapParams.put(key, value);
                }

                valObject = mapParams;
            }
        }
        return valObject;
    }

}
