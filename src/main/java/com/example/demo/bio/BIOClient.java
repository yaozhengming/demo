package com.example.demo.bio;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author yaozhengming
 * @date 2019.2.20
 */
public class BIOClient implements Runnable {
    private String host;
    private int port;
    private Charset charset = Charset.forName("UTF-8");

    public BIOClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }


    @Override
    public void run() {
        try (Socket s = new Socket(host, port); OutputStream out = s.getOutputStream();) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入：");
            String mess = scanner.nextLine();
            out.write(mess.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BIOClient bioClient = new BIOClient("localhost",9200);
        bioClient.run();
    }

}
