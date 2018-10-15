package edu.xmu.test.javase.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TryCatchSample {
    public static void main(String[] args) throws IOException {
        //try (ServerSocket serverSocket = new ServerSocket(12345)) {
        //	while (true) {
        //		try (Socket socket = serverSocket.accept();
        //				DataInputStream dis = new DataInputStream(socket.getInputStream());
        //				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());) {
        //			// do some stuffs here
        //		} catch (IOException e) {
        //			e.printStackTrace();
        //		}
        //	}
        //}

        try {
            System.out.println("try");
            throw new Exception("hello");
        } catch (Exception e) {
            System.out.println("catch exception");
            System.out.println("catch exception");
            System.out.println("catch exception");
            System.out.println("catch exception");
            System.out.println("catch exception");
            return;
        } finally {
            System.out.println("finally");
        }
    }
}
