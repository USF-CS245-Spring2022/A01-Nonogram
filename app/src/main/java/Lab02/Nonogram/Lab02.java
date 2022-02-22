package Lab02.Nonogram;
//@name: Mia Kobayashi
//@date & version: 18 Feb. 2022, v.1
//CS245 Lab02: Nonogram

public class Lab02 {

    /**
     * Checks initial Nonogram board to see if it follows beginning constraints
     * @param rows
     * @param cols
     * @throws IllegalArgumentException
     */
    public static boolean[][] checkInitBoard(int[][] rows, int[][] cols) throws IllegalArgumentException {

        System.out.println(rows.length + "x" + cols.length + " board");

        //checks if board is within 9x9 constraints
        if (rows.length > 9 || cols.length > 9) {
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

        boolean[][] board = new boolean[rows.length][cols.length];
        return board;
    } //end checkInitBoard()


    public static boolean isSafe(int[][] rows, int[][] cols, int rowStart, int colStart, int numBlocks, boolean[][] board) {

        if (numBlocks + rowStart > cols.length) { //check if total blocks in row will satisfy total col length (is within bounds of col length)
            System.out.println(numBlocks + rowStart + " " + cols.length);
            return false;
        }

        int totalBlocksInRow = rows[rowStart][0] + rows[rowStart][1];
        int currentBlocksInRow = numBlocks;
        System.out.println(totalBlocksInRow + " totalBlocksInRow");

        for (int c = 0; c < cols.length; c++) { //check if total blocks will satisfy row block req. (will total number of blocks equal the row's total?)
            if (board[rowStart][c] == true) {
                currentBlocksInRow += 1;
                System.out.println(currentBlocksInRow + " currentBlocksInRow");
            }

            if (currentBlocksInRow > totalBlocksInRow) {
                return false;
            }
        }

        int totalBlocksInCol;
        int currentBlocksInCol;
        for (int c = 0; c < cols.length; c++) { //check if total blocks will satisfy col block req. (will total number of blocks equal the col's total?)
            totalBlocksInCol = cols[c][0] + cols[c][1];
            currentBlocksInCol = 1; //1 bc you're assuming you get to place a block down

            System.out.println(totalBlocksInCol + " totalBlocksInCol, col:" + c);
            for (int r = 0; r < rows.length; r++) {
                if (board[r][c] == true) {
                    currentBlocksInCol += 1;

                }
            }

            if (currentBlocksInCol > totalBlocksInCol) {
                return false;
            }
        }

     	return true; //safe to place blocks
    } //end isSafe()


    public static boolean solve(int[][] rows, int[][] cols, int rowStart, int colStart, int numBlocks, boolean[][] board) {

        //fixme add more stuff

        if (isSafe(rows, cols, rowStart, colStart, numBlocks, board) == true) {
            placeBlocks(rowStart, colStart, numBlocks, board); //place desired blocks in spots

//            if (colStart + numBlocks == cols.length) {
//                rowStart += 1; //go to next row
//                numBlocks = rows[rowStart][0];
//            }
//            else {
//                colStart += numBlocks + 1; //plus 1 bc there needs to be at least a gap between sets of blocks
//                numBlocks = rows[rowStart][1];
//            }
//
//            //move to next place on board
//            if (solve(rows, cols, rowStart, colStart, numBlocks, board) == true) {
//                return true; //
//            }
//            else {
//                removeBlocks(rowStart, colStart, numBlocks, board);
//            }
        }
        //place
        //undo

             //backtracking
     //find first empty box,
     //check row, col, -> isSafe()
     //then place down blocks
     //repeat

         if (rowStart >= rows.length) {
            return false;
         }

         return false; //cannot solve puzzle
    }


     /** nonogram method solves the puzzle with the given two parameters
       * @param rows array of integers that represent the rows
       * @param columns array of integers that represent the columns
       * @returns double boolean array of solution
       */
     public static boolean[][] solveNonogram(int[][] columns, int[][] rows, boolean[][] board){
//         boolean[][] returnBoard = board;
         solve(rows, columns, 0,0,5, board); //try possible things & fills up board
         return board;
     } //end solveNonogram()


    private static void placeBlocks(int row, int col, int numBlocks, boolean[][] board) {
         for (int c = 0; c < numBlocks; c++) {
             board[row][col + c] = true;
         }
    }

    private static void removeBlocks(int row, int col, int numBlocks, boolean[][] board) {
         for (int c = 0; c < numBlocks; c--) {
             board[row][col + c] = false;
         }
    }


    public static void printBoard(int[][] rows, int[][] cols, boolean[][] board) {
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

            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == true) {
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

        boolean[][] board = checkInitBoard(rows, columns);
        printBoard(rows, columns, board);
        board = solveNonogram(columns, rows, board);
        printBoard(rows, columns, board);


//        int[][] columns1 = {{0,2}, {0,2}, {0,2}, {0,1}, {0,1}, {0,1}, {0,2}, {0,2}, {0,1}};
//        int[][] rows1 = {{4,3}, {3,4}};
//
//        boolean[][] board1 = checkInitBoard(rows1, columns1);
//        printBoard(rows1, columns1, board1);
//        board1 = solveNonogram(columns1, rows1, board1);
//        printBoard(rows1, columns1, board1);
//

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
