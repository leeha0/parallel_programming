package chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex_6_2_ThreadPerTaskWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            new Thread(task).start();
        }
    }

    private static void handleRequest(Socket connection) {
        // Do Working
    }
}
