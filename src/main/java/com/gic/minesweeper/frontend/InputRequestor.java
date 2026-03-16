package com.gic.minesweeper.frontend;

import com.gic.minesweeper.backend.service.MineTracker;
import com.gic.minesweeper.backend.utils.Location;
import com.gic.minesweeper.backend.utils.LocationUtils;

import java.util.Scanner;

public class InputRequestor {
    public static String getNumber(int minimumAreaSize, int maximumNumber, String additionalAllowedInput) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            String input = myObj.nextLine();
            if (input.trim().equalsIgnoreCase(additionalAllowedInput)) {
                return additionalAllowedInput;
            }
            try {
                input = input.trim();
                int number = Integer.parseInt(input);
                if (number < minimumAreaSize || number > maximumNumber) {
                    System.out.println("Invalid input.  Please try again.  (issue:  please input " + (minimumAreaSize == maximumNumber ? minimumAreaSize : (minimumAreaSize + " to " + maximumNumber)) + " only)");
                    continue;
                }
                return input;
            } catch (Exception e) {
                System.out.println("Invalid input.  Please try again.");
                continue;
            }
        }
    }

    public static String getAny() {
        Scanner myObj = new Scanner(System.in);
        return myObj.nextLine();
    }

    public static Location getLocation(int size, MineTracker mineTracker, String additionalAllowedInput) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            try {
                String input = myObj.nextLine();
                input = input.trim();
                if (input.equalsIgnoreCase(additionalAllowedInput)) {
                    return null;
                }
                if (input.length() > 2) {
                    System.out.println("Invalid input.  Please try again.  (issue:  input 2 characters only)");
                    continue;
                }
                Location location = LocationUtils.getLocation(input);
                if (location == null) {
                    System.out.println("Invalid input.  Please try again.");
                    continue;
                }
                if (location.row() < 1 || location.row() > size) {
                    System.out.println("Invalid input.  Please try again.  (issue:  row is outside the area)");
                    continue;
                }
                if (location.column() < 1 || location.column() > size) {
                    System.out.println("Invalid input.  Please try again.  (issue:  column is outside the area)");
                    continue;
                }
                if (mineTracker.getSquares().get(location.row() - 1).get(location.column() - 1).isOpened()) {
                    System.out.println("Invalid input.  Please try again.  (issue:  that square has been opened already before)");
                    continue;
                }
                return location;
            } catch (Exception e) {
                System.out.println("Invalid input.  Please try again.");
                continue;
            }
        }
    }
}
