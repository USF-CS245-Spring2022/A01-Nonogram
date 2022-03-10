// @author: Mia Kobayashi
// @date & vesion: 9 March 2022, v.3
// CS245 Assignment 1: Nonogram

package Lab02.Nonogram;

/**
 * This program solves a Nonogram puzzle.
 */
public class Lab02 {

    /**
     * Checks initial constraints to solve a nonogram puzzle.
     * Nonogram must be within 9x9 and each row's / col's block sets has at most 2 elements
     * @param rows int[][], the row block sets
     * @param cols int[][], the col block sets
     * @throws IllegalArgumentException for if nonogram puzzle does not meet initial starting constraints
     */
    private static void checkInitBoard(int[][] rows, int[][] cols) throws IllegalArgumentException {
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
    } //end checkInitBoard()


    /**
     * Goes through board and checks if being solved correctly
     * Mainly checks the columns to see if there is the correct amount (or less if still unsolved) of blocks
     * @param board boolean[][], the nonogram puzzle to be solved of size rows.length x cols.length
     * @param cols int[][], the col block sets
     * @param rLength row length
     * @param rowStart starting row position of blocks to be put down
     * @param atEndOfRow true if at the end of the row, false otherwise
     * @return true if board is safe to place blocks down, false otherwise
     */
    private static boolean isSafe(boolean[][] board, int[][] cols, int rLength, int rowStart, boolean atEndOfRow) { //int placedRows
        for (int c = 0; c < cols.length; c++) { //going through each col to check for its validity
            int blockSet1 = cols[c][0]; //could potentially be 0
            int blockSet2 = cols[c][1];

            if (rowStart < blockSet1 || rowStart < blockSet2) { //row starting idx must be less than the length of blocks to be put down
                continue;
            }

            int placedBlocks = 0; //keeps track of preexisting blocks in a row
            int r = 0; //initial starting row idx

            if (blockSet1 != 0) { //check for 1st set of blocks
                for (; r < rLength ; r++) { //go row by row
                    if (board[r][c] == true) { //if there is a block
                        placedBlocks++; //count current set of blocks
                    }
                    else { //if board[r][c] == false
                        placedBlocks = 0;
                    }

                    if (placedBlocks == blockSet1) { //if counted set of blocks == blockSet1
                        r++;
                        if (board[r][c] == true) { //check if next position is filled
                            return false;
                        }
                        else { //no problem, check 2nd set of blocks
                            break;
                        }
                    }
                }
                placedBlocks = 0; //reset pre-existing block counter so can check 2nd set of blocks
            }

            //check for 2nd set of blocks
            for (; r < rLength; r++) { //starting at next valid rowIdx
                if (board[r][c] == true) { //if there is a block
                    placedBlocks++; //count current set of blocks
                }
                else { //if board[r][c] == false
                    placedBlocks = 0;
                }

                if (placedBlocks == blockSet2) { //if counted set of blocks == blockSet2
                    r++;
                    break; //at the end of the col
                }
            }

            for (; r < rLength; r++) { //check if the remainder of the col is filled; if true, not safe
                if (board[r][c] == true) {
                    return false;
                }
            }

            if (atEndOfRow == true && placedBlocks != blockSet2) { //if at the end of the row & counted blocks is yet to match blockSet2
                return false;
            }
        } //end for(c++), go to next col
        return true; //nothing wrong, isSafe checks out
    } //end isSafe()


    /**
     * Solves the nonogram board with given rows & cols commands
     * When called, it goes row by row, getting a set of blocks to put down
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
        else if (rowStart == rows.length) { //if at the last row (if current row idx is the length of row)
            return isSafe(board, cols, rows.length, rowStart, true); //check if correct placement
        }
        else {
            int totalNumBlocks;
            if (idxZero == true && rows[rowStart][0] != 0) { //first row blockSet & if blockSet != 0
                totalNumBlocks = rows[rowStart][0] + rows[rowStart][1]; //totalNumBlocks to be placed
            }
            else {
                totalNumBlocks = colStart + rows[rowStart][1] - 1; //totalNumBlocks to be placed (adding 2nd set of blocks) - spacer block
            }

            int maxColIdx = cols.length - totalNumBlocks; //maxColIdx that pieces can start to be placed down

            for (int c = 0; c < maxColIdx; c++) { //go through the columns to place blocks
                boolean placed;

                int idx = 1;
                if (idxZero == true) {
                    idx = 0;
                }
                int numBlocks = rows[rowStart][idx]; //number of blocks to be put down

                placeBlocks(rowStart, colStart + c, numBlocks, board); //put blocks down

                //to solve the next piece
                int colIdx = 0;
                if (idxZero == true) { //testing first block
                    if (rows[rowStart][0] != 0) { //if first set of blocks != 0
                        colIdx = rows[rowStart][0] + 1 + c; //the first set of blocks + spacer + a potential col starting place
                    }
                    placed = solve(board, rows, cols, rowStart, colStart + colIdx, false); //go to next set of blocks in row
                }
                else {
                    placed = solve(board, rows, cols, rowStart + 1, 0, true); //go to entire next row
                }

                if (placed == true) {
                    return true; //solved
                }

                removeBlocks(rowStart, colStart + c, numBlocks, board);
            }
        }
        return false; //not solved
    } //end solve()


    /**
     * Initial starting solve method, passes initial conditions to solve board
     * Calls solve() method for recursion
     * @param columns int[][], the col block sets
     * @param rows int[][], the row block sets
     * @return returnBoard, the completed board that is solved
     */
    public static boolean[][] solveNonogram(int[][] columns, int[][] rows) {
        checkInitBoard(rows, columns); //check starting board

        boolean[][] returnBoard = new boolean[rows.length][columns.length];

        if (!solve(returnBoard, rows, columns, 0, 0, true)) { //try possible things & fills up board
            System.out.println("This is an unsolvable Nonogram puzzle.");
        }
        else {
            printBoard(rows, columns, returnBoard);
        }

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
        for (int c = 0; c < numBlocks; c++) {
            board[startRow][startCol + c] = true;
        }
    } //end placeBlocks()


    /**
     * Removes blocks from the board
     * @param startRow starting row position to remove blocks from
     * @param startCol starting col position to remove blocks from
     * @param numBlocks number of blocks to remove
     * @param board boolean[][], the nonogram board that is being modified
     */
    private static void removeBlocks(int startRow, int startCol, int numBlocks, boolean[][] board) {
        for (int c = 0; c  < numBlocks; c++) {
            board[startRow][startCol + c] = false;
        }
    } //end removeBlocks()


    /**
     * Prints nonogram board to the console
     * @param rows int[][], the row block sets
     * @param cols int[][], the col block sets
     * @param board boolean[][], the nonogram board that is being modified
     */
    public static void printBoard(int[][] rows, int[][] cols, boolean[][] board) {
        System.out.print("    ");
        for (int[] col : cols) { //for (int i = 0; i < cols.length; i++) { //prints all first numbers of col blockSets
            System.out.print(col[0] + " ");
        }

        System.out.println();
        System.out.print("    ");

        for (int[] col : cols) { //for (int i = 0; i < cols.length; i++) { //prints all second numbers of col blockSets
            System.out.print(col[1] + " ");
        }
        System.out.println();

        //begin to print the rows with blocks (filled and/or not)
        char block;
        for (int r = 0; r < rows.length; r++) {
            System.out.print(rows[r][0] + " " + rows[r][1] + " "); //prints row blockSets 1 & 2

            for (int c = 0; c < board[0].length; c++) { //print blocks
                if (board[r][c] == true) { //a block should be placed down
                    block = '■';
                }
                else {
                    block = '□';
                }
                System.out.print(block + " ");
            }
            System.out.println(); //go to next row
        }
    } //end printBoard()


    /**
     * main method for testing
     * @param args
     */
    public static void main(String[] args){
         int[][] columns = {{0,0}, {0,1}, {0,0}, {0,0}, {0,1}}; //works
         int[][] rows = {{1, 1}};

//         printBoard(rows, columns, new boolean[rows.length][columns.length]);
         boolean[][] board = solveNonogram(columns, rows);
//         printBoard(rows, columns, board);



         int[][] columns2 = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}}; //works
         int[][] rows2 = {{0, 5}};

//         printBoard(rows2, columns2, new boolean[rows2.length][columns2.length]);
         boolean[][] board2 = solveNonogram(columns2, rows2);
//         printBoard(rows2, columns2, board2);



         int[][] columns1 = {{0,2}, {0,2}, {0,2}, {0,1}, {0,1}, {0,1}, {0,2}, {0,2}, {0,1}}; //works
         int[][] rows1 = {{4,3}, {3,4}};

//         printBoard(rows1, columns1, new boolean[rows1.length][columns1.length]);
         boolean[][] board1 = solveNonogram(columns1, rows1);
//         printBoard(rows1, columns1, board1);



        int[][] columns4 = {{0,3}, {0,4}, {0,2}, {1,1}, {0,2}}; //works
        int[][] rows4 = {{0,2}, {0,4}, {0,2}, {2,2}, {0,1}};

//        printBoard(rows4, columns4, new boolean[rows4.length][columns4.length]);
        boolean[][] board4 = solveNonogram(columns4, rows4);
//        printBoard(rows4, columns4, board4);



        int[][] columns3 = {{1,1}, {2,1}, {0,2}, {1,1}, {1,1}}; //works
        int[][] rows3 = {{1,1}, {0,1}, {0,5}, {0,1}, {1,1}};

//        printBoard(rows3, columns3, new boolean[rows3.length][columns3.length]);
        boolean[][] board3 = solveNonogram(columns3, rows3);
//        printBoard(rows3, columns3, board3);



        int[][] columns5 = {{1,1}, {2,1}, {0,2}, {1,1}, {1,2}}; //should not work
        int[][] rows5 = {{1,1}, {0,1}, {0,5}, {0,1}, {1,1}};

//        printBoard(rows5, columns5, new boolean[rows5.length][columns5.length]);
        boolean[][] board5 = solveNonogram(columns5, rows5);
//        printBoard(rows5, columns5, board5);
    }
}
