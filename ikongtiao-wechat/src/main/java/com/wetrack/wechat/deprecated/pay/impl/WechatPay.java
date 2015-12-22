package com.wetrack.wechat.deprecated.pay.impl;

import com.thoughtworks.xstream.XStream;
import com.wetrack.base.result.AjaxException;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.wechat.deprecated.pay.api.CommonPay;
import com.wetrack.wechat.deprecated.pay.config.WechatPayConfig;
import com.wetrack.wechat.deprecated.pay.constans.PayPlatForm;
import com.wetrack.wechat.deprecated.pay.constans.RefundStatusEnum;
import com.wetrack.wechat.deprecated.pay.dto.*;
import com.wetrack.wechat.deprecated.pay.req.*;
import com.wetrack.wechat.deprecated.pay.resp.*;
import com.wetrack.wechat.deprecated.pay.utils.WechatUtil;
import com.wetrack.wechat.deprecated.pay.utils.client.TenpayHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangsong on 15/11/24.
 */
public class WechatPay implements CommonPay {

	private final static Logger LOGGER = LoggerFactory.getLogger(WechatPay.class);
	/**
	 * 双下划线格式
	 */
	private static final Pattern PATTERN_DOUBLE_UNDERSCORE = Pattern.compile("__", Pattern.LITERAL);

	/**
	 * 换行符格式
	 */
	private static final Pattern PATTERN_RETURN = Pattern.compile("\r\n");

	/**
	 * 日期格式
	 */
	private static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 商户统一下单接口链接
	 */
	private static final String MCH_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 商户查询订单接口链接
	 */
	private static final String MCH_QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	/**
	 * 商户申请退款接口链接
	 */
	private static final String MCH_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	/**
	 * 商户查询退款接口链接
	 */
	private static final String MCH_QUERY_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

	/**
	 * 日期格式
	 */
	private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 预支付请求XStream
	 */
	private static final XStream prepayReqXStream = new XStream();

	/**
	 * 预支付响应XStream
	 */
	private static final XStream prepayRespXStream = new XStream();

	/**
	 * 支付回调请求XStream
	 */
	private static final XStream payNotifyXStream = new XStream();

	/**
	 * 查询订单请求XStream
	 */
	private static final XStream queryOrderReqXStream = new XStream();

	/**
	 * 查询订单响应XStream
	 */
	private static final XStream queryOrderRespXStream = new XStream();

	/**
	 * 退款请求XStream
	 */
	private static final XStream refundReqXStream = new XStream();

	/**
	 * 退款响应XStream
	 */
	private static final XStream refundRespXStream = new XStream();

	/**
	 * 查询退款请求XStream
	 */
	private static final XStream queryRefundReqXStream = new XStream();

	/**
	 * 查询退款响应XStream
	 */
	private static final XStream queryRefundRespXStream = new XStream();

	/**
	 * 微信支付配置
	 */
	private WechatPayConfig wechatPayConfig;

	static {
		prepayReqXStream.processAnnotations(WechatPrepayReq.class);
		prepayReqXStream.setMode(XStream.NO_REFERENCES);

		prepayRespXStream.processAnnotations(WechatPrepayResult.class);
		prepayRespXStream.setMode(XStream.NO_REFERENCES);

		payNotifyXStream.processAnnotations(WechatPayNotifyReq.class);
		payNotifyXStream.setMode(XStream.NO_REFERENCES);

		queryOrderReqXStream.processAnnotations(WechatQueryOrderReq.class);
		queryOrderReqXStream.setMode(XStream.NO_REFERENCES);

		queryOrderRespXStream.processAnnotations(WechatQueryOrderResp.class);
		queryOrderRespXStream.setMode(XStream.NO_REFERENCES);

		refundReqXStream.processAnnotations(WechatRefundReq.class);
		refundReqXStream.setMode(XStream.NO_REFERENCES);

		refundRespXStream.processAnnotations(WechatRefundResp.class);
		refundRespXStream.setMode(XStream.NO_REFERENCES);

		queryRefundReqXStream.processAnnotations(WechatQueryRefundReq.class);
		queryRefundReqXStream.setMode(XStream.NO_REFERENCES);

		queryRefundRespXStream.processAnnotations(WechatQueryRefundResp.class);
		queryRefundRespXStream.setMode(XStream.NO_REFERENCES);
	}

	@Override public <T> PrepayDto<T> prePay(PrepayParam param) throws Exception {
		PrepayDto<WechatPrepayResp> prepayDto = new PrepayDto<WechatPrepayResp>();
		WechatPrepayResp resp = new WechatPrepayResp();
		String outTradeNo = param.getOutTradeNo();

		String timeStamp = WechatUtil.create_timestamp(); // 时间戳
		String nonceStr = WechatUtil.create_md5_nonce_str(timeStamp); // 32位内的防重发随机串:对时间戳进行MD5生成

		WechatPrepayReq req = new WechatPrepayReq();
		// 公众账号ID
		req.setAppid(wechatPayConfig.getAppId());
		// 商户号 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		req.setMch_id(wechatPayConfig.getPartner());
		// 随机字符串
		req.setNonce_str(nonceStr);
		// 商品描述
		req.setBody(param.getProductName());
		// 商户订单号
		req.setOut_trade_no(outTradeNo);
		// 货币类型 符合ISO 4217标准的三位字母代码，默认人民币：CNY
		req.setFee_type("CNY");
		// 总金额 订单总金额，只能为整数
		req.setTotal_fee(param.getTotalFee());
		// 终端IP APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
		req.setSpbill_create_ip(param.getClientIp());
		// 交易起始时间 订单生成时间，格式为yyyyMMddHHmmss 非必填
		if (param.getOrderAddTime() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
			String timeStart = sdf.format(param.getOrderAddTime());
			req.setTime_start(timeStart);
		}
		// 通知地址 接收微信支付异步通知回调地址
		req.setNotify_url(wechatPayConfig.getPayNotifyUrl());
		// 交易类型 取值如下：JSAPI，NATIVE，APP，WAP
		req.setTrade_type("APP");
		// 风控字段
		String risk_info = "line_type=on&goods_type=vir&goods_class=04&deliver=unlog&seller_type=mer";
		req.setRisk_info(risk_info);

		// 生成预支付签名，要采用URLENCODER的原始值进行MD5算法！
		String sign = WechatUtil.md5Digest(req, wechatPayConfig.getPartnerKey());

		// 增加非参与签名的额外参数
		req.setSign(sign);

		// 转化为XML字符串
		String xmlTemp = prepayReqXStream.toXML(req);
		String reqXml = PATTERN_DOUBLE_UNDERSCORE.matcher(xmlTemp).replaceAll(Matcher.quoteReplacement("_"));
		LOGGER.info("Wx prepay req:{}", reqXml);

		// 调微信统一下单接口
		byte[] reqBytes = reqXml.getBytes(WechatPayConfig.CHARSET);
		HttpEntity entity = new ByteArrayEntity(reqBytes);
		String respXml = Utils.get(HttpExecutor.class).post(MCH_UNIFIED_ORDER_URL).setEntity(entity)
		                      .executeAsString();
		LOGGER.info("Wx prepay resp:{}", respXml);

		// 解析结果
		WechatPrepayResult result = (WechatPrepayResult) prepayRespXStream.fromXML(respXml);

		// 通信失败
		if ("FAIL".equals(result.getReturn_code())) {
			throw new AjaxException("WEIXINPAY_GET_PREPAY_ID_FAILED", "文本");
		}

		// 业务失败
		if ("FAIL".equals(result.getResult_code())) {
			throw new AjaxException(result.getErr_code(), "");
		}

		// 验签
		boolean isSignPassed = WechatUtil.verifyMd5Sign(result, wechatPayConfig.getPartnerKey(), result.getSign());
		if (isSignPassed) {
			String prepayId = result.getPrepay_id();

			String timeStamp2 = WechatUtil.create_timestamp(); // 时间戳
			String nonceStr2 = WechatUtil.create_md5_nonce_str(timeStamp2); // 32位内的防重发随机串:对时间戳进行MD5生成

			// 构造支付签名参数
			SortedMap<String, String> prePayParams = new TreeMap<String, String>();
			prePayParams.put("appid", wechatPayConfig.getAppId());
			prePayParams.put("partnerid", wechatPayConfig.getPartner());
			prePayParams.put("prepayid", prepayId);
			prePayParams.put("package", "Sign=WXPay");
			prePayParams.put("noncestr", nonceStr2);
			prePayParams.put("timestamp", timeStamp2);

			// 生成支付签名，要采用URLENCODER的原始值进行MD5算法！
			String paySign = WechatUtil.md5Digest(prePayParams, wechatPayConfig.getPartnerKey(), true);

			resp.setNonceStr(nonceStr2);
			resp.setPartnerId(wechatPayConfig.getPartner());
			resp.setPrepayId(prepayId);
			resp.setSign(paySign);
			resp.setTimeStamp(timeStamp2);
			LOGGER.info("invoke wxpay:outTradeNo={},nonceStr={},prepayId={},paySign={},timeStamp={}", outTradeNo,
					nonceStr2, prepayId, paySign,
					timeStamp2);

			prepayDto.setOrderId(param.getOrderId());
			prepayDto.setOrderPaymentMethod(PayPlatForm.WechatPay.getCode());
			prepayDto.setOutTradeNo(outTradeNo);
			prepayDto.setResp(resp);
			return (PrepayDto<T>) prepayDto;
		}
		throw new RuntimeException("验签失败");
	}

	@Override public PayOrderResp payNotify(HttpServletRequest request) throws Exception {
		PayOrderResp resp = new PayOrderResp();

		// 获取输入流
		InputStream inStream = request.getInputStream();

		// 解析为对象
		WechatPayNotifyReq wechatPayNotify = (WechatPayNotifyReq) payNotifyXStream.fromXML(inStream);
		String reqXml = payNotifyXStream.toXML(wechatPayNotify);
		LOGGER.info("wechat notify:{}", PATTERN_RETURN.matcher(reqXml).replaceAll(Matcher.quoteReplacement("")));

		boolean signPassed = WechatUtil.verifyMd5Sign(wechatPayNotify, wechatPayConfig.getPartnerKey(),
				wechatPayNotify.getSign()); // 验签
		resp.setSignPassed(signPassed);
		if ("SUCCESS".equals(wechatPayNotify.getResult_code())) {
			resp.setCode(0); // 成功
		} else {
			resp.setCode(1); // 失败
		}
		resp.setOutTradeNo(wechatPayNotify.getOut_trade_no()); // 商户订单号
		resp.setTradeNo(wechatPayNotify.getTransaction_id()); // 微信支付订单号
		resp.setAmount(wechatPayNotify.getTotal_fee());
		String timeEnd = wechatPayNotify.getTime_end(); // 支付完成时间
		if (timeEnd != null) {
			Date orderPaidTime = null; // 订单支付时间
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
			try {
				orderPaidTime = sdf.parse(wechatPayNotify.getTime_end());
			} catch (ParseException e) {
				orderPaidTime = new Date();
			}
			resp.setOrderPaidTime(orderPaidTime);
		}
		return resp;
	}

	@Override public PayOrderResp queryPayOrder(QueryOrderParam param) throws Exception {
		PayOrderResp orderResp = new PayOrderResp();

		// 请求对象
		WechatQueryOrderReq req = new WechatQueryOrderReq();
		// 公众账号ID
		req.setAppid(wechatPayConfig.getAppId());
		// 商户号
		req.setMch_id(wechatPayConfig.getPartner());
		// 微信订单号，优先使用
		req.setTransaction_id(param.getTradeNo());
		// 商户订单号 商户系统内部的订单号，当没提供transaction_id时需要传这个
		req.setOut_trade_no(param.getOutTradeNo());
		// 随机字符串，不长于32位
		String timeStamp = WechatUtil.create_timestamp(); // 时间戳
		String nonceStr = WechatUtil.create_md5_nonce_str(timeStamp); // 32位内的防重发随机串:对时间戳进行MD5生成
		req.setNonce_str(nonceStr);

		// MD5签名
		String sign = WechatUtil.md5Digest(req, wechatPayConfig.getPartnerKey());
		req.setSign(sign);

		// 转化为XML字符串
		String xmlTemp = queryOrderReqXStream.toXML(req);
		String reqXml = PATTERN_DOUBLE_UNDERSCORE.matcher(xmlTemp).replaceAll(Matcher.quoteReplacement("_"));

		// 调微信查询订单接口
		byte[] reqBytes = reqXml.getBytes(WechatPayConfig.CHARSET);
		HttpEntity entity = new ByteArrayEntity(reqBytes);
		String respXml = Utils.get(HttpExecutor.class).post(MCH_QUERY_ORDER_URL).setEntity(entity)
		                      .executeAsString();
		LOGGER.debug("query wx resp:{}", respXml);

		// 解析结果
		WechatQueryOrderResp result = (WechatQueryOrderResp) queryOrderRespXStream.fromXML(respXml);

		// 通信失败
		if ("FAIL".equals(result.getReturn_code())) {
			orderResp.setCode(1);
			orderResp.setMsg(result.getReturn_msg());
			return orderResp;
		}

		// 验签
		boolean signPassed = WechatUtil.verifyMd5Sign(result, wechatPayConfig.getPartnerKey(), result.getSign());

		// 交易结果
		orderResp.setSignPassed(signPassed);
		if ("SUCCESS".equals(result.getResult_code())) {
			String tradeState = result.getTrade_state(); // 交易状态
			if ("SUCCESS".equals(tradeState)) {
				orderResp.setCode(0); // 成功
				orderResp.setAmount(result.getTotal_fee());
				orderResp.setOutTradeNo(result.getOut_trade_no());
				orderResp.setTradeNo(result.getTransaction_id());
				String timeEnd = result.getTime_end(); // 支付完成时间
				if (timeEnd != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
					Date orderPaidTime = null; // 订单支付时间
					try {
						orderPaidTime = sdf.parse(timeEnd);
					} catch (ParseException e) {
						orderPaidTime = new Date();
					}
					orderResp.setOrderPaidTime(orderPaidTime);
				}
			} else if ("REFUND".equals(tradeState)) {
				orderResp.setCode(4); // 转入退款
			} else if ("NOTPAY".equals(tradeState)) {
				orderResp.setCode(1); // 未支付
			} else if ("CLOSED".equals(tradeState)) {
				orderResp.setCode(7); // 已关闭
			} else if ("REVOKED".equals(tradeState)) {
				orderResp.setCode(8); // 已撤销
			} else if ("USERPAYING".equals(tradeState)) {
				orderResp.setCode(5); // 用户支付中
			} else if ("PAYERROR".equals(tradeState)) {
				orderResp.setCode(6); // 支付失败
			}
		} else {
			orderResp.setCode(1); // 失败
			orderResp.setMsg(result.getErr_code_des());
		}

		return orderResp;
	}

	@Override public <T> PreRefundDto<T> refund(RefundOrderParam param) throws Exception {
		PreRefundDto<WechatPreRefundResp> preRefundDto = new PreRefundDto<WechatPreRefundResp>();
		WechatPreRefundResp resp = new WechatPreRefundResp();

		// 请求对象
		WechatRefundReq req = new WechatRefundReq();

		// 公众账号ID
		req.setAppid(wechatPayConfig.getAppId());
		// 商户号
		req.setMch_id(wechatPayConfig.getPartner());
		// 随机字符串，不长于32位
		String timeStamp = WechatUtil.create_timestamp(); // 时间戳
		String nonceStr = WechatUtil.create_md5_nonce_str(timeStamp); // 32位内的防重发随机串:对时间戳进行MD5生成
		req.setNonce_str(nonceStr);
		// 微信订单号，优先使用
		req.setTransaction_id(param.getTradeNo());
		// 商户订单号 商户系统内部的订单号，当没提供transaction_id时需要传这个
		req.setOut_trade_no(param.getOutTradeNo());
		// 商户退款单号 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		req.setOut_refund_no(param.getOutRefundNo());
		// 总金额 订单总金额，单位为分，只能为整数
		req.setTotal_fee(param.getTotalFee());
		// 退款金额 退款总金额,单位为分,只能为整数
		req.setRefund_fee(param.getRefundFee());
		// 货币种类 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY 可空
		req.setRefund_fee_type("CNY");
		// 操作员 操作员帐号, 默认为商户号
		req.setOp_user_id(wechatPayConfig.getPartner());

		// MD5签名
		String sign = WechatUtil.md5Digest(req, wechatPayConfig.getPartnerKey());
		req.setSign(sign);

		// 转化为XML字符串
		String xmlTemp = refundReqXStream.toXML(req);
		String reqXml = PATTERN_DOUBLE_UNDERSCORE.matcher(xmlTemp).replaceAll(Matcher.quoteReplacement("_"));

		// 调微信退款订单接口
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setCharset(WechatPayConfig.CHARSET);
		httpClient.setCaJksFile(wechatPayConfig.getCaJksFile());
		httpClient.setCaJksAlias(wechatPayConfig.getCaJksAlias());
		httpClient.setCaInfo(new File(wechatPayConfig.getCaCertFile()));
		httpClient.setCertInfo(new File(wechatPayConfig.getCertFile()),
				wechatPayConfig.getPartner());
		httpClient.setMethod("POST");
		String reqContent = new StringBuilder(MCH_REFUND_URL).append('?').append(reqXml).toString();
		httpClient.setReqContent(reqContent);
		httpClient.call();

		String respXml = httpClient.getResContent();
		LOGGER.info("Wx refund resp:{}", respXml);

		// 解析结果
		WechatRefundResp result = (WechatRefundResp) refundRespXStream.fromXML(respXml);

		preRefundDto.setOrderPaymentMethod(PayPlatForm.WechatPay.getCode());
		preRefundDto.setResp(resp);

		// 通信失败
		if ("FAIL".equals(result.getReturn_code())) {
			resp.setCode(1);
			resp.setMsg(result.getReturn_msg());
			return (PreRefundDto<T>) preRefundDto;
		}

		// 验签
		boolean signPassed = WechatUtil.verifyMd5Sign(result, wechatPayConfig.getPartnerKey(), result.getSign());

		// 申请退款结果
		resp.setSignPassed(signPassed);
		if ("SUCCESS".equals(result.getResult_code())) {
			resp.setCode(0); // 成功
			resp.setRefundId(result.getRefund_id());
			preRefundDto.setOutRefundNo(result.getOut_refund_no());
			preRefundDto.setOutTradeNo(result.getOut_trade_no());
			preRefundDto.setRefundFee(result.getRefund_fee());
			preRefundDto.setTotalFee(result.getTotal_fee());
		} else {
			resp.setCode(1); // 失败
			resp.setMsg(result.getErr_code_des());
		}

		return (PreRefundDto<T>) preRefundDto;
	}

	@Override public RefundOrderResp refundNotify(HttpServletRequest request) {
		return null;
	}

	@Override public QueryRefundOrderResp queryRefundOrder(QueryRefundParam param) throws Exception {
		QueryRefundOrderResp resp = new QueryRefundOrderResp();

		// 请求对象
		WechatQueryRefundReq req = new WechatQueryRefundReq();

		// 公众账号ID
		req.setAppid(wechatPayConfig.getAppId());
		// 商户号
		req.setMch_id(wechatPayConfig.getPartner());
		// 随机字符串，不长于32位
		String timeStamp = WechatUtil.create_timestamp(); // 时间戳
		String nonceStr = WechatUtil.create_md5_nonce_str(timeStamp); // 32位内的防重发随机串:对时间戳进行MD5生成
		req.setNonce_str(nonceStr);
		// 微信订单号
		req.setTransaction_id(param.getTradeNo());
		// 商户订单号
		req.setOut_trade_no(param.getOutTradeNo());
		// 商户退款单号
		req.setOut_refund_no(param.getOutRefundNo());
		// 微信退款单号
		req.setRefund_id(param.getRefundNo());

		// MD5签名
		String sign = WechatUtil.md5Digest(req, wechatPayConfig.getPartnerKey());
		req.setSign(sign);

		// 转化为XML字符串
		String xmlTemp = queryRefundReqXStream.toXML(req);
		String reqXml = PATTERN_DOUBLE_UNDERSCORE.matcher(xmlTemp).replaceAll(Matcher.quoteReplacement("_"));

		// 调微信查询退款接口
		byte[] reqBytes = reqXml.getBytes(WechatPayConfig.CHARSET);
		HttpEntity entity = new ByteArrayEntity(reqBytes);
		String respXml = Utils.get(HttpExecutor.class).post(MCH_QUERY_REFUND_URL).setEntity(entity)
		                      .executeAsString();
		LOGGER.debug("query wx refund resp:{}", respXml);

		// 解析查询结果
		WechatQueryRefundResp result = (WechatQueryRefundResp) queryRefundRespXStream.fromXML(respXml);

		// 通信失败
		if ("FAIL".equals(result.getReturn_code())) {
			resp.setCode(1);
			resp.setMsg(result.getReturn_msg());
			return resp;
		}

		// 验签
		boolean signPassed = WechatUtil.verifyMd5Sign(result, wechatPayConfig.getPartnerKey(), result.getSign());

		// 退款结果
		resp.setSignPassed(signPassed);
		if ("SUCCESS".equals(result.getResult_code())) {
			resp.setCode(0); // 成功
			resp.setRefund_fee(result.getRefund_fee());
			String refundStatus = result.getRefund_status_0();
			Integer refundState = null;
			if ("SUCCESS".equals(refundStatus)) { // 退款成功
				refundState = RefundStatusEnum.REFUND_SUCCESS.getCode();
			} else if ("FAIL".equals(refundStatus)) { // 退款失败
				refundState = RefundStatusEnum.REFUND_ERROR.getCode();
				resp.setMsg(result.getErr_code_des()); // 错误描述
			} else if ("PROCESSING".equals(refundStatus)) { // 退款处理中
				refundState = RefundStatusEnum.REFUNDING.getCode();
			} else if ("NOTSURE".equals(refundStatus)) { // 未确定
				refundState = RefundStatusEnum.UNKNOWN.getCode();
				resp.setMsg(result.getErr_code_des()); // 错误描述
			} else if ("CHANGE".equals(refundStatus)) { // 转入代发
				refundState = RefundStatusEnum.TO_SEND.getCode();
				resp.setMsg(result.getErr_code_des()); // 错误描述
			}
			resp.setRefund_state(refundState);
		} else {
			resp.setCode(1); // 失败
			resp.setMsg(result.getErr_code_des());
		}

		return resp;
	}

	public WechatPayConfig getWechatPayConfig() {
		return wechatPayConfig;
	}

	public void setWechatPayConfig(WechatPayConfig wechatPayConfig) {
		this.wechatPayConfig = wechatPayConfig;
	}
}
