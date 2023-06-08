package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int moveCount = 0;
        char[][] game = new char[3][3];
        for (int i = 0, k = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game[i][j] = '_';
            }
        }
        printTicTacToe(game);
        int[] coordinates = new int[2];
        while (moveCount < 9) {
            coordinates = getInputCoordinates(sc);
            char turn = moveCount % 2 == 0 ? 'X' : 'O';
            while (!isCoordinatesValid(game, coordinates[0], coordinates[1])) {
                coordinates = getInputCoordinates(sc);
            }
            game[coordinates[0] - 1][coordinates[1] - 1] = turn;
            printTicTacToe(game);
            moveCount++;
            int status = getGameStatus(game, moveCount);
            switch (status) {
                case 2 -> {
                    System.out.println("X wins");
                    return;
                }
                case 3 -> {
                    System.out.println("O wins");
                    return;
                }
                case 5 -> {
                    System.out.println("Draw");
                    return;
                }
            }
        }


        // System.out.println(getGameStatus(game, input));

    }
    public static void printTicTacToe(char[][] game) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (game[i][j] == '_') {
                    System.out.print(" " + " ");
                } else {
                System.out.print(game[i][j] + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }


    public static int[] getInputCoordinates(Scanner sc) {
        int[] coordinates = new int[2];
        boolean isInputTypeNumber = false;
        while (coordinates[0] == 0 && coordinates[1] == 0) {
            for (int i = 0; i < 2; i++) {
                if (sc.hasNextInt()) {
                    coordinates[i] = sc.nextInt();
                } else {
                    String temp = sc.nextLine();
                    break;
                }
            }
            if (coordinates[0] != 0 && coordinates[1] != 0) {
                // System.out.println("Valid Array : " + Arrays.toString(coordinates));
                break;
            } else {
                System.out.println("You should enter numbers!");
            }
        }
        return coordinates;
    }

    public static boolean isCoordinatesValid(char[][] game, int x, int y) {
        if (!(x >= 1 && x <= 3 && y >= 1 && y <= 3)) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        } else if (game[x - 1][y - 1] != '_') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        } else {
            return true;
        }
    }

    public static int getGameStatus(char[][] game, int moveCount) {
        // check for impossible first
        if (!isGamePossible(game)) {
            // impossible game
            return 1;
        } else if (checkMatchingRows(game, 'X') == 1 || checkMatchingDiagonals(game, 'X') == 1) {
            // X wins
            return 2;
        } else if (checkMatchingRows(game, 'O') == 1 || checkMatchingDiagonals(game, 'O') == 1) {
            return 3;
        } else if (moveCount < 9) {
            // game is going on
            return 4;
        } else {
            // draw
            return 5;
        }

    }

    public static int checkMatchingRows(char[][] game, char turn) {
        int noOfMatches = 0;
        for (int i = 0; i < 3; i++) {
            int j = 0;
            if (game[i][j] == turn && game[i][j+1] == turn && game[i][j+2] == turn) {
                noOfMatches++;
            } else if (game[j][i] == turn && game[j+1][i] == turn && game[j+2][i] == turn) {
                noOfMatches++;
            }
        }
        return noOfMatches;
    }

    public static int checkMatchingDiagonals(char[][] game, char turn) {
        int noOfMatches = 0;
        if (game[0][0] == turn && game[1][1] == turn && game[2][2] == turn) {
            noOfMatches++;
        }
        if (game[0][2] == turn && game[1][1] == turn && game[2][0] == turn) {
            noOfMatches++;
        }
        return noOfMatches;
    }

    public static boolean isPlayerCountValid(char[][] game) {
        int countX = 0, countO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game[i][j] == 'X') {
                    countX++;
                } else if (game[i][j] == 'O') {
                    countO++;
                }
            }
        }
        if (Math.abs(countX - countO) >= 2) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isGamePossible(char[][] game) {
        if (!isPlayerCountValid(game)) {
            return false;
        } else {
            // check for invalid wins
            int xMatches = checkMatchingRows(game, 'X') + checkMatchingDiagonals(game, 'X');
            int oMatches = checkMatchingRows(game, 'O') + checkMatchingDiagonals(game, 'O');
            // including diagonal matches
            if (xMatches >= 1 && oMatches >= 1) {
                // if game has matched X and O rows together
                return false;
            } else if (xMatches > 2) {
                return false;
            } else if (oMatches > 2) {
                return false;
            } else {
                return true;
            }
        }
    }
}
