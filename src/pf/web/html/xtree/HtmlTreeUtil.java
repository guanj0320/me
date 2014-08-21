package pf.web.html.xtree;

public class HtmlTreeUtil {

	protected boolean checked;
    protected boolean isCheck;
    protected String nodeName;
    
    public HtmlTreeUtil(String nodeName)
    {
        checked = false;
        isCheck = false;
        this.nodeName = "";
        this.nodeName = nodeName;
    }

    public void setChecked(boolean bChecked)
    {
        isCheck = true;
        checked = bChecked;
    }

    public String toString()
    {
        if(isCheck)
        {
            if(checked)
                return nodeName + ".setChecked(true);\n";
            else
                return nodeName + ".setChecked(false);\n";
        } else
        {
            return "";
        }
    }
}
