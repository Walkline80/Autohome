package com.walkline.util.network;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.http.HttpProtocolConstants;
import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.io.transport.TransportInfo;
import com.walkline.util.Function;

public class HttpClient
{
	protected ConnectionFactory cf;

	public HttpClient(ConnectionFactory pcf) {cf = pcf;}

	public StringBuffer doGet(String url) throws Exception
	{
		HttpConnection conn = null;
		StringBuffer buffer = new StringBuffer();

		try {
			if ((url == null) || url.equalsIgnoreCase("") || (cf == null)) {Function.errorDialog("null"); return null;}

			ConnectionDescriptor connd = cf.getConnection(url);
			conn = (HttpConnection) connd.getConnection();

			conn.setRequestProperty(HttpProtocolConstants.HEADER_CONNECTION, HttpProtocolConstants.HEADER_KEEP_ALIVE);
			//conn.setRequestProperty(HttpProtocolConstants.HEADER_USER_AGENT, System.getProperty("browser.useragent"));//"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
			//conn.setRequestProperty(HttpProtocolConstants.HEADER_REFERER, "http://www.weather.com.cn");

			int resCode = conn.getResponseCode();

			switch (resCode)
			{
				case HttpConnection.HTTP_OK: 
				{
					InputStream inputStream = conn.openInputStream();
					int c;

					while ((c = inputStream.read()) != -1) {buffer.append((char) c);}

					inputStream.close();
					break;
				}
			}
		} catch (Exception e) {throw e;}
		  finally {if (conn != null) {try {conn.close(); conn = null;} catch (IOException e) {Function.errorDialog(e.toString());}}}

		return buffer;
	}
}