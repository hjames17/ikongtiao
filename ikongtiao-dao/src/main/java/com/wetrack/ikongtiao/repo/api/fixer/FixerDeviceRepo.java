package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.FixerDevice;

/**
 * Created by zhangsong on 16/1/30.
 */
public interface FixerDeviceRepo {

	FixerDevice getFixerDeviceByFixerId(Integer fixerId);

	FixerDevice save(FixerDevice fixerDevice);

	int update(FixerDevice fixerDevice);
}
