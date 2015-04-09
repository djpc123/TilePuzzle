package org.djpc;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 */
public class Puzzle {

    private final char[][] config;
    private int blankX;
    private int blankY;

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

    private Puzzle(char[][] config, int blankX, int blankY) {
        this.blankX = blankX;
        this.blankY = blankY;
        this.config = config;
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
        final StringBuilder stringBuilder = new StringBuilder(20);
        for (char[] row : config) {
            for (char tile : row) {
                stringBuilder.append(tile);
            }
            stringBuilder.append(" \n");
        }
        return stringBuilder.toString();
    }

    public LinkedList<Puzzle> nextConfigs() {
        final LinkedList<Puzzle> nextConfigs = new LinkedList<>();
        if (blankY > 0) {
            nextConfigs.add(shiftTiles(0,-1));
        }
        if (blankY < 3) {
            nextConfigs.add(shiftTiles(0,1));
        }
        if (blankX > 0) {
            nextConfigs.add(shiftTiles(-1,0));
        }
        if (blankX < 2) {
            nextConfigs.add(shiftTiles(1,0));
        }

        return nextConfigs;
    }

    private Puzzle shiftTiles(int x, int y) {
        final char[][] nextConfig = deepCloneConfig();

        nextConfig[blankY][blankX] = nextConfig[blankY + y][blankX + x];
        nextConfig[blankY + y][blankX + x] = '_';

        return new Puzzle(nextConfig,blankX + x,blankY + y);
    }

    private char[][] deepCloneConfig() {
        final char[][] nextConfig = new char[4][3];

        for (int i = 0; i < config.length; i++) {
            nextConfig[i] = Arrays.copyOf(config[i], config[i].length);
        }
        return nextConfig;
    }
}
