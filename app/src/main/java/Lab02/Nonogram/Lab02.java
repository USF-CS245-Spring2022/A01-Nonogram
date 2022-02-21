package Lab02.Nonogram;
//@name: Mia Kobayashi
//@date & version: 18 Feb. 2022, v.1
//CS245 Lab02: Nonogram

public class Lab02 {

    private boolean[][] board;
    private int[][] allRows, allCols;
    private int nRows, nCols;


    /**
     * Default constructor -- construct an empty Nonogram board, gets number of rows & columns
     * @param rows, all rows board should have
     * @param cols, all cols board should have
     */
    public Lab02(int[][] rows, int[][] cols) {
        this.allRows = rows;
        this.allCols = cols;
        this.nRows = rows.length;
        this.nCols = cols.length;
        this.board = new boolean[this.nRows][this.nCols];
    } //end Nonogram()


    /**
     * Checks initial Nonogram board to see if it follows beginning constraints
     * @param rows
     * @param cols
     * @throws IllegalArgumentException
     */
    public void checkInitBoard(int[][] rows, int[][] cols) throws IllegalArgumentException {

        System.out.println(this.nRows + "x" + this.nCols + " board");

        //checks if board is within 9x9 constraints
        if (this.nRows > 9 || this.nCols > 9) {
//            System.out.println("Problem");
            throw new IllegalArgumentException("Puzzle exceeds the max of a 9x9 matrix.");
        }

        //checks if puzzle does not exceed 2-sets of blocks per row & col
        for (int[] arrR : rows) {
//            System.out.println(arrR.length);
            if (arrR.length > 2) {
                throw new IllegalArgumentException("Puzzle exceeds the constraint of having more than 2 sets of blocks.");
            }
        }
        for (int[] arrC : cols) {
//            System.out.println(arrC.length);
            if (arrC.length > 2) {
                throw new IllegalArgumentException("Puzzle exceeds the constraint of having more than 2 sets of blocks.");
            }
        }
    } //end inputBoard()


     public boolean isSafe(int rowStart, int colStart, int numBlocks) {

        if (numBlocks + rowStart > nCols) {
            System.out.println(numBlocks + rowStart + " " + nCols);
            return false;
        }

//        for (int row = rowStart; rowStart < nRows; rowStart++) {
//            for (int col = colStart; colStart < nCols; colStart++) {
//
//            }
//        }
     	return true; //safe to place blocks
     } //end isSafe()


     /** nonogram method solves the puzzle with the given two parameters
       * @param double array of integers that represent the columns
       * @param double array of integers that represent the rows
       * @returns double boolean array of solution
       */
     public boolean solveNonogram(int rowStart, int colStart, int numBlocks){
     //[TODO: your solution]

     //backtracking
     //find first empty box,
     //check row, col, -> isSafe()
     //then place down blocks
     //repeat

         if (rowStart >= nRows) {
            return false;
         }

//         numBlocks = allCols[colStart][0]; //0 or 1 for second []

         //backtrack
         System.out.println(nRows + "nRows");

//         if (isSafe(rowStart, colStart, numBlocks) == true) {
             placeBlocks(rowStart, colStart, numBlocks);

             if (colStart + numBlocks == nCols) {
                 rowStart += 1;
             }
             else {
                 colStart += numBlocks + 1; //plus 1 bc there needs to be at least a gap between sets of blocks
                 //this is where you get the next numBlocks
             }
             //get net numBlocks fixme
//         numBlocks =

             //move to next place on board
             if (solveNonogram(rowStart, colStart, numBlocks) == true) {
                 return true;
             }
             else {
                 removeBlocks(rowStart, colStart, numBlocks);
             }
//         }

         return false; //cannot solve puzzle
     } //end solveNonogram()


    private void placeBlocks(int row, int col, int numBlocks) {
         for (int c = 0; c < numBlocks; c++) {
             this.board[row][col + c] = true;
         }
    }

    private void removeBlocks(int row, int col, int numBlocks) {
         for (int c = 0; c < numBlocks; c--) {
             this.board[row][col + c] = false;
         }
    }


    public void solve() {
         solveNonogram(0, 0, 0);
    }


    public void printBoard(int[][] rows, int[][] cols, boolean[][] result) {
        System.out.print("    ");
        for (int i = 0; i < cols.length; i++) {
            System.out.print(cols[i][0] + " ");
        }

        System.out.println();
        System.out.print("    ");

        for (int i = 0; i < cols.length; i++) {
            System.out.print(cols[i][1] + " ");
        }

        System.out.println();

        char block = '□';
        for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i][0] + " " + rows[i][1] + " ");

            for (int j = 0; j < result[0].length; j++) {
                if (result[i][j] == true) {
                    block = '■';
                }
                else {
                    block = '□';
                }
                System.out.print(block + " ");
            }
            System.out.println();
        }
    } //end printBoard()


    public static void main(String[] args){
        int[][] columns = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}};
        int[][] rows = {{0,5}};

        Lab02 nonogram = new Lab02(rows, columns);

        nonogram.checkInitBoard(rows, columns);
        nonogram.printBoard(rows, columns, nonogram.board);
        nonogram.solve();
        nonogram.printBoard(rows, columns, nonogram.board);

//        boolean[][] result = {{true,true,true,true,true}, {}};
//        // boolean[][] result = solveNonogram(columns, rows);
//        System.out.println(columns.length + " " + rows.length);
//        printSolvedNonogram(columns, rows, result);
////        System.out.println(result);
//        //    assertEquals(result, solveNonogram(columns, rows));
//
//
//        int[][] col = {{0,1}, {1,1}, {1,1}, {1,1}, {0,1}};
//        int[][] row = {{1,1}, {0,1}, {1,1}, {1,1}, {0,1}};
//        boolean[][] results = {{false, true, false, true, false},
//                {false, false, true, false, false},
//                {true, false, false, false, true},
//                {false, true, false, true, false},
//                {false, false, true, false, false}};
//        printSolvedNonogram(col, row, results);

    }
}
