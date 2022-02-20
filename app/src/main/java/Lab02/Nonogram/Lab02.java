package Lab02.Nonogram;
//@name: Mia Kobayashi
//@date & version: 18 Feb. 2022, v.1
//CS245 Lab02: Nonogram

public class Lab02 {

    private boolean[][] board;
    private int nRows, nCols;


    /**
     * Default constructor -- construct an empty Nonogram board, gets number of rows & columns
     * @param rows, all rows board should have
     * @param cols, all cols board should have
     */
    public Lab02(int[][] rows, int[][] cols) {
        this.nRows = rows.length;
        this.nCols = cols.length;
        this.board = new boolean[this.nRows][this.nCols];
    } //end Nonogram()


    /**
     * Input an inital starting board for Nonogram.
     * @param rows
     * @param cols
     * @throws IllegalArgumentException
     */
    public void inputBoard(int[][] rows, int[][] cols) throws IllegalArgumentException {

        System.out.println(this.nRows + " " + this.nCols);

        //checks if board is within 9x9 constraints
        if (this.nRows > 9 || this.nCols > 9) {
//            System.out.println("Problem");
            throw new IllegalArgumentException("Puzzle exceeds the max of a 9x9 matrix.");
        }

//        if (rows[0].length <= 2 && cols[0].length <=2) {
//            throw new IllegalArgumentException("Puzzle exceeds more than the 2-block of black squares for every row & col constraint.");
//        }

        for (int row = 0; row < this.nRows; row++) {
            for (int col = 0; col < this.nCols; col++) {
                this.board[row][col] = false;
            }
        }
    } //end inputBoard()


//     public boolean isSafe(int row, int col, int numBlocks) {
//
//     	//check if num is already in row, change col#
//     	for (int c = 0; c < boardLen; c++) {
//     		if (this.board[row][c] == ch) {
//     			return false; //desiredNum cannot be placed here
//     		}
//     	}
//
//     	//check if num is already in col, change row#
//     	for (int r = 0; r < boardLen; r++) {
//     		if (this.board[r][col] == ch) {
//     			return false; //desiredNum cannot be placed here
//     		}
//     	}
//
//     	//check if num is already in box, checking 1 of 9 3x3 boxes
//     	int sqrt = (int)Math.sqrt(boardLen); //3
//     	int boxRowStart = row - (row % sqrt); //(row/3)*3; //start 0, 3, or 6		//alt: row - (row % sqrt);
//     	int boxColStart = col - (col % sqrt); //(col/3)*3; //start 0, 3, or 6		//alt: col - (col % sqrt);
//     	for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
//     		for (int c = boxColStart; c < boxColStart + sqrt; c++) {
//     			if (this.board[r][c] == ch) {
//     				return false; //desiredNum cannot be placed here
//     			}
//     		}
//     	}
//
//     	return true; //safe to place desiredNum
//     } //end isSafe()


    // /** nonogram method solves the puzzle with the given two parameters
    //   * @param double array of integers that represent the columns
    //   * @param double array of integers that represent the rows
    //   * @returns double boolean array of solution
    //   */
    // public static boolean[][] solveNonogram(int[][] columns, int[][] rows){
    // //[TODO: your solution]

    // //backtracking
    // //find first empty box,
    // //check row, col, -> isSafe()
    // //then place down blocks
    // //repeat

    // }


    public static void printBoard(int[][] rows, int[][] cols, boolean[][] result) {
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

        char block = 'T';
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
        //fixme test your code
        int[][] columns = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}};
        int[][] rows = {{0,5}};

        Lab02 nonogram = new Lab02(rows, columns);

        nonogram.inputBoard(rows, columns);
        nonogram.printBoard(rows, columns, nonogram.board);
//        nonogram.solveNonogram();
//        nonogram.printBoard();

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
