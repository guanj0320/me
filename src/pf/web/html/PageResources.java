package pf.web.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageResources {
	
	private static final Map resources = new HashMap();

	private static String RESOURCES_HOME = "/";

	public static String outJs(String file) {
		return "<script type=\"text/javascript\" src=\"" + file
				+ "\"></script>";
	}
	
	public static String outCss(String file) {
		return "<link type=\"text/css\" rel=\"stylesheet\" href=\""
				+ file + "\">";
	}
	
	/**
	 * 获取所有资源
	 * 
	 * @param contextPath
	 * @param names
	 * @return
	 */
	public static String[] getResources(String contextPath, String[] names) {
		int len = names.length;
		List reses = new ArrayList();
		for (int i = 0; i < len; i++) {
			ResourceLet lt = (ResourceLet) resources
					.get(names[i].toLowerCase());
			if (lt != null) {
				addResources(reses, contextPath, lt.getCses());
				addResources(reses, contextPath, lt.getJses());
			} else {
				addResources(reses, contextPath, new String[] { names[i] });
			}
		}
		return (String[]) reses.toArray(new String[reses.size()]);
	}

	private static void addResources(List reses, String contextPath,
			String[] addingReses) {
		int len = addingReses.length;
		for (int i = 0; i < len; i++) {
			String res = addingReses[i];
			if (res.startsWith("/")) {
				res = contextPath + res;
			} else {
				if (res.endsWith(".js")) {
					res = contextPath + RESOURCES_HOME + "js/" + res;
				} else if (res.endsWith(".css")) {
					res = contextPath + RESOURCES_HOME + "css/" + res;
				}
			}
			if (res.endsWith(".js")) {
				res = outJs(res);
			} else if (res.endsWith(".css")) {
				res = outCss(res);
			}
			if (!reses.contains(res)) {
				reses.add(res);
			}
		}
	}
	
	class ResourceLet {
		private String[] jses = null;

		private String[] cses = null;

		public String[] getCses() {
			return cses;
		}

		public void setCses(String[] cses) {
			this.cses = cses;
		}

		public String[] getJses() {
			return jses;
		}

		public void setJses(String[] jses) {
			this.jses = jses;
		}
	}
}
