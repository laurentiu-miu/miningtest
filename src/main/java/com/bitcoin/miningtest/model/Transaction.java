package com.bitcoin.miningtest.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Transaction {
    public String data;
    public String txid;
    public String hash;
    public List<Integer> depends;
    public int fee;
    public int sigops;
    public int weight;
}
