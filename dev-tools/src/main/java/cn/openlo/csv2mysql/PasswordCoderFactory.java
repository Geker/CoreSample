package cn.openlo.csv2mysql;

/**
 * <p> Title: PasswordCoderFactory </p>
 * <p> Description: </p>
 * <p> Copyright: openlo.cn Copyright (C) 2016 </p>
 *
 * @author brokeneggs
 * @version
 * @since 2016年8月14日
 */
public enum PasswordCoderFactory {

    INSTANCE;

    private static final String DEFAULT_KEY = "0123456789123456";
    private static final String DEFAULT_PREFIX = "lor:";

    private DefaultPasswordCoder defaultCoder = new DefaultPasswordCoder(DEFAULT_KEY, DEFAULT_PREFIX, false);

    public PasswordCoder getPasswordCoder() {
        return defaultCoder;
    }


    
    public static void main(String[] args) {
        String s = INSTANCE.getPasswordCoder().decodePassword("lor:-4de4a812f5c3922ee48a41e807de8ff9");
        System.err.println(s);
    }
}
