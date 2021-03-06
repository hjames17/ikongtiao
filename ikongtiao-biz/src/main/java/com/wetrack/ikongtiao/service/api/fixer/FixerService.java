package com.wetrack.ikongtiao.service.api.fixer;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.domain.User;

import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
public interface FixerService {

    Integer getFixerIdFromTokenUser(User user);

    Fixer createAccount(String phone, String name, String password) throws Exception;
    Fixer createJKAccount(String phone, String name, String password) throws Exception;

    Token login(String phone, String password) throws Exception;

    PageList<Fixer> listWithParams(FixerQueryForm queryForm) throws Exception;

    void submitCertInfo(FixerCertInfo certInfo) throws Exception;

    void submitInsuranceInfo(FixerInsuranceInfo insuranceInfo) throws Exception;

    void submitProfessInfo(FixerProfessionInfo professionInfo) throws Exception;

    void checkCertInfo(Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception;

    void checkInsuranceInfo(Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception;

    void checkProfessInfo(int type, Integer fixerId, int checkState, String denyReason, Integer adminUserId) throws Exception;

    FixerCertInfo getCertificateInfo(int fixerId) throws Exception;

    FixerInsuranceInfo getInsuranceInfo(int fixerId) throws Exception;

    FixerProfessionInfo getProfessInfo(int fixerId, int type) throws Exception;

    void updateInfo(Fixer fixer) throws Exception;

    void deleteFixer(int fixerId) throws Exception;

    Fixer getFixer(Integer id) throws Exception;

    void changePassword(Integer id, String oldPass, String newPass) throws Exception;

    Fixer getFixerByPhone(String phone);

    void resetPassword(Integer id, String newPass) throws Exception;

    List<Fixer> listInIds(List<Integer> ids);
}
