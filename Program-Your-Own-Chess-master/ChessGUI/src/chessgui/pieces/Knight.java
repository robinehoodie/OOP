package chessgui.pieces;

import chessgui.Board;

public class Knight extends Piece {

    public Knight(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass){
        super(x,y,is_white,file_path, board, PIECETYPE, pass);
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y){
    	
    	int y=Math.abs(destination_y-this.getY());
    	int x=Math.abs(destination_x-this.getX());
 
        Piece possiblePiece=board.getPiece(destination_x, destination_y);
        if(possiblePiece!=null) {
        	if(possiblePiece.isWhite()&&this.isWhite()){
        		return false;
        	}
        	else if(possiblePiece.isBlack()&&this.isBlack()){
        		return false;
        	}
        }
        if(board.validation(destination_x, destination_y, "KNIGHT")){
            if(((x==2&&y==1)||(y==2&&x==1))) {
                return true;
            }
        }else{
            return false; 
        }
        return false;
    }
}
