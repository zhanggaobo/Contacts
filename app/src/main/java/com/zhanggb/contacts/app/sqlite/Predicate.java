package com.zhanggb.contacts.app.sqlite;

/**
 * Predicate
 *
 * @author snowway
 * @since 2/24/11
 */
public interface Predicate<T> {

    public static final Predicate TRUE = new Predicate() {
        @Override
        public boolean eval(Object input) {
            return true;
        }
    };

    boolean eval(T input);
}
