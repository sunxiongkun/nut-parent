package com.sxk.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BlockSocketServer {

  static int port = 8000;

  public static void main(String[] args) {

    try {
      ServerSocket server = new ServerSocket(port);
      System.out.println("InetAddress:" + server.getInetAddress());
      System.out.println("LocalSocketAddress:" + server.getLocalSocketAddress());
      // server将一直等待连接的到来
      System.out.println("server将一直等待连接的到来");
      Socket socket = server.accept();
      // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
      InputStream inputStream = socket.getInputStream();

      OutputStream outputStream = socket.getOutputStream();
      outputStream
          .write("Hello Client,please input your password.".getBytes(StandardCharsets.UTF_8));

      byte[] bytes = new byte[1024];
      int len;
      StringBuilder sb = new StringBuilder();
      while ((len = inputStream.read(bytes)) != -1) {
        //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
        sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
      }
      System.out.println("get message from client: " + sb);

      inputStream.close();
      outputStream.close();
      socket.close();
      server.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
