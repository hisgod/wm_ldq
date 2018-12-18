package com.aib.net


import android.support.v4.util.ArrayMap
import com.aib.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.*

interface ApiService {

    companion object {
        var baseUrl = "http://www.smoneybag.com/loanMarket/"   //线上
//        var baseUrl = "http://192.168.2.124:8080/loanMarket/";   //本地
//        var baseUrl = "http://120.79.50.236:8080/loanMarket/";   //新地址
//        val baseUrl = "http://www.smoneybag.com:8080/loanMarket/"   //测试
    }

    /**
     * 首页数据
     *
     * @return
     */
    @GET("appMenu/getHomepages")
    fun HOME(): Observable<BaseEntity<HomeEntity>>

    /**
     * 上传单张文件
     *
     * @return
     */
    @POST("appUser/editHeadImg")
    fun UPLOAD_SINGLE_FILE(@Body body: MultipartBody): Observable<BaseEntity<String>>

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @POST("appUser/loginOut")
    @FormUrlEncoded
    fun USER_EXIT(@Field("token") token: String): Observable<BaseEntity<String>>

    /**
     * 消息中心列表
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @return
     */
    @POST("sms/notice/list")
    @FormUrlEncoded
    fun MSG_CENTER_LIST(@Field("token") token: String, @Field("pageNum") pageNum: Int, @Field("pageSize") pageSize: Int): Observable<BaseEntity<MsgCenterEntity>>

    /**
     * 综合排序
     *
     * @return
     */
    @POST("appProduct/queryBest")
    @FormUrlEncoded
    fun ZH_SORT(@Field("pageNum") page: Int, @Field("pageSize") size: Int): Observable<BaseEntity<ArrayList<MoreLoanEntity>>>

    /**
     * 筛选
     *
     * @return
     */
    @POST("appProduct/queryByCondition")
    @FormUrlEncoded
    fun QUERY(@FieldMap params: ArrayMap<String, Any>): Observable<BaseEntity<ArrayList<MoreLoanEntity>>>

    /**
     * 搜索分类
     *
     * @return
     */
    @GET("appProduct/queryAppProductTag")
    fun TYPE_LIST(): Observable<BaseEntity<ArrayList<TypeEntity>>>

    /**
     * 获取底部导航栏数据
     *
     * @return
     */
    @GET("appMenu/getBottomMenusNew")
    fun BOTTOM_NAVIGATION_LIST(@Query("packageID") packageID: String, @Query("vserionID") vserionID: String): Observable<BaseEntity<BottomNavigationEntity>>

    /**
     * 获取贷款详情
     *
     * @param productId
     * @return
     */
    @POST("appProduct/getDetail")
    @FormUrlEncoded
    fun LOAN_DETAIL(@Field("productId") productId: Int): Observable<BaseEntity<LoanDetailEntity>>


    /**
     * 获取短信验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @POST("sms/send/verification/code")
    @FormUrlEncoded
    fun VERTIFICATION_CODE(@Field("phone") phone: String, @Field("type") type: Int): Observable<BaseEntity<String>>

    /**
     * 免登录
     *
     * @return
     */
    @POST("appUser/autoLogin")
    @FormUrlEncoded
    fun LOGIN_FREE(@Field("token") token: String): Observable<BaseEntity<UserEntity>>

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param code
     * @param smsToken //验证码返回的smsToken
     * @param type     //标识注册还是修改密码
     * @return
     */
    @POST("appUser/register")
    @FormUrlEncoded
    fun USER_REGISTER(@Field("phone") phone: String, @Field("password") pwd: String, @Field("code") code: String, @Field("smsToken") smsToken: String?, @Field("sysChannelUserId") type: Int): Observable<BaseEntity<String>>

    /**
     * 登录
     *
     * @return
     */
    @POST("appUser/login")
    @FormUrlEncoded
    fun USER_LOGIN(@Field("phone") phone: String, @Field("pwd") pwd: String): Observable<BaseEntity<UserEntity>>

    /**
     * 忘记密码
     *
     * @param phone
     * @param code
     * @param pwd
     * @param smsToken
     * @return
     */
    @POST("appUser/forgetPwd")
    @FormUrlEncoded
    fun FORGET_PWD(@Field("phone") phone: String, @Field("pwd") pwd: String, @Field("code") code: String, @Field("smsToken") smsToken: String?): Observable<BaseEntity<String>>

    /**
     * 用户协议
     *
     * @param text
     * @return
     */
    @GET("common/getAppAgreement")
    fun AGREEMENT(@Query("onlyLabel") text: String): Observable<BaseEntity<AgreementEntity>>

    /**
     * 贷款详情点击量
     *
     * @param productId
     * @return
     */
    @POST("appProduct/countHits")
    @FormUrlEncoded
    fun LOAN_CLICK_COUNT(@Field("productId") productId: Int, @Field("token") token: String): Observable<BaseEntity<String>>

    /**
     * 申请状态推荐列表
     *
     * @return
     */
    @GET("appADV/recommend/getLAPlist")
    fun APPLY_STATUS_AD(): Observable<BaseEntity<List<ApplyInfoEntity>>>

    /**
     * 申请贷款状态
     */
    @POST("applyLoan/getApplyStatus")
    @FormUrlEncoded
    fun APPLY_STATUS(@Field("token") token: String): Observable<BaseEntity<ApplyStatusEntity>>

    /**
     * 申请贷款
     *
     * @return
     */
    @POST("applyLoan/apply")
    @FormUrlEncoded
    fun APPLY_LOAN(@FieldMap params: ArrayMap<String, Any>): Observable<BaseEntity<CommitDataEntity>>

    /**
     * 获取支付宝请求参数
     *
     * @return
     */
    @POST("credit/rpt/createOrder/alipay")
    @FormUrlEncoded
    fun ALPLAY_URL(@Field("idNo") idNo: String, @Field("name") name: String, @Field("payMode") payMode: Int, @Field("phone") phone: String, @Field("token") token: String): Observable<BaseEntity<PayParamsEntity>>

    /**
     * 查询征信报告
     *
     * @return
     */
    @POST("credit/rpt/checkExists")
    @FormUrlEncoded
    fun QURY_CREDIT(@Field("token") token: String?): Observable<BaseEntity<QueryCreditEntity>>

    /**
     * 根据类型查询贷款列表
     *
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @POST("appProduct/queryByCategory")
    @FormUrlEncoded
    fun QURY_LOAN_BY_TYPE(@Field("categoryId") categoryId: Int, @Field("pageNum") pageNum: Int, @Field("pageSize") pageSize: Int): Observable<BaseEntity<ArrayList<TypeLoanEntity>>>

    /**
     * 提交全部联系人
     */
    @POST("access/contacts/phoneBook/save")
    @FormUrlEncoded
    fun PUT_ALL_CONTACTS(@Field("data") data: String, @Field("token") token: String): Observable<BaseEntity<String>>

    /**
     *工作信息图片
     */
    @POST("access/work/imgUrl")
    fun PUT_WORK_INFO_IMG(@Body body: MultipartBody): Observable<BaseEntity<String>>

    /**
     * 提交工作信息
     */
    @POST("access/work/save")
    @FormUrlEncoded
    fun PUT_WORK_INFO(@FieldMap map: ArrayMap<String, Any>): Observable<BaseEntity<String>>

    /**
     * 保存联系人
     */
    @POST("access/contacts/linkMan/save")
    @FormUrlEncoded
    fun PUT_EMERGENCY_CONTACT(@Field("data") data: String, @Field("token") token: String): Observable<BaseEntity<String>>

    /**
     *金融信息图片
     */
    @POST("access/financial/imgUrl")
    fun PUT_FINANCE_IMG(@Body body: MultipartBody): Observable<BaseEntity<String>>

    /**
     * 提交金融信息
     */
    @POST("access/financial/save")
    @FormUrlEncoded
    fun PUT_FINANCE_INFO(@Field("appUserId") appUserId: Int, @Field("imgIdHeld") imgIdHeld: String?, @Field("imgLoanRecord") imgLoanRecord: String?, @Field("imgRepayRecord") imgRepayRecord: String?): Observable<BaseEntity<String>>

    /**
     *获取下一步认证
     */
    @POST("access/step/nextStep")
    @FormUrlEncoded
    fun NEXT_AUTH(@Field("token") token: String): Observable<BaseEntity<NextStepEntity>>

    /**
     * 当前状态
     */
    @POST("app/user/page/state")
    @FormUrlEncoded
    fun STATUS(@Field("userId") userId: Int): Observable<BaseEntity<Int>>

    /**
     *  保存运营商
     */
    @POST("access/operator/save")
    @FormUrlEncoded
    fun POST_OPERATOR(@Field("token") token: String): Observable<BaseEntity<String>>

    /**
     * 发帖-标签列表
     *
     * @return
     */
    @GET("postTag/getAll")
    abstract fun TAG_LIST(): Observable<BaseEntity<List<CardTagEntity>>>

    /**
     * 发布帖子
     *
     *
     * 多文件和参数一起上传
     *
     * @return
     */
    @POST("post/add")
    @Multipart
    abstract fun POST_CARD(@PartMap body: ArrayMap<String, RequestBody>): Observable<BaseEntity<String>>

    /**
     * 帖子列表
     *
     * @param params
     * @return
     */
    @POST("post/queryListbyCon")
    @FormUrlEncoded
    abstract fun CARD_LIST(@FieldMap params: ArrayMap<String, Any>): Observable<BaseEntity<ArrayList<CardListEntity>>>

    /**
     * 帖子详情
     *
     * @param id
     * @return
     */
    @POST("post/queryById")
    @FormUrlEncoded
    abstract fun CARD_DETAIL(@Field("id") id: Int): Observable<BaseEntity<CardDetailEntity>>

    /**
     * 查看帖子评论
     */
    @POST("postComment/selectByPostId")
    @FormUrlEncoded
    abstract fun CARD_COMMENT_LIST(@Field("pageNum") pageNum: Int, @Field("pageSize") pageSize: Int, @Field("postId") postId: Int): Observable<BaseEntity<ArrayList<CommentEntity>>>

    /**
     * 发表评论
     */
    @POST("postComment/add")
    @FormUrlEncoded
    abstract fun SEND_COMMENT(@Field("content") content: String, @Field("postId") postId: Int, @Field("token") token: String): Observable<BaseEntity<String>>

    /**
     * 银行列表
     */
    @GET("access/bindCard/supportBankList")
    fun BANK_LIST(): Observable<BaseEntity<ArrayList<BandCardListEntity>>>

    /**
     * 绑定银行卡
     */
    @POST("access/bindCard")
    @FormUrlEncoded
    fun BIND_BAND(@FieldMap params: ArrayMap<String, Any>): Observable<BaseEntity<BandCardEntity>>

    /**
     * 绑卡通过支付宝
     */
    @POST("access/authFee/payFee")
    @FormUrlEncoded
    fun BIND_CARD_BY_ALIPAY(@Field("payMode") payMode: Int, @Field("token") token: String): Observable<BaseEntity<PayBindCardEntity>>

    /**
     * 绑卡通过银行卡
     */
    @POST("access/authFee/payFee")
    @FormUrlEncoded
    fun BIND_CARD_BY_BANK(@Field("payMode") payMode: Int, @Field("token") token: String): Observable<BaseEntity<String>>

    /**
     * 绑卡验证码
     */
    @POST("access/authFee/bandCard/payAuthFeeSmsConfirm")
    @FormUrlEncoded
    fun CONFIRM_BIND_CARD_SMS(@FieldMap params: ArrayMap<String, Any>): Observable<BaseEntity<String>>

    /**
     * 身份证认证
     */
    @POST("access/identity/save")
    @FormUrlEncoded
    fun ID_CARD_AUTHEN(@Field("token") token: String, @Field("udCreditJson") udCreditJson: String): Observable<BaseEntity<String>>

    /**
     * 绑卡确认短信
     */
    @POST("access/bindCard/confirm")
    @FormUrlEncoded
    fun CONFIRM_SMS(@FieldMap params: ArrayMap<String, String>): Observable<BaseEntity<String>>

    /**
     * 获取绑卡费用
     */
    @GET("access/authFee/fee")
    fun BIND_CARD_COST(): Observable<BaseEntity<String>>
}
