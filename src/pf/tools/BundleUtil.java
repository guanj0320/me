package pf.tools;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import pf.constant.Constants;




public class BundleUtil
{
	private static Locale locale = Constants.LOCALE;

	/**
	 * Gets a localized message from the message bundle.
	 */
	public static String getLocalizedMessage(String key)
	{
		String bundleName = Constants.RESOURCE_FILE;
		ResourceBundle rb = ResourceBundle.getBundle(bundleName, locale);
		return rb.getString(key);
	}

	/**
	 * Gets a localized message from the message bundle and formats it using the parameter array.
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getLocalizedMessage(String key, String[] params)
	{
		String rawString = getLocalizedMessage(key);
		MessageFormat format = new MessageFormat(rawString);
		return format.format(params);
	}

}
