package org.djpc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class PuzzleSolver {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Expected 1 argument, got " + args.length);
            System.exit(1);
        }
        new PuzzleSolver(args[0]);
    }

    public PuzzleSolver(String filename) {
        String[] split = filename.split("[.2]");

        if (split.length != 3) {
            System.err.println("Incorrect filename format: " + filename);
            System.exit(1);
        }

        LinkedList<Puzzle> solution = iterativeDeepening(new Puzzle(split[0]), new Puzzle(split[1]));
        String[] formattedOutput = formatOutput(solution);
        for (String line : formattedOutput) {
            System.out.println(line);
        }
        System.out.println(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : formattedOutput) {
                writer.append(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] formatOutput(LinkedList<Puzzle> solution) {
        String[] lines = new String[4];
        Arrays.fill(lines, "");

        for (Puzzle puzzle : solution) {
            String puzzleString = puzzle.toString();
            String[] split = puzzleString.split("\\n");
            for (int i = 0; i < split.length; i++) {
                lines[i] = lines[i] + split[i];
            }
        }
        return lines;
    }

    private LinkedList<Puzzle> iterativeDeepening(Puzzle start, Puzzle dest) {
        for (int depth = 1; true; depth++) { // doubtful termination
            LinkedList<Puzzle> startList = new LinkedList<>();
            startList.add(start);
            final LinkedList<Puzzle> route = depthFirstDevaVu(startList, dest, depth);
            if (route != null) {
                System.out.println(depth);
                return route; // fast exit
            }
        }
    }

    private LinkedList<Puzzle> depthFirstDevaVu(LinkedList<Puzzle> route, Puzzle dest, int depth) {
        if (depth == 0) return null;
        Puzzle last = route.getLast();
        if (last.equals(dest)) {
            return route;
        }
        else {
            LinkedList<Puzzle> nextConfigs = last.nextConfigs();
            for (Puzzle next : nextConfigs) {
                if (!route.contains(next)) {
                    LinkedList<Puzzle> nextRoute = (LinkedList<Puzzle>) route.clone();
                    nextRoute.add(next);
                    LinkedList<Puzzle> wholeRoute = depthFirstDevaVu(nextRoute, dest, depth - 1);
                    if (wholeRoute != null) {
                        return wholeRoute;
                    }
                }
            }
        }
        return null;
    }
}
