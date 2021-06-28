package chessgui.pieces;

import javax.management.monitor.GaugeMonitor;

import chessgui.Board;

public class King extends Piece {

    public King(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass){
        super(x,y,is_white,file_path, board, PIECETYPE, pass);
    }

    @Override
    public boolean canMove(int destination_x, int destination_y){
        Piece possiblePiece = board.getPiece(destination_x, destination_y);
        
    	int x = this.getX();
    	int y = this.getY();


    	int diagonal_y = Math.abs(destination_y - y);
    	int diagonal_x = Math.abs(destination_x - x);        
        
        
        if(possiblePiece !=null){
            if(possiblePiece.isWhite()&&this.isWhite()){
                return false;
            }
            if(possiblePiece.isBlack()&&this.isBlack()){
                return false;
            }
        }

        boolean valid = false;

        if(board.validation(destination_x, destination_y,"KING")){
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
    
            Piece kingWhite = board.getPiece(4, 7);
            Piece kingBlack = board.getPiece(4, 7);
         
            if((destination_x==6||destination_x==2)&&this.has_moved==false){
                    Piece kingSide1 = board.getPiece(5,7);
                    Piece kingSide2 = board.getPiece(6,7);
                    Piece queenSide1 = board.getPiece(1,7);
                    Piece queenSide2 = board.getPiece(2,7);
                    Piece queenSide3 = board.getPiece(3,7);
                    Piece queenRook = board.getPiece(0,7);
                    Piece kingRook = board.getPiece(7,7);
    
    
                    Piece kingSide1black = board.getPiece(5,0);
                    Piece kingSide2black = board.getPiece(6,0);
                    Piece queenSide1black = board.getPiece(1,0);
                    Piece queenSide2black = board.getPiece(2,0);
                    Piece queenSide3black = board.getPiece(3,0);
                    Piece queenRookblack = board.getPiece(0,0);
                    Piece kingRookblack = board.getPiece(7,0);     
                    if(this.isWhite()){
                        if(destination_x==6&&kingSide1==null&&kingSide2==null&&this.has_moved==false&&kingRook.has_moved==false){
                            this.setX(6);
                            kingRook.setX(5);
                            this.has_moved=true;  
                            kingRook.has_moved=true;
                            return true;  
                          
                        }
                        if(destination_x==2&&queenSide1==null&&queenSide2==null&&queenSide3==null&&queenRook!=null&&this.has_moved==false){
                            this.setX(2);
                            queenRook.setX(3);
                            this.has_moved=true;  
                            queenRook.has_moved=true;
                            return true;  
                          
                        }  
                    }else{
                        if(destination_x==6&&kingSide1black==null&&kingSide2black==null&&this.has_moved==false){ 
                            kingRookblack.setX(5);  
                            this.has_moved=true;   
                            kingRookblack.has_moved=true; 
                            return true;  
                        
                        }
    
                        if(destination_x==2&&queenSide1black==null&&queenSide2black==null&&queenSide3black==null&&queenRookblack!=null&&this.has_moved==false){
                            queenRookblack.setX(3);
                            this.has_moved=true;  
                            queenRookblack.has_moved=true;
                            return true;  
                        }
                    }
            }else{
                if(direction.equals("south")){
                    int spaces_to_move = Math.abs(destination_y - this.getY());
                    for(int i = 1; i < spaces_to_move; i++){
                        Piece p = board.getPiece(this.getX(), this.getY());
                        if(p !=null){
                            return false;
                        }
                    }
                }
                
                if(direction.equals("north")){
                    int spaces_to_move = Math.abs(destination_y - this.getY());
                    for(int i = 1; i < spaces_to_move; i++){
                        Piece p = board.getPiece(this.getX(), this.getY());
                        if(p !=null){
                            return false;
                        }
                    }
                } 
                 
                if(direction.equals("east")){
                    int spaces_to_move = Math.abs(destination_x - this.getX());
                    for(int i = 1; i < spaces_to_move; i++){
                        Piece p = board.getPiece(this.getX(), this.getY());
                        if(p !=null){
                            return false;
                        }
                    }
                }       
                 
                if(direction.equals("west")){
                    int spaces_to_move = Math.abs(destination_x - this.getX());
                    for(int i = 1; i < spaces_to_move; i++){
                        Piece p = board.getPiece(this.getX(), this.getY());
                        if(p !=null){
                            return false;
                        }
                    }
                }
                 
                if(direction.equals("southeast")) {
                    for(int i = 1; i < diagonal_length; i++) {
                    Piece p = board.getPiece(x, y);
                        if( p != null) {
                                return false;
                        }
                    }
                }
                
                if(direction.equals("northeast")) {
                    for(int i = 1; i < diagonal_length; i++) {
                    Piece p = board.getPiece(x, y);
                        if( p != null) {
                            return false;
                        }
                    }
                }
                
                if(direction.equals("southwest")) {
                    for(int i = 1; i < diagonal_length; i++) {
                    Piece p = board.getPiece(x, y);
                        if( p != null) {
                                return false;
                        }
                    }
                }
                
                if(direction.equals("northwest")) {
                    for(int i = 1; i < diagonal_length; i++) {
                    Piece p = board.getPiece(x, y);
                        if( p != null) {
                                return false;
                        }
                    }
                }
                this.has_moved=true;  
                return true;
            }   
        }
        return false;
    }
}