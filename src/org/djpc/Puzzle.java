package org.djpc;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 */
public class Puzzle {

    private char[][] config = new char[4][3];
    private int blankX;
    private int blankY;

    public Puzzle(String config) {
        int pos = 0;
        for (int i = 0; i < this.config.length; i++) {
            for (int j = 0; j < this.config[i].length; j++) {
                this.config[i][j] = config.charAt(pos);
                pos++;
                if (this.config[i][j] == '_') {
                    blankX = j;
                    blankY = i;
                }
            }
        }
    }

    private Puzzle(Puzzle orig) {
        blankX = orig.blankX;
        blankY = orig.blankY;

        for (int i = 0; i < orig.config.length; i++) {
            config[i] = Arrays.copyOf(orig.config[i], orig.config[i].length);
        }
    }

    public boolean equals(Object other) {
        if (other instanceof Puzzle) {
            Puzzle otherPuzzle = (Puzzle) other;
            for (int i = 0; i < config.length; i++) {
                for (int j = 0; j < config[i].length; j++) {
                    if (config[i][j] != otherPuzzle.config[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(20);
        for (char[] row : config) {
            for (char tile : row) {
                stringBuilder.append(tile);
            }
            stringBuilder.append(" \n");
        }
        return stringBuilder.toString();
    }

    public LinkedList<Puzzle> nextConfigs() {
        LinkedList<Puzzle> nextConfigs = new LinkedList<>();
        if (blankY > 0) {
            Puzzle puzzle = new Puzzle(this);
            puzzle.config[blankY][blankX] = puzzle.config[blankY - 1][blankX];
            puzzle.config[blankY - 1][blankX] = '_';
            puzzle.blankY--;
            nextConfigs.add(puzzle);
        }
        if (blankY < 3) {
            Puzzle puzzle = new Puzzle(this);
            puzzle.config[blankY][blankX] = puzzle.config[blankY + 1][blankX];
            puzzle.config[blankY + 1][blankX] = '_';
            puzzle.blankY++;
            nextConfigs.add(puzzle);
        }
        if (blankX > 0) {
            Puzzle puzzle = new Puzzle(this);
            puzzle.config[blankY][blankX] = puzzle.config[blankY][blankX - 1];
            puzzle.config[blankY][blankX - 1] = '_';
            puzzle.blankX--;
            nextConfigs.add(puzzle);
        }
        if (blankX < 2) {
            Puzzle puzzle = new Puzzle(this);
            puzzle.config[blankY][blankX] = puzzle.config[blankY][blankX + 1];
            puzzle.config[blankY][blankX + 1] = '_';
            puzzle.blankX++;
            nextConfigs.add(puzzle);
        }

        return nextConfigs;
    }
}
