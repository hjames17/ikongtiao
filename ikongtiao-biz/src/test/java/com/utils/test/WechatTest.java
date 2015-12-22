package com.utils.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhangsong on 15/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = { "classpath*:spring/*.xml" }
)
public class WechatTest {

	@Test
	void test(){}
	

//	@Resource
//	private WechatMediaService wechatMediaService;
//
//	@Resource
//	private WechatMaterialService wechatMaterialService;
//
//	@Resource
//	private WechatMenuService wechatMenuService;
//
//	@Resource
//	private WechatMessageService wechatMessageService;
//
//	@Resource
//	private WechatQrcodeService wechatQrcodeService;
//
//
//	@Resource
//	private WechatUserService wechatUserService;
//
//	@Test
//	public void testMediaUpload() {
//		File file = new File("/Users/zhangsong/Pictures/avatar/weimei_de_xingfu_qinglv.jpg");
//		WechatMedia wechatMedia = wechatMediaService.mediaUpload(MediaType.image, file);
//		System.out.println(wechatMedia.getMedia_id());
//	}
//	//http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=
//	// zVS3PYqGeEXmczIXFWJSdZPpYBSFceqAOQLs8BzB9L8NgWDWWTVtBYtqboGUCghX73V22Qh2GAsTB_vozCFICm-sdT1sWFao7uy0Lo9PNdsWMDhACAAQH&
//	// media_id=UaIqMxzOFjmuqZ1cfXR1_j7NCyMnSmUqBwnLlYygo_hFktOTLEl9jFd4h5tyahkb
//
//	@Test
//	public void testMediaGet() {
//		byte[] bytes = wechatMediaService.mediaGet("UaIqMxzOFjmuqZ1cfXR1_j7NCyMnSmUqBwnLlYygo_hFktOTLEl9jFd4h5tyahkb");
//		File file = new File("/Users/zhangsong/Pictures/avatar/qinglv.jpg");
//		try {
//			FileOutputStream outputStream = new FileOutputStream(file);
//			outputStream.write(bytes);
//			outputStream.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testMediaImgUpload() {
//		//EuKJhkgRVpMAQlcRsSYy3jljw-6jmRihTCM9hZ4LHZCEm5xiazhouCGYhErJ9YSkZ5Gk
//		//AuXiWWKJ9LsdCqqXhTRVmI3D2mz4jtoZ0oJ_CMyLrqdC4Dc6L4N8dHa3JWxOWKzW
//		//b7DuREUZnBeR15jNraTf9mCrO-h_LcvsL28gHKQQErGKhiU3g_dQCKUnp0VqYMPT
//		//RBamhi6yc_Z8qQ9jY0yUq2xCtKOayUDO9jz4fl05edu0CjIEtJCTniOn6aNb_XZb
//		File file = new File("/Users/zhangsong/Pictures/avatar/qinglv.jpg");
//		WechatMedia wechatMedia = wechatMediaService.mediaUploadimg(file);
//		System.out.println(wechatMedia.getMedia_id());
//	}
//
//	@Test
//	public void testMaterial() {
//		List<WechatArticle> articleList = new ArrayList<>();
//		WechatArticle wechatArticle = new WechatArticle();
//		wechatArticle.setContent("Hello 夏舟");
//		wechatArticle.setAuthor("夏舟");
//		wechatArticle.setShow_cover_pic(1);
//		wechatArticle.setDigest("xiahengzhou");
//		wechatArticle.setThumb_media_id("UaIqMxzOFjmuqZ1cfXR1_j7NCyMnSmUqBwnLlYygo_hFktOTLEl9jFd4h5tyahkb");
//		wechatArticle.setTitle("Nic");
//		articleList.add(wechatArticle);
//
//		WechatArticle wechatArticle1 = new WechatArticle();
//		BeanUtils.copyProperties(wechatArticle, wechatArticle1);
//		wechatArticle1.setTitle("Skyfire");
//		//articleList.add(wechatArticle1);
//		WechatMedia wechatMedia = wechatMaterialService.addMaterialNews(articleList);
//		System.out.println("wecahtData");
//	}
//
//	@Test
//	public void testMaterialCount() {
//		WechatMaterialCountResult we = wechatMaterialService.getMaterialCount();
//		System.out.println(we.getImage_count());
//	}
//
//	@Test
//	public void testCreateMenu() {
//		WechatMenuButtons wechatMenuButtons = new WechatMenuButtons();
//		WechatMenuButtons.WechatButton wechatButton = new WechatMenuButtons.WechatButton();
//		wechatButton.setName("夏舟");
//		wechatButton.setType("view");
//		wechatButton.setUrl("https://www.baidu.com/");
//		wechatButton.setKey("xiazhou");
//		WechatMenuButtons.WechatButton[] wechatButtons = new WechatMenuButtons.WechatButton[1];
//		wechatButtons[0] = wechatButton;
//		wechatMenuButtons.setButton(wechatButtons);
//		WechatBaseResult result = wechatMenuService.createMenu(wechatMenuButtons);
//		System.out.println("");
//		System.out.println("");
//	}
//
//	@Test
//	public void testDeleteMenu() {
//		WechatBaseResult result = wechatMenuService.delMenu();
//		System.out.println("");
//		System.out.println("");
//	}
//
//	@Test
//	public void testSendCustomMessage() {
//		WechatCustomerMessage message = new WechatCustomerMessage();
//		message.setTouser("oqwSFuIR1im7SeMCd0vYMNnBhu1E");
//		message.setMsgtype(WechatMessageType.TEXT.getCode());
//		WechatText wechatText = new WechatText();
//		wechatText.setContent("天下起雨了，我又开始想你了");
//		message.setText(wechatText);
//		WechatBaseResult result = wechatMessageService.sendCustomMessage(message);
//
//		System.out.println("");
//	}
//
//	@Test
//	public void testMassMessage() {
//		WechatMassMessage wechatMassMessage = new WechatMassMessage();
//		wechatMassMessage.setMsgtype(WechatMessageType.TEXT.getCode());
//		Set<String> toUsers = new HashSet<>();
//		toUsers.add("oqwSFuIR1im7SeMCd0vYMNnBhu1E");
//		toUsers.add("oqwSFuM77JhrProdvSqVDrhVSjes");
//		wechatMassMessage.setTouser(toUsers);
//		WechatMassText text = new WechatMassText();
//		text.setContent("测试按照openId群发消息");
//		wechatMassMessage.setText(text);
//		WechatMassMessageSendResult result = wechatMessageService.sendMassMessageByOpenId(wechatMassMessage);
//		System.out.println(result.getMsg_id());
//	}
//
//	@Test
//	public void testTempleteMessage() {
//		System.out.println(Jackson.base().writeValueAsString(wechatUserService.getUserInfo("oqwSFuM77JhrProdvSqVDrhVSjes")));
//	}
//
//	@Test
//	public void testCreateQrcode() {
//		//http://weixin.qq.com/q/g0P9cnrl8AKJuZhF0m1v
//		// gQHZ7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2cwUDljbnJsOEFLSnVaaEYwbTF2AAIEakJRVgMEgDoJAA==
//		WechatQrcodeTicket ticket = wechatQrcodeService.createQrcode(604800, QrcodeType.QR_SCENE.getCode(), 1);
//		System.out.println(ticket.getTicket());
//	}
//
//	@Test
//	public void testGetQrcode() {
//		try {
//			wechatQrcodeService.showQrcode(
//					"gQHZ7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2cwUDljbnJsOEFLSnVaaEYwbTF2AAIEakJRVgMEgDoJAA==");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
