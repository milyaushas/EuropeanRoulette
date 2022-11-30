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
        for (int i = 0; i < players_num; i++) {
            Scanner in = new Scanner(System.in);
            Player player = players[i];

            System.out.println(player.name + ", у вас есть " + player.score + " очков. Введите ставку");
            player.bet = in.nextInt();

            System.out.println("Введите -1, если будете ставить на цвет, 1 -- если на число");
            player.field = in.nextInt();

            if (player.field < 0) {
                System.out.println("Введите 1, если будете ставить на красный, 2 -- если на чёрный");
            } else {
                System.out.println("Выберете число от 0 до 36");
            }

            player.field *= in.nextInt();
            if (result(i, rotateRoulette())) {
                System.out.print("Победа!\n\n\n");
                updateScore(i, true);
            } else {
                updateScore(i, false);
                System.out.print("Поражение :(\n\n\n");
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
