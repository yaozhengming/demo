package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author yaozhengming
 * @date 2019.2.20
 */
public class NIOClient implements Runnable {
    private String host;
    private int port;
    private Charset charset = Charset.forName("UTF-8");
    private static boolean input = true;

    public NIOClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }


    @Override
    public void run() {
        try (SocketChannel sc = SocketChannel.open()) {
            //连接会阻塞
            boolean connected = sc.connect(
                    new InetSocketAddress(host, port));

            System.out.println("connected:" + connected);

            //写
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入：");
            String mess = scanner.nextLine();
            if (mess.equalsIgnoreCase("cyrus")) {
                input = false;
                mess="已退出";
            }
            ByteBuffer bf = ByteBuffer.wrap(mess.getBytes(charset));

            while (bf.hasRemaining()) {
                int writedCount = sc.write(bf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        do {
            NIOClient nioClient = new NIOClient("localhost", 9200);
            nioClient.run();
        } while (input);

    }
}
