package com.ruoyi.traces.fabric;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;

public class PeerObj {

	private HFClient hfClient;

	private Channel channel;

	private ChaincodeID chaincodeID;

	public HFClient getHfClient() {
		return hfClient;
	}

	public void setHfClient(HFClient hfClient) {
		this.hfClient = hfClient;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChaincodeID getChaincodeID() {
		return chaincodeID;
	}

	public void setChaincodeID(ChaincodeID chaincodeID) {
		this.chaincodeID = chaincodeID;
	}

}
