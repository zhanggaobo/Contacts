package com.zhanggb.contacts.app.sqlite;

/**
 * root exception of foundation framework
 *
 * @author snowway
 * @since 2/25/11
 */
public class FoundationException extends RuntimeException {

    private static final long serialVersionUID = -2167705868135900188L;

    public FoundationException() {
    }

    public FoundationException(String s) {
        super(s);
    }

    public FoundationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FoundationException(Throwable throwable) {
        super(throwable);
    }
}
