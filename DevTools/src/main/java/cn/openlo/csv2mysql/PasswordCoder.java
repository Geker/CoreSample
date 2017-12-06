package cn.openlo.csv2mysql;


/**
 * <p> Title: PasswordCoder </p>
 * <p> Description: </p>
 * <p> Copyright: openlo.cn Copyright (C) 2016 </p>
 *
 * @author brokeneggs
 * @version
 * @since 2016年8月14日
 */
public interface PasswordCoder {

    public String decodePassword(String encodePasswd);

    public String encodePassword(String passwd);

}
