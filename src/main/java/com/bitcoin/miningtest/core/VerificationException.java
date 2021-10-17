package com.bitcoin.miningtest.core;

@SuppressWarnings("serial")
public class VerificationException extends Exception {
    public VerificationException(String msg) {
        super(msg);
    }
}
