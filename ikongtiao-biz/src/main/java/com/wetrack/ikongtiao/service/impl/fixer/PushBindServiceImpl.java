package com.wetrack.ikongtiao.service.impl.fixer;

import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import com.wetrack.ikongtiao.service.api.fixer.PushBindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 16/1/30.
 */
@Service("pushBindService")
public class PushBindServiceImpl implements PushBindService {

	@Resource
	private FixerDeviceRepo fixerDeviceRepo;
	@Override public FixerDevice save(FixerDevice fixerDevice) {
		FixerDevice temp = fixerDeviceRepo.getFixerDeviceByFixerId(fixerDevice.getFixerId());
		if(temp==null){
			fixerDeviceRepo.save(fixerDevice);
		}else{
			fixerDevice.setId(temp.getId());
			fixerDeviceRepo.update(fixerDevice);
		}
		return fixerDevice;
	}
}
