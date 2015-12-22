package com.wetrack.ikongtiao.Utils;

import com.wetrack.base.container.ContainerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsong on 15/12/15.
 */
@Service
public class ApplicationContextConfig implements ApplicationContextAware{

	/**
	 * 初始化ContainerContext，并且设置ApplicationContext。
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ContainerContext.get().setContext(applicationContext);
	}
}
