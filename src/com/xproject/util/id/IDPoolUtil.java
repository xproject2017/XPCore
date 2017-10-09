package com.xproject.util.id;


import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2014/8/29.
 */
public class IDPoolUtil {
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
    public static  int IDVersion = 15;  //通用池标签的版本号 默认为15
    @SuppressWarnings("unused")
    private static final int IDVersionBits = 4;

    // ProviceAuthID
    private static final int ProvinceAuthIDBits = 8;
    public static  int ProvinceAuthId = IDUtil.ProvinceAuthId;   //省局id
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
    public static final int codeTypeIDBits = 48;           // 单独的TypeID，数据库中TypeID包含AuthID和VendorID

    // ProductID, 含DeviceID
    public static final int ProductIDBits = 32;          // 产品ID位数，含DeviceID
    public static final int ProductIDMask = 0x07ffffff;  // 不含DeviceID时的掩码 通用池码容量：2^16*2^27=2^43=8.7万亿

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
    public static final long maxProductid=Math.round(Math.pow(2,IDPoolUtil.binaryPIDEnd)-1);
    //通用池初始化值
//    public static final int initProvinceId = 0;
//    public static final int initAuthId = 0;
//    public static final int initVendorId = 0;
    public static final int initPtypeIdcnt = 1;
    public static final int initMaxProductId = 0;
    public static final int initMaxProductIdCNT = 500000; //一次性最多能生产的表签数


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
        long tmp1 = (long) (IDPoolUtil.IDVersion) << (IDPoolUtil.VendorIDBits
                + IDPoolUtil.AuthIDBits + IDPoolUtil.ProvinceAuthIDBits);
        // province auth
        long tmp2 = (long) (IDPoolUtil.ProvinceAuthId) << (IDPoolUtil.VendorIDBits + IDPoolUtil.AuthIDBits);
        // auth
        long tmp3 = (long) (authId) << IDPoolUtil.VendorIDBits;
        // vendor
        long tmp4 = (vendorCount + IDPoolUtil.VendorStartID) & IDPoolUtil.VendorIDMask;

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
        long tmp1 = (typeCount + IDPoolUtil.TypeStartID) & IDPoolUtil.TypeIDMask;
        long tmp2 = (vendorId) << IDPoolUtil.TypeIDBits;
        return (tmp1 + tmp2);
    }

    /*
     * 根据设备ID和当前该设备所包含的产品数量，生成产品ID
     */
    public static long getProductId(int deviceId, long currentCount) {
        //long productId = (((long) deviceId) << (ProductIDBits - DeviceIDBits)) + (currentCount & ProductIDMask) + 1;
        long productId =  (currentCount & ProductIDMask) + 1;
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
     * 根据版本号，类型ID，产品ID，生成认证ID
     *
     * @param typeId
     *    已经包含IDVersion
     * @param productId
     *    商品类型内部的产品编号
     */
    public static String getProductId(long typeId, long productId) {
        String fmtTypeId = "%0" + (IDPoolUtil.TagIDVersionLength + IDPoolUtil.TagTypeIDLength) + "x";
        String fmtProductId = "%0" + IDPoolUtil.TagProductIDLength + "x";
        String strTotalId = String.format(fmtTypeId, typeId) +
                String.format(fmtProductId, productId);

        return strTotalId;
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
    public static String getBatchTableByVendorid(long Vendorid) {
        String batchTable = "T_BUSI_BATCH" + Long.toHexString(Vendorid);
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
    public static String getBatchTableByTypeId(long TypeId) {
        String batchTable = "T_BUSI_BATCH" + Long.toHexString(TypeId);
        return batchTable.toUpperCase();
    }    /*
 * 根据标签ID的16进制字符串获取类型ID
 */
    public static long getVendorId(String code) {
        long typeId = getTypeId(code);

        String vendorid_binary = StringUtils.leftPad(Long.toBinaryString(typeId), IDPoolUtil.codeTypeIDBits, '0').substring(0, IDPoolUtil.codeVendorIDBits);
        long vendorId = Long.valueOf(vendorid_binary, 2);
        return vendorId;
    }

    /*
     * 根据标签ID的16进制字符串获取类型ID        有问题的老方法，少取了一位
     *      * code_1==001026f80a9d000000011dc31d
     * typeid_hex=11=001026f80a9d
     * typeid_hex=oo= 01026f80a9d
     */
    public static long getTypeId(String code) {
        //String strTypeId = String.copyValueOf(code.toCharArray(), TagTypeIDStart, TagTypeIDLength);
        String strTypeId = String.copyValueOf(code.toCharArray(), IDUtil.CodeTypeIDStart, IDUtil.CodeTypeIDEnd + 1);
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


    public static int getOrgid(String code) {
        long typeid = IDPoolUtil.getTypeId(code);
        //System.out.println("typeid=="+typeid);
        long t = typeid;
        t = t << 4;
        t = t & 0xff0000000000l;
        t = t >> 40;
        return (int) t;
    }

    public static int getSubOrgid(String code) {
        long typeid = IDPoolUtil.getTypeId(code);
        //System.out.println("typeid=="+typeid);
        long t = typeid;
        t = t << 12;
        t = t & 0xff0000000000l;
        t = t >> 42;
        return (int) t;
    }

    public static Long getEnigma(String code) {
        return Long.valueOf(IDPoolUtil.getAuthCode(code), 16);
    }

    public static void parse(String code) {
        //setCode(code);
        long typeid = IDPoolUtil.getTypeId(code);
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

        long productid = IDPoolUtil.getProductId(code);
//        System.out.println("productid==" + productid);
        Long enigma = Long.valueOf(IDPoolUtil.getAuthCode(code), 16);
//        System.out.println("enigma==" + enigma);

    }

    /*
    * 根据标签ID获取标签的版本值
    */
    public static int getVersionID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Integer.parseInt(binary_typeid.substring(IDPoolUtil.binaryIDVersionStart, IDPoolUtil.binaryIDVersionEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取省局id
	 */
    public static int getProvinceID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Integer.parseInt(binary_typeid.substring(IDPoolUtil.binaryProvinceIDStart, IDPoolUtil.binaryProvinceIDEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取子局id
	 */
    public static int getAuthID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Integer.parseInt(binary_typeid.substring(IDPoolUtil.binaryAuthIDStart, IDPoolUtil.binaryAuthIDEnd + 1), 2);
    }

    /*
  	 * 根据标签ID获取企业id
  	 */
    public static Long getVendorID_Dec(String code) {
        String binary_typeid = getBinaryTypeID_Dec(code);
        return Long.valueOf(binary_typeid.substring(IDPoolUtil.binaryVendorIDStart, IDPoolUtil.binaryVendorIDEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取typeid
	 */
    public static Long getTypeID_Dec(String code) {
        //code:28个16进制字符的字符串
        //typeid=[1,12],productid=[13,20],authcode=[21,28]
        //typeid=[0,11],productid=[12,19],authcode=[20,27]
        return Long.valueOf(code.substring(IDPoolUtil.CodeTypeIDStart, IDPoolUtil.CodeTypeIDEnd + 1), 16);
    }

    /*
	 * 根据标签ID获取typeid的二进制字符串
	 */
    public static String getBinaryTypeID_Dec(String code) {
        //code:28个16进制字符的字符串
        //typeid=[1,12],productid=[13,20],authcode=[21,28]
        //typeid=[0,11],productid=[12,19],authcode=[20,27]
        //补足48位
        return StringUtils.leftPad(Long.toBinaryString(getTypeID_Dec(code)), IDPoolUtil.codeTypeIDBits, '0');
    }

    /*
	 * 根据typeid获取typeid的二进制字符串
	 */
    public static String getBinaryTypeID_Dec(Long typeid) {
        //code:28个16进制字符的字符串
        //typeid=[1,12],productid=[13,20],authcode=[21,28]
        //typeid=[0,11],productid=[12,19],authcode=[20,27]
        //补足48位
        return StringUtils.leftPad(Long.toBinaryString(typeid), IDPoolUtil.codeTypeIDBits, '0');
    }

    /*
	 * 根据标签ID获取设备id
	 */
    public static int getDeviceID_Dec(String code) {
        String binary_productid = getBinaryProductID_Dec(code);
        return Integer.parseInt(binary_productid.substring(IDPoolUtil.binaryDeviceIDStart, IDPoolUtil.binaryDeviceIDEnd + 1), 2);
    }

    /*
	 * 根据标签ID获取productid
	 */
    public static Long getProductID_Dec(String code) {
        return Long.valueOf(code.substring(IDPoolUtil.CodeProductIDStart, IDPoolUtil.CodeProductIDEnd + 1), 16);
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
        return StringUtils.leftPad(Long.toBinaryString(getProductID_Dec(code)), IDPoolUtil.ProductIDBits, '0');
    }
    /*
   * 根据productid获取productid的二进制字符串
   */
    public static String getBinaryProductID_Dec(Long productid) {
        return StringUtils.leftPad(Long.toBinaryString(productid), IDPoolUtil.ProductIDBits, '0');
    }

    /*
	 * 根据typeid获取标签的版本值
	 */
    public static int getVersionID_Dec(Long typeid) {
        return Integer.parseInt(getBinaryTypeID_Dec(typeid).substring(IDPoolUtil.binaryIDVersionStart, IDPoolUtil.binaryIDVersionEnd + 1), 2);
    }

    /*
	 * 根据typeid获取省局id
	 */
    public static int getProvinceID_Dec(Long typeid) {
        return Integer.parseInt(getBinaryTypeID_Dec(typeid).substring(IDPoolUtil.binaryProvinceIDStart, IDPoolUtil.binaryProvinceIDEnd + 1), 2);
    }

    /*
	 * 根据typeid获取子局id
	 */
    public static int getAuthID_Dec(Long typeid) {
        return Integer.parseInt(getBinaryTypeID_Dec(typeid).substring(IDPoolUtil.binaryAuthIDStart, IDPoolUtil.binaryAuthIDEnd + 1), 2);
    }

    /*
  	 * 根据typeid获取企业id
  	 */
    public static Long getVendorID_Dec(Long typeid) {
        return Long.valueOf(getBinaryTypeID_Dec(typeid).substring(IDPoolUtil.binaryVendorIDStart, IDPoolUtil.binaryVendorIDEnd + 1), 2);
    }

    /*
	 * 根据productid获取标签的设备id
	 */
    public static int getDeviceID_Dec(Long productid) {
        return Integer.parseInt(getBinaryProductID_Dec(productid).substring(IDPoolUtil.binaryDeviceIDStart, IDPoolUtil.binaryDeviceIDEnd + 1), 2);
    }

    /*
	 * 根据productid获取标签的流水号 暂时没有用到这个流水号
	 */
    public static Long getProductID_Dec(Long productid) {
        return Long.valueOf(getBinaryProductID_Dec(productid).substring(IDPoolUtil.binaryPIDStart, IDPoolUtil.binaryPIDEnd + 1), 2);
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
     * 比较通用池标签大小_String
     * @param code1 通用池标签码
     * @param code2 通用池标签码
     * @return if(code1>code2) return true;else return false
     */
    public static boolean comparePoolCode_larger(String code1, String code2) {
        //解析code
//        typeid为高位
        long code1ProductId = IDPoolUtil.getProductID_Dec(code1);
        long code1Typeid = IDPoolUtil.getTypeID_Dec(code1);

        long code2ProductId = IDPoolUtil.getProductID_Dec(code2);
        long code2Typeid = IDPoolUtil.getTypeID_Dec(code2);

        if (code1Typeid > code2Typeid) {
            return true;
        } else if (code1Typeid == code2Typeid) {
            //typeid相同，比较productid
            if (code1ProductId > code2ProductId) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 比较通用池标签大小_数字
     * @param productId1 通用池标签码产品编号1
     * @param productId2 通用池标签码产品编号2
     * @return if(productId1>productId2) return true;else return false
     */
    public static boolean comparePoolCode_larger_Dec(long productId1, long productId2) {
        //解析code
//        typeid为高位
        return  (productId1>productId2);
    }

    /**
     * 计算两个通用池标签之间的标签数量(包括输入的标签)
     * @param code1 通用池标签码
     * @param code2 通用池标签码
     * @return 输出code2-code1+1
     */
    public static long getCodeCount(String code1, String code2) throws Exception {
        //解析code
//        typeid为高位
        //productId从0~ 2^31-1
        try {
            long code1ProductId = IDPoolUtil.getProductID_Dec(code1);
            long code1Typeid = IDPoolUtil.getTypeID_Dec(code1);

            long code2ProductId = IDPoolUtil.getProductID_Dec(code2);
            long code2Typeid = IDPoolUtil.getTypeID_Dec(code2);
            if (code1Typeid == code2Typeid) {
                return code2ProductId - code1ProductId + 1;
            } else {
                return (long) (Math.pow(2, 31) + 1) * (code2Typeid - code1Typeid) - code1ProductId + code2ProductId + 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 计算两个通用池标签之间的标签数量(包括输入的标签)
     * @param productId1 产品编号1
     * @param productId2 产品编号2
     * @return 输出productId2-productId1+1
     */
    public static long getCodeCount_Dec(long productId1, long productId2) throws Exception {
        //解析code
//        typeid为高位
        //productId从0~ 2^31-1
        try {

            if (productId1 <= productId2) {
                return productId2 - productId1 + 1;
            } else {
                return (long)Math.pow(2, 31) - productId1 + productId2 + 2;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
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

        long productid = getProductIdFromBoxCode(code);
//        System.out.println("productid==" + productid);
        Long enigma = Long.valueOf(IDUtil.getAuthCodeFromBatchCode(code), 16);
//        System.out.println("enigma==" + enigma);

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
}
