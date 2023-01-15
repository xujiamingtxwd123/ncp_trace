package com.ruoyi.traces.runner;

import com.ruoyi.traces.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BlockchainRunner implements CommandLineRunner {

    @Autowired
    private BlockchainService blockchainService;

    @Override
    public void run(String... args) throws Exception {
        blockchainService.init();
    }
}
