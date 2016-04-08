package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.auth.service.TokenService;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by zhanghong on 16/1/4.
 */

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserInfoService userInfoService;
	@Autowired
	FixerService fixerService;
	@Autowired
	TokenService tokenService;

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) UserInfo info(
			@PathVariable(value = "id") String id) throws Exception {
		return userInfoService.getBasicInfoById(id);
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST) void updateInfo(
			@RequestBody UpdateForm updateForm) throws Exception {
		if (updateForm == null || updateForm.getId() == null) {
			throw new Exception("无效提交内容");
		}
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(updateForm, userInfo);
		userInfoService.update(userInfo);

	}

	// 登录需要提供用户名，密码，密码是md5加密过的
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	LoginOut login(@RequestBody LoginForm loginForm) throws Exception{
		Token token = userInfoService.login(loginForm.getEmail(), loginForm.getPassword());
		LoginOut out = new LoginOut();
		out.setToken(token.getToken());
		return out;
	}

	@SignTokenAuth
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	void login(@RequestBody LoginOut form, HttpServletRequest request) throws Exception{
		tokenService.logout(form.getToken());
	}



	/**
	 * TODO 请求发送绑定邮件
	 * @param userId 微信公众号注册的用户id
	 * @param email 集控系统的用户邮箱
	 */
	void getBindingCode(String userId, String email){

	}

	/**
	 * TODO
	 * 把通过微信公众号注册的用户和集控系统的用户绑定起来
	 * @param userId 用户id
	 * @param email 集控系统的用户邮箱
	 * @param code 集控系统的用户邮箱收到的绑定码
	 */
	void bind(String userId, String email, String code){

	}


	static class LoginOut {
		String token;
		String id;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}

	static class LoginForm{
		String email;
		String password;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	static class UpdateForm {
		String id;
		Integer type;
		String avatar;
		String organization;
		String accountEmail;
		String phone;
		String contacterName;
		String contacterPhone;
		private Integer provinceId;
		private Integer cityId;
		private Integer districtId;
		private String address;
		private BigDecimal latitude;
		private BigDecimal longitude;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization;
		}

		public String getAccountEmail() {
			return accountEmail;
		}

		public void setAccountEmail(String accountEmail) {
			this.accountEmail = accountEmail;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getContacterName() {
			return contacterName;
		}

		public void setContacterName(String contacterName) {
			this.contacterName = contacterName;
		}

		public String getContacterPhone() {
			return contacterPhone;
		}

		public void setContacterPhone(String contacterPhone) {
			this.contacterPhone = contacterPhone;
		}

		public Integer getProvinceId() {
			return provinceId;
		}

		public void setProvinceId(Integer provinceId) {
			this.provinceId = provinceId;
		}

		public Integer getCityId() {
			return cityId;
		}

		public void setCityId(Integer cityId) {
			this.cityId = cityId;
		}

		public Integer getDistrictId() {
			return districtId;
		}

		public void setDistrictId(Integer districtId) {
			this.districtId = districtId;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public BigDecimal getLatitude() {
			return latitude;
		}

		public void setLatitude(BigDecimal latitude) {
			this.latitude = latitude;
		}

		public BigDecimal getLongitude() {
			return longitude;
		}

		public void setLongitude(BigDecimal longitude) {
			this.longitude = longitude;
		}
	}

}
