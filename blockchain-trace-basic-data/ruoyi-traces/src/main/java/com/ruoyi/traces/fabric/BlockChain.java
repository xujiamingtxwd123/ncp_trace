package com.ruoyi.traces.fabric;

import com.google.common.base.Strings;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class BlockChain {
	private Map<String, PeerObj> fabricMap = new HashMap<String, PeerObj>();

	public void initConn(String basePath) {
		Const.BASE_PATH = basePath;
		fabricMap = new HashMap<String, PeerObj>();
		try {
			fabricMap.put(Const.PEER0_ORG1_DOMAIN_NAME, init(Const.PEER0_ORG1_DOMAIN_NAME));
			fabricMap.put(Const.PEER0_ORG2_DOMAIN_NAME,init(Const.PEER0_ORG2_DOMAIN_NAME));
			fabricMap.put(Const.PEER0_ORG3_DOMAIN_NAME,init(Const.PEER0_ORG3_DOMAIN_NAME));
			fabricMap.put(Const.PEER0_ORG4_DOMAIN_NAME,init(Const.PEER0_ORG4_DOMAIN_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String send(String peerName, String function, String[] args) throws InvalidArgumentException, ProposalException {
		PeerObj peerObj = fabricMap.get(peerName);
		String message = null;
		message = invoke(peerObj.getHfClient(), peerObj.getChannel(), peerObj.getChaincodeID(), function,
					args);
		return message;
	}

	public String query(String peerName, String function, String[] args) throws InvalidArgumentException, ProposalException {
		PeerObj peerObj = fabricMap.get(peerName);
		String queryData = null;
		queryData = query(peerObj.getHfClient(), peerObj.getChannel(), peerObj.getChaincodeID(), function,
					args);

		return queryData;
	}

	private Properties loadTLSFile(String rootTLSCert, String hostName) throws IOException {
		Properties properties = new Properties();
		properties.put("pemBytes", Files.readAllBytes(Paths.get(Const.BASE_PATH + rootTLSCert)));
		properties.setProperty("sslProvider", "openSSL");
		properties.setProperty("negotiationType", "TLS");
		properties.setProperty("trustServerCertificate", "true");
		properties.setProperty("hostnameOverride", hostName);
		return properties;
	}

	private String query(HFClient hfClient, Channel channel, ChaincodeID chaincodeID, String func, String[] args)
			throws ProposalException, InvalidArgumentException {
		QueryByChaincodeRequest req = hfClient.newQueryProposalRequest();
		req.setChaincodeID(chaincodeID);
		req.setFcn(func);
		req.setArgs(args);
		// 向peer节点发送调用链码的提案并等待返回查询响应集合
		String queryData = null;
		Collection<ProposalResponse> queryResponse = channel.queryByChaincode(req);
		for (ProposalResponse pres : queryResponse) {
			if (!Strings.isNullOrEmpty(pres.getMessage())) {
				return pres.getMessage();
			}
			queryData = pres.getProposalResponse().getResponse().getPayload().toStringUtf8();
		}
		return queryData;
	}

	private String invoke(HFClient hfClient, Channel channel, ChaincodeID chaincodeID, String func, String[] args)
			throws InvalidArgumentException, ProposalException {
		// 提交链码交易
		TransactionProposalRequest req2 = hfClient.newTransactionProposalRequest();
		req2.setChaincodeID(chaincodeID);
		req2.setFcn(func);
		req2.setArgs(args);
		// 配置提案等待时间
		req2.setProposalWaitTime(10000);
		// 向peer节点发送调用链码的提案并等待返回背书响应集合
		Collection<ProposalResponse> rsp2 = channel.sendTransactionProposal(req2);
		for (ProposalResponse pres : rsp2) {
			if (!Strings.isNullOrEmpty(pres.getMessage())) {
				return pres.getMessage();
			}
		}
		try {
			channel.sendTransaction(rsp2).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private PeerObj init(String peerAndOrg) throws Exception {
		// 创建默认的加密套件
		CryptoSuite suite = CryptoSuite.Factory.getCryptoSuite();
		// Hyperledger Fabric 客户端
		HFClient hfClient = HFClient.createNewInstance();
		hfClient.setCryptoSuite(suite);

		FabUser fabUser = null;
		if(peerAndOrg.equals(Const.PEER0_ORG1_DOMAIN_NAME)) {
			// 创建一个用户并加载证书与秘钥文件
			fabUser = new FabUser("admin", Const.USER1_MSP_ID, Const.USER1_KEY_FILE, Const.USER1_CERT_FILE);
		}
		if(peerAndOrg.equals(Const.PEER0_ORG2_DOMAIN_NAME)) {
			// 创建一个用户并加载证书与秘钥文件
			fabUser = new FabUser("admin", Const.USER2_MSP_ID, Const.USER2_KEY_FILE, Const.USER2_CERT_FILE);
		}
		if(peerAndOrg.equals(Const.PEER0_ORG3_DOMAIN_NAME)) {
			// 创建一个用户并加载证书与秘钥文件
			fabUser = new FabUser("admin", Const.USER3_MSP_ID, Const.USER3_KEY_FILE, Const.USER3_CERT_FILE);
		}
		if(peerAndOrg.equals(Const.PEER0_ORG4_DOMAIN_NAME)) {
			// 创建一个用户并加载证书与秘钥文件
			fabUser = new FabUser("admin", Const.USER4_MSP_ID, Const.USER4_KEY_FILE, Const.USER4_CERT_FILE);
		}


		hfClient.setUserContext(fabUser);

		// 创建通道实例
		Channel channel = hfClient.newChannel(Const.CHANNEL_NAME);

		Peer peer = null;
		if (peerAndOrg.equals(Const.PEER0_ORG1_DOMAIN_NAME)) {
			peer = hfClient.newPeer(Const.PEER0_ORG1_DOMAIN_NAME, Const.PEER0_ORG1_HOST,
					loadTLSFile(Const.ORG1_TLS_DIR, Const.PEER0_ORG1_DOMAIN_NAME));
			channel.addPeer(peer);
		}
		if (peerAndOrg.equals(Const.PEER0_ORG2_DOMAIN_NAME)) {
			peer = hfClient.newPeer(Const.PEER0_ORG2_DOMAIN_NAME, Const.PEER0_ORG2_HOST,
					loadTLSFile(Const.ORG2_TLS_DIR, Const.PEER0_ORG2_DOMAIN_NAME));
			channel.addPeer(peer);
		}

		if (peerAndOrg.equals(Const.PEER0_ORG3_DOMAIN_NAME)) {
			peer = hfClient.newPeer(Const.PEER0_ORG3_DOMAIN_NAME, Const.PEER0_ORG3_HOST,
					loadTLSFile(Const.ORG3_TLS_DIR, Const.PEER0_ORG3_DOMAIN_NAME));
			channel.addPeer(peer);
		}
		if (peerAndOrg.equals(Const.PEER0_ORG4_DOMAIN_NAME)) {
			peer = hfClient.newPeer(Const.PEER0_ORG4_DOMAIN_NAME, Const.PEER0_ORG4_HOST,
					loadTLSFile(Const.ORG4_TLS_DIR, Const.PEER0_ORG4_DOMAIN_NAME));
			channel.addPeer(peer);
		}

		Orderer orderer0 = hfClient.newOrderer(Const.ORDERER0_DOMAIN_NAME, Const.ORDERER0_HOST,
				loadTLSFile(Const.ORDERER0_TLS_DIR, Const.ORDERER0_DOMAIN_NAME));
		Orderer orderer1 = hfClient.newOrderer(Const.ORDERER1_DOMAIN_NAME, Const.ORDERER1_HOST,
				loadTLSFile(Const.ORDERER1_TLS_DIR, Const.ORDERER1_DOMAIN_NAME));
		Orderer orderer2 = hfClient.newOrderer(Const.ORDERER2_DOMAIN_NAME, Const.ORDERER2_HOST,
				loadTLSFile(Const.ORDERER2_TLS_DIR, Const.ORDERER2_DOMAIN_NAME));
		channel.addOrderer(orderer0);
		channel.addOrderer(orderer1);
		channel.addOrderer(orderer2);
		// 通道初始化
		channel.initialize();
		// 创建与Fabric中链码对应的实例
		ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName(Const.CHAINCODE_NAME).build();
		PeerObj peerObj = new PeerObj();
		peerObj.setHfClient(hfClient);
		peerObj.setChaincodeID(chaincodeID);
		peerObj.setChannel(channel);
		return peerObj;
	}
}
