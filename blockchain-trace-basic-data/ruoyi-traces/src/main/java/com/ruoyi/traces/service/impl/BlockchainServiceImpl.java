package com.ruoyi.traces.service.impl;

import com.ruoyi.traces.config.Config;
import com.ruoyi.traces.fabric.BlockChain;
import com.ruoyi.traces.fabric.Const;
import com.ruoyi.traces.service.BlockchainService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BlockchainServiceImpl implements BlockchainService {
    @Autowired
    private Config config;

    private BlockChain bc;

    private String[] peers = new String[] {Const.PEER0_ORG1_DOMAIN_NAME, Const.PEER0_ORG3_DOMAIN_NAME,
            Const.PEER0_ORG2_DOMAIN_NAME, Const.PEER0_ORG4_DOMAIN_NAME};

    @Override
    public void init() {
        bc = new BlockChain();
        bc.initConn(config.getBasePath());
    }

    @Override
    public String send(String peerName, String function, String[] args) throws InvalidArgumentException, ProposalException {
        return bc.send(peerName, function, args);
    }

    @Override
    public String query(String peerName, String function, String[] args) throws InvalidArgumentException, ProposalException {
        return bc.query(peerName, function, args);
    }


    public String query(Integer count, String function, String[] args) throws InvalidArgumentException, ProposalException {
        String productID = args[0];
        int index = productID.hashCode() % count;
        return bc.query(peers[index], function, args);
    }

    @Override
    public void test() {
    }

}
