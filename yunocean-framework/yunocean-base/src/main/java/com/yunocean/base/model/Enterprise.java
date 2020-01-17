/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  Enterprise.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月6日 下午2:41:11   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * @ClassName:  Enterprise   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月6日 下午2:41:11   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ENTERPRISE")
public class Enterprise extends BaseModel 
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -9198280497308386336L;
	
	//企业名称
	private String name;
	
	//企业地址
	private String address;
	
	//电话号码
	private String phone;
	
	//固定电话或座机或传真
	private String telephone;
	
	//会员身份
	private String membership;
	
	//营业年限，3年以内，5年以内，10年以内，10年以上
	private Integer busiYears;
	
	//企业规模，枚举定义  100人以下，100到300人，300到500人，500人以上
	private Integer esize;
	
	//主营业务
	private String mainBusi;
	
	//公司网址
	private String url;	

}
