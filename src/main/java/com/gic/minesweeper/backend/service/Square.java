package com.gic.minesweeper.backend.service;

import java.util.Objects;

public class Square {
    private int row;
    private int column;

    public void setHavingMine(boolean havingMine) {
        this.havingMine = havingMine;
    }

    private boolean havingMine;

    private boolean opened;

    public boolean isHavingMine() {
        return havingMine;
    }

    private int numberOfAdjacentSquaresWithMines;

    public Square(int column, int row) {
        this.column = column;
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row && column == square.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public void putMine() {
        this.havingMine = true;
    }

    public void open() {
        this.opened = true;
    }

    public boolean isOpened() {
        return opened;
    }

    public int getNumberOfAdjacentMines() {
        return numberOfAdjacentSquaresWithMines;
    }

    public void setNumberOfAdjacentSquaresWithMines(int numberOfAdjacentSquaresWithMines) {
        this.numberOfAdjacentSquaresWithMines = numberOfAdjacentSquaresWithMines;
    }
}
