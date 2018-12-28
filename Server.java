package lab;

import java.io.*;
import java.net.*;

public class Server {

    static Box oblicz(Box x) {
        long y = x.pierwsza;
        if (x.rozszerzenie) {
            if (x.operation == '*') {
                if (x.pierwsza * x.druga < x.rozmiar) x.pierwsza = x.pierwsza * x.druga;
            }
            if (x.operation == '/') {
                if (x.druga != 0) x.pierwsza = (long) x.pierwsza / x.druga;
            }
            if (x.operation == '+') {
                if (x.pierwsza + x.druga < x.rozmiar) x.pierwsza = x.pierwsza + x.druga;
            }
            if (x.operation == '-') {
                x.pierwsza = x.pierwsza - x.druga;
            }
        } else {
            x.pierwsza = x.silnia(x.pierwsza);
        }
        if (y == x.pierwsza) {
            x.status = 3;
        } else x.status = 2;
        return x;
    }


    public static void main(String args[]) throws Exception {

        DatagramSocket serverSocket = new DatagramSocket(2137);
        byte[] receiveData = new byte[19];
        byte[] sendData = new byte[19];

        while (true) {
            // message from client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            Box c = new Box();
            c = c.rozpakuj(receivePacket.getData());
            System.out.println("MESSAGE FROM CLIENT: ");
            c.wyswietl();
            oblicz(c);
            InetAddress IP = receivePacket.getAddress();
            int port = receivePacket.getPort();

            // sending response to client
            System.out.println("MESSAGE TO CLIENT: ");
            c.wyswietl();
            sendData = c.pakuj();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, port);
            serverSocket.send(sendPacket);
        }
    }

}
