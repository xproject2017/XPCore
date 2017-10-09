package com.xproject.util.sys;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface ResultInfo4Contorl {
    public static final int Success = 0;//成功，
    public static final int Fail = 1; //失败

    public static final String ErrorTokenStr ="无效的Token";

    public static final String  ErrorParameter     = "参数错误！";
    public static final String  ErrorOption     = "操作失败！";
    public static final String  SuccessOption     = "操作成功！";
    public static final String  ErrorOptionFound     = "查询失败！";

    public static final String  ErrorFileNotFound     = "文件不存在！";

    public static final String  ErrorLogin          = "用户名或密码错误！";
    public static final String  ErrorPassword          = "密码错误！";
    public static final String  ErrorLoginFail      = "登录失败！";
    public static final String  ErrorLoginStatusDisabled      = "账号已被禁用，请联系管理员！";
    public static final String  ErrorVendorLoginStatusDisabled      = "企业已被禁用，请联系管理员！";
    public static final String  ErrorLogout         = "用户退出错误！";
    public static final String  ErrorRegister       = "用户信息不完整！";
    public static final String  ErrorUserNameExist  = "用户名已经存在！";
    public static final String  ErrorUserOffline    = "用户未登录！";
    public static final String  ErrorAddVendor      = "企业信息不完整！";
    public static final String  ErrorAddAuth        = "授权方信息不完整！";
    public static final String  ErrorAddVPType      = "企业商品类型信息不完整！";
    public static final String  ErrorNoVendor        = "厂商不存在";
    public static final String  ErrorNoUser          = "用户不存在";
    public static final String  SessionNotExist     = "Session不存在";
    public static final String  ProductNotExist     = "产品不存在";

    public static final String  ErrorProduct    = "商品备案失败！";
    public static final String  ErrorVendorNameExist = "企业名称已存在！";
    public static final String  ErrorVendorRecordExist = "统一社会信用代码已存在！";

    public static final String  ErrorBuyTag         = "CA防伪标签授权失败！";
    public static final String  ErrorLackofBalance  = "余额不足";
    public static final String  ErrorApplyZeroTag = "申请0个标签";
    public static final String  ErrorApplyTag       = "CA防伪标签申请失败，信息不完整！";
    public static final String  ErrorConfirmTag     = "CA防伪标签使用确认失败！";
    public static final String  ErrorConfirmTagId   = "CA防伪标签使用确认：起始标签存在问题！";
    public static final String  ErrorQueryBatch     = "商品批次查询失败！";
    public static final String  ErrorModifyBatch    = "商品批次修改失败！";
    public static final String  ErrorQueryTagDeal   = "CA防伪标签交易记录查询失败！";
    public static final String  ErrorCheckTag       = "商品未经授权！";
    public static final String  ErrorMatchTag       = "认证码不匹配！";
    public static final String  ErrorNoTag          = "商品未经授权！";
    public static final String  ErrorAddInspect      = "添加报批号失败";
    public static final String  ErrorNewAuth      = "创建新的授权方信息时，操作失败";
    public static final String  ErrorQueryAuth = "查找授权方信息时，操作失败";
    public static final String  ErrorModifyAuth = "修改授权方信息时，操作失败";
    public static final String  ErrorQueryTagAuth = "查找商品认证信息时，操作失败";
    public static final String  ErrorQueryVPBatch = "查找商品批次信息时，操作失败";
    public static final String  ErrorQueryVendor = "查找企业信息时，操作失败";
    public static final String  ErrorModifyTagAuth = "修改商品认证计数时，操作失败";
    public static final String  ErrorQueryVPType = "查找企业商品类新信息时，操作失败";
    public static final String  ErrorDeleteVendApply = "删除企业注册信息时，操作失败";
    public static final String  ErrorDeleteVPType = "删除企业商品类信息时，操作失败";
    public static final String  ErrorNewTagDeal = "新建商品标签交易信息时，操作失败";
    public static final String  ErrorQueryTagDEal = "查找商品标签交易信息时，操作失败";
    public static final String  ErrorCreateTmpPA = "临时新建商品认证时，操作失败";
    public static final String  ErrorNewBatch = "新建商品批次信息时，操作失败";
    public static final String  ErrorModifyVPType = "修改企业商品类新信息时，操作失败";
    public static final String  ErrorDeleteTmpPA = "删除临时认证表时，操作失败";
    public static final String  ErrorOperation = "操作失败";
    public static final String  ErrorModifyUser = "更新用户状态时，操作失败";
    public static final String  ErrorNewUser = "新建用户时，操作失败";
    public static final String  ErrorQueryUser = "查找用户信息时，操作失败";
    public static final String  ErrorDeleteUser="删除用户信息时，操作失败";
    public static final String  ErrorModifyVendor = "修改企业信息时，操作失败";
    public static final String  ErrorNewVendor = "新增企业时，操作失败";
    public static final String  ErrorModifyVPBatch = "修改商品批次信息时，操作失败";
    public static final String  ErrorCreateVPBatch = "修改商品批次信息表时，操作失败";
    public static final String  ErrorCreatePA = "新建商品认证时，操作失败";
    public static final String  ErrorNewVPType = "新建企业商品类型时，操作失败";
    public static final String  ErrorTmpPA2PA = "拷贝临时认证信息入库时，操作失败";
    public static final String  ErrorNewTmpPA = "在临时认证表中新增认证信息时，操作失败";
    public static final String  ErrorQueryTag = "标签查询失败";
    public static final String  ErrorLogQuery = "日志查询失败";
    public static final String  ErrorVPType = "错误的TypeID";
    public static final String  ErrorNoAppliedTag = "请先下载认证码";
    public static final String  ErrorTagUserd="部分标签已被使用，请确认。";
    public static final String  ErrorWriteLog = "修改商品类型时，日志写入失败";
    public static final String  ErrorNewInspect = "新建商品报批表时，操作失败";
    public static final String  ErrorQueryInspect = "查找商品报批表时，操作失败";
    public static final String  ErrorModifyInspect = "修改商品报批表时，操作失败";
    public static final String  ErrorLogisticsAdd = "出区核销出错，操作失败";
    public static final String  ErrorQueryLogistics = "查找出区核销表时，操作失败";
    public static final String  ErrorModifyLogistics = "修改出区核销表时，操作失败";
    public static final String  ErrorModifyLogisticsNoID = "修改出区核销表时，缺少参数LogisticsID";
    public static final String  ErrorNoParameter = "无传入参数";
    public static final String  ErrorAddAPPFormat    = "添加APP定制信息失败！";
    public static final String  ErrorModifyAPPFormat    = "修改APP定制信息失败！";
    public static final String  ErrorDelAPPFormat    = "删除APP定制信息失败！";
    public static final String  ErrorQueryAPPFormat    = "查询APP定制信息失败！";
    public static final String  ErrorAPPFormatNoID    = "缺少参数！";
    public static final String  ErrorAddMailLog    = "添加Email信息失败！";
    public static final String  ErrorModifyMailLog    = "修改Email信息失败！";
    public static final String  ErrorDelMailLog    = "删除Email信息失败！";
    public static final String  ErrorQueryMailLog    = "查询Email信息失败！";
    public static final String  ErrorMailLogNoID    = "缺少参数！";
    public static final String  ErrorQueryReport    = "报表查询失败！";
    public static final String  ErrorQueryReportNoParameter    = "报表查询失败，缺少参数！";
    public static final String  ErrorQueryReportErrorParameter    = "报表查询失败，错误参数！";
    public static final String  ErrorPrintPoolList    = "打印失败";
    public static final String  ErrorAddPoolList    = "生产失败";
    public static final String  ErrorTagNoStart    = "输入的起始标签非通用池标签，请重新确认！";
    public static final String  ErrorTagNoEnd    = "输入的结束标签非通用池标签，请重新确认！";
    public static final String  USERNAMESUFFIXEXISTERROR    = "该后缀的用户名已存在";
    public static final String  ErrorAddSubVendor    = "子局插入失败！";
    public static final String  ErrorUpdateSubVendor    = "子局更新失败！";
    public static final String  ErrorTagFormat="编号格式有误，请确认。";
    public static final String  ErrorVendApply="注册失败";

    public static final String SuccessModify = "修改成功";

    String PRODUCTNAMEEXISTS = "商品备案名已存在";
    String  ErrorAddOption     = "添加失败！";
    String  ErrorUpdateOption     = "更新失败！";
    String  ErrorUploadOption     = "上传失败！";
    String  ErrorDownloadOption     = "下载失败！";
    String  ErrorDownloadNull     = "暂无可下载的文件！";
    String  ErrorBatchNotExist     = "请填写正确的批次编号！";

    String  ErrorInspectCodeExist = "报检单已存在！";
    String  InspectCodeCanNotBeNull = "报检单号不能为空！";

    String  ErrorOriSealNoNotExist = "原铅封号不存在！";//铅封替换功能错误信息
    String  ErrorNEWSealNoNotExist = "新铅封号不存在！";//"铅封替换功能错误信息
    public static final String  ErrorNEWSealNoHasBatch = "新铅封号已被关联！";//
    public static final String  ErrorNEWSealNoNOTBELONGTOVENDOR = "新铅封号对应标签不属于本企业！";//
    public static final String  BATCHNODATA = "没有该批号对应的数据！";//"没有该批号对应的数据";
    public static final String  SYNSUCCESS = "同步成功！";//"没有该批号对应的数据";
    public static final String  RECORDLOGFAIL = "记录二次认证记录失败！";//"记录二次认证记录失败";
}
