package pf.web.html.xtree;

import java.util.ArrayList;
import java.util.List;

public class HtmlTreeNode {

	protected String name;
    protected String caption;
    protected String icon;
    protected String openIcon;
    protected String herf;
    protected String target;
    protected String tag;
    protected String tag2;
    protected List children;
    protected HtmlTreeNode parent;
    protected HtmlTreeUtil treeUtil;
    
    public HtmlTreeNode(String name, String caption)
    {
        icon = "";
        openIcon = "";
        herf = "";
        target = "";
        tag = "";
        tag2 = "";
        children = new ArrayList();
        this.name = name;
        this.caption = caption;
        treeUtil = new HtmlTreeUtil(this.name);
    }

    public void setChecked(boolean bChecked)
    {
        treeUtil.setChecked(bChecked);
    }

    public void setIcon(String iconUrl)
    {
        icon = iconUrl;
    }

    public void setOpenIcon(String iconUrl)
    {
        openIcon = iconUrl;
    }

    public String getIconUrl()
    {
        return icon;
    }

    public String getOpenIcon()
    {
        return openIcon;
    }

    public String getName()
    {
        return name;
    }

    public String getCaption()
    {
        return caption;
    }

    protected void setParent(HtmlTreeNode parent)
    {
        this.parent = parent;
    }

    public void addChild(HtmlTreeNode child)
    {
        child.setParent(this);
        children.add(child);
    }

    public String toString()
    {
        StringBuffer scriptCode = new StringBuffer();
        scriptCode.append(generateSelfScript());
        if(!"".equals(icon))
            scriptCode.append(name + ".icon=\"" + icon + "\";\n");
        if(!"".equals(openIcon))
            scriptCode.append(name + ".openIcon=\"" + openIcon + "\";\n");
        if(!"".equals(tag))
            scriptCode.append(name + ".tag=\"" + tag + "\";\n");
        if(!"".equals(tag2))
            scriptCode.append(name + ".tag2=\"" + tag2 + "\";\n");
        int size = children.size();
        for(int i = 0; i < size; i++)
            scriptCode.append(children.get(i));

        scriptCode.append(generatePostScript());
        return scriptCode.toString();
    }

    public int hashCode()
    {
        return name.hashCode();
    }

    public boolean equals(Object parm1)
    {
        return ((HtmlTreeNode)parm1).getName().equals(name);
    }

    protected String generatePostScript()
    {
        return "";
    }

    protected String generateSelfScript()
    {
        return "";
    }

    public String getHerf()
    {
        return herf;
    }

    public void setHerf(String herf)
    {
        this.herf = herf;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getTag2()
    {
        return tag2;
    }

    public void setTag2(String tag2)
    {
        this.tag2 = tag2;
    }
}
