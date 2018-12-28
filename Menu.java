package lab;
import static java.lang.Math.*;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    //final long size = (long) pow(2,63);
    final long size = 8;
    Scanner in = new Scanner(System.in);
    Random rand = new Random();
    final int n = rand.nextInt(127) + 1;

    public Box create(){
        System.out.println("Wprowadź dane według wzoru:");
        System.out.println("Pierwsza liczba, znak operacji, druga liczba");
        System.out.println("Przykład: 5 + 5");
        System.out.println("Każdy znak oddziel spacją");
        System.out.println("Jesli chcesz obliczyć silnie podaj tylko jedną liczbę");
        //zczytanie danych od użytkownika
        String d;
        String [] split;
        try {
        d = in.nextLine();
        split = d.split(" ");
        if (d.length() == 1){
            System.out.println("silnia");
            int status = 1;
            int nrs = n;
            long pierwsza = 0;
            if (Integer.parseInt(split[0]) > size) {
                System.out.println("błąd - pierwsza liczba za duża");
                create();
            } else {
                pierwsza = Integer.parseInt(split[0]);
            }

            Box eeee = new Box('+', status, nrs, false, pierwsza);
            return eeee;
        }
            //pierwsza liczba
            long pierwsza = 0;
            if (Integer.parseInt(split[0]) > size) {
                System.out.println("błąd - pierwsza liczba za duża");
                create();
            } else {
                pierwsza = Integer.parseInt(split[0]);
            }

            //druga liczba
            long druga = Integer.parseInt(split[2]);
            if (Integer.parseInt(split[2]) > size) {
                System.out.println("błąd - pierwsza liczba za duża");
                create();
            } else {
                druga = Integer.parseInt(split[2]);
            }

            // status
            int status = 1;

            // losowo generowany numer sesji
            int nrs = n;

            // rozszerzenie oraz znak operacji
            char operacja;
            boolean rozszerzenie = true;
            if (Integer.parseInt(split[2]) <= 0 || Integer.parseInt(split[2]) >= 0) {
                rozszerzenie = true;
                operacja = split[1].charAt(0);
            } else {
                rozszerzenie = false;
                operacja = '+';
            }

            Box boxik = new Box(operacja, status, nrs, rozszerzenie, pierwsza, druga);
            return boxik;
        } catch (NumberFormatException n){
            System.out.println("Zly format danych.");
        }
        Box error = new Box();
        return error;
    }
}
