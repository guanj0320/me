package pf.web.html.xtree;

public class HtmlTree extends HtmlTreeNode {

	protected String onCheckFunction;
    protected String behavior;
    protected boolean dataReady;
    protected boolean sepNode;
    
    public HtmlTree(String name, String caption)
    {
        super(name, caption);
        onCheckFunction = "";
        behavior = "classic";
        dataReady = true;
        sepNode = false;
    }

    protected String generateSelfScript()
    {
        StringBuffer script = new StringBuffer();
        script.append("var " + name + " = null;");
        script.append("if (document.getElementById){\n");
        script.append(name + " = new WebFXTree('");
        script.append(caption + "');\n ");
        script.append(name + ".setBehavior('" + behavior + "');\n");
        if(!"".equals(onCheckFunction))
        {
            script.append(name + ".onCheck = function(srcNode, checkCtrl){\n");
            script.append("\t return " + onCheckFunction + "(this, srcNode, checkCtrl);\n}\n");
        }
        if(!"".equals(herf))
            script.append(name + ".action=\"" + herf + "\";\n");
        if(!"".equals(target))
            script.append(name + ".target=\"" + target + "\";\n");
        if(!dataReady)
            script.append(name + ".dataReady=false;\n");
        if(sepNode)
            script.append(name + ".sepNode=true;\n");
        return script.toString();
    }

    public void setOnCheck(String function)
    {
        onCheckFunction = function;
    }

    public void setBehavior(String behavior)
    {
        this.behavior = behavior;
    }

    protected String generatePostScript()
    {
        return "document.write(" + name + ");\n}\n";
    }

    public void setDataReady(boolean dy)
    {
        dataReady = dy;
    }

    public void setSepNode(boolean sn)
    {
        sepNode = sn;
    }
}
