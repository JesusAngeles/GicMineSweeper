package com.gic.minesweeper.backend.service;

public interface MineTrackerService {
    MineTracker create(int size, int numberOfMines);

    void sweep(MineTracker minetracker, int row, int column);
}
