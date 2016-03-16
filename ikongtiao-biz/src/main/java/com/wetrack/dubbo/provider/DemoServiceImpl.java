package com.wetrack.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by zhangsong on 16/3/12.
 */
//@Service("demoService")
@Service
public class DemoServiceImpl implements DemoService {
	@Override public DemoResult execute(DemoDto dto) {
		String s = "名字：" + dto.getName() + "年龄:" + dto.getAge();
		System.out.println(s);
		DemoResult demoResult = new DemoResult();
		demoResult.setData(s);
		return demoResult;
	}
}
