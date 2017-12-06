package cn.openlo.csv2mysql;

import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;


/**
 * <p> Title: DefaultPasswordCoder </p>
 * <p> Description: </p>
 * <p> Copyright: openlo.cn Copyright (C) 2016 </p>
 *
 * @author brokeneggs
 * @version
 * @since 2016年8月14日
 */
public class DefaultPasswordCoder implements PasswordCoder {

    private String prefix;
    private SecretKey key;

    @SuppressWarnings("unused")
    private boolean prdMode;

    public DefaultPasswordCoder(String keyString, String prefix, boolean prdMode) {
        if (StringUtils.isEmpty(keyString)) {
            return;
        }
        this.prefix = prefix;
        this.key = new SecretKeySpec(keyString.getBytes(), "AES");
        this.prdMode = prdMode;
    }

    /**
     *
     * (non-Javadoc)
     *
     * @see cn.openlo.resource.security.PasswordCoder#decodePassword(java.lang.String)
     */
    @Override
    public String decodePassword(String encodePasswd) {
        if (StringUtils.isEmpty(encodePasswd)) {
            return encodePasswd;
        }

        if (encodePasswd.startsWith(prefix)) {
            try {
                encodePasswd = encodePasswd.substring(prefix.length());
                BigInteger n = new BigInteger(encodePasswd, 16);
                byte[] encoding = n.toByteArray();
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] decode = cipher.doFinal(encoding);
                return new String(decode);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return encodePasswd;
        }

    }

    /**
     *
     * (non-Javadoc)
     * 
     * @throws LOResourceException
     * 
     * @see cn.openlo.resource.security.PasswordCoder#encodePassword(java.lang.String)
     */
    @Override
    public String encodePassword(String passwd) {
        if (StringUtils.isEmpty(passwd)) {
            return passwd;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encoding = cipher.doFinal(passwd.getBytes());
            BigInteger n = new BigInteger(encoding);
            return prefix + n.toString(16);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
