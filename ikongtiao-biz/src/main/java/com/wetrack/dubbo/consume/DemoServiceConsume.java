package com.wetrack.dubbo.consume;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wetrack.dubbo.provider.DemoDto;
import com.wetrack.dubbo.provider.DemoResult;
import com.wetrack.dubbo.provider.DemoService;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 16/3/12.
 */
@Service("demoServiceConsume")
public class DemoServiceConsume {

	@Reference
	private DemoService demoService;

	public void handle() {
		DemoDto demoDto = new DemoDto();
		demoDto.setAge(12);
		demoDto.setName("xiazhou");
		DemoResult result = demoService.execute(demoDto);
		System.out.println(result.getData());
	}

}

