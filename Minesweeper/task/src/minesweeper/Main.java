package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void printField (char[][] field) {
        System.out.println("|123456789|\n"+
                "-|---------|");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }
    public static void findNeighbourMine (char[][] field, int row, int column) {
        int mineCounter = 0;
        String adapter;
        if (field[row][column] != 'X') {
            if (row > 0) {
                if (field[row - 1][column] == 'X') {
                    mineCounter++;
                }
                if (column > 0) {
                    if (field[row - 1][column - 1] == 'X') {
                        mineCounter++;
                    }
                }
                if (column < 8) {
                    if (field[row - 1][column + 1] == 'X') {
                        mineCounter++;
                    }
                }
            }
            if (row < 8) {
                if (field[row + 1][column] == 'X') {
                    mineCounter++;
                }
                if (column > 0) {
                    if (field[row + 1][column - 1] == 'X') {
                        mineCounter++;
                    }
                }
                if (column < 8) {
                    if (field[row + 1][column + 1] == 'X') {
                        mineCounter++;
                    }
                }
            }
            if (column > 0) {
                if (field[row][column - 1] == 'X') {
                    mineCounter++;
                }
            }
            if (column < 8) {
                if (field[row][column + 1] == 'X') {
                    mineCounter++;
                }
            }
            if (mineCounter > 0) {
                adapter = "" + mineCounter;
                field[row][column] = adapter.charAt(0);
            }else {
                field[row][column] = '/';
            }
        }
    }

    public static void explore (char[][] field, char[][] foggedField, int row, int column) {
        if (row < 0 || row >= field.length || column < 0 || column >= field[0].length) {
            return;
        }
        if (field[row][column] == 'X') {
            return;
        }
        if (foggedField[row][column] == '/') {
            return;
        }
        findNeighbourMine(field, row, column);
        foggedField[row][column] = field[row][column];

        if (foggedField[row][column] != '/' && foggedField[row][column] != '.') {
            return;
        }

        explore(field, foggedField, row-1, column);
        explore(field, foggedField, row+1, column);
        explore(field, foggedField, row, column-1);
        explore(field, foggedField, row, column+1);
        explore(field, foggedField, row+1, column+1);
        explore(field, foggedField, row-1, column+1);
        explore(field, foggedField, row+1, column-1);
        explore(field, foggedField, row-1, column-1);
    }
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> markCoordinates = new ArrayList<>();

        char[][] field = new char[9][9];
        char[][] foggedField = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = '.';
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                foggedField[i][j] = '.';
            }
        }

        System.out.print("How many mines do you want on the field?");
        int numberOfMine = scanner.nextInt();

        int markedMineNumber = 0;
        int counter = 0;
        while (true) {
            printField(foggedField);

            System.out.println("Set/unset mines marks or claim a cell as free:");
            int column = scanner.nextInt() - 1;
            int row = scanner.nextInt() - 1;
            String input = scanner.next();

            if (counter < 1) {
                int index1;
                int index2;
                ArrayList<Integer> randoms = new ArrayList<>();
                for (int i = 0; i < numberOfMine; i++) {
                    boolean weFoundFreeSpace = false;
                    index1 = (int) (Math.random() * 8);
                    index2 = (int) (Math.random() * 8);
                    /*if (index1 == row && index2 == column) {
                        i--;
                        continue;
                    }*/
                    if (field[index1][index2] == '.') {
                        field[index1][index2] = 'X';
                        randoms.add(index1);
                        randoms.add(index2);
                        markCoordinates.add(i, randoms);
                    }else {
                        for (int j = 0; j < 9; j++) {
                            if (field[index1][Math.abs(index2 - j)] == '.') {
                                field[index1][index2] = 'X';
                                randoms.add(index1);
                                randoms.add(Math.abs(index2 - j));
                                markCoordinates.add(i, randoms);
                                weFoundFreeSpace = true;
                                break;
                            }else if (field[Math.abs(index1 - j)][(index2)] == '.') {
                                field[index1][index2] = 'X';
                                randoms.add(Math.abs(index1 - j));
                                randoms.add(index2);
                                markCoordinates.add(i, randoms);
                                weFoundFreeSpace = true;
                                break;
                            }
                        }
                        if (!weFoundFreeSpace) {
                            i--;
                        }
                    }
                }
                counter++;
            }
            if (Objects.equals(input, "free")) {
                explore(field, foggedField, row, column);
                if (field[row][column] == 'X') {
                    for (int i = 0; i < markCoordinates.size(); i++) {
                        foggedField[markCoordinates.get(i).get(0)][markCoordinates.get(i).get(1)] = 'X';
                    }
                    printField(foggedField);
                    System.out.println("You stepped on a mine and failed!");
                    break;
                }

                int leftOut = 0;
                for (int i = 0; i < foggedField.length; i++) {
                    for (int j = 0; j < foggedField[i].length; j++) {
                        if (foggedField[i][j] == '.') {
                            leftOut++;
                        }
                    }
                }
                if (leftOut == numberOfMine) {
                    printField(foggedField);
                    System.out.println("Congratulations! You found all mines!");
                    break;
                }
            }else if (Objects.equals(input, "mine")) {
                if (field[row][column] != '.' && field[row][column] != '*' && field[row][column] == '/') {
                    System.out.println("There is a number here!");
                } else if (foggedField[row][column] == '*') {
                    foggedField[row][column] = '.';
                } else {
                    foggedField[row][column] = '*';
                }
                for (int j = 0; j < numberOfMine; j++) {
                    if (field[row][column] == field[markCoordinates.get(j).get(0)][markCoordinates.get(j).get(1)]) {
                        markedMineNumber++;
                    }
                }
                if (markedMineNumber == numberOfMine) {
                    printField(foggedField);
                    System.out.println("Congratulations! You found all mines!");
                    break;
                }
            }
        }
    }
}
