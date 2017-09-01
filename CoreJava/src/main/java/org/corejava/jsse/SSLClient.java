package org.corejava.jsse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {

    private SSLSocket socket = null;

    public SSLClient() throws IOException {
        // 通过套接字工厂，获取一个客户端套接字
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
    }

    public void connect() throws IOException {

        // 获取客户端套接字输出流
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        // 将用户名和密码通过输出流发送到服务器端
        String userName = "principal";
        output.println(userName);
        String password = "credential";
        output.println(password);
        output.flush();

        // 获取客户端套接字输入流
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 从输入流中读取服务器端传送的数据内容，并打印出来
        String response = input.readLine();
        response += "\n " + input.readLine();
        System.out.println(response);

        // 关闭流资源和套接字资源
        input.close();
        socket.close();
    }

    public static void main(String args[]) throws IOException {
        new SSLClient().connect();
    }
}
