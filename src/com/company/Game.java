package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    public Player player;

    public Game() {
        player = new Player();
    }

    public Game(Player player) {
        this.player = player;
    }

    public void start() throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("У вас доступно " + player.score + ". Введите ставку");
        this.player.bet = in.nextInt();

        System.out.println("Введите -1, если будете ставить на цвет, 1 -- если на число");
        this.player.field = in.nextInt();

        if (player.field < 0) {
            System.out.println("Введите 1, если будете ставить на красный, 2 -- если на чёрный");
        }
        else {
            System.out.println("Выберете число от 0 до 36");
        }

        this.player.field *= in.nextInt();
        if (result(rotateRoulette())) {
            System.out.print("Победа!");
            updateScore(true);
        }
        else {
            updateScore(false);
            System.out.print("Поражение :(");
        }
    }

    public void updateScore(boolean isWin) {
        if (isWin) {
            if (player.field < 0) {
                player.score += player.bet;
            } else {
                player.score += player.bet * 36;
            }
        }
        else {
            player.score -= player.bet;
        }
    }

    public boolean result(int resultRotate) {
        if (player.field < 0) {
            return (-player.field) % 2 == resultRotate % 2;
        }
        return this.player.field == resultRotate;
    }

    public static int rotateRoulette() {
        return (int) (Math.random() * 37);
    }
}
