package pieces;

/**
 * guaranteed to be valid updatePieceCoordinates
 * Created by SiyangLiu on 2018/2/3.
 */
public class Move {
    private Piece piece;
    private Piece toCapture;


    private int startRow;
    private int startCol;

    private int destRow;
    private int destCol;

    public Move(Piece piece, int destRow, int destCol, Piece toCapture) {
        this.piece = piece;
        this.startRow = piece.getRowIndex();
        this.startCol = piece.getColIndex();
        this.destRow = destRow;
        this.destCol = destCol;
        this.toCapture = toCapture;
    }

    // Overriding equals() to compare two Move objects
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of Move or not
        if (!(o instanceof Move)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Move move = (Move) o;

        // Compare the data members and return accordingly
        boolean retval = toCapture == null ? move.toCapture == null : toCapture.equals(move.toCapture);
//        boolean retval = true;

        return startRow == move.startRow
                && startCol == move.startCol
                && destRow == move.destRow
                && destCol == move.destCol
                && piece.equals(move.piece)
                && retval;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getDestRow() {
        return this.destRow;
    }

    public int getDestCol() {
        return this.destCol;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public int getStartCol() {
        return this.startCol;
    }

    public Piece getToCapture() {
        return toCapture;
    }
}
