package org.corejava.jsse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class SSLClient {

	private static final String CLIENT_KEY_STORE_PASSWORD = "123456";
	private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";
	private SSLSocket sslSocket = null;
	private String DEFAULT_HOST = "localhost";
	private int DEFAULT_PORT = 7070;

	public SSLClient() throws IOException {
		init();

		// // 通过套接字工厂，获取一个客户端套接字
		// SSLSocketFactory socketFactory = (SSLSocketFactory)
		// SSLSocketFactory.getDefault();
		//
		// socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
	}

	public void connect() throws IOException {

		// 获取客户端套接字输出流
		PrintWriter output = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
		// 将用户名和密码通过输出流发送到服务器端
		String userName = "principal";
		output.println(userName);
		String password = "credential";
		output.println(password);
		output.flush();

		// 获取客户端套接字输入流
		BufferedReader input = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
		// 从输入流中读取服务器端传送的数据内容，并打印出来
		String response = input.readLine();
		response += "\n " + input.readLine();
		System.out.println(response);

		// 关闭流资源和套接字资源
		input.close();
		sslSocket.close();
	}

	public static void main(String args[]) throws IOException {
		new SSLClient().connect();
	}

	public void init() {
		try {
			// 获取SSlContext对象
			SSLContext ctx = SSLContext.getInstance("SSL");
			// JSSE密钥管理器KeyManagerFactory对象
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			// 信任管理器TrustManagerFactory对象
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			// 密钥和证书的存储设施
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");
			// 载入keystore
			ks.load(new FileInputStream("kclient.keystore"), CLIENT_KEY_STORE_PASSWORD.toCharArray());
			tks.load(new FileInputStream("tclient.keystore"), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
			// KeyManagerFactory对象初始化
			kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
			// TrustManagerFactory对象初始化
			tmf.init(tks);
			// SSLContext对象初始化
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			// 创建连接sslSocket对象
			sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
