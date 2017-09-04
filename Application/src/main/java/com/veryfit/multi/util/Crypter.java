package com.veryfit.multi.util;

public interface Crypter {
    String decrypt(String str);

    String encrypt(String str);

    void setKey(String str);
}
