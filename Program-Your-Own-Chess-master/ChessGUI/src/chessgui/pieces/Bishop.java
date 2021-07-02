package chessgui.pieces;

import chessgui.Board;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass){
        super(x,y,is_white,file_path, board, PIECETYPE,pass);
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y){

    	int x = this.getX();
    	int y = this.getY();
    	
    	int length_Y = Math.abs(destination_y - y);
    	int length_X = Math.abs(destination_x - x);        
        
        Piece possiblePiece = board.getPiece(destination_x, destination_y);
        
        if(possiblePiece !=null){
            if(possiblePiece.isWhite()&&this.isWhite()){
                return false;
            }
            if(possiblePiece.isBlack()&&this.isBlack()){
                return false;
            }
        }
        
        if(length_X != length_Y) {
        	return false;
        }
        if(board.validation(destination_x, destination_y,"BISHOP")){      
            int diagonal_length = length_X;
            
            String direction = "";
            
            if(destination_y > y && destination_x < x) {
                direction = "southwest";
            }
            if(destination_y < y && destination_x < x) {
                direction = "northwest";
            }
            if(destination_y > y && destination_x > x) {
                direction = "southeast";
            }
            if(destination_y < y && destination_x > x) {
                direction = "northeast";
            }
            
            if(direction.equals("southeast")) {
                for(int i = 1; i < diagonal_length; i++) {
                Piece p = board.getPiece(x + i, y + i);
                    if( p != null) {
                            return false;
                    }
                }
            }
            
            if(direction.equals("northeast")) {
                for(int i = 1; i < diagonal_length; i++) {
                Piece p = board.getPiece(x + i, y - i);
                    if( p != null) {
                        return false;
                    }
                }
            }
            
            if(direction.equals("southwest")) {
                for(int i = 1; i < diagonal_length; i++) {
                Piece p = board.getPiece(x - i, y + i);
                    if( p != null) {
                            return false;
                    }
                }
            }
            
            if(direction.equals("northwest")) {
                for(int i = 1; i < diagonal_length; i++) {
                Piece p = board.getPiece(x - i, y - i);
                    if( p != null) {
                            return false;
                    }
                }
            }
            return true;
        }else{
            return false;
        }  
    }
}
