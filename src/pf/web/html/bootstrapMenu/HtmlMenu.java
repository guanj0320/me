package pf.web.html.bootstrapMenu;

import pf.tools.xml.XMLDocument;
import pf.tools.xml.XMLNode;

public class HtmlMenu {
	private String menuClass = "";
	private String menuName = "";
	private String menuHead = "";
	private String menuBody = "";
	private String htmlMenu = "";
	private String menuBodyRight = "";
	private String homeHref = "#";
	public String getMenuClass() {
		return menuClass;
	}
	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuHead() {
		return menuHead;
	}
	public void setMenuHead(String menuHead) {
		this.menuHead = menuHead;
	}
	
	public String getMenuBody() {
		return menuBody;
	}
	public void setMenuBody(String menuBody) {
		this.menuBody = menuBody;
	}
	
	public String getMenuBodyRight() {
		return menuBodyRight;
	}
	public void setMenuBodyRight(String menuBodyRight) {
		this.menuBodyRight = menuBodyRight;
	}
	public String getHtmlMenu() {
		return htmlMenu;
	}
	public void setHtmlMenu(String htmlMenu) {
		this.htmlMenu = htmlMenu;
	}
	
	public String getHomeHref() {
		return homeHref;
	}
	public void setHomeHref(String homeHref) {
		this.homeHref = homeHref;
	}
	public HtmlMenu() {
		this.menuName="Welcome";
		this.menuClass="navbar navbar-default navbar-fixed-top";
	}
	
	/**
	 * 设置默认样式导航条
	 */
	public void setMenuClassDefault() {
		this.menuClass="navbar navbar-default";
	}
	/**
	 * 设置置于顶部的默认样式导航条
	 */
	public void setMenuClassDefaultStatic() {
		this.menuClass="navbar navbar-default navbar-static-top";
	}
	/**
	 * 设置置于固定位置的默认模式导航条
	 */
	public void setMenuClassDefaultTop() {
		this.menuClass="navbar navbar-default navbar-fixed-top";
	}
	/**
	 * 设置反色差样式导航条
	 */
	public void setMenuClassInverse() {
		this.menuClass="navbar navbar-inverse";
	}
	/**
	 * 设置置于固定位置的反色差样式导航条
	 */
	public void setMenuClassInverseTop() {
		this.menuClass="navbar navbar-inverse navbar-fixed-top";
	}
	/**
	 * 设置置于顶部位置的反色差样式导航条
	 */
	public void setMenuClassInverseStatic() {
		this.menuClass="navbar navbar-inverse navbar-static-top";
	}
	
	/**
	 * 生成菜单
	 * @param xmlDoc
	 * @return
	 */
	public String genBootstrapMenuFromXML(XMLDocument xmlDoc) {
		StringBuffer sb = new StringBuffer();
		XMLNode root = xmlDoc.getRoot().getChildNode(0);
		if (root != null) {
			int nCount = root.getChildNodesCount();
			for (int i = 0; i < nCount; i++) {
				this.handleMenu(root.getChildNode(i),sb);
			}
		}
		this.menuHead="<div class=\"navbar-header\"><button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button>"
				+"<a class=\"navbar-brand\" href=\""+this.homeHref+"\">"+this.menuName+"</a></div>";	
		this.menuBody = "<ul class=\"nav navbar-nav\">" + sb.toString() + "</ul>";
		this.menuBodyRight = "<ul class=\"nav navbar-nav navbar-right\">" + sb.toString() + "</ul>";
		this.htmlMenu = "<div class=\""+this.menuClass+"\" role=\"navigation\"><div class=\"container\">"
				+ this.menuHead 
				+ "<div class=\"collapse navbar-collapse\">"
				+ this.menuBody
				+ this.menuBodyRight
				+ "</div>"
				+ "</div></div>";
		
		return htmlMenu;
	}
	

	public String genBootstrapMenuFromXML(XMLDocument xmlDocLeft,XMLDocument xmlDocRight) {
		StringBuffer sb;
		XMLNode root = xmlDocLeft.getRoot().getChildNode(0);
		if (root != null) {
			sb = new StringBuffer();
			int nCount = root.getChildNodesCount();
			for (int i = 0; i < nCount; i++) {
				this.handleMenu(root.getChildNode(i),sb);
			}
			this.menuBody = "<ul class=\"nav navbar-nav\">" + sb.toString() + "</ul>";
		}
		StringBuffer sbr;
		XMLNode rootR = xmlDocRight.getRoot();
		if (rootR != null) {
			sbr = new StringBuffer();
			int nCount = rootR.getChildNodesCount();
			for (int i = 0; i < nCount; i++) {
				this.handleMenu(rootR.getChildNode(i),sbr);
			}
			this.menuBodyRight = "<ul class=\"nav navbar-nav navbar-right\">" + sbr.toString() + "</ul>";
		}
		this.menuHead="<div class=\"navbar-header\"><button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button>"
				+"<a class=\"navbar-brand\" href=\""+this.homeHref+"\">"+this.menuName+"</a></div>";	
		this.htmlMenu = "<div class=\""+this.menuClass+"\" role=\"navigation\"><div class=\"container\">"
				+ this.menuHead 
				+ "<div class=\"collapse navbar-collapse\">"
				+ this.menuBody
				+ this.menuBodyRight
				+ "</div>"
				+ "</div></div>";
		
		return htmlMenu;
	}
	
	private void handleMenu(XMLNode node, StringBuffer sb) {
		int nCount = node.getChildNodesCount();
		if("1".equals(node.getAttributeValue("istop"))) {
			sb.append("<li class=\"divider\"></li>");
		}
		if (nCount == 0) {	
			//取链接类型是不是模态对话框
			if("modal".equals(node.getAttributeValue("restype"))) {
				sb.append("<li><a id=\""+node.getAttributeValue("id")+"\" data-toggle=\"modal\" data-target=\""+node.getAttributeValue("url")+"\" href=\"#\">"+node.getNodeName()+"</a></li>");
			} else {
				sb.append("<li><a id=\""+node.getAttributeValue("id")+"\"");
				
				String href = node.getAttributeValue("url");
				if ("".equals(href.trim())) 
					href="#";
				sb.append(" href=\""+href+"\"");
				
				String target = node.getAttributeValue("target");
				if (!"".equals(target.trim())) 
					sb.append(" target=\""+target+"\"");
				
				sb.append(" >"+node.getNodeName()+"</a></li>");
			}
		} else {
			sb.append("<li class=\"dropdown\"><a id=\""+node.getAttributeValue("id")+"\" href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"+node.getNodeName()+" <b class=\"caret\"></b></a><ul class=\"dropdown-menu\">");
			for (int i = 0; i < nCount; i++) {
				handleMenu(node.getChildNode(i), sb);
			}
			sb.append("</ul></li>");
		}
	}

}
