package hades.douban2imdb.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author zs
 */
public class MD5Utils {

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final String data) {
        return DigestUtils.md5Hex(data);
    }
}
