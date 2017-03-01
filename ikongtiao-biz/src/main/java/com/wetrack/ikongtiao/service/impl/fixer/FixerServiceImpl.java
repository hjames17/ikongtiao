package com.wetrack.ikongtiao.service.impl.fixer;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.admin.UserType;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import com.wetrack.ikongtiao.repo.api.fixer.FixerCertInfoRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerInsuranceInfoRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerProfessionInfoRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.domain.User;
import studio.wetrack.accountService.auth.service.TokenService;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhanghong on 15/12/30.
 */
@Service
public class FixerServiceImpl implements FixerService {

    @Autowired
    FixerRepo fixerRepo;
    @Autowired
    FixerCertInfoRepo certInfoRepo;
    @Autowired
    FixerInsuranceInfoRepo insuranceInfoRepo;
    @Autowired
    FixerProfessionInfoRepo professionInfoRepo;
    @Autowired
    TokenService tokenService;

//    @Resource
//    MessageProcess messageProcess;
    @Resource
    MessageService messageService;


    @Override
    public PageList<Fixer> listWithParams(FixerQueryForm form) throws Exception{
        PageList<Fixer> page = new PageList<Fixer>();
        page.setPage(form.getPage());
        page.setPageSize(form.getPageSize());
        form.setStart(page.getStart());
        // 设置总数量
        page.setTotalSize(fixerRepo.countFixerByQueryParam(form));
        // 获取内容
        List<Fixer> fixers = fixerRepo.listFixerByQueryParam(form);
        page.setData(fixers);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitCertInfo(FixerCertInfo certInfo) throws Exception {
        //create cert info record
        certInfo = certInfoRepo.create(certInfo.getFixerId(), certInfo.getIdNum(), certInfo.getIdWithFaceImg()
                , certInfo.getIdImgFront(), certInfo.getIdImgBack());
        if(certInfo != null) {
            //save id to Fixer
            Fixer fixer = new Fixer();
            fixer.setId(certInfo.getFixerId());
//            fixer.setCertInfoId(certInfo.getId());
            fixer.setCertificateState(certInfo.getCheckState());
            int i = fixerRepo.update(fixer);
            if(i != 1){
                throw new BusinessException("更新维修员表纪录" + i +"条, 错误!");
            }
        }else{
            throw new BusinessException("提交实名认证信息没有成功保存!");
        }

//        MessageSimple messageSimple = new MessageSimple();
//        messageSimple.setFixerId(certInfo.getFixerId());
//        messageProcess.process(MessageType.FIXER_SUBMIT_CERT_AUDIT, messageSimple);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, certInfo.getFixerId());
        params.put(MessageParamKey.FIXER_AUDIT_INFO, certInfo);
        messageService.send(MessageId.FIXER_SUBMIT_CERT_AUDIT, params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitInsuranceInfo(FixerInsuranceInfo insuranceInfo) throws Exception {
        insuranceInfo = insuranceInfoRepo.create(insuranceInfo.getFixerId(), insuranceInfo.getInsuranceNum(), insuranceInfo.getInsuranceImg()
                                , insuranceInfo.getInsuranceDate(), insuranceInfo.getExpiresAt());
        if(insuranceInfo != null){
            //save id to fixer
            Fixer fixer = new Fixer();
            fixer.setId(insuranceInfo.getFixerId());
//            fixer.setInsuranceInfoId(insuranceInfo.getId());
            fixer.setInsuranceState(insuranceInfo.getCheckState());
            int i = fixerRepo.update(fixer);
            if(i != 1){
                throw new BusinessException("更新维修员表纪录错误!" + i +"条");
            }
        }else{
            throw new BusinessException("提交保险信息没有成功保存!");
        }


        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, insuranceInfo.getFixerId());
        params.put(MessageParamKey.FIXER_AUDIT_INFO, insuranceInfo);
        messageService.send(MessageId.FIXER_SUBMIT_INSURANCE_AUDIT, params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitProfessInfo(FixerProfessionInfo professionInfo) throws Exception {
        professionInfo = professionInfoRepo.create(professionInfo.getProfessType(), professionInfo.getFixerId(), professionInfo.getProfessNum()
                                                    , professionInfo.getProfessImg());
        if(professionInfo != null){
            //save id to fixer
            Fixer fixer = new Fixer();
            fixer.setId(professionInfo.getFixerId());
            if(professionInfo.getProfessType() == 0) { //电工
//                fixer.setElectricianInfoId(professionInfo.getId());
                fixer.setElectricianState(professionInfo.getCheckState());
            }else{
//                fixer.setWelderInfoId(professionInfo.getId());
                fixer.setWelderState(professionInfo.getCheckState());
            }
            int i = fixerRepo.update(fixer);
            if(i != 1){
                throw new BusinessException("更新维修员表纪录错误!" + i +"条");
            }
        }else{
            throw new BusinessException("提交技能信息没有成功保存!");
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, professionInfo.getFixerId());
        params.put(MessageParamKey.FIXER_AUDIT_INFO, professionInfo);
        messageService.send(MessageId.FIXER_SUBMIT_PROFESS_AUDIT, params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkCertInfo(Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception {
        if(adminUserId == null){
            throw new BusinessException("审核人为空!");
        }
        FixerCertInfo fixerCertInfo = certInfoRepo.findLatestForFixer(fixerId);
        if(fixerCertInfo == null){
            throw new BusinessException("维修员"+fixerId+"没有待审核的实名认证!");
        }else if(fixerCertInfo.getCheckState() == checkState){
            throw new BusinessException("重复操作!");
        }
        if(checkState < 0 && StringUtils.isEmpty(denyReason)){
            throw new BusinessException("驳回实名认证原因未填写");
        }

        //update cert info
        fixerCertInfo.setCheckState(checkState);
        if(!StringUtils.isEmpty(denyReason)){
            fixerCertInfo.setDenyReason(denyReason);
        }
        fixerCertInfo.setCheckTime(new Date());
        fixerCertInfo.setAdminUserId(adminUserId);
        certInfoRepo.update(fixerCertInfo);
        //update fixer info
        Fixer fixer = new Fixer();
        fixer.setId(fixerId);
        fixer.setCertificateState(fixerCertInfo.getCheckState());
//        fixer.setCertInfoId(fixerCertInfo.getId());
        fixerRepo.update(fixer);


        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, fixerId);
        params.put(MessageParamKey.ADMIN_ID, adminUserId);
        params.put(MessageParamKey.FIXER_AUDIT_INFO, fixerCertInfo);
        if(fixerCertInfo.getCheckState() == -1) {
            messageService.send(MessageId.FIXER_CERT_AUDIT_DENY, params);
        }else if(fixerCertInfo.getCheckState() == 2){
            messageService.send(MessageId.FIXER_CERT_AUDIT_PASS, params);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkInsuranceInfo(Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception {
        if(adminUserId == null){
            throw new BusinessException("审核人为空!");
        }
        FixerInsuranceInfo insuranceInfo = insuranceInfoRepo.findLatestForFixer(fixerId);
        if(insuranceInfo == null){
            throw new BusinessException("维修员"+fixerId+"没有待审核的保险认证!");
        }else if(insuranceInfo.getCheckState() == checkState){
            throw new BusinessException("重复操作!");
        }
        if(checkState < 0 && StringUtils.isEmpty(denyReason)){
            throw new BusinessException("驳回保险认证申请原因未填写");
        }

        //update cert info
        insuranceInfo.setCheckState(checkState);
        if(!StringUtils.isEmpty(denyReason)){
            insuranceInfo.setDenyReason(denyReason);
        }
        insuranceInfo.setCheckTime(new Date());
        insuranceInfo.setAdminUserId(adminUserId);
        insuranceInfoRepo.update(insuranceInfo);
        //update fixer info
        Fixer fixer = new Fixer();
        fixer.setId(fixerId);
        fixer.setInsuranceState(insuranceInfo.getCheckState());
//        fixer.setInsuranceInfoId(insuranceInfo.getId());
        fixerRepo.update(fixer);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, fixerId);
        params.put(MessageParamKey.ADMIN_ID, adminUserId);
        params.put(MessageParamKey.FIXER_AUDIT_INFO, insuranceInfo);
        if(insuranceInfo.getCheckState() == -1) {
            messageService.send(MessageId.FIXER_INSURANCE_AUDIT_DENY, params);
        }else if(insuranceInfo.getCheckState() == 2){
            messageService.send(MessageId.FIXER_INSURANCE_AUDIT_PASS, params);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkProfessInfo(int type, Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception {
        if(adminUserId == null){
            throw new BusinessException("审核人为空!");
        }
        FixerProfessionInfo professionInfo = professionInfoRepo.findLatestForFixerIdAndType(fixerId, type);
        if(professionInfo == null){
            throw new BusinessException("维修员"+fixerId+"没有待审核的技能认证!");
        }else if(professionInfo.getCheckState() == checkState){
            throw new BusinessException("重复操作!");
        }
        if(checkState < 0 && StringUtils.isEmpty(denyReason)){
            throw new BusinessException("驳回技能申请原因未填写");
        }

        //update cert info
        professionInfo.setCheckState(checkState);
        if(!StringUtils.isEmpty(denyReason)){
            professionInfo.setDenyReason(denyReason);
        }
        professionInfo.setCheckTime(new Date());
        professionInfo.setAdminUserId(adminUserId);
        professionInfoRepo.update(professionInfo);
        //update fixer info
        Fixer fixer = new Fixer();
        fixer.setId(fixerId);
        if(type == 0){
            fixer.setElectricianState(professionInfo.getCheckState());
//            fixer.setElectricianInfoId(professionInfo.getId());
        }else{
            fixer.setWelderState(professionInfo.getCheckState());
//            fixer.setWelderInfoId(professionInfo.getId());
        }
        fixerRepo.update(fixer);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MessageParamKey.FIXER_ID, fixerId);
        params.put(MessageParamKey.ADMIN_ID, adminUserId);
        params.put(MessageParamKey.FIXER_AUDIT_INFO, professionInfo);
        if(professionInfo.getCheckState() == -1) {
            messageService.send(MessageId.FIXER_PROFESS_AUDIT_DENY, params);
        }else if(professionInfo.getCheckState() == 2){
            messageService.send(MessageId.FIXER_PROFESS_AUDIT_PASS, params);
        }
    }

    @Override
    public FixerCertInfo getCertificateInfo(int fixerId) throws Exception {
        return certInfoRepo.findLatestForFixer(fixerId);
    }

    @Override
    public FixerInsuranceInfo getInsuranceInfo(int fixerId) throws Exception {
        return insuranceInfoRepo.findLatestForFixer(fixerId);
    }

    @Override
    public FixerProfessionInfo getProfessInfo(int fixerId, int type) throws Exception {
        return professionInfoRepo.findLatestForFixerIdAndType(fixerId, type);
    }

    @Override
    public void updateInfo(Fixer fixer) throws Exception {
//        if(fixer.getAddress() != null){
//            GeoLocation geoLocation = GeoUtil.getGeoLocationFromAddress(fixer.getAddress());
//            if(geoLocation == null){
//                throw new Exception("该地址无法获取经纬度，需要重新填写!");
//            }else{
//                fixer.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
//                fixer.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
//            }
//        }
        if(fixer.getJkMaintainer() != null){
            Collection<Token> tokens = tokenService.findAllByUserId(Constants.TOKEN_ID_PREFIX_FIXER + fixer.getId().toString());
            if(fixer.getJkMaintainer()){
                fixer.setType(UserType.MAINTAINER);
            }else{
                fixer.setType(UserType.COMMON);
            }
            //给token改权限，这样维修员无需重新登录，权限变化马上可以生效
            if(tokens != null){
                for(Token token : tokens){
                    User authUser = new User(Constants.TOKEN_ID_PREFIX_FIXER + fixer.getId().toString(),
                            null, User.NEVER_EXPIRED, fixer.getType().getRolesStringArray());
                    token.setUser(authUser);
                    tokenService.updateToken(token);
                }
            }
        }
        fixerRepo.update(fixer);
    }

    @Override
    public void deleteFixer(int fixerId) throws Exception{
        if(fixerRepo.getFixerById(fixerId) == null){
            throw new BusinessException("不存在的维修人员");
        }
        fixerRepo.lDelete(fixerId);
    }

    @Override
    public Fixer getFixer(Integer id) throws Exception {
        return fixerRepo.getFixerById(id);
    }

    @Override
    public void changePassword(Integer id, String oldPass, String newPass) throws Exception {
        Fixer fixer = fixerRepo.getFixerById(id);
        if(!fixer.getPassword().equals(oldPass)){
            throw new BusinessException("原密码不对");
        }

        fixer.setPassword(newPass);
        fixerRepo.update(fixer);
    }

    @Override
    public Fixer getFixerByPhone(String phone) throws Exception {
        return fixerRepo.getFixerByPhone(phone);
    }

    @Override
    public void resetPassword(Integer id, String newPass) throws Exception {
        Fixer fixer = new Fixer();
        fixer.setId(id);
        fixer.setPassword(newPass);
        fixerRepo.update(fixer);
    }

    @Override
    public List<Fixer> listInIds(List<Integer> ids) {
        return fixerRepo.listFixerOfIds(ids);
    }

    @Override
    public Integer getFixerIdFromTokenUser(User user) {
        return Integer.valueOf(user.getId().substring(Constants.TOKEN_ID_PREFIX_FIXER.length()));
    }

    @Override
    public Fixer createAccount(String phone, String name, String password) throws Exception {
        FixerQueryForm query = new FixerQueryForm();
        query.setPhone(phone);
        query.setDeleted(false);
        int count = fixerRepo.countFixerByQueryParam(query);
        if(count > 0){
            throw new BusinessException("该号码"+phone+"已经被注册");
        }
        Fixer fixer = new Fixer();
        fixer.setPhone(phone);
        fixer.setName(name);
        fixer.setPassword(password);
        fixer.setJkMaintainer(false);
        fixer.setType(UserType.COMMON);
        return fixerRepo.save(fixer);
    }

    @Override
    public Fixer createJKAccount(String phone, String name, String password) throws Exception {
        FixerQueryForm query = new FixerQueryForm();
        query.setPhone(phone);
        query.setDeleted(false);
        int count = fixerRepo.countFixerByQueryParam(query);
        if(count > 0){
            throw new BusinessException("该号码"+phone+"已经被注册");
        }
        Fixer fixer = new Fixer();
        fixer.setPhone(phone);
        fixer.setName(name);
        fixer.setPassword(password);
        fixer.setJkMaintainer(true);
        fixer.setType(UserType.MAINTAINER);
        return fixerRepo.save(fixer);
    }

    @Override
    public Token login(String phone, String password) throws Exception {
        FixerQueryForm form = new FixerQueryForm();
        form.setPhone(phone);
        form.setDeleted(false);
        Fixer fixer = fixerRepo.getFixerByPhone(phone);
        if(fixer != null){
//            Fixer found = results.get(0);
            if(fixer.getPassword().equals(password)){
                User authUser = new User(Constants.TOKEN_ID_PREFIX_FIXER + fixer.getId().toString(),
                        password, User.NEVER_EXPIRED, fixer.getType().getRolesStringArray());
                return tokenService.login(authUser);
            }else{
                throw new BusinessException("密码错误");
//                throw new AjaxException(MissionErrorMessage.MISSION_LIST_PARAM_ERROR);
            }
        }else{
            throw new BusinessException("不存在的用户");
        }
    }
}
