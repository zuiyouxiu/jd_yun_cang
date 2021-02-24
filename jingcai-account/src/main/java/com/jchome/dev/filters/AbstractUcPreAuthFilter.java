package com.lqh.dev.filters;

import com.jd.common.web.DotnetAuthenticationTicket;
import com.jd.common.web.DotnetAuthenticationUtil;
import com.lqh.dev.domain.CurrentLoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public abstract class AbstractUcPreAuthFilter extends AbstractPreAuthFilter {

    private static final String ucAuthCookieName = "thor";
    private static final String ucAuthenticationKey = "8B6697227CBCA902B1A0925D40FAA00B353F2DF4359D2099";

    @Override
    protected CurrentLoginContext parseTicket(HttpServletRequest request, HttpServletResponse response) {
        DotnetAuthenticationTicket ticket = DotnetAuthenticationTicket.getTicket();
        DotnetAuthenticationTicket.remove();
        CurrentLoginContext.remove();

        String cookieValue = this.cookieUtil.getCookieValue(request, ucAuthCookieName);
        log.info("cookieValue：{}", cookieValue);
        if (StringUtils.hasText(cookieValue)) {
            cookieValue = cookieValue.replace("\"", "");
            try {
                ticket = DotnetAuthenticationUtil.getFormsAuthenticationTicket(cookieValue, ucAuthenticationKey);
                log.info("ticket={}", ticket);
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    log.debug("decrypt uc cookie error!", e);
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                log.debug("uc cookie value is null, cookie name is {}", ucAuthCookieName);
            }
        }

        if (ticket != null && StringUtils.hasText(ticket.getUsername()) && !ticket.isExpired()) {
            CurrentLoginContext currentLoginContext = new CurrentLoginContext(ticket.getUsername(),
                    ticket.getExpires());
            currentLoginContext.setSource("uc");
            currentLoginContext.setCookieValue(cookieValue);
            return currentLoginContext;
        } else {
            CurrentLoginContext.setTicket(null);
            if (logger.isDebugEnabled()) {
                logger.debug("uc ticket error or ticket expired");
            }
            return null;
        }
    }

    @Override
    protected String getCookieName() {
        return ucAuthCookieName;
    }
}
