package com.easymother.configure;

public class BaseInfo {
	//基本地址
//		public final static String BASE_URL="http://192.168.1.102:8080/easyMother/";//基本url
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
		public final static String SAVE_COMMENT="app/ordercomment/toComment";//提交评价
		public final static String CHECK_VIDEO="app/nursejobmedia/findMediaByJobId";//查看视频
		public final static String ORDER_LIST="app/order/selectOrdersByUserId";//订单列表
		public final static String CHANGE_ORDER_MSG="app/orderask/save";//修改订单
	
		public final static String CHCLK_LETTER="app/nursepushletter/findByNurseIdAndJob";//查看订单
		public final static String CHCLK_ALL_COMMENTS="app/ordercomment/selectByNurseIdAndJob";//查看订单
		
		
		public final static String YSYQ_INFO="app/newstype/toIndex";//医食衣趣
		public final static String NURSE_ZOME="app/forumpost/spacelist";//护理师空间
		public final static String STAR_NURSE="app/forumupdownlog/toPraise";//给护理师点赞
		public final static String SAVE_COLLECTION="app/forumcollect/save";//收藏空间和话题
		


		
		
		
		
		
		public final static String BABYTIME_INDEX="app/babyinfo/toIndex";//囡囡记首页
		public final static String BABYINFO_DETAIL="app/babyinfo/toDetail";//囡囡信息
		public final static String BABYINFO_SAVEINFO="app/babyinfo/save";//囡囡记保存囡囡记信息
		public final static String BABYTIME_SAVEINFO="app/babytime/save";//囡囡记保存囡囡日志
		public final static String BABYTIME_LIST="app/babytime/findByUserId";//查看囡囡记列表
		
			
		
		
		
		
		
		
		
		public final static String COMMNUTITY="app/forumpost/index";//社区首页
		
		
		//打开activity需要返回结果时的requestCode
		public final static int REQUEST_CODE_LOGIN=0;
		
		
		//打开activity需要返回结果时的reusltCode
		public final static int RESULT_CODE_LOGIN_SUCCESS=0;
}
