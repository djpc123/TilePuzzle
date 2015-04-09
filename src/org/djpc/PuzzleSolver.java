package org.djpc;

import com.sun.istack.internal.Nullable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Finds the shortest route between a two 11 tile puzzle configurations.
 */
public class PuzzleSolver {

    /**
     * Finds the shortest route between a two 11 tile puzzle configurations.
     * @param filename The filename containing the start and destination configurations, eg "adddbadcbd_b2ad_dabdcbddb.txt"
     */
    public PuzzleSolver(String filename) {
        String[] split = filename.split("[.2]"); // Split the filename at '.' and '2' characters.

        if (split.length != 3) { // Check that this gives us 3 parts to the filename.
            System.err.println("Incorrect filename format: " + filename);
            System.exit(1);
        }

        List<Puzzle> solution = iterativeDeepening(new Puzzle(split[0]), new Puzzle(split[1]));

        String[] formattedOutput = formatOutput(solution);

        // Print the result to the console.
        for (String line : formattedOutput) {
            System.out.println(line);
        }
        System.out.println(filename);

        // Write the result out to file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : formattedOutput) {
                writer.append(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the shortest route between a two 11 tile puzzle configurations.
     * @param args Expects 1 argument, which should be the filename containing the start and destination configurations,
     *             eg "adddbadcbd_b2ad_dabdcbddb.txt"
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Expected 1 argument, got " + args.length);
            System.exit(1);
        }
        new PuzzleSolver(args[0]);
    }

    /**
     * Formats a list of puzzle configurations into 4 lines of text.
     * @param solution A route between two configurations.
     * @return An array of strings, one for each line, containing the route between two puzzle configurations.
     */
    private String[] formatOutput(List<Puzzle> solution) {
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

    /**
     * Finds the shortest path between two puzzle configurations by incrementing the maximum depth of paths and
     * attempting to find a path between configurations at each depth.
     * @param start The start configuration.
     * @param dest The destination configuration.
     * @return A List containing the route between the configurations.
     */
    private List<Puzzle> iterativeDeepening(Puzzle start, Puzzle dest) {
        for (int depth = 1; true; depth++) { // Loop forever, incrementing the maximum depth at each iteration.
            LinkedList<Puzzle> startList = new LinkedList<>();
            startList.add(start);

            final List<Puzzle> route = depthFirstDevaVu(startList, dest, depth);

            if (route != null) {
                System.out.println(depth); // Print the length of the route.
                return route; // If we have found a route return it.
            }
        }
    }

    /**
     * Attempt to find a route from the end of the current route to the destination configuration. Checks to make sure
     * that it does not go back to the same configuration.
     * @param route The previous configurations in the route.
     * @param dest The destination configuration.
     * @param depth The number of moves remaining.
     * @return A route to the destination configuration, or null if a route is not found.
     */
    @Nullable
    private LinkedList<Puzzle> depthFirstDevaVu(LinkedList<Puzzle> route, Puzzle dest, int depth) {
        if (depth == 0) {
            return null; // If we have no moves remaining, a route is not possible
        }

        Puzzle last = route.getLast();
        if (last.equals(dest)) {
            return route; // If the last node in the route is the destination, we have found a route
        } else {
            LinkedList<Puzzle> nextConfigs = last.nextConfigs(); // Get the next possible moves
            for (Puzzle next : nextConfigs) {
                if (!route.contains(next)) { // Check if we have already visited this configuration
                    LinkedList<Puzzle> nextRoute = (LinkedList<Puzzle>) route.clone();
                    nextRoute.add(next);
                    LinkedList<Puzzle> wholeRoute = depthFirstDevaVu(nextRoute, dest, depth - 1); // Try to find a route from this configuration to the destination
                    if (wholeRoute != null) {
                        return wholeRoute; // Return a route that has been found
                    }
                }
            }
        }
        return null;
    }
}
