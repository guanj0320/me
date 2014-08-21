/**
 * @(#)ValidateIDCard.java			
 * @create		09/04/22
 * @update		2010/03/08
 * 
 */

package pf.tools;

/**
 * 
 * 此类的作用，判断输入的身份证号码是否合法
 * 
 * 身份证号码长度有15位和18位，都做了验证
 * 
 * 15位的身份证号码会被转成18位的进行验证
 * 
 * 
 * @author 徐沛
 * @version 1.0.0 2010.03.08
 */

public class ValidateIDCard {

	// wi =2(n-1)(mod 11)
    final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
    // verify digit
    final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
    private int[] ai = new int[18];

    public ValidateIDCard() {
    }

    /** 传入身份证号码，通过方法验证其是否合法
     * 
     * @param idcard  传入的身份证号码
     * @return boolean 验证合法返回结果True,否则返回结果False
     */
    public boolean isVerify(String idcard) {
        if (idcard.length() == 15) {
            idcard = uptoeighteen(idcard);
        }
        if (idcard.length() != 18) {
            return false;
        }
        String verify = idcard.substring(17, 18);
        if (verify.equals(getVerify(idcard))) {
            System.out.println(idcard);
            return true;
        }
        System.out.println(idcard);
        return false;
    }

    /** 传入18位身份证号码，通过计算得出最后一位的号码
     * 
     * @param idcard  传入18位的身份证号码
     * @return String 返回得出的最后一位号码
     */
    public String getVerify(String eightcardid) {
        int remaining = 0;
        if (eightcardid.length() == 18) {
            eightcardid = eightcardid.substring(0, 17);
        }
        if (eightcardid.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eightcardid.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }
            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }
        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    /** 传入15位身份证号码，将其转化为18位的身份证号码
     * 
     * @param idcard  传入15位的身份证号码
     * @return String 返回计算后得出的18位身份证号码
     */
    public String uptoeighteen(String fifteencardid) {
        String eightcardid = fifteencardid.substring(0, 6);
        eightcardid = eightcardid + "19";
        eightcardid = eightcardid + fifteencardid.substring(6, 15);
        eightcardid = eightcardid + getVerify(eightcardid);
        return eightcardid;
    }

 
	public static void main(String[] args) {
		ValidateIDCard idcard = new ValidateIDCard();
		System.out.println(idcard.isVerify("320102198203200811"));
	}
}
