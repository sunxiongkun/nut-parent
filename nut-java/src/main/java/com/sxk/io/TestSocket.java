package com.sxk.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocket {


  public static void main(String[] args) throws Exception {
    ServerSocket server = new ServerSocket(8090);
    System.out.println("step1:new serverSocket..");
    while (true) {
      Socket client = server.accept();
      System.out.println("step2:client" + client.getPort());
      new Thread(() -> {
        try {
          InputStream is = client.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          System.out.println(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();

    }
  }

}
