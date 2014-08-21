package pf.web.html.xtree;

public class HtmlTreeBranchImp extends HtmlTreeNode {

	public HtmlTreeBranchImp(String name, String caption)
    {
        super(name, caption);
    }

    protected String generateSelfScript()
    {
        StringBuffer scriptCode = new StringBuffer();
        scriptCode.append("var " + name);
        scriptCode.append("= new WebFXTreeItem('");
        scriptCode.append(caption);
        scriptCode.append("');\n");
        scriptCode.append(parent.getName());
        scriptCode.append(".add(");
        scriptCode.append(name);
        scriptCode.append(");\n");
        if(!"".equals(herf))
            scriptCode.append(name + ".action=\"" + herf + "\";\n");
        if(!"".equals(target))
            scriptCode.append(name + ".target=\"" + target + "\";\n");
        scriptCode.append(treeUtil);
        return scriptCode.toString();
    }
}
