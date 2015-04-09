package org.djpc;

import java.util.Arrays;
import java.util.LinkedList;

/** Object representing a puzzle configuration. */
public class Puzzle {

    /** 2D array representing the positions of tiles in the puzzle. */
    private final char[][] config;
    /** The X position of the blank tile. */
    private int blankX;
    /** The Y position of the blank tile. */
    private int blankY;

    /**
     * Construct a puzzle from its string representation.
     * @param config The string representation of the puzzle configuration, eg "adddbadcbd_b".
     */
    public Puzzle(String config) {
        int pos = 0;
        this.config = new char[4][3];
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

    /**
     * Deep copies the original puzzle, then shifts the configuration by moving the blank tile the specified amount.
     * @param orig The puzzle to clone.
     * @param x The amount to move the blank tile in the X direction.
     * @param y The amount to move the blank tile in the Y direction.
     */
    private Puzzle(Puzzle orig, int x, int y) {
        config = orig.deepCloneConfig();

        config[blankY][blankX] = config[blankY + y][blankX + x];
        config[blankY + y][blankX + x] = '_';

        blankX = orig.blankX + x;
        blankY = orig.blankY + y;
    }

    /**
     * Checks each position to ensure that the tiles are identical. If this is true then the configurations are identical.
     * @param other The object to check equality against.
     * @return True if the object is a Puzzle, and it has the same tile configuration.
     */
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

    /**
     * Returns a string representation of the puzzle configuration.
     * @return A string representation of the puzzle configuration spilt over 4 lines, eg "add \ndba \ndcb \nd_b \n"
     */
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder(20);
        for (char[] row : config) {
            for (char tile : row) {
                stringBuilder.append(tile);
            }
            stringBuilder.append(" \n");
        }
        return stringBuilder.toString();
    }

    /**
     * Generates the next possible valid configurations from the current configuration.
     * @return A LinkedList containing the next possible valid configurations.
     */
    public LinkedList<Puzzle> nextConfigs() {
        final LinkedList<Puzzle> nextConfigs = new LinkedList<>();
        if (blankY > 0) { // Don't move the blank tile up if its already on the top row.
            nextConfigs.add(new Puzzle(this, 0,-1));
        }
        if (blankY < 3) { // Don't move the blank tile down if its already on the bottom row.
            nextConfigs.add(new Puzzle(this, 0,1));
        }
        if (blankX > 0) { // Don't move the blank tile left if its already on the leftmost row.
            nextConfigs.add(new Puzzle(this, -1,0));
        }
        if (blankX < 2) { // Don't move the blank tile right if its already on the rightmost row.
            nextConfigs.add(new Puzzle(this, 1,0));
        }

        return nextConfigs;
    }

    /**
     * Deep clones the tile configuration.
     * @return An exact copy of the tile configuration.
     */
    private char[][] deepCloneConfig() {
        final char[][] nextConfig = new char[4][3];

        for (int i = 0; i < config.length; i++) {
            nextConfig[i] = Arrays.copyOf(config[i], config[i].length);
        }
        return nextConfig;
    }
}
