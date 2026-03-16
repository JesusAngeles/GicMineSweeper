package com.gic.minesweeper.backend.service;

import com.gic.minesweeper.backend.utils.LocationUtils;
import com.gic.minesweeper.backend.utils.RandomIntegerProvider;

import java.util.ArrayList;
import java.util.List;

public class MineTracker {
    private final int size;
    private List<List<Square>> squares;
    private int numberOfMines;

    private int numberOfUncoveredSquares;

    public int getNumberOfUncoveredSquares() {
        return numberOfUncoveredSquares;
    }

    public void setNumberOfUncoveredSquares(int numberOfUncoveredSquares) {
        this.numberOfUncoveredSquares = numberOfUncoveredSquares;
    }

    public boolean playerHasWon() {
        return numberOfUncoveredSquares + numberOfMines == size * size;
    }

    public MineTracker(int size, int numberOfMines) {
        this.size = size;
        this.numberOfMines = numberOfMines;
        squares = new ArrayList<>();
        for (int row = 1; row <= size; row++) {
            List<Square> ls = new ArrayList<>();
            for (int column = 1; column <= size; column++) {
                ls.add(new Square(row, column));
            }
            squares.add(ls);
        }
        this.fillMines(numberOfMines);
        this.fillCountsOfAdjacentMines();
    }

    public void fillCountsOfAdjacentMines() {
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                int numberOfAdjacentMines = 0;
                for (int rowToCheck = row - 1; rowToCheck <= row + 1; rowToCheck++) {
                    for (int columnToCheck = column - 1; columnToCheck <= column + 1; columnToCheck++) {
                        if ((rowToCheck == row && columnToCheck == column) || rowToCheck < 1 || rowToCheck > size || columnToCheck < 1 || columnToCheck > size) {
                            continue;
                        }
                        if (squares.get(rowToCheck - 1).get(columnToCheck - 1).isHavingMine()) {
                            numberOfAdjacentMines++;
                        }
                    }
                }
                squares.get(row - 1).get(column - 1).setNumberOfAdjacentSquaresWithMines(numberOfAdjacentMines);
            }
        }
    }

    public int getSize() {
        return size;
    }

    private void fillMines(int numberOfMines) {

        int minesAdded = 0;
        while (minesAdded < numberOfMines) {
            int randomNumber = RandomIntegerProvider.getRandomNumber(this.getSize());
            Square square = squares.get(LocationUtils.getRowNumber(randomNumber, size) - 1).get(LocationUtils.getColumnNumber(randomNumber, size) - 1);
            if (square.isHavingMine()) {
                continue;
            }
            square.putMine();
            minesAdded++;
        }

    }

    public boolean allNonMineSquaresOpened() {

        for (List<Square> rows : squares) {
            for (Square square : rows) {
                if (!square.isHavingMine()) {
                    if (!square.isOpened()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<List<Square>> getSquares() {
        return squares;
    }
}
