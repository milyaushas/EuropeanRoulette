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
        }
    }
}
