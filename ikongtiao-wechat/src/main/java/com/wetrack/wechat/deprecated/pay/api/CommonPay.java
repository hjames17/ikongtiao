package com.wetrack.wechat.deprecated.pay.api;

import com.wetrack.wechat.deprecated.pay.resp.PayOrderResp;
import com.wetrack.wechat.deprecated.pay.resp.QueryRefundOrderResp;
import com.wetrack.wechat.deprecated.pay.resp.RefundOrderResp;
import com.wetrack.wechat.deprecated.pay.dto.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 通用支付
 * 涵盖生成支付URL,支付回调,订单查询,退款请求,退款回调,退款查询等
 *
 * Created by zhangsong on 15/11/24.
 */
public interface CommonPay {
	/**
	 * <pre>
	 * 功能描述:
	 * 支付预处理:生成支付URL或提供给支付平台的数据
	 *
	 * @param param 预支付参数
	 * @return 支付URL或提供给支付平台的数据
	 * @throws Exception
	 */
	<T> PrepayDto<T> prePay(PrepayParam param) throws Exception;

	/**
	 * <pre>
	 * 功能描述:
	 * 支付回调验签
	 *
	 * @param request 支付回调请求
	 * @return 处理结果
	 */
	PayOrderResp payNotify(HttpServletRequest request) throws Exception;

	/**
	 * <pre>
	 * 功能描述:
	 * 查询支付订单
	 *
	 * @param param 查询支付订单参数
	 * @return 支付订单结果
	 * @throws Exception
	 */
	PayOrderResp queryPayOrder(QueryOrderParam param) throws Exception;

	/**
	 * <pre>
	 * 功能描述:
	 * 申请退款
	 *
	 * @param param 退款订单参数
	 * @return
	 */
	<T> PreRefundDto<T> refund(RefundOrderParam param) throws Exception;

	/**
	 * <pre>
	 * 功能描述:
	 * 退款回调
	 *
	 * @param request 退款回调请求
	 * @return 处理结果
	 */
	RefundOrderResp refundNotify(HttpServletRequest request);

	/**
	 * <pre>
	 * 功能描述:
	 * 〈功能详细描述〉
	 *
	 * @param param 查询退款请求
	 * @return 退款结果
	 * @throws Exception
	 */
	QueryRefundOrderResp queryRefundOrder(QueryRefundParam param) throws Exception;
}
