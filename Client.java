package lab;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;


public class Client {

    public static boolean same(Box y, Box x){
        if(y.operation==x.operation && y.status==x.status && y.nrs==x.nrs && y.rozszerzenie==x.rozszerzenie && y.pierwsza==x.pierwsza && y.druga==x.druga){
            return true;
        } else return false;
    }

    public static boolean checknrs(Box y, Box x) {
        if(y.nrs==x.nrs) return true;
        else return false;
    }

    static List<Box> historia = new LinkedList<Box>();
    public static void his(){
        System.out.println("chcesz szukac poprzez nr sesji(nrs) czy poprzez operator(operator)?");
        Scanner in = new Scanner(System.in);
        String d = in.nextLine();
        if (d.equals("nrs")) {
            System.out.println("po jakim numerze?");
            String p=in.nextLine();
            int x = Integer.parseInt(p);
            for(int i=0; i<historia.size(); i++) {
                if (historia.get(0).nrs==x) historia.get(i).wyswietl();
            }
        }
        if(d.equals("operator")) {
            System.out.println("po jakim operatorze");
            String p=in.nextLine();
            for(int i=0; i<historia.size(); i++) {
                if (historia.get(0).operation==p.charAt(0)) historia.get(i).wyswietl();
            }
        }
    }

    public static void main(String args[]) throws Exception {

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IP = InetAddress.getByName("127.0.0.1");

        byte[] sendData = new byte[19];
        byte[] receiveData = new byte[19];
        Scanner in = new Scanner(System.in);
        Menu m = new Menu();
        boolean x = true;
        while (x) {
            int whatToDo;
            System.out.println("Co robimy? \n 1. Działamy! \n 2. Historia. \n 3. Wyjście. \n");
            whatToDo = in.nextInt();
            if (whatToDo == 1) {

                // client input and sending message
                Box c = new Box();
                Box a = m.create();
                System.out.println("MESSAGE FOR SERVER: ");
                a.wyswietl();
                c.wyswietl();
                if (same(a,c)) System.out.println("Nie wysłano pakietu. Pakiet pusty. \n");
                else {
                    sendData = a.pakuj();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, 2137);
                    clientSocket.send(sendPacket);
                    historia.add(a);

                    // response from server
                    Box b = new Box();
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    b = b.rozpakuj(receivePacket.getData());
                    System.out.println("RESPONSE FROM SERVER: ");
                    if (checknrs(b, a)) b.wyswietl();
                    else System.out.println("odebrano zly pakiet");
                }
            } else if (whatToDo == 2) {
                his();
            } else if (whatToDo == 3) {
                x = false;
            }
        }
        clientSocket.close();
    }
}
