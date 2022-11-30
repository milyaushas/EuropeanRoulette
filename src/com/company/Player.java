package com.company;

public class Player {
    public String name = "Игрок";
    public int score = 1000;
    public int bet = 100;
    public int field;

    public Player() {
    }
    public Player(String name){
        this.name = name;
    }
    public Player(int bet, int field) {
        this.bet = bet;
        this.field = field;
    }
    public Player(int score, int bet, int field) {
        this.score = score;
        this.bet = bet;
        this.field = field;
    }
    public Player(int score) {
        this.score = score;
    }
}
