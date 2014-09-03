package com.walkline.util.network;

import com.walkline.app.AutohomeAppConfig;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.io.transport.TransportInfo;
import net.rim.device.api.io.transport.options.BisBOptions;

public class MyConnectionFactory extends ConnectionFactory
{
	public MyConnectionFactory()
	{
		setPreferredTransportTypes(AutohomeAppConfig.preferredTransportTypes);
		setDisallowedTransportTypes(AutohomeAppConfig.disallowedTransportTypes);
		setTransportTypeOptions(TransportInfo.TRANSPORT_BIS_B, new BisBOptions("nds-public"));
		setTimeoutSupported(true);
		setAttemptsLimit(10);
		setRetryFactor(2000);
		setConnectionTimeout(10000);
		setTimeLimit(10000);
	}
}