package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        for (int col = 0; col < board.size();col += 1 ) {
            rowMechanism(col);
        }
        changed = true;
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Function That does the following per row:
     * Starting from the top all the way to the bottom
     * 1- where is the next tile above if there is one  (done)
     * 2- check if the next tile is the same number, if there is one (done)
     * 3- Once i get that location i should move the tile to that position (done)
     * 4- If merge happened i should update the score (done)
     * 5- go the the next row (done )
     *
     */

    private void rowMechanism(int col) {
        int row_blocked = 0;
        for (int row = board.size() - 2; row >= 0 ; row -= 1) {
            Tile t = board.tile(col, row);
            if (board.tile(col, row) != null) {
                int next_tile_row = getNextTileInSameColumnUpper(board, col, row);
                boolean scoreIncrement = moveToNextPossibleRow(board, col, row, next_tile_row, row_blocked);
                if (scoreIncrement){
                    score += 2 * t.value();
                    row_blocked = next_tile_row;
                }
            }

        }
    }

    /** This funciton checks where is the next tile up on the same column
     * respect itself, if something is fined it retunrs the row of the next tile
     * if nothing is found returns 0.
     * */

    private  int getNextTileInSameColumnUpper(Board b, int col, int current_row){
        for (int row = current_row + 1; row < b.size(); row +=1){
            if (row < b.size()){
                if (b.tile(col, row) == null){
                    continue;
                } else {
                    return row;
                }
            }
        }
        return 0;

    }

    /* Function that checks if two tiles are have the same value

    * */

    private boolean hasTilesSameValue(Tile t1, Tile t2){
        return t1.value() == t2.value();
    }


    /** This function would decide if move or not, and it will return how much should increment the
     * score. It also recives a row_blocked, what it would now if a tile in a row was genrerated in the current
     * move, in that case it would not allow to move to that part
     * */

    private boolean moveToNextPossibleRow(Board b, int col, int row,  int next_tile_row, int row_blocked){
        Tile t1 = b.tile(col,row);
        if (next_tile_row ==0){
           return b.move(col, b.size()-1, t1);

        }
        else {
            Tile t2 = b.tile(col, next_tile_row);
            if (hasTilesSameValue(t1, t2) && row_blocked != next_tile_row){
                return b.move(col, next_tile_row, t1);

            } else if (row+1 != next_tile_row ) {
                return b.move(col,next_tile_row-1,t1);

            } else {
                return false;
            }

        }
    }


    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col+=1){
            for (int row = 0;row < b.size(); row+=1){
                if(b.tile(col,row)==null){
                    return  true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (b.tile(col, row) != null && b.tile(col, row).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return  false;
    }
    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b)){
            return true;
        }
        else if (twoAdjacentSameValueExists(b)) {
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Helper Function that checks if two adjacent tiles has
     * the same value, if it does, then return true, if not
     * return false. This does not allow to check outside the boundaries
     * of the board
     */
    public static boolean twoAdjacentSameValueExists(Board b){
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (col + 1 < b.size()) {
                    if (b.tile(col, row).value() == b.tile(col + 1, row).value()) {
                        return true;
                    }
                }
                if (row + 1 < b.size()) {
                    if (b.tile(col, row).value() == b.tile(col, row+1).value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
