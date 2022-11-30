package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите количество игроков (число от 1 до 6)");
        int num = in.nextInt();
        while (num < 1 || num > 6) {
            System.out.println("Некорректное число игроков. В игре могут участвовать от 1 до 6 человек. Снова введите количество игроков");
            num = in.nextInt();
        }

        Game game = new Game(num);
        game.start();
   }
}