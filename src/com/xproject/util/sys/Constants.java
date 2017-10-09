package com.xproject.util.sys;

/**
 * Created by Administrator on 2016/3/19.
 */
public interface Constants {
    public final String CATOKEN_Attribute = "CA-Token";
    public final String USER_SESSION_INFO= "userinfo";
    public final String CLIENTFLAG_Attribute = "Client-Flag";
    ////clientType:请求来源： 0：默认值， 1：业务层，2：移动端，3：工控端，4：对外接口，如优特网
    public static final int clientflag_0 = 0;
    public static final int clientflag_1 = 1;
    public static final int clientflag_2 = 2;
    public static final int clientflag_3 = 3;
    public static final int clientflag_4 = 4;

    //{"result":0,"data":[],"size":0,"message":"提示！"}
    public final String json="data"; //数据key
    public final String size="size"; //数据大小，对应jsonarray的长度
    public final String result="result";//处理结果key
    public final String message="message";//返回提示信息key
    public final String url="url";//返回预览url
    public final String totalnum="totalnum";//返回总条数
    public final String currentpage="currentpage";//当前页码
    public final String totalpage="totalpage";//总页数
    public final String pagesize="pagesize";//
    public final String failinfo="Failinfo";//返回提示信息key
    public final String Result="Result";//处理结果key

    ////0局方账号，1企业账号，4support
    public final int usertype_0=0;
    public final int usertype_1=1;
    public final int usertype_4=4;
    public final int usertype_5=5;

    public final int check_on=1;
    public final int check_off=2;
    ////会话状态 0有效  1无效
    public static final int SessionValid_0 = 0;
    public static final int SessionValid_1 = 1;

    public static final String ROLENAMEGOVERNMENT = "国检";
    public static final String ROLENAMEFACTORYMAIN = "企业主账号";
    public static final String ROLENAMEFACTORYSUB = "企业子账号";
    public static final String MENUZHGL = "账号管理";
    public static final String USERPASSWORD = "123456";

    public static final String ROLENAMEGK= "工控";
    public static final String ROLENAMESMQ = "扫描枪";

    public final static int USER_TYPE_GOVERNMENT = 0;//用户类型：局方(子局国检)
    public final static int USER_TYPE_FACTORY = 1;//用户类型：企业
    public final static int USER_TYPE_MAINTANCE = 2;//用户类型：运维
    public final static int USER_TYPE_THIRD = 3;//用户类型：第三方检测机构
    public final static int USER_TYPE_PSUPPORT = 4;//用户类型：省局运维
    public final static int USER_TYPE_PADMIN = 4;//用户类型：省局管理员
    public final static int USER_TYPE_GOVERNMENT4ORG = 5;//用户类型：局方(省局国检)
    public final static int USER_TYPE_BUSINESSSUPPORT = 6;//用户类型：局方(省局国检)

    public final static int STATUS_ENABLED = 1;//用户状态启用
    public final static int STATUS_DIABLED = 0;//用户状态禁用

    public final static int USER_STATUS_ONLINE = 1;//在线
    public final static int USER_STATUS_OFFLINE = 2;//离线

    public final static int USER_UFLAG_ENABLED = 0;//用户状态正常
    public final static int USER_UFLAG_DIABLED = 1;//用户状态注销

    public final static int NUSER_TYPE_MAIN = 0;//主账号
    public final static int NUSER_TYPE_SUB = 1;//子账号

    public final static int SALE_FLAG_EC= 1;//电商
    public final static int SALE_FLAG_PR = 2;//价格
    public final static int COVERTYPE_ALL= 1;//覆盖类型：全部
    public final static int COVERTYPE_PART = 2;//覆盖类型：部分
    public final static int STATUS_OPEN= 1;//状态：打开
    public final static int STATUS_CLOSE = 0;//状态：关闭
    public final static int MODE_DEFAULT= 0;//增值服务中的模式：默认模式
    public final static int MODE_IMBED = 1;//增值服务中的模式：嵌入模式
    public final static int ISDIRECT= 1;//嵌入模式 是否直接映射：否
    public final static int ISNOTDIRECT = 0;//嵌入模式 是否直接映射：是

    int INSPECT_STATUS_INVALID=-2;
    int INSPECT_STATUS_DRAFT =-1;
    int INSPECT_STATUS_WAIT_APPLY = 0;
    int INSPECT_STATUS_APPLYED = 1;
    int INSPECT_STATUS_WAIT_MODIFY = 2;
    int INSPECT_STATUS_QUALIFIED = 3;
    int INSPECT_STATUS_UNQUALIFIED = 4;
    int INSPECT_STATUS_RECALL = 5;
    int INSPECT_STATUS_PASS = 10;

    //出区核销中的状态
    //0手工单，1空白单，2同步单
    int utype_0 = 0;
    int utype_1 = 1;
    int utype_2 = 2;
    //1进2出口、3普通
    int catagory_1 = 1;
    int catagory_2 = 2;
    int catagory_3 = 3;
    //0未出区，1出区中，2出区完成
    int flag_unFinished = 0;
    int flag_going = 1;
    int flag_finished = 2;
    int flag_cancel = 3;

    //是否PEOP
    int IS_PEOP = 1;
    int NOT_PEOP = 3;
    //0未生产，1生产中，2已生产
    int batch_unpro = 0;
    int batch_proing = 1;
    int batch_done = 2;

    //备案商品草稿状态
    String[]   PRODUCT_ERROR_INFO={"","重复","存在同名商品","同名商品已创建批次，无法备案"};

    //小笨鸟
    String COMPANYID_Attribute = "CompanyID";

    int TAG_MENU_TYPE_BOTH=0;//委托加工+自主加工

    public final String APPLY_VENDOR_USEREMAIL_TO1 = "zhouxun@xproject.com";
    public final String APPLY_VENDOR_USEREMAIL_TO2 = "support@xproject.com";
 }
