package com.sxk.io;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOSocket {


  public static void main(String[] args) throws Exception {
    nioServer();
  }

  public static void nioServer() throws Exception {
    // 打开一个未绑定的server socket channel
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    Selector selector = Selector.open();// 创建一个Selector
    serverChannel.configureBlocking(false);//设置非阻塞模式
    //将ServerSocketChannel注册到Selector
    serverChannel.register(selector, SelectionKey.OP_READ);

    while (true) {
      int readyChannels = selector.select();
      if (readyChannels == 0) {
        continue;
      }
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        if (key.isAcceptable()) {
          // a connection was accepted by a ServerSocketChannel.
        } else if (key.isConnectable()) {//连接就绪
          // a connection was established with a remote server.
        } else if (key.isReadable()) {//读就绪
          // a channel is ready for reading
        } else if (key.isWritable()) {//写就绪
          // a channel is ready for writing
        }
        keyIterator.remove();
      }
    }
  }
}
