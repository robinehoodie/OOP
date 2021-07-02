package chessgui.pieces;

import chessgui.Board;

public class King extends Piece {

    public King(int x, int y, boolean is_white, String file_path, Board board, String PIECETYPE, boolean pass){
        super(x,y,is_white,file_path, board, PIECETYPE, pass);
    }

    @Override
    public boolean canMove(int destination_x, int destination_y){
        Piece possiblePiece = board.getPiece(destination_x, destination_y);
              
        if(possiblePiece !=null){
            if(possiblePiece.isWhite()&&this.isWhite()){
                return false;
            }
            if(possiblePiece.isBlack()&&this.isBlack()){
                return false;
            }
        }

        if(board.validation(destination_x, destination_y,"KING")){   
            if((destination_x==6||destination_x==2)&&this.has_moved==false){
                if(board.canCastle(destination_x, destination_y)){
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
                        if(destination_x==2&&queenSide1==null&&queenSide2==null&&queenSide3==null&&queenRook!=null&&this.has_moved==false&&queenRook.has_moved==false){
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
                        if(destination_x==2&&queenSide1black==null&&queenSide2black==null&&queenSide3black==null&&queenRookblack!=null&&this.has_moved==false&&queenRook.has_moved==false){
                            queenRookblack.setX(3);
                            this.has_moved=true;  
                            queenRookblack.has_moved=true;
                            return true;  
                        }
                    }
                }else{
                    return false;
                }
            }else{
                int x,y;

                x = Math.abs(this.getX()-destination_x);
                y = Math.abs(this.getY()-destination_y);

                if((x==1&&y==1)||(x==0&&y==1)||(x==1&&y==0)){
                    this.has_moved=true;
                    return true;
                }else{
                    return false;
                }
            }   
        }
        return false;
    }
}