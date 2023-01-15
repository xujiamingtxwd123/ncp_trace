package com.ruoyi.traces.service;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

public interface BlockchainService {
    void init();
    String send(String peerName, String function, String[] args) throws InvalidArgumentException, ProposalException;
    String query(String peerName, String function, String[] args) throws InvalidArgumentException, ProposalException;
    void test();
}
