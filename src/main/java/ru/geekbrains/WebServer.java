package ru.geekbrains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WebServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8088)) { // С помощью ServerSocket начинаем слушать порт

            while(true) {
                Socket socket = serverSocket.accept(); // Method accept подключает к нам клиента, и получаем socket, через который можем коммуницировать с клиентом.
                System.out.println("New client connected!");
                // Socket имеет возможность получать и отсылать данные через getInputStream() и getOutputStread()
                BufferedReader input = new BufferedReader( // BufferedReader преобразуется пресылаемые в байти в символы и строки
                        new InputStreamReader(
                                socket.getInputStream(), StandardCharsets.UTF_8));

                PrintWriter output = new PrintWriter(socket.getOutputStream());

                while (!input.ready()) ;

                while (input.ready()) {
                    System.out.println(input.readLine());
                }

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charest=utf-8");
                output.println();
                output.println("<h1>Greetings from the server<h1>");
                output.flush();

                input.close();
                output.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Continue from 39:00