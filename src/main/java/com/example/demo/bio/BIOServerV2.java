package com.example.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author yaozhengming
 * @date 2019.2.20
 */
public class BIOServerV2 {
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) {
        int port = 9010;
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                Socket s = ss.accept();
                //新开一个线程处理这个连接
                new Thread(new SocketProcess(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class SocketProcess implements Runnable {
        Socket s;

        public SocketProcess(Socket s) {
            super();
            this.s = s;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader;

                reader = new BufferedReader(
                        new InputStreamReader(s.getInputStream(), charset));
                String mess;
                //连接数据
                while ((mess = reader.readLine()) != null) {
                    System.out.println(mess);
                }
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
