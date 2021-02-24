package com.lqh.dev.interceptors;

import com.jd.ssa.domain.UserInfo;
import com.jd.ssa.exception.SsoException;
import com.jd.ssa.service.SsoService;
import com.lqh.dev.agent.service.SsoServiceApi;
import org.springframework.stereotype.Service;

/**
 * Created by chenlijuan6 on 2018/3/10.
 */
@Service
public class ErpSsoService implements SsoService {

    private SsoServiceApi ssoServiceApi;

    @Override
    public UserInfo verifyTicket(String s, String s1, String s2) throws SsoException {
        return ssoServiceApi.verifyTicket(s, s1, s2);
    }

    @Override
    public String getTicket(String s, String s1, String s2) throws SsoException {
        return ssoServiceApi.getTicket(s, s1, s2);
    }

    @Override
    public String getTicketByPwd(String s, String s1, String s2, int i) throws SsoException {
        throw new RuntimeException();
    }

    @Override
    public String getTicketByAuth(String s, String s1, String s2, String s3, int i) throws SsoException {
        throw new RuntimeException();
    }

    @Override
    public UserInfo verify(String s, String s1, String s2) throws SsoException {
        throw new RuntimeException();
    }

    @Override
    public UserInfo getUserInfo(String s) throws SsoException {
        throw new RuntimeException();
    }

    @Override
    public String getCoderKey(int i, String s) {
        throw new RuntimeException();
    }

    @Override
    public String getPublicKey() {
        throw new RuntimeException();
    }

    @Override
    public String getVerificationCode(String s, String s1, Long aLong, String s2, String s3, String s4) {
        throw new RuntimeException();
    }

    @Override
    public String verifyUserWithVerificationCode(String s, String s1, Long aLong, String s2, String s3, String s4) {
        throw new RuntimeException();
    }

    @Override
    public String changePwd(String s, String s1, Long aLong, String s2, String s3, String s4) {
        throw new RuntimeException();
    }
}
