package com.bitcoin.miningtest.model;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    public List<String> capabilities;
    public int version;
    public List<String> rules;
    public Vbavailable vbavailable;
    public int vbrequired;
    public String previousblockhash;
    public List<Transaction> transactions;
    public Coinbaseaux coinbaseaux;
    public int coinbasevalue;
    public String longpollid;
    public String target;
    public int mintime;
    public List<String> mutable;
    public String noncerange;
    public int sigoplimit;
    public int sizelimit;
    public int weightlimit;
    public int curtime;
    public String bits;
    public int height;
    public String default_witness_commitment;
}
