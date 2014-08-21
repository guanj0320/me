/**
 * @(#)IsChinese.java			
 * @update		10/03/09
 */
package pf.tools;
/**
 * 类作用：判断传入的字符串是否是中文
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class IsChinese {
	/**
	* 作用：判断传入的字符串是否是GB2312编码格式
	* 
	* @param	str					需要判读的字符串
	* @return	boolean				当是GB2312编码时返回true，反之返回false
	*/
	public static boolean isChinses(String str){
		char[] chars=str.toCharArray(); 
	    boolean isGB2312=false; 
	    for(int i=0;i<chars.length;i++){
	    	byte[] bytes=(""+chars[i]).getBytes(); 
	        if(bytes.length==2){ 
	        	int[] ints=new int[2]; 
	        	ints[0]=bytes[0]& 0xff; 
	            ints[1]=bytes[1]& 0xff; 
	            if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){ 
	            	isGB2312=true; 
	            	break; 
	            } 
	        } 
	     } 
	    return isGB2312;
	 } 
}
