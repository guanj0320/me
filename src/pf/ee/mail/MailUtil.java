package pf.ee.mail;

import pf.tools.Coder;

/**
 * Created by guanj on 2014/5/7.
 */
public class MailUtil {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBase64Password() throws Exception {
        byte[] inputData = password.getBytes();
        String code = Coder.encryptBASE64(inputData);
        return code;
    }

    public String getRealPassword() throws Exception{
        byte[] output = Coder.decryptBASE64(password);
        String outputStr = new String(output);
        return outputStr;
    }
}
