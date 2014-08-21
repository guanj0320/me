package pf.web.html;

import java.util.ArrayList;
import java.util.List;

public class ScriptArea {

	public static final String DEFAULT_LANG = "javascript";
    private String methodInvoke;
    private Object scriptObject;
    protected List functions;
    protected List scripts;
    
    public ScriptArea(Object scriptObject, String lang)
    {
        this(lang);
        this.scriptObject = scriptObject;
    }

    public void addFunction(JSScriptFunction function)
    {
        if(functions == null)
            functions = new ArrayList();
        functions.add(function);
    }

    public void addScript(Object source)
    {
        if(scripts == null)
            scripts = new ArrayList();
        scripts.add(source);
    }

    public ScriptArea(String lang)
    {
        methodInvoke = "";
        scriptObject = null;
        functions = null;
        scripts = null;
    }

    public ScriptArea(Object scriptObject)
    {
        this(scriptObject, "javascript");
    }

    public ScriptArea()
    {
        this("javascript");
    }

    public void setMethodInvoke(String methodName)
    {
        methodInvoke = methodName;
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("<script language=\"javascript\">\n");
        if(functions != null)
        {
            int size = functions.size();
            for(int i = 0; i < size; i++)
                buf.append(functions.get(i));

        }
        if(scriptObject != null)
            if(!"".equals(methodInvoke))
            {
                JSScriptFunction function = new JSScriptFunction(methodInvoke);
                function.addBody(scriptObject);
                buf.append(function);
                buf.append(methodInvoke + "();\n");
            } else
            {
                buf.append(scriptObject);
            }
        if(scripts != null)
        {
            int size = scripts.size();
            for(int i = 0; i < size; i++)
                buf.append(scripts.get(i));

        }
        buf.append("</script>\n");
        return buf.toString();
    }
}
