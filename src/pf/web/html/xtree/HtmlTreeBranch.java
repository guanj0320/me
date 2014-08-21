package pf.web.html.xtree;

public interface HtmlTreeBranch {
	
	public abstract void addChild(HtmlTreeBranch htmltreebranch);

    public abstract void setParent(HtmlTreeBranch htmltreebranch);

    public abstract HtmlTreeBranch getParent();

    public abstract String getName();

    public abstract String getCaption();
}
