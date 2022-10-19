package com.company;

import java.io.IOException;

public class Game {
    private Player player;

    public Game() {
        player = new Player();
    }

    public Game(Player player) {
        this.player = player;
    }

    public void start() throws IOException {
        System.out.println("Выберете число от 0 до 36");
        this.player.field = System.in.read();
        if (result(rotateRoulette())) {
            System.out.print("Победа!");
        }
        else {
            System.out.print("Поражение :(");
        }
    }

    public boolean result(int resultRotate) {
        return this.player.field == resultRotate;
    }

    public static int rotateRoulette() {
        return (int) (Math.random() * 37);
    }
}
