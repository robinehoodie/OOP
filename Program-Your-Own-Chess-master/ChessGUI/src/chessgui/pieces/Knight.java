package chessgui.pieces;

import chessgui.Board;

public class Knight extends Piece {

    public Knight(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass)
    {
        super(x,y,is_white,file_path, board, PIECETYPE, pass);
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {

        // Remember: a knight can move in any L shape and can jump over anyone
        // in order to do so. He cannot attack his own pieces.
        // By an L shape, I mean it can move to a square that is 2 squares away
        // horizontally and 1 square away vertically, or 1 square away horizontally
        // and 2 squares away vertically.
        // some examples:
        //
        //  * *       * * *           *       *
        //  *             *       * * *       *
        //  *                                 * *
            
                // WRITE CODE HERE
        int x = this.getX();
    	int y = this.getY();
    	
    	int diagonal_y = Math.abs(destination_y - y);
    	int diagonal_x = Math.abs(destination_x - x);
 
        Piece pPiece = board.getPiece(destination_x, destination_y);
        if( pPiece != null) {
        	if(pPiece.isWhite() && this.isWhite()) {
        		return false;
        	}
        	else if(pPiece.isBlack() && this.isBlack()) {
        		return false;
        	}
        }
        
        if(((diagonal_x == 2 && diagonal_y == 1)||(diagonal_y == 2 && diagonal_x == 1))) {
        	return true;
        }
        return false; 
    }
}
