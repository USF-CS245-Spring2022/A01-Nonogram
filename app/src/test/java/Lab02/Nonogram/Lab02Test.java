package Lab02.Nonogram;

import org.junit.Test;
import static org.junit.Assert.*;

public class Lab02Test {
    @Test 
    public void test01() {
        int[][] columns = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}}; 
        int[][] rows = {{0,5}};
        boolean[][] result = {{true,true,true,true,true}};
        assertArrayEquals(result, Lab02.solveNonogram(columns, rows));
    }
    @Test
    public void test02() {
        int[][] columns = {{0,2}, {0,2}, {0,2}, {0,1}, {0,1}, {0,1}, {0,2}, {0,2}, {0,1}};
        int[][] rows = {{4, 3}, {3, 4}};
        boolean[][] result = {{true,true,true,true,false,false,true, true, true},
                {true,true,true,false,true,true,true,true,false}};
        assertArrayEquals(result, Lab02.solveNonogram(columns, rows));
    }
    @Test
    public void test03() {
        int[][] columns = {{0,2}, {2,1}, {0,4}, {0,3}, {0,1}};
        int[][] rows = {{0,4}, {0,4}, {0,3}, {0,1}, {0,1}};

        boolean[][] result = {{true, true, true, true, false},
                {true, true, true, true, false},
                {false, false, true, true, true},
                {false, false, true, false, false},
                {false, true, false, false, false}};


        assertArrayEquals(result, Lab02.solveNonogram(columns, rows));
    }
    @Test
    public void test04() {
        int[][] columns = {{1,1}, {2,1}, {0,2}, {1,1}, {1,1}};
        int[][] rows = {{1,1}, {0,1}, {0,5}, {0,1}, {1,1}};

        /*boolean[][] result = {{true, false, false, false, true},
                {false, true, false, false, false},
                {true, true, true, true, true},
                {false, false, true, false, false},
                {false, true, false, true, false}};*/
        boolean[][] result = {{true, false, false, true, false},
                {false, true, false, false, false},
                {true, true, true, true, true},
                {false, false, true, false, false},
                {false, true, false, false, true}};


        assertArrayEquals(result, Lab02.solveNonogram(columns, rows));
    }
}
