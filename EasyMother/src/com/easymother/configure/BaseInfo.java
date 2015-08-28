package com.easymother.configure;

public class BaseInfo {
	//基本地址
//		public final static String BASE_URL="http://192.168.1.103:8080/easyMother/";//基本url
		public final static String BASE_URL="http://121.40.152.3/easyMother/";//基本url
		public final static String BASE_PICTURE="resources/admin/store/";//图片基本url
		public final static String DELETE_WISH="app/nursecollection/toDelete";//删除心愿单
		public final static String SEARCH_URL="app/nursejob/search";//搜索url（月嫂，育婴师，催乳师）
		public final static String WISH_LIST="app/nursecollection/collectionlist";//心愿单列表
		public final static String ADD_TO_WISH="app/nursecollection/addCollection";//添加到心愿单
		public final static String LOGIN="app/userinfo/login";//登录
		public final static String REGIST="app/userinfo/regist";//注册
		public final static String LOGOUT="app/userinfo/logout";//登出
		public final static String DETAIL="app/nursebase/detail";//详情页
		public final static String SEND_SMS_CODE="app/userinfo/sendSmsCode";//发送验证码
		public final static String UPLOADPHTO="app/file/upload";//上传图片
		public final static String CHANGEINFO="app/userinfo/save";//修改信息
		public final static String CHANGEPASSWORD="app/userinfo/updatePassword";//修改密码
		public final static String SAVE_SUGGESTION="app/suggestion/save";//提交意见
		public final static String SAVE_CONTRACT="app/ordercontract/toSaveContract";//提交合同
		public final static String SAVE_ORDER="app/order/toSaveOrder";//提交合同
		public final static String ORDER_DETAIL="app/order/toDetail";//查看订单详情
		
			
		
		
		
		
		
		
		
		public final static String COMMNUTITY="app/forumpost/index";//社区首页
		
		
		//打开activity需要返回结果时的requestCode
		public final static int REQUEST_CODE_LOGIN=0;
		
		
		//打开activity需要返回结果时的reusltCode
		public final static int RESULT_CODE_LOGIN_SUCCESS=0;
}