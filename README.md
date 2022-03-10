# Assignment 1 | Nonogram
Mia Kobayashi  
10 March 2022  

**How the code works:**  
This program recursively solves a Nonogram Puzzle (which must be a size within 9x9 and each row/column has no more than 2 sets of blocks (starting constraints).  
An initial puzzle (two int[][] of columns and rows) is passed to solveNonogram() where it is then checked with checkInitBoard() method to see if it meets the starting constraints.  

In the case that the puzzle does not meet the starting constraints, an IllegalArgumentException will be thrown.  

If a puzzle is solveable, the program continues with solve().  
Until the puzzle is solved, solve() is called recursively, taking potential sets of blocks by row, placing those blocks down, validates the blocks by checking the puzzle by column in isSafe(), and then repeats the process.  
When a desired set of blocks is placed onto the puzzle board at a specific spot, the isSafe() method checks the entire puzzle by column to make sure that it meets the column sets of blocks requirements.  

If a puzzle is unsolveable, the program will print, "This is an unsolvable Nonogram puzzle."  

## Nonogram

The goal of this assignment is to demonstrate your understanding of arrays and recursion by solving these puzzles. 

## Background

Nonogram is a logical puzzle with simple rules. Given a grid of squares, which must be filled in black or marked with X, to indicate that it cannot be black. Beside each row of the grid are listed the lengths of the runs of black squares on that row. Above each column are listed the length of the run of black squares in that column. The objective is to find all black squares. There are stacked numbers, which indicate black squares must be connected within the puzzle. 

## Example 1:
<pre><code>Input: columns = {{0,1}, {0,1}, {0,1}, {0,1}, {0,1}}; rows = {{0,5}}
Output: {{T, T, T, T, T},{}}
</code></pre>

## Example 2:
<pre><code>Input: columns = {{0,2}, {0,2}, {0,2}, {0,1}, {0,1}, {0,1}, {0,2}, {0,2}, {0,2}}; rows = {{4, 3}, {4, 3}}
Output: {{T, T, T, T, F, F, T, T, T}, {T, T, T, F, T, T, T, T, F}}
</code></pre>

## Example 3:
<pre><code>Input: columns = {{1,1}, {2,1}, {0,2}, {1,1}, {1,1}};  rows = {{1,2}, {0,1}, {0,5}, {0,1}, {1,2}}
Output: {{T, F, F, F, T},
         {F, T, F, F, F},
         {T, T, T, T, T},
         {F, F, T, F, F},
         {F, T, F, T, F}}
</code></pre>

## Example 4: 
<pre><code>Input: columns = {{0,1}, {1,1}, {0,2}, {0,1}, {0,1}, {1,1}}; rows = {{0,1}, {3,1}, {1,1}, {1,1}}
Output: {{T, F, F, F, F, F},
         {F, T ,T ,T, F, T},
         {F, F, T, F, T, F},
         {F, T, F, F, F, T}}
</code></pre>
         
## Example 5:
<pre><code>Input: columns = {{0,1}, {1,1}, {1,1}, {1,1}, {0,1}}; rows = {{1,1}, {0,1}, {1,1}, {1,1}, {0,1}}
Output: {{F, T, F, T, F},
         {F, F, T, F, F},
         {T, F, F, F, T},
         {F, T, F, T, F},
         {F, F, T, F, F}}
</code></pre>
         
## Example 6:
<pre><code>Input: columns = {{1,1}, {1,3}, {1,3}, {2,1}, {2,1}, {2,2}, {1,3}, {2,1}}; rows = {{1,1}, {1,3}, {2,3}, {4,1}, {2,2}, {1,6}}
Output: {{F, T, F, F, F, F, F, T},
         {F, F, T, F, F, T, T, T},
         {T, T, F, T, T, T, F, F},
         {F, T, T, T, T, F, T, F},
         {F, T, T, F, F, T, T, F},
         {T, F, T, T, T, T, T, T}}
</code></pre>
         
## Example 7:
<pre><code>Input: columns = {{2,1}, {1,1}, {3,1}, {1,1}, {2,1}}; rows = {{0,1}, {2,1}, {0,3}, {2,1}, {0,1}}
Output: null
Explanation: this puzzle with the given columns and rows is impossible to solve as a nonogram.
</code></pre>
