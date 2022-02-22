package Lab02.Nonogram;
//@name: Mia Kobayashi
//@date & version: 21 Feb. 2022, v.2
//CS245 Lab02: Nonogram

public class Lab02 {

    /**
     * Checks initial Nonogram board to see if it follows beginning constraints
     *
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


    public static int isSafe(int[][] rows, int[][] cols, int rowStart, int colStart, int numBlocks, boolean[][] board) {


        //false row = 1
        //false col = -1
        //true = 0
        System.out.println("in isSafe()");

        if (numBlocks + rowStart > cols.length) { //check if total blocks in row will satisfy total col length (is within bounds of col length)
            System.out.println(numBlocks + rowStart + " " + cols.length);
            return 1;
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
                return 1;
            }
        }

        int totalBlocksInCol;
        int currentBlocksInCol = 0;
        for (int c = colStart; c < cols.length; c++) { //check if total blocks will satisfy col block req. (will total number of blocks equal the col's total?)
            totalBlocksInCol = cols[c][0] + cols[c][1];

            //fixme

            for (int r = 0; r < rows.length; r++) {
                if (board[r][c] == true) {
                    currentBlocksInCol += 1;
                }
            }

            // if (totalBlocksInCol == 0) { //don't want to place any blocks on board
            //     return -1;
            // }

            System.out.println(totalBlocksInCol + " totalBlocksInCol, :" + c);
            System.out.println(currentBlocksInCol + " currentBlocksInCol");

            if (currentBlocksInCol > totalBlocksInCol) {
                System.out.println("currentBlocksInCol > totalBlocksInCol: " + currentBlocksInCol + " > " + totalBlocksInCol);
                return -1;
            }
        }

        return 0; //safe to place blocks
    } //end isSafe()


    /**
     * nonogram method solves the puzzle with the given two parameters
     *
     * @param rows    array of integers that represent the rows
     * @param columns array of integers that represent the columns
     * @returns double boolean array of solution
     */
    public static boolean[][] solveNonogram(int[][] columns, int[][] rows, boolean[][] board) {
//        boolean[][] returnBoard = new boolean[rows.length][columns.length];
        solve(rows, columns, 0, 0, 0, board); //try possible things & fills up board
        return board;
    } //end solveNonogram()


    public static boolean solve(int[][] rows, int[][] cols, int rowStart, int colStart, int numBlocks, boolean[][] board) {

        System.out.println("1: rowStart: " + rowStart + " colStart: " + colStart + " numBlocks: " + numBlocks); //del

        if (rowStart == 0 && colStart == 0 && numBlocks == 0) { //base case
            if (rows[rowStart][colStart] == 0) {
                numBlocks = rows[rowStart][colStart + 1];
            } else {
                numBlocks = rows[rowStart][colStart];
            }
        }

        System.out.println("1a: rowStart: " + rowStart + " colStart: " + colStart + " numBlocks: " + numBlocks); //del

        boolean placed = false;

        while (placed == false) {
            int safe = isSafe(rows, cols, rowStart, colStart, numBlocks, board);

            if (safe == 0) { //true isSafe()
                System.out.println("t1 sc: " + colStart); // del
                placeBlocks(rowStart, colStart, numBlocks, board); //place desired blocks in spots
                placed = true;

                System.out.println("blocks placed");
                printBoard(rows, cols, board);


                //fixme:
                System.out.println(colStart + numBlocks + " colStart + numBlocks");
                System.out.println(cols.length - 1 + " cols.length");
                //to get the next numBlocks
                if (colStart + numBlocks == cols.length - 1) { //go to next row
                    rowStart += 1;
                    colStart = 0;

                    if (rowStart == rows.length - 1) {
                        return true;
                    }

                    if (rows[rowStart][colStart] == 0) {
                        numBlocks = rows[rowStart][1]; //gets second num
                    } else {
                        numBlocks = rows[rowStart][0]; //gets first num
                    }
                } else { //go to next col
                    System.out.println("going to next col !!!!");
                    colStart += numBlocks + 1; //+1 bc there needs to be at least a gap between sets of blocks
                    numBlocks = rows[rowStart][1];
                }
            }

            if (safe == 1) { //false isSafe(), row error
                rowStart += 1; //fixme?
            }

            if (safe == -1) { //false isSafe(), col error
                System.out.println("col++");
                colStart += 1;
            }
        }
//
        System.out.println("2: rowStart: " + rowStart + " colStart: " + colStart + " numBlocks: " + numBlocks);
        solve(rows, cols, rowStart, colStart, numBlocks, board);
//            //move to next place on board
//            if (solve(rows, cols, rowStart, colStart, numBlocks, board) == true) {
//                return true; //
//            }
//            else {
//                removeBlocks(rowStart, colStart, numBlocks, board);
//            }


        //place
        //undo

        //backtracking
        //find first empty box,
        //check row, col, -> isSafe()
        //then place down blocks
        //repeat

        if (rowStart >= rows.length) {
            System.out.println("hi");
            return false;
        }

        return false; //cannot solve puzzle
    } //end solve()


    private static void placeBlocks(int startRow, int startCol, int numBlocks, boolean[][] board) {
        System.out.println("sr: " + startRow + "sc: " + startCol + "nb: " + numBlocks);
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

