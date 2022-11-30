package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    public final int players_num;
    public Player[] players;

    public Game(int players_num) {
        this.players_num = players_num;
        this.players = new Player[players_num];
        for (int i = 0; i < players_num; i++){
            String name = "Игрок " + Integer.toString(i + 1);
            this.players[i] = new Player(name);
        }
    }

    public Game(Player player) {
        players_num = 1;
        players = new Player[1];
        players[0] = player;
    }

    public void start() throws IOException {
        while (true) {
            Scanner in = new Scanner(System.in);
            for (int i = 0; i < players_num; i++) {
                Player player = players[i];
                if (player.score <= 0) {
                    System.out.println(player.name + ", у вас недостаточно очков, чтобы продолжать игру.");
                    continue;
                }

                System.out.println(player.name + ", у вас есть " + player.score + " очков. Введите ставку");
                player.bet = in.nextInt();
                while (player.bet < 0 || player.bet > player.score) {
                    System.out.println("Ставка слишком большая или маленькая. Выберите другую");
                    player.bet = in.nextInt();
                }

                System.out.println("Введите -1, если будете ставить на цвет, 1 -- если на число");
                player.field = in.nextInt();

                if (player.field < 0) {
                    System.out.println("Введите 1, если будете ставить на красный, 2 -- если на чёрный");
                } else {
                    System.out.println("Выберите число от 0 до 36");
                }

                player.field *= in.nextInt();
            }

            int roulette_result = rotateRoulette();
            for (int i = 0; i < players_num; i++) {
                if (players[i].score <= 0) {
                    continue;
                }
                if (result(i, roulette_result)) {
                    System.out.println(players[i].name + ", вы выиграли :)\n");
                    updateScore(i, true);
                } else {
                    updateScore(i, false);
                    System.out.println(players[i].name + ", вы проиграли :(\n");
                }
            }
            System.out.println("Введите 1, если хотите продолжить игру, или любое другое число, если хотите закончить");
            int num = in.nextInt();
            if (num != 1) {
                break;
            }
        }
    }

    public void updateScore(int player_ind, boolean isWin) {
        if (isWin) {
            if (players[player_ind].field < 0) {
                players[player_ind].score += players[player_ind].bet;
            } else {
                players[player_ind].score += players[player_ind].bet * 36;
            }
        }
        else {
            players[player_ind].score -= players[player_ind].bet;
        }
    }

    public boolean result(int player_ind, int resultRotate) {
        if (players[player_ind].field < 0) {
            return (-players[player_ind].field) % 2 == resultRotate % 2;
        }
        return this.players[player_ind].field == resultRotate;
    }

    public static int rotateRoulette() {
        return (int) (Math.random() * 37);
    }
}
