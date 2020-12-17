package chessgui.pieces;

import chessgui.Board;

public class Queen extends Piece {

    public Queen(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
        // Remember: A Queen can move as many squares as she wants forward, 
        // backward, sideways, or diagonally, without jumping over any pieces.
        // She cannot attack her own pieces.
        
        // WRITE CODE HERE
        
    	int x = this.getX();
    	int y = this.getY();
    	
    	int diagonal_y = Math.abs(destination_y - y);
    	int diagonal_x = Math.abs(destination_x - x);        
        
        Piece possiblePiece = board.getPiece(destination_x, destination_y);
        
        if(possiblePiece !=null){
            if(possiblePiece.isWhite()&&this.isWhite()){
                return false;
            }
            if(possiblePiece.isBlack()&&this.isBlack()){
                return false;
            }
        }
        
        if(diagonal_x != diagonal_y && (this.getX() != destination_x && this.getY() != destination_y)) {
        	return false;
        }

        int diagonal_length = diagonal_x;
         
        String direction = "";
        
        if(destination_y > this.getY()){
            direction = "south";
        }
        
        if(destination_y < this.getY()){
            direction = "north";
        }
        
        if(destination_x > this.getX()){
            direction = "east";
        }
        
        if(destination_x < this.getX()){
            direction ="west";
        }
        
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
        
        if(direction.equals("south")){
            int spaces_to_move = Math.abs(destination_y - this.getY());
            for(int i = 1; i < spaces_to_move; i++){
                Piece p = board.getPiece(this.getX(), this.getY() + i);
                if(p !=null){
                    return false;
                }
            }
        }
        
        if(direction.equals("north")){
            int spaces_to_move = Math.abs(destination_y - this.getY());
            for(int i = 1; i < spaces_to_move; i++){
                Piece p = board.getPiece(this.getX(), this.getY() - i);
                if(p !=null){
                    return false;
                }
            }
        } 
         
        if(direction.equals("east")){
            int spaces_to_move = Math.abs(destination_y - this.getY());
            for(int i = 1; i < spaces_to_move; i++){
                Piece p = board.getPiece(this.getX() +i, this.getY());
                if(p !=null){
                    return false;
                }
            }
        }       
         
        if(direction.equals("west")){
            int spaces_to_move = Math.abs(destination_y - this.getY());
            for(int i = 1; i < spaces_to_move; i++){
                Piece p = board.getPiece(this.getX() -i, this.getY());
                if(p !=null){
                    return false;
                }
            }
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
    }
}
