package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import com.wetrack.verification.VerificationCodeService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.service.TokenService;

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

	@Autowired
	VerificationCodeService verificationCodeService;

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
		if(StringUtils.isEmpty(loginForm.getAccount())){
			throw new BusinessException("请输入帐号");
		}
		Token token = userInfoService.login(loginForm.getAccount(), loginForm.getPassword());
		LoginOut out = new LoginOut();
		out.setToken(token.getToken());
		out.setId(token.getUser().getId().substring(Constants.TOKEN_ID_PREFIX_CUSTOMER.length()));
		return out;
	}

	@Autowired
	UserInfoRepo userInfoRepo;
	//需要提供用户名，密码，密码是md5加密过的
	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	UserInfo signup(@RequestBody UserInfo userInfo) throws Exception{
		if(StringUtils.isEmpty(userInfo.getAccountEmail()) && StringUtils.isEmpty(userInfo.getContacterPhone())){
			throw new BusinessException("请输入帐号邮箱或者联系手机");
		}
		if(StringUtils.isEmpty(userInfo.getPassword())){
			throw new BusinessException("请输入密码");
		}
		if(!StringUtils.isEmpty(userInfo.getAccountEmail())){
			if(userInfoRepo.findByAccountEmail(userInfo.getAccountEmail()) != null){
				throw new BusinessException("注册邮箱已存在");
			}
		}
		if(!StringUtils.isEmpty(userInfo.getContacterPhone())){
			if(userInfoService.findByOrganizationOrContacterPhone(null, userInfo.getContacterPhone()) != null){
				throw new BusinessException("注册手机已存在");
			}
		}

		userInfo = userInfoService.create(userInfo);
		return userInfo;
	}

	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	void reset(@RequestBody ResetForm form) throws Exception{
		if(!verificationCodeService.verifyCode(form.getContacterPhone(), form.getCode())){
			throw new BusinessException("手机验证码无效");
		}
		if(StringUtils.isEmpty(form.getPassword())){
			throw new BusinessException("请设置一个密码");
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setContacterPhone(form.getContacterPhone());
		userInfo.setPassword(form.getPassword());
		userInfoService.update(userInfo);
	}

	@SignTokenAuth
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	void login(@RequestBody LoginOut form, HttpServletRequest request) throws Exception{
		tokenService.logout(form.getToken());
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
		String account;
		String password;

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {

			this.password = password;
		}
	}

	static class ResetForm{
		String contacterPhone;
		String code;
		String password;

		public String getContacterPhone() {
			return contacterPhone;
		}

		public void setContacterPhone(String contacterPhone) {
			this.contacterPhone = contacterPhone;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
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
