package chessgui.pieces;

import chessgui.Board;

public class Pawn extends Piece {

    private boolean has_moved;
    
    public Pawn(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass){
        super(x,y,is_white,file_path, board, PIECETYPE, pass);
        has_moved = false;
    }
    
    public void setHasMoved(boolean has_moved){
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved(){
        return has_moved;
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y){
        
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
        if(board.validation(destination_x, destination_y,"PAWN")){
			/* en passant */
			if(p==null){
				int passantX;
				int passantY;
				int captureDistance;

				captureDistance = Math.abs(destination_x-this.getX());

				Piece enPassant;
				if(this.isWhite()&&this.getY()==3){
					passantX = (this.getX()>destination_x)?this.getX()-1:this.getX()+1;
					passantY = destination_y+1;
					enPassant = board.getPiece(passantX,passantY);
					if(enPassant!=null&&enPassant.PIECETYPE.equals("PAWN")&&board.enPassant!=null&&captureDistance==1){
						board.Black_Pieces.remove(enPassant);
						return true;
					}
				}
			
				if(this.isBlack()&&this.getY()==4){
					passantX = (this.getX()<destination_x)?this.getX()+1:this.getX()-1;
					passantY = destination_y-1;
					enPassant = board.getPiece(passantX,passantY);
					if(enPassant!=null&&enPassant.PIECETYPE.equals("PAWN")&&board.enPassant!=null&&captureDistance==1){
						board.White_Pieces.remove(enPassant);
						return true;
					}
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
				if(this.isBlack()) {
					return false;
				}
				if(p == null && distance_x == 1) {
					return false;
				}
				if(distance_x != 1) {
					Piece move = board.getPiece(x, y - 1);
					Piece firstMove = board.getPiece(x, y - 2);
					if(move != null) {
						return false;
					}
					if(distance_y == 2 && firstMove != null) {
						return false;
					}

					if(firstMove==null&&distance_y==2){
						this.pass=true;
					}
				}
			}
			if("south".equals(direction)) {
				if(this.isWhite()) {
					return false;
				}
				if(p == null && distance_x == 1) {
					return false;
				}
				if(distance_x != 1) {
					Piece move = board.getPiece(x, y + 1);
					Piece firstMove = board.getPiece(x, y + 2);
					if(move != null) {
						return false;
					}
					if(distance_y == 2 && firstMove != null) {
						return false;
					}

					if(firstMove==null&&distance_y==2){
						this.pass=true;
					}
				}
			}		
			return true; 
		}else{
			return false;
		}
	}
}
