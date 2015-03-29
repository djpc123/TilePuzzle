package org.djpc;

import org.junit.Test;

import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class PuzzleTest {

    private Puzzle testPuzzle1 = new Puzzle("dbaabdc_bddd");
    private Puzzle testPuzzle2 = new Puzzle("adbdb_abdcdd");

    @Test
    public void thatEqualPuzzlesAreEqual() throws Exception {
        assertThat(testPuzzle1, is(testPuzzle1));
    }

    @Test
    public void thatNonEqualPuzzlesAreNotEqual() throws Exception {
        assertThat(testPuzzle1, not(testPuzzle2));
    }

    @Test
    public void thatToStringPrintsCorrectFormat() throws Exception {
        assertThat(testPuzzle1.toString(), is("dba\nabd\nc_b\nddd\n"));
    }
}