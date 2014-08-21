/**
 * @(#)GenerateUUID.java			
 * @update		10/03/09
 */
package pf.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类作用：生成一个唯一主键，采用单例模式
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class GenerateUUID
{
	private static GenerateUUID INSTANCE = null;
	protected static String MID_VALUE = null;

	private GenerateUUID()
	{
	}

	public static GenerateUUID getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new GenerateUUID();
		}
		return INSTANCE;
	}

	/**
	 * 作用：生成UUID主键
	 * 
	 * @return	String					返回生成的主键
	 */
	public String getUUID()
	{
		long timeNow = System.currentTimeMillis();
		int timeLow = (int) timeNow & 0xFFFFFFFF;

		if (MID_VALUE == null)
		{
			try
			{
				InetAddress inet = InetAddress.getLocalHost();
				byte[] bytes = inet.getAddress();
				String hexInetAddress = hexFormat(getInt(bytes), 8);
				String thisHashCode = hexFormat(System.identityHashCode(this), 8);
				MID_VALUE = hexInetAddress + thisHashCode;
			}
			catch (UnknownHostException e)
			{
				throw new RuntimeException(e);
			}
		}

		SecureRandom oRandom = new SecureRandom();
		int node = oRandom.nextInt();

		return (hexFormat(timeLow, 8) + MID_VALUE + hexFormat(node, 8));
	}

	/**
	 * 作用：从一个二进制数组中获取的整数
	 * 
	 * @param	bytes				二进制数组			
	 * @return	int					返回获取的整数
	 */
	private static int getInt(byte[] bytes)
	{
		int i = 0;
		int j = 24;

		for (int k = 0; j >= 0; k++)
		{
			int l = bytes[k] & 0xff;
			i += (l << j);
			j -= 8;
		}

		return i;
	}

	/**
	 * Returns the string representation of an integer with the necessary padding
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private static String hexFormat(int i, int j)
	{
		String s = Integer.toHexString(i);

		return padHex(s, j) + s;
	}

	/**
	 * Pads a string with the required number of zeroes
	 * 
	 * @param s
	 * @param i
	 * @return
	 */
	private static String padHex(String s, int i)
	{
		StringBuffer tmpBuffer = new StringBuffer();

		if (s.length() < i)
		{
			for (int j = 0; j < (i - s.length()); j++)
			{
				tmpBuffer.append('0');
			}
		}

		return tmpBuffer.toString();
	}

    /**
     * 格式为:yyyyMMddHHmmssSS(17位) + 随机数(5位)
     * @return
     */
    public String getID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        StringBuffer sb = new StringBuffer();

        sb.append(sdf.format(new Date()));

        SecureRandom oRandom = new SecureRandom();
        int node = oRandom.nextInt(99999);

        while (node < 10000) {
            node = node * 10;
        }

        sb.append(node);

        return sb.toString();
    }
}
