/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  AccountEnums.java   
 * @Package com.yunocean.base.common.enums   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月6日 下午2:54:59   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.enums;

import java.util.HashMap;
import java.util.Map;

/**   
 * @ClassName:  AccountEnums   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月6日 下午2:54:59   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
public enum AccountLevelEnums 
{
	//系统级账号
	SYS(1, "system account"),    
    //企业级账号
    ENTER(2, "enterprise account");
	
	private static Map<Integer, AccountLevelEnums> MAP = new HashMap<Integer, AccountLevelEnums>();
	
	static
    {
        for (AccountLevelEnums ue : values())
        {
            MAP.put(ue.value, ue);
        }
    }

	AccountLevelEnums(int value, String name)
    {
        this.value = value;
        this.name = name;
    }
	
	/**
     * 模式类型值
     */
    private int value;

    /**
     * 模式名
     */
    private String name;

    /**
     * <p>Get Method   :   value int</p>
     * @return value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * <p>Get Method   :   name String</p>
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 转换成枚举
     * @param value
     * @return
     * UserEvent
     */
    public static AccountLevelEnums transfer(int value)
    {
        return MAP.get(value);
    }
}
