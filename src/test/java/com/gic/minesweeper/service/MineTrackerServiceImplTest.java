package com.gic.minesweeper.service;

import com.gic.minesweeper.backend.exception.InvalidRequestException;
import com.gic.minesweeper.backend.exception.SteppedOnMineException;
import com.gic.minesweeper.backend.service.MineTracker;
import com.gic.minesweeper.backend.service.MineTrackerService;
import com.gic.minesweeper.backend.service.MineTrackerServiceImpl;
import com.gic.minesweeper.backend.service.Square;
import com.gic.minesweeper.frontend.MineTrackerDisplayer;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MineTrackerServiceImplTest {

    MineTrackerService mineTrackerService = new MineTrackerServiceImpl();

    @Test
    public void createMineTrackerSuccessful() {
        MineTracker mineTracker = mineTrackerService.create(2, 1);

        Assert.assertEquals(2, mineTracker.getSize());
        Assert.assertEquals(2, mineTracker.getSquares().size());
        Assert.assertEquals(2, mineTracker.getSquares().get(0).size(), 2);

        int numberOfMinesAdded = 0;
        for (List<Square> row : mineTracker.getSquares()) {
            for (Square square : row) {
                if (square.isHavingMine()) {
                    numberOfMinesAdded++;
                }
            }
        }
        Assert.assertEquals(1, numberOfMinesAdded);
    }

    @Test(expected = InvalidRequestException.class)
    public void createMineTrackerFailedInvalidSize() {
        MineTracker mineTracker = mineTrackerService.create(0, 1);
    }

    @Test(expected = InvalidRequestException.class)
    public void createMineTrackerFailedInvalidNumberOfMines() {
        MineTracker mineTracker = mineTrackerService.create(4, 0);
    }

    //test case:  sweep square, which has no adjacent mines, and therefore, recursive uncovering of adjacent squares that has no adjacent mines
    //6x6, mine is only in the row 2, column 2
    //user uncovers row 4, column 4, which has no adjacent mines
    //result:  recursive uncovering of adjacent squares which has no adjacent mines
    //result:  all square uncovered, except (row, column):  1,1; 1,2; 2,1; 2,2;
    @Test
    public void sweepSquareWithNoAdjacentMinesAndRecursivelyUncoversAdjacentSquaresWithNoAdjacentMines() {
        int size = 6;
        MineTracker mineTracker = mineTrackerService.create(size, 1);

        //force 2,2 as the only item with mine
        mineTracker.getSquares().forEach(row -> row.forEach(square -> square.setHavingMine(false)));
        mineTracker.getSquares().get(1).get(1).setHavingMine(true);

        //due to forcing in previous step, recalculate number of adjacent mines for all squares
        mineTracker.fillCountsOfAdjacentMines();

        //display for debugging, in case needed
        System.out.println("before user uncovers anything");
        MineTrackerDisplayer.displaySolutionSquaresWithMines(mineTracker);
        MineTrackerDisplayer.displaySolutionAdjacentMines(mineTracker);

        //user chooses square 4 4 to uncover
        mineTrackerService.sweep(mineTracker, 4, 4);

        //display for debugging, in case needed
        System.out.println("after user uncovers something");
        MineTrackerDisplayer.display(mineTracker);

        //check if correct
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                //these are the expected to remained uncovered
                if ((row == 1 && column == 1) || (row == 1 && column == 2) || (row == 2 && column == 1) || (row == 2 && column == 2)) {
                    Assert.assertFalse(mineTracker.getSquares().get(row - 1).get(column - 1).isOpened());
                } else {
                    //these are expected to be uncovered, including recursively
                    Assert.assertTrue(mineTracker.getSquares().get(row - 1).get(column - 1).isOpened());
                }
            }
        }
    }

    //test case:  sweep square, which has adjacent mines
    //6x6, mine is only in the row 2, column 2
    //user uncovers row 3, column 3, which has adjacent mines
    //result:  no recursive uncovering of adjacent squares which has no adjacent mines
    //result:  only 1 square uncovered (row, column):  3,3
    @Test
    public void sweepSquareWithAdjacentMine() {
        int size = 6;
        MineTracker mineTracker = mineTrackerService.create(size, 1);

        //force 2,2 as the only item with mine
        mineTracker.getSquares().forEach(row -> row.forEach(square -> square.setHavingMine(false)));
        mineTracker.getSquares().get(1).get(1).setHavingMine(true);

        //due to forcing in previous step, recalculate number of adjacent mines for all squares
        mineTracker.fillCountsOfAdjacentMines();

        //display for debugging, in case needed
        System.out.println("before user uncovers anything");
        MineTrackerDisplayer.displaySolutionSquaresWithMines(mineTracker);
        MineTrackerDisplayer.displaySolutionAdjacentMines(mineTracker);

        //user chooses square 3 3 to uncover
        mineTrackerService.sweep(mineTracker, 3, 3);

        //display for debugging, in case needed
        System.out.println("after user uncovers something");
        MineTrackerDisplayer.display(mineTracker);

        //check if correct
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                //this is expected to be uncovered
                if ((row == 3 && column == 3)) {
                    Assert.assertTrue(mineTracker.getSquares().get(row - 1).get(column - 1).isOpened());
                } else {
                    //these are the expected to remained uncovered
                    Assert.assertFalse(mineTracker.getSquares().get(row - 1).get(column - 1).isOpened());
                }
            }
        }
    }

    //test case:  player wins
    //6x6, mine is only in the row 2, column 2
    //user uncovers row 4, column 4, which has no adjacent mines
    //user uncovers row 1, column 1, which has adjacent mines
    //user uncovers row 1, column 2, which has adjacent mines
    //user uncovers row 2, column 1, which has adjacent mines
    //result:  all squares with no mine are uncovered, and identified as 'win'
    @Test
    public void playerWins() {
        int size = 6;
        MineTracker mineTracker = mineTrackerService.create(size, 1);

        //force 2,2 as the only item with mine
        mineTracker.getSquares().forEach(row -> row.forEach(square -> square.setHavingMine(false)));
        mineTracker.getSquares().get(1).get(1).setHavingMine(true);

        //due to forcing in previous step, recalculate number of adjacent mines for all squares
        mineTracker.fillCountsOfAdjacentMines();

        //display for debugging, in case needed
        System.out.println("before user uncovers anything");
        MineTrackerDisplayer.displaySolutionSquaresWithMines(mineTracker);
        MineTrackerDisplayer.displaySolutionAdjacentMines(mineTracker);

        //user uncovers squares
        mineTrackerService.sweep(mineTracker, 4, 4);
        mineTrackerService.sweep(mineTracker, 1, 1);
        mineTrackerService.sweep(mineTracker, 1, 2);
        mineTrackerService.sweep(mineTracker, 2, 1);

        //display for debugging, in case needed
        System.out.println("after user uncovers something");
        MineTrackerDisplayer.display(mineTracker);

        //check if correct
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                //these are the expected to remained uncovered
                if ((row == 2 && column == 2)) {
                    Assert.assertFalse(mineTracker.getSquares().get(row - 1).get(column - 1).isOpened());
                } else {
                    //this is expected to be uncovered
                    Assert.assertTrue(mineTracker.getSquares().get(row - 1).get(column - 1).isOpened());
                }
            }
        }
        Assert.assertTrue(mineTracker.allNonMineSquaresOpened());
    }

    //test case:  player loses
    //6x6, mine is only in the row 2, column 2
    //user uncovers row 2, column 2, which has mine
    //result:  SteppedOnMineException is thrown; identified as 'loss'
    @Test(expected = SteppedOnMineException.class)
    public void playerLoses() {
        int size = 6;
        MineTracker mineTracker = mineTrackerService.create(size, 1);

        //force 2,2 as the only item with mine
        mineTracker.getSquares().forEach(row -> row.forEach(square -> square.setHavingMine(false)));
        mineTracker.getSquares().get(1).get(1).setHavingMine(true);

        //due to forcing in previous step, recalculate number of adjacent mines for all squares
        mineTracker.fillCountsOfAdjacentMines();

        //display for debugging, in case needed
        System.out.println("before user uncovers anything");
        MineTrackerDisplayer.displaySolutionSquaresWithMines(mineTracker);
        MineTrackerDisplayer.displaySolutionAdjacentMines(mineTracker);

        //user uncovers squares
        mineTrackerService.sweep(mineTracker, 2, 2);
    }
}
