package pf.constant;

import java.util.Locale;

public interface Constants {
	public static Locale LOCALE = Locale.CHINA;
	
	public static final String RESOURCE_FILE = "resource";
	
	public static final int ROWS_PER_PAGE = 20;
	public static final int PAGINATOR_MAX_PAGES = 9;
	
	public static final String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";
	public static final String DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";

    /**
     * redis中的集合名称
     */
    public static final String SUBSCRIBE_CENTER = "0000";

    public static final String MESSAGE_TXID = "1000";
}
