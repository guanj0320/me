package pf.web.html;

import java.util.ArrayList;
import java.util.List;

public class JSScriptFunction {

	protected String functionName;
    protected Object functionBody;
    protected List params;
    
    public JSScriptFunction(String functionName)
    {
        params = null;
        this.functionName = functionName;
    }

    public void addBody(Object body)
    {
        functionBody = body;
    }

    public void addParam(String paramName)
    {
        if(params == null)
            params = new ArrayList();
        params.add(paramName);
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("function " + functionName + "(");
        if(params != null)
        {
            int size = params.size();
            for(int i = 0; i < size; i++)
            {
                String paramName = (String)params.get(i);
                buf.append(paramName);
                if(i < size - 1)
                    buf.append(",");
            }

        }
        buf.append("){\n");
        if(functionBody != null)
            buf.append(functionBody);
        buf.append("}\n");
        return buf.toString();
    }
}
