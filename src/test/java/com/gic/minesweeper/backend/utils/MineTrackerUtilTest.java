package com.gic.minesweeper.backend.utils;

import org.junit.Assert;
import org.junit.Test;

public class MineTrackerUtilTest {

    @Test
    public void getMaximumNumberOfMines() {
        Assert.assertEquals(1, MineTrackerUtil.getMaximumNumberOfMines(2));
        Assert.assertEquals(3, MineTrackerUtil.getMaximumNumberOfMines(3));
        Assert.assertEquals(35, MineTrackerUtil.getMaximumNumberOfMines(10));
    }
}
