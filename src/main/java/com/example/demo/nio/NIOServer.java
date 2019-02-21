package com.example.demo.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yaozhengming
 * @date 2019.2.20
 */
public class NIOServer {
    private static Charset charset = Charset.forName("UTF-8");

    private static CharsetDecoder decoder = charset.newDecoder();

    public static void main(String[] args) throws IOException {
        //1、创建一个selector
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        int port = 9200;
        ssc.bind(new InetSocketAddress(port));

        //2、注册到selecotr
        //设置为非阻塞
        ssc.configureBlocking(false);

        //ssc向selecotr注册，监听连接到来
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //连接计数
        int connectionCount = 0;
        //线程数
        int threads = 3;
        ExecutorService tpool = Executors.newFixedThreadPool(threads);

        while (true) {
            //阻塞等待就绪的事件
            int readyChannelsCount = selector.select();
            //因为select()阻塞可被中断
            if (readyChannelsCount == 0) {
                continue;
            }

            //得到就绪的channel的key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    //接收连接
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //selector监听数据到了没
                    //设置非阻塞
                    socketChannel.configureBlocking(false);

                    //向selecotr注册
                    socketChannel.register(selector, SelectionKey.OP_READ, ++connectionCount);


                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {//读就绪
                    tpool.execute(new SocketProcess(key));
                    //取消注册 避免重复选择
                    key.cancel();

                } else if (key.isWritable()) {//写就绪

                }

                keyIterator.remove();//处理后，从selectedKey集合中移除
            }


        }


    }

    static class SocketProcess implements Runnable {
        SelectionKey key;

        public SocketProcess(SelectionKey key) {
            super();
            this.key = key;
        }

        @Override
        public void run() {
            try {
                System.out.println("连接" + key.attachment() + "发来了：" + readFromChannel());
                //如果连接不需要，则关闭
                key.channel().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private String readFromChannel() throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();

            int bfsize = 1024;

            ByteBuffer rbf = ByteBuffer.allocateDirect(bfsize);

            //定义一个更大的buffer
            ByteBuffer bigBf = null;

            //读的次数
            int count = 0;
            while ((sc.read(rbf)) != -1) {
                count++;
                ByteBuffer temp = ByteBuffer.allocateDirect(bfsize * (count + 1));
                if (bigBf != null) {
                    //转为读模式
                    bigBf.flip();
                    temp.put(bigBf);
                }

                bigBf = temp;

                //将这次读到的数据放入大buffer
                rbf.flip();
                bigBf.put(rbf);
                //为下次读，清空buffer
                rbf.clear();

            }

            if (bigBf != null) {
                bigBf.flip();
                try {
                    //字节转为字符，返回字符串
                    return decoder.decode(bigBf).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

    }
}
