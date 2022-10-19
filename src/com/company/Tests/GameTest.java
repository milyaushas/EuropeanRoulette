package com.company.Tests;

import com.company.Game;
import com.company.Player;
import org.junit.Test;
import org.junit.Assert;

public class GameTest {
    @Test
    public void rotateRouletteTest() {
        for (int i = 0; i < 10000; i++) {
            int res = Game.rotateRoulette();
            assert(res >= 0 && res <= 36);
        }
    }

    @Test
    public void resultTest() {
        for (int i = 0; i <= 36; i++) {
            for (int j = 0; j <= 36; j++) {
                Player player = new Player(0, i);
                Game game = new Game(player);
                Assert.assertEquals(i == j, game.result(j));
            }

            Player player1 = new Player(0, -1);
            Game game1 = new Game(player1);
            Assert.assertEquals(i % 2 == 1, game1.result(i));

            Player player2 = new Player(0, -2);
            Game game2 = new Game(player2);
            Assert.assertEquals(i % 2 == 0, game2.result(i));
        }
    }

    @Test
    public void updateScore() {
        for (int i = 0; i <= 100; i++) {
            int score = (int) (Math.random() * 1000);
            int bit = (int) (Math.random() * 1000);
            int field = Game.rotateRoulette();
            Player player = new Player(score, bit, field);
            Game game = new Game(player);
            game.updateScore(true);
            Assert.assertEquals(score + bit * 36L, game.player.score);
        }

        for (int i = 0; i <= 100; i++) {
            int score = (int) (Math.random() * 1000);
            int bit = (int) (Math.random() * 1000);
            int field = Game.rotateRoulette();
            Player player = new Player(score, bit, field);
            Game game = new Game(player);
            game.updateScore(false);
            Assert.assertEquals(score - bit, game.player.score);
        }

        for (int i = 0; i <= 100; i++) {
            int score = (int) (Math.random() * 1000);
            int bit = (int) (Math.random() * 1000);
            int field = -((int) (Math.random() * 2) + 1);
            Player player = new Player(score, bit, field);
            Game game = new Game(player);
            game.updateScore(true);
            Assert.assertEquals(score + bit, game.player.score);
        }

        for (int i = 0; i <= 100; i++) {
            int score = (int) (Math.random() * 1000);
            int bit = (int) (Math.random() * 1000);
            int field = -((int) (Math.random() * 2) + 1);
            Player player = new Player(score, bit, field);
            Game game = new Game(player);
            game.updateScore(false);
            Assert.assertEquals(score - bit, game.player.score);
        }
    }
}
