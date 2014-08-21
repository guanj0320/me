package pf.tools.xml;

public interface XMLNode {
	public abstract XMLDocument getDocument();

    public abstract int getChildNodesCount();

    public abstract XMLNode getParent();

    public abstract XMLNode getChildNode(int i);

    public abstract XMLNode getChildNode(String s);

    public abstract XMLNode createChildNode(String s);

    public abstract XMLNode addChildNode(XMLNode xmlnode);

    public abstract void replace(XMLNode xmlnode);

    public abstract void merge(XMLNode xmlnode);

    public abstract void replace(XMLNode xmlnode, boolean flag);

    public abstract void removeChildNode(String s);

    public abstract void removeChildNode(XMLNode xmlnode);

    public abstract void removeChildren();

    public abstract void removeAttributes();

    public abstract String getNodeName();

    public abstract String getNodeValue();

    public abstract Object getNodeValueObject();

    public abstract Object getSource();

    public abstract void setText(String s);

    public abstract void setCDATAText(String s);

    public abstract int getAttributesCount();

    public abstract String[] getAttributeNames();

    public abstract String getAttributeValue(String s);

    public abstract String getAttributeValueWithStrict(String s);

    public abstract void setAttribute(String s, String s1)
        throws XMLException;

    public abstract XMLNode[] selectNodes(String s);
}
