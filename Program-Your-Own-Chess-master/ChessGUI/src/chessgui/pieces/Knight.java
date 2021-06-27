package chessgui.pieces;

import chessgui.Board;

public class Knight extends Piece {

    public Knight(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass){
        super(x,y,is_white,file_path, board, PIECETYPE, pass);
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y){
        boolean ret=false;
        
        int x = this.getX();
    	int y = this.getY();
    	
    	int diagonal_y = Math.abs(destination_y - y);
    	int diagonal_x = Math.abs(destination_x - x);
 
        Piece p = board.getPiece(destination_x, destination_y);
        if( p != null) {
        	if(p.isWhite() && this.isWhite()) {
        		return false;
        	}
        	else if(p.isBlack() && this.isBlack()) {
        		return false;
        	}
        }
        if(board.validation(destination_x, destination_y,"KNIGHT")){
            if(((diagonal_x == 2 && diagonal_y == 1)||(diagonal_y == 2 && diagonal_x == 1))) {
                return true;
            }
        }else{
            return false; 
        }
        return ret;
    }
}
