package com.wetrack.ikongtiao.repo.impl.im;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.domain.ImSessionCount;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import com.wetrack.ikongtiao.repo.api.im.dto.ImMessageUserParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhangsong on 16/3/9.
 */
@Repository("imSessionRepo")
public class ImSessionRepoImpl implements ImSessionRepo {
	@Resource
	private CommonDao commonDao;

	@Override public ImSession save(ImSession imSession) {
		commonDao.mapper(ImSession.class).sql("insertSelective").session().insert(imSession);
		return imSession;
	}

	@Override public Integer update(ImSession imSession) {
		return commonDao.mapper(ImSession.class).sql("updateByPrimaryKeySelective").session().update(imSession);
	}

	/*@Override public ImSession findSessionByMessageToAndMessageFrom(String messageTo, String messageForm) {
		Map<String, Object> param = new HashMap<>(2);
		param.put("messageTo", messageTo);
		param.put("messageFrom", messageForm);
		return commonDao.mapper(ImSession.class).sql("findSessionByMessageToAndMessageFrom").session().selectOne(param);
	}*/

	@Override public ImSession getImSessionBySessionId(Integer sessionId) {
		return commonDao.mapper(ImSession.class).sql("selectByPrimaryKey").session().selectOne(sessionId);
	}

	@Override public ImSession findSessionByParam(String messageFrom, String messageTo) {
		ImMessage imMessage = new ImMessage();
		imMessage.setMessageTo(messageTo);
		imMessage.setMessageFrom(messageFrom);
		return commonDao.mapper(ImSession.class).sql("findSessionByParam").session().selectOne(imMessage);
	}

	@Override public List<ImSession> listImSessionByMessage(ImMessage imMessage) {
		return commonDao.mapper(ImSession.class).sql("findSessionByMessage").session().selectList(imMessage);
	}

	@Override public PageList<ImSession> listImMessageUserByParam(ImMessageUserParam param) {
		PageList<ImSession> page = new PageList<>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		int totalSize = countMessageUserByCloudId(param);
		if (totalSize > 0) {
			page.setData(listMessageUserByCloudId(param));
		}
		return page;
	}

	@Override
	public List<ImSessionCount> countActiveSessionsForKefus(List<String> kefuCloudIdList) {
		List<ImSessionCount> list1 = commonDao.mapper(ImSession.class).sql("countSessionOfCloudIds").session().selectList(kefuCloudIdList);
		List<ImSessionCount> list2 = commonDao.mapper(ImSession.class).sql("countSessionOfToCloudIds").session().selectList(kefuCloudIdList);

		//合并两个列表的数量
		Map<String, ImSessionCount> map = new HashMap<String, ImSessionCount>();
		if(list1 != null && list1.size() > 0){
			for(ImSessionCount item : list1){
				map.put(item.getPeerId(), item);
			}
		}
		if(list2 != null && list2.size() > 0){
			for(ImSessionCount item : list2){
				ImSessionCount itemExist = map.get(item.getPeerId());
				if(itemExist != null){
					itemExist.setCount(itemExist.getCount() + item.getCount());
				}else{
					map.put(item.getPeerId(), item);
				}
			}
		}

		//如果有当前会话的客服个数少于全部的在线客服个数，则说明有些客服完全空闲，这时候把他们优先返回
		if(map.size() < kefuCloudIdList.size()){
			//随机找个一个即可
			for(String cloudId : kefuCloudIdList){
				if(map.get(cloudId) == null){
					ImSessionCount zeroCount = new ImSessionCount();
					zeroCount.setCount(0);
					zeroCount.setPeerId(cloudId);
					map.put(cloudId, zeroCount);
				}
			}
		}

		//排序
		Collection<ImSessionCount> collection = map.values();
		List<ImSessionCount> list = new ArrayList<ImSessionCount>(collection);
		list.sort(new Comparator<ImSessionCount>() {
			@Override
			public int compare(ImSessionCount o1, ImSessionCount o2) {
				return o1.getCount() - o2.getCount();
			}
		});

		return list;
	}

	private List<ImSession> listMessageUserByCloudId(ImMessageUserParam param) {
		return commonDao.mapper(ImSession.class).sql("listMessageUserByCloudId").session().selectList(param);
	}

	private int countMessageUserByCloudId(ImMessageUserParam param) {
		ImMessageUserParam result = commonDao.mapper(ImSession.class).sql("countMessageUserByCloudId").session()
		                                     .selectOne(param);
		if (result == null) {
			return 0;
		}
		return result.getTotalSize() == null ? 0 : result.getTotalSize();
	}

	public static void main(String[] args){
		List<Integer> list = Arrays.asList(4,2,3,1,222,9,5,6);
		list.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});

		for(Integer i : list){
			System.out.println(i);
		}
	}
}
