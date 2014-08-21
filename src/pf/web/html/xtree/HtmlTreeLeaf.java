package pf.web.html.xtree;

public class HtmlTreeLeaf extends HtmlTreeNode {

	public HtmlTreeLeaf(String name, String caption, String herf, String target)
    {
        this(name, caption);
        this.herf = herf;
        this.target = target;
    }

    public HtmlTreeLeaf(String name, String caption)
    {
        super(name, caption);
    }

    public String toString()
    {
        StringBuffer scriptCode = new StringBuffer();
        String realTarget = target;
        if("".equals(target))
            realTarget = "_self";
        scriptCode.append("var " + name);
        scriptCode.append("= new WebFXTreeItem('");
        scriptCode.append(caption);
        scriptCode.append("'");
        if(herf != null)
        {
            scriptCode.append(",'");
            scriptCode.append(herf);
            scriptCode.append("'");
        }
        scriptCode.append(");\n");
        scriptCode.append(name);
        scriptCode.append(".target='");
        scriptCode.append(realTarget);
        scriptCode.append("';\n");
        scriptCode.append(name);
        scriptCode.append(".tag='");
        scriptCode.append(tag);
        scriptCode.append("';\n");
        scriptCode.append(name);
        scriptCode.append(".tag2='");
        scriptCode.append(tag2);
        scriptCode.append("';\n");
        scriptCode.append(parent.getName() + ".add(" + name + ");\n");
        scriptCode.append(treeUtil);
        return scriptCode.toString();
    }
    
}
