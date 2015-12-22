package com.wetrack.wechat.deprecated.pay.api;

import com.wetrack.wechat.deprecated.pay.resp.BatchPayResp;
import com.wetrack.wechat.deprecated.pay.dto.ProxyPayParam;
import com.wetrack.wechat.deprecated.pay.dto.QueryProxyPayParam;
import com.wetrack.wechat.deprecated.pay.resp.TenpayQueryResp;

/**
 * <pre>
 * 代付接口
 * 代付提交和代付查询接口
 *
 * Created by zhangsong on 15/11/24.
 */
public interface ProxyPay {
	/**
	 * <pre>
	 * 功能描述:
	 * 代付提交
	 * 样例
	 * bank_type = "1001";
	 * rec_bankacc = "6226095711196228";
	 * rec_name = "张三";
	 * acc_type = 1; // 账户类型(1为个人账户,2为公司账户)
	 * subbank_name = "招商银行杭州钱江支行";
	 * area = 12;
	 * city = 571;
	 * desc = "代付测试";
	 * pay_amt = 100;
	 * recv_mobile = "18688881688";
	 *
	 * @param param 代付参数
	 * @return 提交结果
	 * @throws Exception
	 */
	BatchPayResp batchPay(ProxyPayParam param) throws Exception;

	/**
	 * <pre>
	 * 功能描述:
	 * 代付查询
	 *
	 * @param param 代付查询参数
	 * @return 查询结果
	 * @throws Exception
	 */
	TenpayQueryResp queryPay(QueryProxyPayParam param) throws Exception;

}
