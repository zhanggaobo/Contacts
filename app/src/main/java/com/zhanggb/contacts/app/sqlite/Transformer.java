package com.zhanggb.contacts.app.sqlite;

/**
 * Transformer
 *
 * @author snowway
 * @since 2/24/11
 */
public interface Transformer<F, T> {

    T transform(F input);
}
