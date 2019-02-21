package com.example.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yaozhengming
 * @date 2019.2.20
 */
public class BIOServerV3 {
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) {
        int port = 9010;
        int threads = 100;
        ExecutorService tpool = Executors.newFixedThreadPool(threads);
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                Socket s = ss.accept();

                //丢进线程池
                tpool.execute(new SocketProcess(s));
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
