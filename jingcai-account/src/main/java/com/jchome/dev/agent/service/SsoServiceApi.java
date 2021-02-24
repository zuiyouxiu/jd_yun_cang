package com.lqh.dev.agent.service;

import com.jd.ssa.domain.UserInfo;
import com.jd.ssa.exception.SsoException;

/**
 * hession client
 */
public interface SsoServiceApi {

    UserInfo verifyTicket(String var1, String var2, String var3) throws SsoException;

    String getTicket(String var1, String var2, String var3) throws SsoException;

    String getTicketByPwd(String var1, String var2, String var3, int var4) throws SsoException;

    UserInfo verify(String var1, String var2, String var3) throws SsoException;

    UserInfo getUserInfo(String var1) throws SsoException;

    String getCoderKey(int var1, String var2);

    String getPublicKey();
}
