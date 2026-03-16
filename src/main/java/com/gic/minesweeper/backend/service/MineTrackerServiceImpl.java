package com.gic.minesweeper.backend.service;

import com.gic.minesweeper.backend.exception.InvalidRequestException;
import com.gic.minesweeper.backend.exception.SteppedOnMineException;

public class MineTrackerServiceImpl implements MineTrackerService {

    @Override
    public MineTracker create(int size, int numberOfMines) {
        if (size < 1 || numberOfMines < 1) {
            throw new InvalidRequestException();
        }
        return new MineTracker(size, numberOfMines);
    }

    @Override
    public void sweep(MineTracker minetracker, int row, int column) {
        Square square = minetracker.getSquares().get(row - 1).get(column - 1);
        if (square.isHavingMine()) {
            throw new SteppedOnMineException();
        }
        if (square.isOpened()) {
            return;
        }
        square.open();
        minetracker.setNumberOfUncoveredSquares(minetracker.getNumberOfUncoveredSquares() + 1);
        if (square.getNumberOfAdjacentMines() > 0) {
            return;
        }

        int size = minetracker.getSize();

        for (int rowToCheck = row - 1; rowToCheck <= row + 1; rowToCheck++) {
            for (int columnToCheck = column - 1; columnToCheck <= column + 1; columnToCheck++) {
                if ((rowToCheck == row && columnToCheck == column) || rowToCheck < 1 || rowToCheck > size || columnToCheck < 1 || columnToCheck > size) {
                    continue;
                }
                //if (minetracker.getSquares().get(rowToCheck - 1).get(columnToCheck - 1).getNumberOfAdjacentMines() == 0) {
                sweep(minetracker, rowToCheck, columnToCheck);
                //}
            }
        }
    }
}
