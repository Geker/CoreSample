package org.corejava.jsse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/*
 * 需要创建密钥对，注意这里严格使用了keyManager  trustManager
 *
 *  1.服务端密钥生成
 *   keytool -genkey -alias serverkey -keystore kserver.keystore
 *  2.导出服务端的证书，并导入到client端的tstore中
 *   keytool -export -alias serverkey -keystore kserver.keystore -file server.crt
 *   keytool -import -alias serverkey -file server.crt -keystore tclient.keystore
 *
 *  同理生成客户端的密钥对，然后将公钥导入到服务端。
 *  3.  keytool -genkey -alias clientkey -keystore kclient.keystore
		keytool export -alias clientkey -keystore kclient.keystore -file client.crt
		keytool -import -alias client -file client.crt -keystore tserver.keystore
		然后将文件放置到项目中。
 * */
public class SSLServer {
    // 服务器端授权的用户名和密码

    private static final String USER_NAME = "principal";
    private static final String PASSWORD = "credential";// 服务器端保密内容

    private static final String SECRET_CONTENT = "This is confidential content from server X, for your eye!";
    private static final String SERVER_KEY_STORE_PASSWORD = "123456";
    private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "123456";
    private SSLServerSocket serverSocket = null;
    private int DEFAULT_PORT = 7070;

    public SSLServer() throws Exception {
        init();
    }

    private void runServer() throws IOException {
        while (true) {

            System.out.println("Waiting for connection...");
            // 服务器端套接字进入阻塞状态，等待来自客户端的连接请求
            SSLSocket socket = (SSLSocket) serverSocket.accept();

            // 获取服务器端套接字输入流
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 从输入流中读取客户端用户名和密码
            String userName = input.readLine();
            String password = input.readLine();

            // 获取服务器端套接字输出流
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 对请求进行认证，如果通过则将保密内容发送给客户端
            if (userName.equals(USER_NAME) && password.equals(PASSWORD)) {
                output.println("Welcome, " + userName);
                output.println(SECRET_CONTENT);
            }
            else {
                output.println("Authentication failed, you have noaccess to server X...");
            }

            // 关闭流资源和套接字资源
            output.close();
            input.close();
            socket.close();
        }
    }

    public static void main(String args[]) throws Exception {
        SSLServer server = new SSLServer();
        server.runServer();
    }

    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");
            // InputStream is = SSLServer.class.getClassLoader().getResourceAsStream("org/corejava/classloader/4.txt");
            // InputStreamReader isr = new InputStreamReader(is);
            // List<String> ss = IOUtils.readLines(isr);
            // System.err.println(ss);

            ks.load(SSLServer.class.getClassLoader().getResourceAsStream("org/corejava/classloader/kserver.keystore"),
                SERVER_KEY_STORE_PASSWORD.toCharArray());
            tks.load(SSLServer.class.getClassLoader().getResourceAsStream("org/corejava/classloader/tserver.keystore"),
                SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());

            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(DEFAULT_PORT);
            serverSocket.setNeedClientAuth(true);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
