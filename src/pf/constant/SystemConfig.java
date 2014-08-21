package pf.constant;

import java.io.File;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

public final class SystemConfig {
	public static String SYSTEM_VERSION = "Spring 3.2.5;Ibatis 2.3.4.726";
    private static Locale TargetLocale = null;
    public static String PLAT_VERSION = "5.0.0";
	public static String ENCODING = "GB2312";
    public static String CONTAINER_ENCODING = "ISO-8859-1";
    public static String WEB_ROOT_PATH = "";    
    public static ServletContext SERVLET_CONTEXT;
    public static String WEB_INF_PATH = File.separator;
    public static String APP_NAME = "";
    public static String APP_VERSION = "";
    public static String APP_COPYRIGHT = "";
    public static String SERVER_IP = "127.0.0.1";
    public static String INIT_PWD = "";
    public static String TELEPHONE = "";
    public static String TECH_SUPPORT = "";
    public static String SYS_RESOURCES_PATH = "";
    public static String DOC_SEARCH_PATH = "";
    public static String DOC_INDEX_PATH = "";
    
    public static String ADMIN = "";
    public static String PASSWORD = "";
    public static String MENU = "";
    public static boolean IS_STARTUP_VALIDATE = false;
    
    /**导出excel最大行数**/
    public static String MAXROWPERSHEET="0";
    
    /**是否记录webservice日志*/
	public static boolean WSINVOKELOG = true;
	/**
	 * 系统使用的数据库
	 */
	public static String DATABASE = "";
	/**
	 * 是否启用CAS的SSO
	 */
	public static String IS_STARTUP_CAS = "0";
	/**
	 * CAS的登出地址
	 */
	public static String CAS_LOGOUT_URL = "";
	
	/**
     * Spring的ApplicationContext
     */
    public static ApplicationContext CONTEXT;
    
    /**
     * 居右菜单
     */
    public static String RIGHTMENU="";

    /**
     * 首页上的小banner1
     */
    public static String S_BANNER1 = "";

    /**
     * 首页上的小banner2
     */
    public static String S_BANNER2 = "";

    /**
     * 首页上的小banner3
     */
    public static String S_BANNER3 = "";

    /**
     * 得到上传路径
     * @return
     */
    public static String getUploadPath() {
        return WEB_INF_PATH + "uploads" + File.separator;
    }

	/**
	 * 得到缩略图上传路径
	 * @return
	 */
    public static String getScaleDrawingPath() {
        return getUploadPath() + "scale_drawing" + File.separator;
    }

    /**
     * 得到banner图上传路径
     * @return
     */
    public static String getBannerPath()
    {
        return getUploadPath() + "banner" + File.separator;
    }


    /**
     * 得到照片上传路径
     * @return
     */
    public static String getPhotosPath() {
        return getUploadPath() + "photos" + File.separator;
    }

    /**
     * 得到相册上传路径
     * @return
     */
    public static String getAlbumsPath() {
        return getUploadPath() + "albums" + File.separator;
    }
    
    /**
	 * 取得本地化对象
	 * 
	 * @return 返回本地化对象
	 */
	public static Locale getTargetLocale() {
		if (TargetLocale == null) {
			TargetLocale = Locale.getDefault();
		}
		return TargetLocale;
	}

	/**
	 * 设置本地化
	 * 
	 * @param language
	 *            语言
	 * @param country
	 *            国家
	 * @param variant
	 */
	public static void setTargetLocale(String language, String country,
			String variant) {
		if (!language.equals("")) {
			TargetLocale = new Locale(language, country, variant);
		}
	}
	
	/**
	 * 取得WEB-INF绝对路径
	 */
	public static String getWebInfPath() {
		return WEB_INF_PATH;
	}
}
