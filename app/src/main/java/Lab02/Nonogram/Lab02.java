package Lab02.Nonogram;
//@name: Mia Kobayashi
//@date & version: 8 March 2022, v.2
//CS245 Lab02: Nonogram

public class Lab02 {

    /**
     * Checks initial Nonogram board to see if it follows beginning constraints
     *
     * @param rows
     * @param cols
     * @throws IllegalArgumentException
     */
    public static void checkInitBoard(int[][] rows, int[][] cols) throws IllegalArgumentException {

        System.out.println(rows.length + "x" + cols.length + " board");

        //checks if board is within 9x9 constraints
        if (rows.length > 9 || cols.length > 9) {
            throw new IllegalArgumentException("Puzzle exceeds the max of a 9x9 matrix.");
        }

        //checks if puzzle does not exceed 2-sets of blocks per row & col
        for (int[] arrR : rows) {
            if (arrR.length > 2) {
                throw new IllegalArgumentException("Puzzle exceeds the constraint of having more than 2 sets of blocks.");
            }
        }
        for (int[] arrC : cols) {
            if (arrC.length > 2) {
                throw new IllegalArgumentException("Puzzle exceeds the constraint of having more than 2 sets of blocks.");
            }
        }
    } //end checkInitBoard()


    private static boolean checkColSum(int[][] rows, int[][] cols, int colStart, boolean[][] board) {
        for (int c = colStart; c < cols.length; c++) { //check if total blocks will satisfy col block req. (will total number of blocks equal the col's total?)
            int blockReq = cols[c][0] + cols[c][1];

            if (blockReq == 0) { //don't want to place any blocks on this board position
                return false; //inc colStart
            }

            int blocks = 0;
            for (int r = 0; r < rows.length; r++) { //going down a col, changing row
                if (board[r][c] == true) {
                    blocks += 1;
                }
            }
            if (blocks > blockReq) {
                return false;
            }
        }
        return true;
    } //end checkColSum()


    private static int isSafe(int[][] rows, int[][] cols, int colStart, boolean[][] board) {
        if (checkColSum(rows, cols, colStart, board) == false) { //!checkCol
            return -1; //increase col
        }
        return 0; //safe to place blocks
        //false row = 1
        //false col = -1
        //true = 0
    } //end isSafe()


    private static boolean checkSpaces(int numRows, int[][] cols, boolean[][] board) {
        for (int c = 0; c < cols.length; c++) {
            int blockSet1 = cols[c][0]; //potentially be 0
            int blockSet2 = cols[c][1];
            int counter = 0;
            boolean start;
            int r = 0;

            if (blockSet1 != 0) { //only check for one set of blocks
                for (; r < numRows - 1; r++) {
                    if (board[r][c])
                        start = true;
                    else {
                        start = false;
                        counter = 0;
                    }
                    if (start == true)
                        counter++;
                    if (counter == blockSet1) {
                        if (board[++r][c]) // next is filled
                            return false;
                        else // no problem
                            break;
                    }
                }
            }
            counter = 0;
            //check for 2 sets of blocks
            for (; r < numRows; r++) {
                if (board[r][c])
                    start = true;
                else {
                    start = false;
                    counter = 0;
                }
                if (start == true)
                    counter++;
                if (counter == blockSet2)
                    break;
            }
            if (counter != blockSet2)
                return false;
        }
        return true; //nothing wrong w spaces
    } //end checkSpaces()


    private static boolean solve(int[][] rows, int[][] cols, int rowStart, int colStart, int numBlocks, boolean blockIdx, boolean[][] board) {

        if (rowStart >= rows.length) { //is completed board?
            System.out.println("returning true");
            return true;
        }

        if (rowStart == 0 && colStart == 0 && numBlocks == 0) { //base case, gets first numBlocks
            if (rows[0][0] == 0) {
                numBlocks = rows[0][1];
                blockIdx = false;
            } else {
                numBlocks = rows[0][0];
                blockIdx = true;
            }
        }

        boolean placed = false;

        //recursion
        while (placed == false) {

            int safe = isSafe(rows, cols, colStart, board); //check if safe spot

            if (safe == 0) { //true isSafe()
                placeBlocks(rowStart, colStart, numBlocks, board); //place desired blocks in spots
                printBoard(rows, cols, board);
                placed = true;

                if (checkSpaces(rows.length, cols, board) == true) {
                    System.out.println("checkSpaces passed");

                    //bc placed == true, get the next numBlocks
                    if (blockIdx == false) { //go to next row; replace w/ recursive call
                        rowStart += 1;
                        colStart = 0;

                        if (rowStart >= rows.length) { //is completed board?
                            System.out.println("returning true");
                            return true;
                        }

                        if (rows[rowStart][0] == 0) {
                            numBlocks = rows[rowStart][1]; //gets second num
                        }
                        else {
                            numBlocks = rows[rowStart][0]; //gets first num
                            blockIdx = true;
                        }
                    }
                    else { //go to next col
                        System.out.println("going to next col !!!!");
                        colStart += numBlocks + 1; //+1 bc there needs to be at least a gap between sets of blocks
                        numBlocks = rows[rowStart][1];
                        blockIdx = false;
                    }

                    if (solve(rows, cols, rowStart, colStart, numBlocks, blockIdx, board) == true) {
                        System.out.println("returning true");
                        return true;
                    }
                }
//                else {
                removeBlocks(rowStart, colStart, numBlocks, board);
//                }
            } //end safe == 0
            if (safe == -1) { //false isSafe(), col error
                System.out.println("col++");
                colStart += 1;
                placed = false;
            }
        } //end while

        System.out.println("returning false");
        return false;
    } //end solve()


    public static boolean[][] solveNonogram(int[][] columns, int[][] rows) {
        boolean[][] returnBoard = new boolean[rows.length][columns.length];
        solve(rows, columns, 0, 0, 0, true, returnBoard); //try possible things & fills up board
        return returnBoard;
    } //end solveNonogram()


    private static void placeBlocks(int startRow, int startCol, int numBlocks, boolean[][] board) {
        for (int c = 0; c < numBlocks; c++) {
            board[startRow][startCol + c] = true;
        }
    } //end placeBlocks()


    private static void removeBlocks(int startRow, int startCol, int numBlocks, boolean[][] board) {
        for (int c = 0; c  < numBlocks; c++) {
            board[startRow][startCol + c] = false;
        }
    } //end removeBlocks()


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
        int[][] columns = {{0,0}, {0,1}, {0,0}, {0,0}, {0,1}}; //does not solve
        int[][] rows = {{1, 1}};

        checkInitBoard(rows, columns);
        // printBoard(rows, columns, board);
        boolean[][] board = solveNonogram(columns, rows);
        printBoard(rows, columns, board);



        // int[][] columns2 = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}}; //solves
        // int[][] rows2 = {{0, 5}};

        //  checkInitBoard(rows2, columns2);
        // // printBoard(rows2, columns2, board2);
        // boolean[][] board2 = solveNonogram(columns2, rows2);
        // printBoard(rows2, columns2, board2);


        // int[][] columns1 = {{0,2}, {0,2}, {0,2}, {0,1}, {0,1}, {0,1}, {0,2}, {0,2}, {0,1}}; //does not solve
        // int[][] rows1 = {{4,3}, {3,4}};

        // checkInitBoard(rows1, columns1);
        // // printBoard(rows1, columns1, board1);
        // boolean[][] board1 = solveNonogram(columns1, rows1);
        // printBoard(rows1, columns1, board1);


        // int[][] columns3 = {{1,1}, {2,1}, {0,2}, {1,1}, {1,1}}; //does not solve
        // int[][] rows3 = {{1,1}, {0,1}, {0,5}, {0,1}, {1,1}};

        // checkInitBoard(columns3, rows3);
        // boolean [][]board3 = solveNonogram(columns3, rows3);
        // printBoard(rows3, columns3, board3);
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
