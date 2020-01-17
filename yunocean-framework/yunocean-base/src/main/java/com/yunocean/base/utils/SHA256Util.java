/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  SHA256Util.java   
 * @Package com.yunocean.base.utils   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月28日 下午4:43:43   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**   
 * @ClassName:  SHA256Util   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月28日 下午4:43:43   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
public class SHA256Util 
{
	//干扰数据 盐 防破解
    private static final String SALT = "yun%o#c$e@a&n";
    //散列算法类型为MD5
    public static final String ALGORITH_NAME = "SHA-256";
    //hash的次数
    public static final int HASH_ITERATIONS = 128;

    public static String encrypt(String pswd, String name) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT + name), HASH_ITERATIONS).toHex();
        return newPassword;
    }
}
