package com.wetrack.ikongtiao.service.impl.fixer;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import com.wetrack.ikongtiao.repo.api.fixer.FixerCertInfoRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerInsuranceInfoRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerProfessionInfoRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.message.MessageProcess;
import com.wetrack.message.MessageSimple;
import com.wetrack.message.MessageType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Resource
    MessageProcess messageProcess;


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
                throw new Exception("更新维修员表纪录" + i +"条, 错误!");
            }
        }else{
            throw new Exception("提交信息没有成功保存!");
        }
        MessageSimple messageSimple = new MessageSimple();
        messageSimple.setFixerId(certInfo.getFixerId());
        messageProcess.process(MessageType.FIXER_SUBMIT_AUDIT,messageSimple);
        //TODO 发送消息
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
                throw new Exception("更新维修员表纪录错误!" + i +"条");
            }
        }else{
            throw new Exception("提交信息没有成功保存!");
        }
        //TODO 发送消息
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
                throw new Exception("更新维修员表纪录错误!" + i +"条");
            }
        }else{
            throw new Exception("提交信息没有成功保存!");
        }
        //TODO 发送消息
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkCertInfo(Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception {
        if(adminUserId == null){
            throw new Exception("审核人为空!");
        }
        FixerCertInfo fixerCertInfo = certInfoRepo.findLatestForFixer(fixerId);
        if(fixerCertInfo == null){
            throw new Exception("维修员没有待审核认证!");
        }else if(fixerCertInfo.getCheckState() == checkState){
            throw new Exception("重复操作!");
        }
        if(checkState < 0 && StringUtils.isEmpty(denyReason)){
            throw new Exception("驳回原因未填写");
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

        //TODO 发送消息
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkInsuranceInfo(Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception {
        if(adminUserId == null){
            throw new Exception("审核人为空!");
        }
        FixerInsuranceInfo insuranceInfo = insuranceInfoRepo.findLatestForFixer(fixerId);
        if(insuranceInfo == null){
            throw new Exception("维修员没有待审核认证!");
        }else if(insuranceInfo.getCheckState() == checkState){
            throw new Exception("重复操作!");
        }
        if(checkState < 0 && StringUtils.isEmpty(denyReason)){
            throw new Exception("驳回原因未填写");
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

        //TODO 发送消息
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkProfessInfo(int type, Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception {
        if(adminUserId == null){
            throw new Exception("审核人为空!");
        }
        FixerProfessionInfo professionInfo = professionInfoRepo.findLatestForFixerIdAndType(fixerId, type);
        if(professionInfo == null){
            throw new Exception("维修员没有待审核认证!");
        }else if(professionInfo.getCheckState() == checkState){
            throw new Exception("重复操作!");
        }
        if(checkState < 0 && StringUtils.isEmpty(denyReason)){
            throw new Exception("驳回原因未填写");
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

        //TODO 发送消息
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
        fixerRepo.update(fixer);
    }

    @Override
    public Fixer getFixer(Integer id) throws Exception {
        return fixerRepo.getFixerById(id);
    }

    @Override
    public void changePassword(Integer id, String oldPass, String newPass) throws Exception {
        Fixer fixer = fixerRepo.getFixerById(id);
        if(!fixer.getPassword().equals(oldPass)){
            throw new Exception("原密码不对");
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
    public Fixer createAccount(String phone, String name, String password) throws Exception {
        FixerQueryForm query = new FixerQueryForm();
        query.setPhone(phone);
        int count = fixerRepo.countFixerByQueryParam(query);
        if(count > 0){
            throw new Exception("该号码已经被注册");
        }
        Fixer fixer = new Fixer();
        fixer.setPhone(phone);
        fixer.setName(name);
        fixer.setPassword(password);
        return fixerRepo.save(fixer);
    }

    @Override
    public Token login(String phone, String password) throws Exception {
        FixerQueryForm form = new FixerQueryForm();
        form.setPhone(phone);
        List<Fixer> results = fixerRepo.listFixerByQueryParam(form);
        if(results != null && results.size() > 0){
            Fixer found = results.get(0);
            if(found.getPassword().equals(password)){
                return tokenService.login(found.getId().toString(), password);
            }else{
                throw new Exception("密码错误");
            }
        }else{
            throw new Exception("不存在的用户");
        }
    }
}
