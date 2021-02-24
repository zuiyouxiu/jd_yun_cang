package com.lqh.dev.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CurrentLoginContext {

    private static final ThreadLocal<CurrentLoginContext> HOLDER = new ThreadLocal<>();
    private String userPin;
    private Date expiresDate;
    private String cookieValue;
    private String source;

    public static CurrentLoginContext getTicket() {
        return HOLDER.get();
    }

    public static void setTicket(CurrentLoginContext ticket) {
        HOLDER.set(ticket);
    }

    public static void remove() {
        HOLDER.remove();
    }

    public CurrentLoginContext(String userPin, Date expiresDate) {
        this.userPin = userPin;
        if (expiresDate == null) {
            //给半小时
            this.expiresDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000L);
        } else {
            this.expiresDate = expiresDate;
        }
    }
}
