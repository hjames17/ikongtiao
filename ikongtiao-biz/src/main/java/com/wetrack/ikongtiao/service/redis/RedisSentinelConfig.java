package com.wetrack.ikongtiao.service.redis;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by zhangsong on 15/11/16.
 */
@Service
public class RedisSentinelConfig {

	private RedisSentinelConfiguration config;

	@Value("${redis.sentinel}")
	private String sentinelStr;

	@Value("${redis.master}")
	private String master;

	@Bean(name = "redisSentinelConfiguration")
	public RedisSentinelConfiguration getConfig() {
		config = new RedisSentinelConfiguration();
		config.setMaster(master);

		if (StringUtils.isBlank(sentinelStr)) {
			return null;
		}

		String[] strs = sentinelStr.split(",");
		Set<RedisNode> sentinels = Sets.newHashSet();

		for (String str : strs) {
			String[] nodeStr = str.split(":");
			RedisNode rn = new RedisNode(nodeStr[0], Integer.valueOf(nodeStr[1]));
			sentinels.add(rn);
		}
		config.setSentinels(sentinels);
		return config;
	}
}
