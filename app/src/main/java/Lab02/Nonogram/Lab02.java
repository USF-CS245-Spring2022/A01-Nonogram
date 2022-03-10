package Lab02.Nonogram;

public class Lab02 {

    public static boolean[][] checkInitBoard(int[][] rows, int[][] cols) throws IllegalArgumentException {
        System.out.println(rows.length + "x" + cols.length + " board");

        //checks if board is within 9x9 constraints
        if (rows.length > 9 || cols.length > 9) {
            throw new IllegalArgumentException("\nPuzzle exceeds the max of a 9x9 matrix.");
        }

        //checks if puzzle does not exceed 2-sets of blocks per row / col
        for (int[] arrR : rows) {
            if (arrR.length > 2) {
                throw new IllegalArgumentException("\nPuzzle exceeds the constraint of having more than 2 sets of blocks.");
            }
        }
        for (int[] arrC : cols) {
            if (arrC.length > 2) {
                throw new IllegalArgumentException("\nPuzzle exceeds the constraint of having more than 2 sets of blocks.");
            }
        }

        boolean[][] board = new boolean[rows.length][cols.length];
        return board;
    } //end checkInitBoard()


    /**
     *
     * @param board boolean[][], the nonogram puzzle to be solved of size rows.length x cols.length
     * @param cols int[][], the col block sets
     * @param rLength row length
     * @param rowStart starting row position of blocks to be put down
     * @param isComplete true if at the end of the row, false otherwise
     * @return true if board is safe to place blocks down, false otherwise
     */
    private static boolean isSafe(boolean[][] board, int[][] cols, int rLength, int rowStart, boolean isComplete) { //int placedRows
        for (int c = 0; c < cols.length; c++) { //going through
            int blockSet1 = cols[c][0]; //potentially be 0
            int blockSet2 = cols[c][1];

            if (rowStart < blockSet1 || rowStart < blockSet2) {
                continue;
            }

            int placedBlocks = 0; //keeps track of preexisting blocks in a row
            int r = 0;

            if (blockSet1 != 0) { //check for 1st set of blocks
                for (; r < rLength ; r++) { //go row by row
                    if (board[r][c] == true) { //if there is a block,
                        placedBlocks++; //count current set of blocks
                    }
                    else { //if board[r][c] == false
                        placedBlocks = 0;
                    }

                    if (placedBlocks == blockSet1) { //if
                        r++;
                        if (board[r][c]) { //next is filled
                            return false;
                        }
                        else { // no problem
                            break;
                        }
                    }
                }
                placedBlocks = 0; //reset prexisting block counter so can check 2nd set of blocks
            }

            //check for 2nd set of blocks
            for (; r < rLength; r++) {
                if (board[r][c] == true) { //if there is a block,
                    placedBlocks++; //count current set of blocks
                }
                else { //if there is no block
                    placedBlocks = 0;
                }

                if (placedBlocks == blockSet2) {
                    r++;
                    break;
                }
            }

            for (; r < rLength; r++) { //check if filled
                if (board[r][c] == true) {
                    return false;
                }
            }

            if (isComplete == true && placedBlocks != blockSet2) {
                return false;
            }
        }
        return true; //nothing wrong, isSafe checks out
    } //end isSafe()


    /**
     *
     * @param board boolean[][], the nonogram puzzle to be solved of size rows.length x cols.length
     * @param rows int[][], the row block sets
     * @param cols int[][], the col block sets
     * @param rowStart starting row position of blocks to be put down
     * @param colStart starting col position of blocks to be put down
     * @param idxZero true if getting the first row block set, false if getting the second row block set
     * @return true if board is solved, false if not
     */
    private static boolean solve(boolean[][] board, int[][] rows, int[][] cols, int rowStart, int colStart, boolean idxZero) {
        if (!isSafe(board, cols, rows.length, rowStart, false)) { //if not safe
            return false; //cannot solve puzzle in this way
        }
        else if (rowStart == rows.length) { //if at the end of the row (if current row idx is the length of row)
            return isSafe(board, cols, rows.length, rowStart, true); //check if correct placement
        }
        else {
            int num = 0;

            if (idxZero == true && rows[rowStart][0] != 0) {
                num = rows[rowStart][0] + rows[rowStart][1];
            }
            else {
                num = colStart + rows[rowStart][1] - 1;
            }

            int cond = cols.length - num;

            for (int i = 0; i < cond; i++) {
                boolean placed;

                int idx = 1;
                if (idxZero == true) {
                    idx = 0;
                }
                int numBlocks = rows[rowStart][idx];

                placeBlocks(rowStart, colStart + i, numBlocks, board);


                //placed = solve(board, allRows, allCols, rowStart, )
                if (idxZero == true) { //testing first block
                    int colIdx = 0;
                    if (rows[rowStart][0] != 0) {
                        colIdx = rows[rowStart][0] + 1 + i;
                    }
                    placed = solve(board, rows, cols, rowStart, colStart + colIdx, false);
                }
                else {
                    placed = solve(board, rows, cols, rowStart + 1, 0, true);
                }

                if (placed == true) {
                    return true; //solved
                }
                idx = 1;
                if (idxZero == true) {
                    idx = 0;
                }
                numBlocks = rows[rowStart][idx];
                removeBlocks(rowStart, colStart + i, numBlocks, board);
            }
        }
        return false; //not solved
    } //end solve()


    /**
     *
     * @param columns int[][], the col block sets
     * @param rows int[][], the row block sets
     * @return returnBoard, the completed board that is solved
     */
    public static boolean[][] solveNonogram(int[][] columns, int[][] rows) {
        boolean[][] returnBoard = new boolean[rows.length][columns.length];
        solve(returnBoard, rows, columns, 0, 0, true); //try possible things & fills up board
        return returnBoard;
    } //end solveNonogram()


    /**
     * Places blocks down onto the board
     * @param startRow starting row position to put blocks down
     * @param startCol starting col position to put blocks down
     * @param numBlocks number of blocks to put down
     * @param board boolean[][] the nonogram board that is being modified
     */
    private static void placeBlocks(int startRow, int startCol, int numBlocks, boolean[][] board) {
        for (int c = 0; c < numBlocks; c++)
            board[startRow][startCol + c] = true;
    } //end placeBlocks()


    /**
     * Removes blocks from the board
     * @param startRow starting row position to remove blocks from
     * @param startCol starting col position to remove blocks from
     * @param numBlocks number of blocks to remove
     * @param board boolean[][], the nonogram board that is being modified
     */
    private static void removeBlocks(int startRow, int startCol, int numBlocks, boolean[][] board) {
        for (int c = 0; c  < numBlocks; c++)
            board[startRow][startCol + c] = false;
    } //end removeBlocks()


    /**
     * Prints nonogram board to the console
     * @param rows int[][], the row block sets
     * @param cols int[][], the col block sets
     * @param board boolean[][], the nonogram board that is being modified
     */
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
        for (int r = 0; r < rows.length; r++) {
            System.out.print(rows[r][0] + " " + rows[r][1] + " ");

            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == true) {
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


    /**
     *
     * @param args
     */
    public static void main(String[] args){
        // int[][] columns = {{0,0}, {0,1}, {0,0}, {0,0}, {0,1}}; //works
        // int[][] rows = {{1, 1}};

        // checkInitBoard(rows, columns);
        // printBoard(rows, columns, new boolean[rows.length][columns.length]);
        // boolean[][] board = solveNonogram(columns, rows);
        // printBoard(rows, columns, board);



        // int[][] columns2 = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}}; //works
        // int[][] rows2 = {{0, 5}};

        // checkInitBoard(rows2, columns2);
        // printBoard(rows2, columns2, new boolean[rows2.length][columns2.length]);
        // boolean[][] board2 = solveNonogram(columns2, rows2);
        // printBoard(rows2, columns2, board2);



        // int[][] columns1 = {{0,2}, {0,2}, {0,2}, {0,1}, {0,1}, {0,1}, {0,2}, {0,2}, {0,1}}; //works
        // int[][] rows1 = {{4,3}, {3,4}};

        // checkInitBoard(rows1, columns1);
        // printBoard(rows1, columns1, new boolean[rows1.length][columns1.length]); //fixme
        // boolean[][] board1 = solveNonogram(columns1, rows1);
        // printBoard(rows1, columns1, board1);



        int[][] columns4 = {{0,3}, {0,4}, {0,2}, {1,1}, {0,2}}; //works
        int[][] rows4 = {{0,2}, {0,4}, {0,2}, {2,2}, {0,1}};

        checkInitBoard(rows4, columns4);
        printBoard(rows4, columns4, new boolean[rows4.length][columns4.length]);
        boolean[][] board4 = solveNonogram(columns4, rows4);
        printBoard(rows4, columns4, board4);



        // int[][] columns3 = {{1,1}, {2,1}, {0,2}, {1,1}, {1,1}}; //works
        //   	int[][] rows3 = {{1,1}, {0,1}, {0,5}, {0,1}, {1,1}};

        //   	checkInitBoard(rows3, columns3);
        //   	printBoard(rows3, columns3, new boolean[rows3.length][columns3.length]);
        //   	boolean [][]board3 = solveNonogram(columns3, rows3);
        //   	printBoard(rows3, columns3, board3);




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