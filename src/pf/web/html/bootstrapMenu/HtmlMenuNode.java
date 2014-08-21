package pf.web.html.bootstrapMenu;

import java.util.List;

public class HtmlMenuNode {
	private String id;
	private String name;
	private String style;
	private String herf;
	private String target;
	private String role;
	private boolean dataToggle;
	private String dataTarget;
	private List children;
	private HtmlMenuNode parent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getHerf() {
		return herf;
	}
	public void setHerf(String herf) {
		this.herf = herf;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isDataToggle() {
		return dataToggle;
	}
	public void setDataToggle(boolean dataToggle) {
		this.dataToggle = dataToggle;
	}
	public String getDataTarget() {
		return dataTarget;
	}
	public void setDataTarget(String dataTarget) {
		this.dataTarget = dataTarget;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public HtmlMenuNode getParent() {
		return parent;
	}
	public void setParent(HtmlMenuNode parent) {
		this.parent = parent;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	
}
