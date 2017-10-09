package com.xproject.util.id;


import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2014/8/29.
 */
public class IDUtil {
    /*
     * Old:
     * TagID:        10字节，即20个16进制数。从左到右如下：
     *    IDVersion:  0.5字节，即    1 Hex数
     *    AuthID:     1字节，       即    2 Hex数
     *    VendorID:   2.5字节，即    5 Hex数
     *    VPTypeID:   1.5字节，即    3 Hex数
     *    ProductID:  4.5字节，即    9 Hex数，其中高5位DeviceID
     *
     *    AuthCode: 4字节
     * New:
     * TagID:        10字节，即20个16进制数。从左到右如下：
     *    IDVersion:  0.5字节，即    1 Hex数
     *    AuthID:     1      字节， 即    2 Hex数    省局
     *    VendorID:   2.5字节，即    5 Hex数    子局+企业
     *    VPTypeID:   2      字节，即    4 Hex数      商品类别
     *    ProductID:  4       字节，即    8 Hex数，其中高5位DeviceID
     *
     *    AuthCode:   4       字节
     * New 2014.4.3:
     * TagID:         10字节，即20个16进制数。从左到右如下：
     *    IDVersion:  0.5字节，即    1 Hex数
     *    ProAuthID:  1      字节， 即    2 Hex数    省局
     *    AuthID   :  6  bits 子局
     *    VendorID:   14 bits，企业
     *    VPTypeID:   2      字节，即    4 Hex数      商品类别
     *    ProductID:  4       字节，即    8 Hex数，其中高5位DeviceID
     *
     *    AuthCode:   4       字节
     */
    // Version域
    public static  int IDVersion = 0;
    @SuppressWarnings("unused")
    private static final int IDVersionBits = 4;

    // ProviceAuthID
    private static final int ProvinceAuthIDBits = 8;
    public static  int ProvinceAuthId = 1; //省局id
    /*
IDUTIL.IDVERSION  //普通标签的版本号0--14
IDUTIL.PROVINCEAUTHID //普通标签的省局编号
IDUTIL.POOLPROVINCEAUTHID //通用池标签的省局编号
*/
    private static final int AuthIDBits = 6;


    // VendorID域
    private static final int VendorStartID = 0x3a6f8;
    private static final int VendorIDMask = 0x00003fff;
    private static final int VendorIDBits = 14;         // 单独的VendorID，数据库中包含AuthID
    private static final int codeVendorIDBits = 32;           // 单独的TypeID，数据库中TypeID包含AuthID和VendorID

    // TypeID域
    private static final int TypeStartID = 0xa9d;
    private static final int TypeIDMask = 0x0000ffff;
    private static final int TypeIDBits = 16;           // 单独的TypeID，数据库中TypeID包含AuthID和VendorID
    private static final int codeTypeIDBits = 48;           // 单独的TypeID，数据库中TypeID包含AuthID和VendorID

    // ProductID, 含DeviceID
    public static final int ProductIDBits = 32;          // 产品ID位数，含DeviceID
    public static final int ProductIDMask = 0x07ffffff;  // 不含DeviceID时的掩码

    // DeviceID
    public static final int DeviceMaxId = 31;      // 喷码设备的ID为：0-31
    public static final int DeviceIDBits = 5;       // 喷码设备的ID为：0-31，占据商品ID的最高5位

    // 16进制字符串中标签字段定义
    public static final int TagIDVersionLength = 1;   //标签版本长度
    public static final int TagTypeIDStart = 1;   //左边开始，标签中TypeID起始字符
    public static final int TagTypeIDLength = 11;  //左边开始，标签中TypeID结束字符
    public static final int TagProductIDStart = 12;  //左边开始，标签中ProductID起始字符
    public static final int TagProductIDLength = 8;   //左边开始，标签中ProductID结束字符
    public static final int TagAuthCodeStart = 20;  //左边开始，标签中AuthCode起始字符
    public static final int TagAuthCodeLength = 8;   //左边开始，标签中AuthCode结束字符

    public static final int AuthCodeLength = 28;   //认证码字符串总长度

    // 16进制字符串中标签字段定义
    //code:28个16进制字符的字符串
    //typeid=[1,12],productid=[13,20],authcode=[21,28]
    public static final int CodeTypeIDStart = 0;   //左边开始，标签中TypeID起始字符
    public static final int CodeTypeIDEnd = 11;  //左边开始，标签中TypeID结束字符
    public static final int CodeProductIDStart = 12;  //左边开始，标签中ProductID起始字符
    public static final int CodeProductIDEnd = 19;   //左边开始，标签中ProductID结束字符
    public static final int CodeAuthCodeStart = 20;  //左边开始，标签中AuthCode起始字符
    public static final int CodeAuthCodeEnd = 27;   //左边开始，标签中AuthCode结束字符
    //转成二进制后 typeid =[1,4]     [5,12]      [13,18]      [19,32]         [33,48]
    public static final int binaryIDVersionStart = 0;
    public static final int binaryIDVersionEnd = 3;
    public static final int binaryProvinceIDStart = 4;
    public static final int binaryProvinceIDEnd = 11;
    public static final int binaryAuthIDStart = 12;
    public static final int binaryAuthIDEnd = 17;
    public static final int binaryVendorIDStart = 0;
    public static final int binaryVendorIDEnd = 31;
    public static final int binaryTypeIDStart = 0;
    public static int binaryTypeIDEnd = 47;
    //转成二进制后 productid =[1,5]     [6,32]
    public static final int binaryDeviceIDStart = 0;
    public static final int binaryDeviceIDEnd = 4;
    public static final int binaryPIDStart = 5;
    public static final int binaryPIDEnd = 31;

    //批次码
    //新增批次码 长度24,前面12位的typeid编码规则保持不变，后8位的认证码保持不变，只是productid的长度只有4位
    //code:24个16进制字符的字符串
    public static final int BatchCodeAuthCodeLength = 24;   //认证码字符串总长度
    //typeid=[1,12],productid=[13,16],authcode=[17,24]
    public static final int BatchCodeCodeProductIDStart = 12;  //左边开始，标签中ProductID起始字符
    public static final int BatchCodeCodeProductIDEnd = 15;   //左边开始，标签中ProductID结束字符
    public static final int BatchCodeCodeAuthCodeStart = 16;  //左边开始，标签中AuthCode起始字符
    public static final int BatchCodeCodeAuthCodeEnd = 23;   //左边开始，标签中AuthCode结束字符
    // ProductID,
    public static final int BatchCodeProductIDBits = 16;          // 产品ID位数，不含DeviceID
    public static final int BatchCodeProductIDMask = 0xffff;  // 掩码
    //typeid=[1,12],productid=[13,16],authcode=[17,24]
    public static final int BatchCodeTagProductIDStart = 12;  //左边开始，标签中ProductID起始字符
    public static final int BatchCodeTagProductIDLength = 4;   //左边开始，标签中ProductID结束字符
    public static final int BatchCodeTagAuthCodeStart = 16;  //左边开始，标签中AuthCode起始字符
    //转成二进制之后
    public static final int binaryBatchCodeTagProductIDStart = 0;
    public static final int binaryBatchCodeTagProductIDEnd = 15;


    //箱码
    //新增箱码码 长度26,前面12位的typeid和中间8位的productid编码规则保持不变，后8位的认证码减少为6位
    //code:26个16进制字符的字符串
    public static final int BoxCodeAuthCodeLength = 26;   //认证码字符串总长度
    //typeid=[1,12],productid=[13,20],authcode=[21,26]
    public static final int BoxCodeCodeProductIDStart = 12;  //左边开始，标签中ProductID起始字符
    public static final int BoxCodeCodeProductIDEnd = 19;   //左边开始，标签中ProductID结束字符
    public static final int BoxCodeCodeAuthCodeStart = 20;  //左边开始，标签中AuthCode起始字符
    public static final int BoxCodeCodeAuthCodeEnd = 25;   //左边开始，标签中AuthCode结束字符
    // ProductID,
    public static final int BoxCodeProductIDBits = 32;          // 产品ID位数，不含DeviceID
    public static final int BoxCodeProductIDMask = 0x7fffffff;  // 掩码
    //typeid=[1,12],productid=[13,20],authcode=[21,26]
    public static final int BoxCodeTagProductIDStart = 12;  //左边开始，标签中ProductID起始字符
    public static final int BoxCodeTagProductIDLength = 8;   //左边开始，标签中ProductID结束字符
    public static final int BoxCodeTagAuthCodeStart = 20;  //左边开始，标签中AuthCode起始字符
    public static final int BoxCodeTagAuthCodeLength  = 6;  //左边开始，标签中AuthCode起始字符
    //转成二进制之后
    public static final int binaryBoxCodeTagProductIDStart = 0;
    public static final int binaryBoxCodeTagProductIDEnd = 31;


    /*
     * 获得最新VendorID，由版本号，省局ID，厂家编号（子局和企业一起编码）组成
     *
     * @param authId
     *    授权方ID，不含IDVersion
     * @param vendorCount
     *    authId授权方当前所包含的厂家数量
     */
    public static long getVendorId(int authId, int vendorCount) {
        // id version
        long tmp1 = (long) (IDUtil.IDVersion) << (IDUtil.VendorIDBits
                + IDUtil.AuthIDBits + IDUtil.ProvinceAuthIDBits);
        // province auth
        long tmp2 = (long) (IDUtil.ProvinceAuthId) << (IDUtil.VendorIDBits + IDUtil.AuthIDBits);
        // auth
        long tmp3 = (long) (authId) << IDUtil.VendorIDBits;
        // vendor
        long tmp4 = (vendorCount + IDUtil.VendorStartID) & IDUtil.VendorIDMask;

        return (tmp1 + tmp2 + tmp3 + tmp4);
//		long tmp1 = (vendorCount + IDUtil.VendorStartID) & IDUtil.VendorIDMask;
//		long tmp2 =  (long)(authId) << IDUtil.VendorIDBits;
//		//long tmp2 =  (long)(ProviceAuthId) << IDUtil.VendorIDBits;
//		long tmp3 =  (long)(IDUtil.IDVersion) << (IDUtil.VendorIDBits + IDUtil.AuthIDBits + IDUtil.ProviceAuthIDBits);
//		return (tmp1 + tmp2 + tmp3);
    }

    /*
     * 获得最新厂家商品类型ID，由VendorID和商品类型编号组成。
     *
     * @param vendorId
     *    厂家ID，已经包含IDVersion
     * @param typeCount
     *    authId授权方当前所包含的厂家数量
     */
    public static long getTypeId(long vendorId, int typeCount) {
        long tmp1 = (typeCount + IDUtil.TypeStartID) & IDUtil.TypeIDMask;
        long tmp2 = (vendorId) << IDUtil.TypeIDBits;
        return (tmp1 + tmp2);
    }

    /*
     * 根据设备ID和当前该设备所包含的产品数量，生成产品ID
     */
    public static long getProductId(int deviceId, long currentCount) {
        long productId = (((long) deviceId) << (ProductIDBits - DeviceIDBits)) + (currentCount & ProductIDMask) + 1;
        return productId;
    }

    /*
 * 根据设备ID和当前该设备所包含的产品数量，生成产品ID
 */
    public static long getBoxCodeProductId( long currentCount) {
        long productId =  (currentCount & BoxCodeProductIDMask) + 1;
        return productId;
    }

    /*
 * 根据设备ID和当前该设备所包含的产品数量，生成产品ID
 */
    public static long getBatchCodeProductId( long currentCount) {
        long productId =  (currentCount & BatchCodeProductIDMask) + 1;
        return productId;
    }

    /*
     * 根据版本号，类型ID，产品ID，生成认证ID
     *
     * @param typeId
     *    已经包含IDVersion
     * @param productId
     *    商品类型内部的产品编号
     */
    public static String getProductId(long typeId, long productId) {
        String fmtTypeId = "%0" + (IDUtil.TagIDVersionLength + IDUtil.TagTypeIDLength) + "x";
        String fmtProductId = "%0" + IDUtil.TagProductIDLength + "x";
        String strTotalId = String.format(fmtTypeId, typeId) +
                String.format(fmtProductId, productId);

        return strTotalId;
    }

    /*
 * 根据版本号，类型ID，产品ID，后8位校验码，生成24位批次码
 *
 * @param typeId
 *    已经包含IDVersion
 * @param productId
 *    商品类型内部的产品编号
 * @param authCode
 *    后8位校验码
 */
    public static String getBatchAuthCode(long typeId, long productId,String authCode) {
        String fmtTypeId = "%0" + (IDUtil.TagIDVersionLength + IDUtil.TagTypeIDLength) + "x";
        String fmtProductId = "%0" + IDUtil.BatchCodeTagProductIDLength + "x";
        String strTotalId = String.format(fmtTypeId, typeId) +
                String.format(fmtProductId, productId);

        return strTotalId+authCode;
    }

    /*
* 根据版本号，类型ID，产品ID，后6位校验码，生成26位批次码
*
* @param typeId
*    已经包含IDVersion
* @param productId
*    商品类型内部的产品编号
* @param authCode
*    后6位校验码
*/
    public static String getBoxCodeAuthCode(long typeId, long productId,String authCode) {
        String fmtTypeId = "%0" + (IDUtil.TagIDVersionLength + IDUtil.TagTypeIDLength) + "x";
        String fmtProductId = "%0" + IDUtil.BoxCodeTagProductIDLength + "x";
        String strTotalId = String.format(fmtTypeId, typeId) +
                String.format(fmtProductId, productId);

        return strTotalId+authCode;
    }


    public static String getBatchTable(long typeId) {
        String strTypeId = Long.toHexString(typeId);
        String batchTable = "batch" + strTypeId;
        return batchTable;
    }

    public static String getProductAuthTable(long typeId) {
        String strTypeId = Long.toHexString(typeId);
        String paTable = "pa" + strTypeId;
        return paTable;
    }

    public static String getBatchTableByVendorid(long Vendorid) {
        String batchTable = "T_BUSI_BATCH" + Long.toHexString(Vendorid);
        return batchTable.toUpperCase();
    }
    public static String getBatchTableByTypeId(long TypeId) {
        String batchTable = "T_BUSI_BATCH" + Long.toHexString(TypeId);
        return batchTable.toUpperCase();
    }
    public static String getProductAuthTableByTypeId(long typeId) {
        String paTable = "T_BUSI_PA" + Long.toHexString(typeId);
        return paTable.toUpperCase();
    }
    public static String getTempPaTableByTypeId(long typeId) {
        String paTable = "T_BUSI_TMPPA" + Long.toHexString(typeId);
        return paTable.toUpperCase();
    }

    public static String getT_BUSI_TAGByTypeId(long typeId) {
        String paTable = "T_BUSI_TAG" + Long.toHexString(typeId);
        return paTable.toUpperCase();
    }
    public static String getT_BUSI_TAGSTATByTypeId(long typeId) {
        String paTable = "T_BUSI_TAGSTAT" + Long.toHexString(typeId);
        return paTable.toUpperCase();
    }
    public static String getT_BUSI_TMPLGByTypeId(long typeId) {
        String paTable = "T_BUSI_TMPLG" + Long.toHexString(typeId);
        return paTable.toUpperCase();
    }

    /*
     * 临时认证表表名: typeId的十六进制字符串 + 用户ID + 喷码器设备ID或标签烧写软件ID
     *
     * @param typeId
     *    已经包含IDVersion
     * @param userId
     *    当前登录会话的用户ID
     * @param deviceID
     *    喷码器设备ID或标签烧写软件ID
     */
    public static String getTempPaTable(long typeId, int userId, int deviceID) {
        String strTypeId = Long.toHexString(typeId);
        String strUserId = Integer.toHexString(userId);
        String strDeviceId = Integer.toHexString(deviceID);
        String paTable = "tmppa" + strTypeId + strUserId + strDeviceId;
        return paTable;
    }
    public static String getTempPaTableByTypeId(long typeId, int userId, int deviceID) {
        String strTypeId = Long.toHexString(typeId);
        String strUserId = Integer.toHexString(userId % (int)Math.pow(16,5)); //防止表超长
        String strDeviceId = Integer.toHexString(deviceID);
        String paTable = "T_BUSI_TMPPA" + strTypeId + strUserId + strDeviceId;
        return paTable.toUpperCase();
    }

    /*
 * 根据标签ID的16进制字符串获取类型ID
 */
    public static long getVendorId(String code) {
        long typeId = getTypeId(code);

        String vendorid_binary = StringUtils.leftPad(Long.toBinaryString(typeId), IDUtil.codeTypeIDBits, '0').substring(0, IDUtil.codeVendorIDBits);
        long vendorId = Long.valueOf(vendorid_binary, 2);
        return vendorId;
    }

    /*
     * 根据标签ID的16进制字符串获取类型ID    有问题的老方法，少取了一位
     * code_1==001026f80a9d000000011dc31d
     * typeid_hex=11=001026f80a9d
     * typeid_hex=oo= 01026f80a9d
     */
    public static long getTypeId(String code) {
        //String strTypeId = String.copyValueOf(code.toCharArray(), TagTypeIDStart, TagTypeIDLength);
        String strTypeId = String.copyValueOf(code.toCharArray(), IDUtil.CodeTypeIDStart, IDUtil.CodeTypeIDEnd + 1);
       // System.out.println("typeid_hex=oo="+strTypeId);
        long typeId = Long.valueOf(strTypeId, 16);
        return typeId;
    }

    /*
     * 根据标签ID的16进制字符串获取产品ID
     */
    public static long getProductId(String code) {
        String strProductId = String.copyValueOf(code.toCharArray(), TagProductIDStart, TagProductIDLength);
        long productId = Long.valueOf(strProductId, 16);
        return productId;
    }
    /*
  * 根据标签ID的16进制字符串--批次码获取产品ID
  */
    public static long getProductIdFromBatchCode(String code) {
        String strProductId = String.copyValueOf(code.toCharArray(), BatchCodeTagProductIDStart, BatchCodeTagProductIDLength);
        long productId = Long.valueOf(strProductId, 16);
        return productId;
    }

    /*
* 根据标签ID的16进制字符串--批次码获取产品ID
*/
    public static long getProductIdFromBoxCode(String code) {
        String strProductId = String.copyValueOf(code.toCharArray(), BoxCodeTagProductIDStart, BoxCodeTagProductIDLength);
//        System.out.println("code=="+code+"strProductId=1=="+strProductId);
        long productId = Long.valueOf(strProductId, 16);
        return productId;
    }

    /*
     * 根据标签ID和认证码的16进制字符串获取认证码部分字符串
     */
    public static String getAuthCode(String code) {
        String strAuthCode = String.copyValueOf(code.toCharArray(), TagAuthCodeStart, TagAuthCodeLength);

        return strAuthCode;
    }
    /*
 * 根据标签ID和认证码的16进制字符串获取认证码部分字符串
 */
    public static String getAuthCodeFromBoxCode(String code) {
        String strAuthCode = String.copyValueOf(code.toCharArray(), BoxCodeTagAuthCodeStart, BoxCodeTagAuthCodeLength);

        return strAuthCode;
    }

    /*
 * 根据标签ID和认证码的16进制字符串--批次码获取认证码部分字符串
 */
    public static String getAuthCodeFromBatchCode(String code) {
        String strAuthCode = String.copyValueOf(code.toCharArray(), BatchCodeTagAuthCodeStart, TagAuthCodeLength);

        return strAuthCode;
    }

    public static int getOrgid(String code) {
        long typeid = IDUtil.getTypeId(code);
        //System.out.println("typeid=="+typeid);
        long t = typeid;
        t = t << 4;
        t = t & 0xff0000000000l;
        t = t >> 40;
        return (int) t;
    }

    public static int getSubOrgid(String code) {
        long typeid = IDUtil.getTypeId(code);
        //System.out.println("typeid=="+typeid);
        long t = typeid;
        t = t << 12;
        t = t & 0xff0000000000l;
        t = t >> 42;
        return (int) t;
    }

    public static Long getEnigma(String code) {
        return Long.valueOf(IDUtil.getAuthCode(code), 16);
    }

    public static void parse(String code) {
        //setCode(code);
        long typeid = IDUtil.getTypeId(code);
//        System.out.println("typeid_old==" + typeid);
        long t = typeid;
        t = t << 4;
        t = t & 0xff0000000000l;
        t = t >> 40;
        int org = (int) t;
//        System.out.println("org==" + org);

        t = typeid;
        t = t << 12;
        t = t & 0xff0000000000l;
        t = t >> 42;
        int suborg = (int) t;
//        System.out.println("suborg==" + suborg);

        long productid = IDUtil.getProductId(code);
//        System.out.println("productid==" + productid);
        Long enigma = Long.valueOf(IDUtil.getAuthCode(code), 16);
//        System.out.println("enigma==" + enigma);

    }

    public static void parseBatchCode(String code) {
        //setCode(code);
        long typeid = IDUtil.getTypeId(code);
//        System.out.println("typeid_old==" + typeid);
        long t = typeid;
        t = t << 4;
        t = t & 0xff0000000000l;
        t = t >> 40;
        int org = (int) t;
//        System.out.println("org==" + org);

        t = typeid;
        t = t << 12;
        t = t & 0xff0000000000l;
        t = t >> 42;
        int suborg = (int) t;
//        System.out.println("suborg==" + suborg);

        long productid = IDUtil.getProductIdFromBatchCode(code);
//        System.out.println("productid==" + productid);
        Long enigma = Long.valueOf(IDUtil.getAuthCodeFromBatchCode(code), 16);
//        System.out.println("enigma==" + enigma);

    }

    public static void parseBoxCode(String code) {
        //setCode(code);
        long typeid = IDUtil.getTypeId(code);
//        System.out.println("typeid_old==" + typeid);
        long t = typeid;
        t = t << 4;
        t = t & 0xff0000000000l;
        t = t >> 40;
        int org = (int) t;
//        System.out.println("org==" + org);

        t = typeid;
        t = t << 12;
        t = t & 0xff0000000000l;
        t = t >> 42;
        int suborg = (int) t;
//        System.out.println("suborg==" + suborg);

        long productid = IDUtil.getProductIdFromBoxCode(code);
//        System.out.println("productid==" + productid);
        Long enigma = Long.valueOf(IDUtil.getAuthCodeFromBoxCode(code), 16);
//        System.out.println("enigma==" + enigma);

    }

    /*
    * 根据标签ID获取标签的版本值
    */
    public static int getVersionID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Integer.parseInt(binary_typeid.substring(IDUtil.binaryIDVersionStart, IDUtil.binaryIDVersionEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取省局id
	 */
    public static int getProvinceID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Integer.parseInt(binary_typeid.substring(IDUtil.binaryProvinceIDStart, IDUtil.binaryProvinceIDEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取子局id
	 */
    public static int getAuthID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Integer.parseInt(binary_typeid.substring(IDUtil.binaryAuthIDStart, IDUtil.binaryAuthIDEnd + 1), 2);
    }

    /*
  	 * 根据标签ID获取企业id
  	 */
    public static Long getVendorID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Long.valueOf(binary_typeid.substring(IDUtil.binaryVendorIDStart, IDUtil.binaryVendorIDEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取typeid
	 */
    public static Long getTypeID_Dec(String code) {
        //code:28个16进制字符的字符串
        //typeid=[1,12],productid=[13,20],authcode=[21,28]
        //typeid=[0,11],productid=[12,19],authcode=[20,27]
       // System.out.println("typeid_hex=11="+code.substring(IDUtil.CodeTypeIDStart, IDUtil.CodeTypeIDEnd + 1));
        return Long.valueOf(code.substring(IDUtil.CodeTypeIDStart, IDUtil.CodeTypeIDEnd + 1), 16);
    }

    /*
	 * 根据标签ID获取typeid的二进制字符串
	 */
    public static String getBinaryTypeID_Dec(String code) {
        //code:28个16进制字符的字符串
        //typeid=[1,12],productid=[13,20],authcode=[21,28]
        //typeid=[0,11],productid=[12,19],authcode=[20,27]
        //补足48位
        return StringUtils.leftPad(Long.toBinaryString(getTypeID_Dec(code)), IDUtil.codeTypeIDBits, '0');
    }

    /*
	 * 根据typeid获取typeid的二进制字符串
	 */
    public static String getBinaryTypeID_Dec(Long typeid) {
        //code:28个16进制字符的字符串
        //typeid=[1,12],productid=[13,20],authcode=[21,28]
        //typeid=[0,11],productid=[12,19],authcode=[20,27]
        //补足48位
        return StringUtils.leftPad(Long.toBinaryString(typeid), IDUtil.codeTypeIDBits, '0');
    }

    /*
	 * 根据标签ID获取设备id
	 */
    public static int getDeviceID_Dec(String code) {
        String binary_productid = getBinaryProductID_Dec(code);
        return Integer.parseInt(binary_productid.substring(IDUtil.binaryDeviceIDStart, IDUtil.binaryDeviceIDEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取productid
	 */
    public static Long getProductID_Dec(String code) {
        return Long.valueOf(code.substring(IDUtil.CodeProductIDStart, IDUtil.CodeProductIDEnd + 1), 16);
    }

    /*
  * 根据标签ID--批次码获取productid
  */
    public static Long getProductID_DecFromBatchCode(String code) {
        return Long.valueOf(code.substring(IDUtil.BatchCodeCodeProductIDStart, IDUtil.BatchCodeCodeProductIDEnd + 1), 16);
    }

    /*
* 根据标签ID--箱码获取productid
*/
    public static Long getProductID_DecFromBoxCode(String code) {
//        System.out.println("code=="+code+",strProductId=0=="+code.substring(IDUtil.BoxCodeCodeProductIDStart, IDUtil.BoxCodeCodeProductIDEnd + 1));
        return Long.valueOf(code.substring(IDUtil.BoxCodeCodeProductIDStart, IDUtil.BoxCodeCodeProductIDEnd + 1), 16);
    }

    /*
	 * 根据标签ID获取productid的二进制字符串
	 */
    public static String getBinaryProductID_Dec(String code) {
        return StringUtils.leftPad(Long.toBinaryString(getProductID_Dec(code)), IDUtil.ProductIDBits, '0');
    }
    /*
   * 根据productid获取productid的二进制字符串
   */
    public static String getBinaryProductID_Dec(Long productid) {
        return StringUtils.leftPad(Long.toBinaryString(productid), IDUtil.ProductIDBits, '0');
    }


    /*
	 * 根据typeid获取标签的版本值
	 */
    public static int getVersionID_Dec(Long typeid) {
        return Integer.parseInt(getBinaryTypeID_Dec(typeid).substring(IDUtil.binaryIDVersionStart, IDUtil.binaryIDVersionEnd + 1), 2);
    }

    /*
	 * 根据typeid获取省局id
	 */
    public static int getProvinceID_Dec(Long typeid) {
        return Integer.parseInt(getBinaryTypeID_Dec(typeid).substring(IDUtil.binaryProvinceIDStart, IDUtil.binaryProvinceIDEnd + 1), 2);
    }

    /*
	 * 根据typeid获取子局id
	 */
    public static int getAuthID_Dec(Long typeid) {
        return Integer.parseInt(getBinaryTypeID_Dec(typeid).substring(IDUtil.binaryAuthIDStart, IDUtil.binaryAuthIDEnd + 1), 2);
    }

    /*
  	 * 根据typeid获取企业id
  	 */
    public static Long getVendorID_Dec(Long typeid) {
        return Long.valueOf(getBinaryTypeID_Dec(typeid).substring(IDUtil.binaryVendorIDStart, IDUtil.binaryVendorIDEnd + 1), 2);
    }

    /*
	 * 根据productid获取标签的设备id
	 */
    public static int getDeviceID_Dec(Long productid) {
        return Integer.parseInt(getBinaryProductID_Dec(productid).substring(IDUtil.binaryDeviceIDStart, IDUtil.binaryDeviceIDEnd + 1), 2);
    }

    /*
	 * 根据productid获取标签的流水号 暂时没有用到这个流水号
	 */
    public static Long getProductID_Dec(Long productid) {
        return Long.valueOf(getBinaryProductID_Dec(productid).substring(IDUtil.binaryPIDStart, IDUtil.binaryPIDEnd + 1), 2);
    }

    /*
    * 比对typeid和authcode中的typeid是否相同
    * */
    public static boolean equalsTypeID(Long TypeID,String authcode) {
         Long new_typeid=getTypeID_Dec(authcode);
        if(new_typeid.equals(TypeID))
            return true;
        else
            return false;
    }

    /**
     * 长码转短码  去掉typeid中的省局和子局，,同时去掉了产品编号高位的0，同时去掉后4个认证串，同时将版本号标示为0和1（0认证码，1通用池码）
     * 短码结构：长度[9,16]  1-8是固定长度的短typeid，后面的[9,16]是变长的产品编号
     * stypeid  sproductid
     * 27320aa4 11e    --001027320aa4 0000011e c704d9ee
     * 66f90a9e 5888   --f01026f90a9e 00005888 e77c4512
     * @param ccode  28位
     */
    public static String code2short(String ccode) {
		/*
		 *typeid        productid authcode
		 *001027320aa4  0000011e  c704d9ee
		 *f01026f90a9e  00005888  e77c4512
        //             版本 省局     子局    企业         商品类型
        //             1-4  5-12     13-18  19-32          33-48
		//69373332127==0000 00000001 000000 10011011111001 0000101010011111
              typeid1::0000 00000001 000000 10011100110010 0000101010100100
              typeid2::1111 00000001 000000 10011011111001 0000101010011110

        short_typeid1::0000                 10011100110010 0000101010100100
        short_typeid2::0001                 10011011111001 0000101010011110
        short_typeid1::0000100111001100100000101010100100      --34 高三位都是0
        short_typeid2::0001100110111110010000101010011110      --34 高三位都是0
		 */
        String hex_productid="";
        String hex_typeid="";
//        System.out.println("ccode=0="+ccode);

        if("0".equals(ccode.substring(0, 1))){

            long typeid=IDUtil.getTypeID_Dec(ccode);
            String bin_typeid= StringUtils.leftPad(Long.toBinaryString(typeid), IDUtil.codeTypeIDBits, '0'); //48位补齐
            long productid=IDUtil.getProductID_Dec(ccode);

            //hex_productid=ccode.substring(IDUtil.CodeProductIDStart, IDUtil.CodeProductIDEnd + 1);
            hex_productid=Long.toHexString(productid);

            String bin_version=bin_typeid.substring(IDUtil.binaryIDVersionStart, IDUtil.binaryIDVersionEnd + 1);
            String bin_vp=bin_typeid.substring(18, 48);
            hex_typeid=Long.toHexString(Long.valueOf(bin_version+bin_vp, 2));

//            System.out.println("typeid=0="+typeid);
//            System.out.println("productid=0="+productid);
//            System.out.println("bin_typeid=0="+bin_typeid);
//            System.out.println("hex_productid=0="+hex_productid);
//            System.out.println("hex_typeid=0="+hex_typeid);
//            System.out.println("code2short=0="+hex_typeid+hex_productid);
        }else{
            if("F".equals(ccode.substring(0, 1).toUpperCase())){
                long typeid=IDPoolUtil.getTypeID_Dec(ccode);
                String bin_typeid= StringUtils.leftPad(Long.toBinaryString(typeid), IDPoolUtil.codeTypeIDBits, '0');
                long productid=IDPoolUtil.getProductID_Dec(ccode);

                //hex_productid=ccode.substring(IDPoolUtil.CodeProductIDStart, IDPoolUtil.CodeProductIDEnd + 1);
                hex_productid=Long.toHexString(productid);

                //String bin_version=bin_typeid.substring(IDPoolUtil.binaryIDVersionStart, IDPoolUtil.binaryIDVersionEnd + 1);
                String bin_version="0001";
                String bin_vp=bin_typeid.substring(18, 48);
                hex_typeid=Long.toHexString(Long.valueOf(bin_version+bin_vp, 2));

//                System.out.println("typeid=0="+typeid);
//                System.out.println("productid=0="+productid);
//                System.out.println("bin_typeid=0="+bin_typeid);
//                System.out.println("hex_productid=0="+hex_productid);
//                System.out.println("hex_typeid=0="+hex_typeid);
//                System.out.println("code2short=0="+hex_typeid+hex_productid);
            }
        }
        return hex_typeid+hex_productid;
    }

    /**
     * 短码转认证码（不包含后4个认证串）
     * @param shortcode  认证码的短码（长度[9,16]）
     */
    public static String code2short_DEC(String shortcode,int proviceid,int authid) {

        String hex_stypeid=shortcode.substring(0, 8); //没有去掉头上的第一位(二进制的高4位)
        String hex_sproductid=shortcode.substring(8);

        long stypeid=Long.valueOf(hex_stypeid, 16);
        String bin_stypeid= StringUtils.leftPad(Long.toBinaryString(stypeid),34,"0");  // 48-8-6-4+4=34 （其实是34，默认高三位都是0，所以只需要31位）
        String bin_version="0000";
        if("0001".equals(bin_stypeid.substring(0,4))){
            bin_version="1111";
        }
        String bin_proviceid= StringUtils.leftPad(Long.toBinaryString(proviceid),8,"0");
        String bin_authid= StringUtils.leftPad(Long.toBinaryString(authid),6,"0");

        String bin_typeid= StringUtils.leftPad(bin_version+bin_proviceid+bin_authid+bin_stypeid.substring(4),48,"0"); //48位补齐

//        System.out.println("bin_version=1="+bin_version);
//        System.out.println("bin_proviceid=1="+bin_proviceid);
//        System.out.println("bin_authid=1="+bin_authid);
//        System.out.println("bin_stypeid=1="+bin_stypeid.substring(1));
//        System.out.println("hex_stypeid=1="+hex_stypeid);
//        System.out.println("bin_typeid=1="+bin_typeid);
//        System.out.println("typeid=1="+Long.valueOf(bin_typeid,2));
//        System.out.println("hex_sproductid=1="+hex_sproductid);

        return StringUtils.leftPad(Long.toHexString(Long.valueOf(bin_typeid,2)),12,"0")+ StringUtils.leftPad(hex_sproductid,8,"0"); //12+8  还原前20位
    }

    /**批次码不支持通用池码
     * 24位批次码 //typeid=[1,12],productid=[13,16],authcode=[17,24]
     * 24位批次码转短码  去掉后8个认证串，去掉typeid中的省局和子局，4个字符的productid保持不变， 同时高位补一个字符f，总长度13
     * 短码结构：长度13  [1]f批次码标识符，[2,9]typeid,[10,13]productid
     *  stypeid  sproductid
     *  f26f90aa20002
     *f 26f90aa2 0002    --021026f90aa2 0002
     *                   --021026f90aa2 0002 b0c2c1eb
     * @param bccode  24的认证码
     */
    public static String bcode2short(String bccode) {
/*
		 *typeid        productid authcode
		 *021026f90aa2  0002      b0c2c1eb
                   021026f90aa2 0002 b0c2c1eb
          bccode=0=021026f90aa2 0002 b0c2c1eb
          typeid=0=2268396587682
          shortbcode=0=f26f90aa20002
        //             版本 省局     子局    企业          商品类型
        //             1-4  5-12     13-18  19-32          33-48
                      :0000 00000001 000000 10011100110010 0000101010100100
          bin_typeid=0=0000 00100001 000000 10011011111001 0000101010100010
          hex_productid=0=0002
		 */
        String hex_productid = "";
        String hex_typeid = "";
//        System.out.println("bccode=0=" + bccode);

        long typeid = IDUtil.getTypeID_Dec(bccode);
        String bin_typeid = StringUtils.leftPad(Long.toBinaryString(typeid), IDUtil.codeTypeIDBits, '0');  //补齐48位

        hex_productid=bccode.substring(IDUtil.BatchCodeCodeProductIDStart, IDUtil.BatchCodeCodeProductIDEnd + 1);

        String bin_version = bin_typeid.substring(IDUtil.binaryIDVersionStart, IDUtil.binaryIDVersionEnd + 1);
        String bin_vp = bin_typeid.substring(18, 48);
        //hex_typeid = StringUtils.leftPad(Long.toHexString(Long.valueOf(bin_version + bin_vp, 2)),9,"0");
        hex_typeid = Long.toHexString(Long.valueOf(bin_version + bin_vp, 2));

//            System.out.println("typeid=0="+typeid);
//            System.out.println("bin_typeid=0="+bin_typeid);
//            System.out.println("sbin_typeid=0="+bin_version + bin_vp);
//            System.out.println("hex_productid=0="+hex_productid);
//            System.out.println("hex_typeid=0="+hex_typeid);
//            System.out.println("bcode2short=0="+"f"+hex_typeid+hex_productid);

        return "f"+hex_typeid + hex_productid;
    }

    /**
     * 短码转认证码（不包含后8个认证串）
     * @param shortbcode  认证码的短码（长度13）
     */
    public static String bcode2short_DEC(String shortbcode,int proviceid,int authid) {

        String hex_stypeid=shortbcode.substring(1, 9);//去掉头上的第一位
        String hex_sproductid=shortbcode.substring(9);

        long stypeid=Long.valueOf(hex_stypeid, 16);
        String bin_stypeid= StringUtils.leftPad(Long.toBinaryString(stypeid),34,"0");
        String bin_version="0000";

        String bin_proviceid= StringUtils.leftPad(Long.toBinaryString(proviceid),8,"0");
        String bin_authid= StringUtils.leftPad(Long.toBinaryString(authid),6,"0");

        String bin_typeid= StringUtils.leftPad(bin_version+bin_proviceid+bin_authid+bin_stypeid.substring(4),48,"0"); //补齐typeid的48位

//        System.out.println("shortbcode=0="+shortbcode);
//        System.out.println("hex_stypeid=0="+hex_stypeid);

//        System.out.println("bin_typeid=0="+bin_typeid);

        return StringUtils.leftPad(Long.toHexString(Long.valueOf(bin_typeid,2)),12,"0")+ StringUtils.leftPad(hex_sproductid, 4, "0");//还原12+4=16的批次码前16个字符
    }
 }
