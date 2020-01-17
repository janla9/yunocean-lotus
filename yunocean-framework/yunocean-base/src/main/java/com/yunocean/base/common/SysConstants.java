/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  SysConstants.java   
 * @Package com.yunocean.base   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午2:21:41   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common;

/**   
 * 
 * 1 匹配首尾空格的正则表达式：(^\s*)|(\s*$)
  2 整数或者小数：^[0-9]+\.{0,1}[0-9]{0,2}$
  3 只能输入数字："^[0-9]*$"。
  4 只能输入n位的数字："^\d{n}$"。
  5 只能输入至少n位的数字："^\d{n,}$"。
  6 只能输入m~n位的数字：。"^\d{m,n}$"
  7 只能输入零和非零开头的数字："^(0|[1-9][0-9]*)$"。
  8 只能输入有两位小数的正实数："^[0-9]+(.[0-9]{2})?$"。
  9 只能输入有1~3位小数的正实数："^[0-9]+(.[0-9]{1,3})?$"。
 10 只能输入非零的正整数："^\+?[1-9][0-9]*$"。
 11 只能输入非零的负整数："^\-[1-9][]0-9"*$。
 12 只能输入长度为3的字符："^.{3}$"。
 13 只能输入由26个英文字母组成的字符串："^[A-Za-z]+$"。
 14 只能输入由26个大写英文字母组成的字符串："^[A-Z]+$"。
 15 只能输入由26个小写英文字母组成的字符串："^[a-z]+$"。
 16 只能输入由数字和26个英文字母组成的字符串："^[A-Za-z0-9]+$"。
 17 只能输入由数字、26个英文字母或者下划线组成的字符串："^\w+$"。
 18 验证用户密码："^[a-zA-Z]\w{5,17}$"正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
 19 验证是否含有^%&',;=?$\"等字符："[^%&',;=?$\x22]+"。
 20 只能输入汉字："^[\u4e00-\u9fa5]{0,}$"
 21 验证Email地址："^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$"。
 22 验证InternetURL："^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$"。
 23 验证电话号码："^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$"正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX"。
 24 验证身份证号（15位或18位数字）："^\d{15}|\d{18}$"。
 25 验证一年的12个月："^(0?[1-9]|1[0-2])$"正确格式为："01"～"09"和"1"～"12"。
 26 验证一个月的31天："^((0?[1-9])|((1|2)[0-9])|30|31)$"正确格式为；"01"～"09"和"1"～"31"。
 27 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
 28 匹配双字节字符(包括汉字在内)：[^\x00-\xff]
 29 应用：计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）
 30 String.prototype.len=function(){return this.replace(/[^\x00-\xff]/g,"aa").length;}
 31 匹配空行的正则表达式：\n[\s| ]*\r
 32 匹配html标签的正则表达式：<(.*)>(.*)<\/(.*)>|<(.*)\/>
 
 * @ClassName:  SysConstants   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月27日 下午2:21:41   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
public class SysConstants 
{
	//常量1
	public static final Integer NUM_ONE = 1;
	
	//一次最大查询数据量
	public static final Integer QUERY_MAX_COUNT= 10000;
	
	//系统全局的分隔符号
	public static final String SPLIT_COMMA = ",";
	
	//认证 http header key名称
	public static final String AUTHORIZATION = "Authorization";
	
	public static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
	
	//默认的shiro sesion的前缀
	public static final String DEFAULT_SHIRO_SESSION_KEY_PREFIX = "shiro:session:";
	public static final String DEFAULT_SHIRO_CACHE_KEY_PREFIX = "shiro:cache:";
	public static final String DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME = "id";
	
	
	//默认的session的过期时间  shiro存redis的session超时时间默认使用此值
	public static final Long DEFAULT_SESSION_KEY_TIMEOUT_MILLI = 1 * 24 * 60 * 60 * 1000l;
	//默认SESSION 校验周期ValidationInterval
	public static final Long DEFAULT_SESSION_VALIDATION_INTERVAL = 1 * 60 * 60 * 1000l;
	
	
	//-------------------- swagger-----------------------
	public static final String SWAGGER_DEFAULT_PATH = "/help";	
	//api接口包扫描路径
	public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.yunocean.base.controller";
	public static final String SWAGGER_API_DOC_VERSION = "1.0.0";
	
	//-------------------- swagger end-----------------------
}
