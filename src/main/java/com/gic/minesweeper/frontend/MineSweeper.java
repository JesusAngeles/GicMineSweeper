package com.gic.minesweeper.frontend;

import com.gic.minesweeper.backend.exception.SteppedOnMineException;
import com.gic.minesweeper.backend.service.MineTracker;
import com.gic.minesweeper.backend.service.MineTrackerServiceImpl;
import com.gic.minesweeper.backend.utils.Location;
import com.gic.minesweeper.backend.utils.MineTrackerUtil;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MineSweeper {
    private static final String quit = "q";
    private static int minimumAreaSize = 2;
    private static int maximumAreaSize = 9;

    private static boolean debug;

    public static void main(String[] args) {
        if (System.getProperty("debug") != null) {
            debug = true;
        }
        new MineSweeper().play();
    }

    private void play() {

        while (true) {
            System.out.println("Welcome to Minesweeper!");
            System.out.println("(At any time, input q to quit)");
            System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): (minimum of " + minimumAreaSize + ", maximum of " + maximumAreaSize + ")");
            String request = InputRequestor.getNumber(minimumAreaSize, maximumAreaSize, quit);
            if (request.equalsIgnoreCase(quit)) {
                break;
            }
            int requestedSize = Integer.parseInt(request);
            int maxNumberOfMines = MineTrackerUtil.getMaximumNumberOfMines(requestedSize);
            System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares, which is " + maxNumberOfMines + "): ");
            request = InputRequestor.getNumber(1, maxNumberOfMines, quit);
            if (request.equalsIgnoreCase(quit)) {
                break;
            }
            int requestedNumberOfMines = Integer.parseInt(request);

            playGame(requestedSize, requestedNumberOfMines);

            System.out.println("Press any key to play again...");
            request = InputRequestor.getAny();
            if (request.equalsIgnoreCase(quit)) {
                break;
            }
        }
        System.out.println("We hope you had a nice time! See you again!");
    }


    private void playGame(int size, int numberOfMines) {
        MineTracker mineTracker = new MineTrackerServiceImpl().create(size, numberOfMines);
        if (debug) {
            MineTrackerDisplayer.displaySolutionSquaresWithMines(mineTracker);
            MineTrackerDisplayer.displaySolutionAdjacentMines(mineTracker);
        }
        Location request;
        while (true) {
            MineTrackerDisplayer.display(mineTracker);

            System.out.print("Select a square to reveal (e.g. A1): ");
            request = InputRequestor.getLocation(size, mineTracker, quit);
            if (request == null) {
                break;
            }
            try {
                new MineTrackerServiceImpl().sweep(mineTracker, request.row(), request.column());
            } catch (
                    SteppedOnMineException e) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                break;
            }
            if (mineTracker.playerHasWon()) {
                System.out.println("Congratulations, you have won the game!");
                break;
            }
        }
        if (debug) {
            MineTrackerDisplayer.displaySolutionSquaresWithMines(mineTracker);
            MineTrackerDisplayer.displaySolutionAdjacentMines(mineTracker);
        }
    }
}