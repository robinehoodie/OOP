package chessgui.pieces;

import chessgui.Board;

public class Pawn extends Piece {

    private boolean has_moved;
    
    public Pawn(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
        has_moved = false;
    }
    
    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved()
    {
        return has_moved;
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
        // Remember: A pawn may only move towards the oponent's side of the board.
        // If the pawn has not moved yet in the game, for its first move it can 
        // move two spaces forward. Otherwise, it may only move one space. 
        // When not attacking it may only move straight ahead.
        // When attacking it may only move space diagonally forward

                // WRITE CODE HERE
        
        boolean hasMoved = this.getHasMoved();
        
    	int x = this.getX();
    	int y = this.getY();
        
    	int distance_x = Math.abs(destination_x - x);    	
    	int distance_y = Math.abs(destination_y - y);
    	
    	
        Piece p = board.getPiece(destination_x, destination_y);
        
        if( p != null) {
        	if(p.isWhite() && this.isWhite()) {
        		return false;
        	}
        	else if(p.isBlack() && this.isBlack()) {
        		return false;
        	}
        }
        
    	String direction = "";
        
        if(distance_x > 1 || distance_y > 2) {
        	return false;
        }
    	if(distance_x == 1 && distance_y != 1) {
    		return false;
    	}
    	
        if(y > destination_y) {
        	direction = "north";
        }
        if(y < destination_y) {
        	direction = "south";
        }
        if(has_moved && distance_y > 1) {
        	return false;
        }
        
        if("north".equals(direction)) {
        	if(this.isWhite()) {
        		return false;
        	}
        	if(p == null && distance_x == 1) {
        		return false;
        	}
        	if(distance_x != 1) {
        		Piece nPiece = board.getPiece(x, y - 1);
        		Piece nPiece1 = board.getPiece(x, y - 2);
        		if(nPiece != null) {
        			return false;
        		}
        		if(distance_y == 2 && nPiece1 != null) {
        			return false;
        		}
        	}
        }
        if("south".equals(direction)) {
        	if(this.isBlack()) {
        		return false;
       		}
       		if(p == null && distance_x == 1) {
           		return false;
           	}
           	if(distance_x != 1) {
           		Piece nPiece = board.getPiece(x, y + 1);
           		Piece nPiece1 = board.getPiece(x, y + 2);
           		if(nPiece != null) {
           			return false;
           		}
           		if(distance_y == 2 && nPiece1 != null) {
           			return false;
           		}
        	}
        }       
        return true;
    }
}
