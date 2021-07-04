package chessgui;

import chessgui.pieces.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JComponent {
        
    public int turnCounter = 0;
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

    private final int Square_Width = 65;
    public ArrayList<Piece> White_Pieces;
    public ArrayList<Piece> Black_Pieces;
    
    String Directions[] = {"NORTH","SOUTH","EAST","WEST","NORTHEAST","NORTHWEST","SOUTHEAST","SOUTHWEST","KNIGHT"};    

    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    public Piece Active_Piece;
    public Piece candidate_Piece;
    public Piece enPassant;
    public Piece Square_Piece;
    
    private final int rows = 8;
    private final int cols = 8;
    private Integer[][] BoardGrid;
    private String board_file_path = "images" + File.separator + "board.png";
    private String active_square_file_path = "images" + File.separator + "active_square.png";

    public void initGrid(Piece P,int row, int col){
         Object[] option = {
            "Queen" , "Rook", "Bishop", "Knight"
        };
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                BoardGrid[i][j] = 0;
            }
        }

        //Image white_piece = loadImage("images/white_pieces/" + piece_name + ".png");
        //Image black_piece = loadImage("images/black_pieces/" + piece_name + ".png");  
        if(P==null){
            Black_Pieces.add(new King(4,0,false,"King.png",this,"KING",false));
            Black_Pieces.add(new Queen(3,0,false,"Queen.png",this,"QUEEN",false));
            Black_Pieces.add(new Bishop(2,0,false,"Bishop.png",this,"BISHOP",false));
            Black_Pieces.add(new Bishop(5,0,false,"Bishop.png",this,"BISHOP",false));
            Black_Pieces.add(new Knight(1,0,false,"Knight.png",this,"KNIGHT",false));
            Black_Pieces.add(new Knight(6,0,false,"Knight.png",this,"KNIGHT",false));
            Black_Pieces.add(new Rook(0,0,false,"Rook.png",this,"ROOK",false));
            Black_Pieces.add(new Rook(7,0,false,"Rook.png",this,"ROOK",false));
            Black_Pieces.add(new Pawn(0,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(1,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(2,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(3,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(4,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(5,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(6,1,false,"Pawn.png",this,"PAWN",false));
            Black_Pieces.add(new Pawn(7,1,false,"Pawn.png",this,"PAWN",false));
    
            White_Pieces.add(new King(4,7,true,"King.png",this,"KING",false));
            White_Pieces.add(new Queen(3,7,true,"Queen.png",this,"QUEEN",false));
            White_Pieces.add(new Bishop(2,7,true,"Bishop.png",this,"BISHOP",false));
            White_Pieces.add(new Bishop(5,7,true,"Bishop.png",this,"BISHOP",false));
            White_Pieces.add(new Knight(1,7,true,"Knight.png",this,"KNIGHT",false));
            White_Pieces.add(new Knight(6,7,true,"Knight.png",this,"KNIGHT",false));
            White_Pieces.add(new Rook(0,7,true,"Rook.png",this,"ROOK",false));
            White_Pieces.add(new Rook(7,7,true,"Rook.png",this,"ROOK",false));
            White_Pieces.add(new Pawn(0,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(1,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(2,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(3,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(4,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(5,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(6,6,true,"Pawn.png",this,"PAWN",false));
            White_Pieces.add(new Pawn(7,6,true,"Pawn.png",this,"PAWN",false));            
        }else{
            if(P.isWhite()){                
                int button_pressed = JOptionPane.showOptionDialog(null, "Choose Piece" ,"Promotion",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0] );
                if (button_pressed == 0){
                    White_Pieces.add(new Queen(col,row,true,"Queen.png",this,"QUEEN",false)); 
                }else if(button_pressed == 1){
                    White_Pieces.add(new Rook(col,row,true,"Rook.png",this,"ROOK",false)); 
                }else if(button_pressed == 2){
                    White_Pieces.add(new Bishop(col,row,true,"Bishop.png",this,"BISHOP",false)); 
                }else if(button_pressed == 3){
                    White_Pieces.add(new Knight(col,row,true,"Knight.png",this,"KNIGHT",false)); 
                }
            }else{
                int button_pressed = JOptionPane.showOptionDialog(null, "Choose Piece" ,"Promotion",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0] );
                if (button_pressed == 0){
                    Black_Pieces.add(new Queen(col,row,false,"Queen.png",this,"QUEEN",false)); 
                }else if(button_pressed == 1){
                    Black_Pieces.add(new Rook(col,row,false,"Rook.png",this,"ROOK",false)); 
                }else if(button_pressed == 2){
                    Black_Pieces.add(new Bishop(col,row,false,"Bishop.png",this,"BISHOP",false)); 
                }else if(button_pressed == 3){
                    Black_Pieces.add(new Knight(col,row,false,"Knight.png",this,"KNIGHT",false)); 
                }
            }
        }    
    }

    public Board() {

        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();

        Piece P=null;

        initGrid(P,0,0);

        this.setBackground(new Color(255,255,255));
        this.setPreferredSize(new Dimension(520, 570));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
        this.addKeyListener(keyAdapter);
        
        this.setEnabled(false);
        this.setVisible(true);
        this.requestFocus();
        drawBoard();
    }

    private void drawBoard(){
        Piece_Graphics.clear();
        Static_Shapes.clear();
        // initGrid();
        
        Image board = loadImage(board_file_path);
        Static_Shapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
        if (Active_Piece != null){
            possibleMoves();
            Image active_square = loadImage("images" + File.separator + "active_square.png");
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Active_Piece.getX(),Square_Width*Active_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }
        for (int i = 0; i < White_Pieces.size(); i++){
            int COL = White_Pieces.get(i).getX();
            int ROW = White_Pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "white_pieces" + File.separator + White_Pieces.get(i).getFilePath());  
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width*COL,Square_Width*ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        for (int i = 0; i < Black_Pieces.size(); i++){
            int COL = Black_Pieces.get(i).getX();
            int ROW = Black_Pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + Black_Pieces.get(i).getFilePath());  
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width*COL,Square_Width*ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        this.repaint();
    }

    public Piece getPiece(int x, int y) {

        for (Piece p : White_Pieces){
            if (p.getX() == x && p.getY() == y){
                return p;
            }
        }
        for (Piece p : Black_Pieces){
            if (p.getX() == x && p.getY() == y){
                return p;
            }
        }
        return null;
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {               
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX();
            int d_Y = e.getY();  
        
            int Clicked_Row = d_Y / Square_Width;
            
            int Clicked_Column = d_X / Square_Width;
        
            boolean is_whites_turn = true;
            if (turnCounter%2 == 1){
                is_whites_turn = false;
            }
            
            Piece clicked_piece = getPiece(Clicked_Column, Clicked_Row);

            if (Active_Piece == null && clicked_piece != null && ((is_whites_turn && clicked_piece.isWhite()) || (!is_whites_turn && clicked_piece.isBlack()))){
                Active_Piece = clicked_piece;
                candidate_Piece = clicked_piece;
                Square_Piece = clicked_piece;
            }else if (Active_Piece != null && Active_Piece.getX() == Clicked_Column && Active_Piece.getY() == Clicked_Row){
                Active_Piece = null;
            }else if (Active_Piece != null && Active_Piece.canMove(Clicked_Column, Clicked_Row) && ((is_whites_turn && Active_Piece.isWhite()) || (!is_whites_turn && Active_Piece.isBlack()))){
                if (clicked_piece != null){
                    if (clicked_piece.isWhite()){
                        White_Pieces.remove(clicked_piece);
                    }else{
                        Black_Pieces.remove(clicked_piece);
                    }
                }

                if(Active_Piece.isWhite()){
                    if(Active_Piece.PIECETYPE=="PAWN"&&Clicked_Row==0){
                        White_Pieces.remove(Active_Piece);
                        initGrid(Active_Piece,Clicked_Row,Clicked_Column);             
                    }
                }else{
                    if(Active_Piece.PIECETYPE=="PAWN"&&Clicked_Row==7){
                        Black_Pieces.remove(Active_Piece);
                        initGrid(Active_Piece,Clicked_Row,Clicked_Column);             
                    }                        
                }

                Active_Piece.setX(Clicked_Column);
                Active_Piece.setY(Clicked_Row);
                
                if (Active_Piece.getClass().equals(Pawn.class)){
                    Pawn castedPawn = (Pawn)(Active_Piece);
                    castedPawn.setHasMoved(true);
                }

                enPassant = null;
                if(candidate_Piece!=null&&candidate_Piece.PIECETYPE.equals("PAWN")&&candidate_Piece.pass){
                    enPassant = candidate_Piece;
                }else{
                    candidate_Piece.pass=false;
                }

                Active_Piece = null;
                candidate_Piece = null;
                turnCounter++;
            }
            drawBoard();
        }

        @Override
        public void mouseDragged(MouseEvent e) {		
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }	
    };
    
    public void possibleMoves(){
        Image active_square = loadImage("images" + File.separator + "active_square.png");
        // Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Active_Piece.getX(),Square_Width*Active_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));   
        if(Square_Piece.PIECETYPE.equals("QUEEN")){
            if(Square_Piece.isWhite()){
                for(int i=Square_Piece.getY()-1;i>=0;i--){//north
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;             
                        }
                       if(p.isWhite()){
                           break;
                       }  
                    }
                }
                for(int i=Square_Piece.getY()+1;i<=70;i++){//south
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;     
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1;i<=7;i++){//east
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;              
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1;i>=0;i--){//west
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;          
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()-1;i<=7&&j>=0;i++,j--){//northeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;       
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()+1;i<=7&&j<=7;i++,j++){//southeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;      
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()-1;i>=0&&j>=0;i--,j--){//northwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;          
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()+1;i>=0&&j<=7;i--,j++){//southwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;           
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
            }else{//black queen
                for(int i=Square_Piece.getY()-1;i>=0;i--){//north
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;         
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getY()+1;i<=70;i++){//south
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;          
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1;i<=7;i++){//east
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;            
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1;i>=0;i--){//west
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;                   
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()-1;i<=7&&j>=0;i++,j--){//northeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;        
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()+1;i<=7&&j<=7;i++,j++){//southeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;                     
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()-1;i>=0&&j>=0;i--,j--){//northwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;          
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()+1;i>=0&&j<=7;i--,j++){//southwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;           
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }                
            }
        }
        if(Square_Piece.PIECETYPE.equals("ROOK")){
            if(Square_Piece.isWhite()){
                for(int i=Square_Piece.getY()-1;i>=0;i--){//north
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;         
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getY()+1;i<=70;i++){//south
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;              
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1;i<=7;i++){//east
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;                     
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1;i>=0;i--){//west
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;           
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
            }else{//black rook
                for(int i=Square_Piece.getY()-1;i>=0;i--){//north
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;           
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getY()+1;i<=70;i++){//south
                    Piece p = getPiece(Square_Piece.getX(), i);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*i, active_square.getWidth(null), active_square.getHeight(null))));
                            break;       
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1;i<=7;i++){//east
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;       
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1;i>=0;i--){//west
                    Piece p = getPiece(i, Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*Square_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
                            break;      
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
            }
        }

        if(Square_Piece.PIECETYPE.equals("BISHOP")){
            if(Square_Piece.isWhite()){
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()-1;i<=7&&j>=0;i++,j--){//northeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;       
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()+1;i<=7&&j<=7;i++,j++){//southeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;      
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()-1;i>=0&&j>=0;i--,j--){//northwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;          
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()+1;i>=0&&j<=7;i--,j++){//southwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;           
                        }
                       if(p.isWhite()){
                           break;
                       }
                    }
                }
            }else{//black bishop
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()-1;i<=7&&j>=0;i++,j--){//northeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;        
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()+1, j=Square_Piece.getY()+1;i<=7&&j<=7;i++,j++){//southeast
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;                     
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()-1;i>=0&&j>=0;i--,j--){//northwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;          
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
                for(int i=Square_Piece.getX()-1, j=Square_Piece.getY()+1;i>=0&&j<=7;i--,j++){//southwest
                    Piece p = getPiece(i, j);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*i,Square_Width*j, active_square.getWidth(null), active_square.getHeight(null))));
                            break;           
                        }
                       if(p.isBlack()){
                           break;
                       }
                    }
                }
            }
        }

        if(Square_Piece.PIECETYPE.equals("KNIGHT")){
            int x,y;
            if(Square_Piece.isWhite()){
                Piece p;
                x = Square_Piece.getX()+2;
                y = Square_Piece.getY()-1;
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()+2; 
                y = Square_Piece.getY()+1;
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()+1; 
                y = Square_Piece.getY()+2;          
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()-1; 
                y = Square_Piece.getY()+2;          
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }         
                x = Square_Piece.getX()-2; 
                y = Square_Piece.getY()+1;       
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()-2; 
                y = Square_Piece.getY()-1;    
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()-1; 
                y = Square_Piece.getY()-2;   
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()+1; 
                y = Square_Piece.getY()-2;  
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
            }else{//black knight
                Piece p;
                x = Square_Piece.getX()+2;
                y = Square_Piece.getY()-1;
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()+2; 
                y = Square_Piece.getY()+1;
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()+1; 
                y = Square_Piece.getY()+2;          
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()-1; 
                y = Square_Piece.getY()+2;          
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }         
                x = Square_Piece.getX()-2; 
                y = Square_Piece.getY()+1;       
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()-2; 
                y = Square_Piece.getY()-1;    
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()-1; 
                y = Square_Piece.getY()-2;   
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                x = Square_Piece.getX()+1; 
                y = Square_Piece.getY()-2;  
                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
            }    
        }

        if(Square_Piece.PIECETYPE.equals("KING")){
            if(Square_Piece.isWhite()){
                Piece p;
                int x,y;

                x=Square_Piece.getX()+1;//east
                y=Square_Piece.getY();

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()-1;//west
                y=Square_Piece.getY();

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX();//north
                y=Square_Piece.getY()-1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }                

                x=Square_Piece.getX();//south
                y=Square_Piece.getY()+1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()+1;//northeast
                y=Square_Piece.getY()-1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()-1;//northwest
                y=Square_Piece.getY()-1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()+1;//southeast
                y=Square_Piece.getY()+1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()-1;//southwest
                y=Square_Piece.getY()+1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isBlack()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                /////////////////////////////////////////////////////////(castling squares)
                Piece rook, knight, bishop, queen;
                rook = getPiece(7,7);
                knight = getPiece(6,7);
                bishop = getPiece(5,7);
                if(candidate_Piece.getX()==4&&candidate_Piece.getY()==7&&candidate_Piece.has_moved==false&&rook!=null&&rook.has_moved==false&&knight==null&&bishop==null){
                    Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*6,Square_Width*7, active_square.getWidth(null), active_square.getHeight(null))));
                }
                rook = getPiece(0,7);
                knight = getPiece(1,7);
                bishop = getPiece(2,7);
                queen = getPiece(3,7);
                if(candidate_Piece.getX()==4&&candidate_Piece.getY()==7&&candidate_Piece.has_moved==false&&rook!=null&&rook.has_moved==false&&knight==null&&bishop==null&&queen==null){
                    Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*2,Square_Width*7, active_square.getWidth(null), active_square.getHeight(null))));
                }

            }else{//black king
                Piece p;
                int x,y;

                x=Square_Piece.getX()+1;//east
                y=Square_Piece.getY();

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()-1;//west
                y=Square_Piece.getY();

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX();//north
                y=Square_Piece.getY()-1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }                

                x=Square_Piece.getX();//south
                y=Square_Piece.getY()+1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()+1;//northeast
                y=Square_Piece.getY()-1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()-1;//northwest
                y=Square_Piece.getY()-1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()+1;//southeast
                y=Square_Piece.getY()+1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }

                x=Square_Piece.getX()-1;//southwest
                y=Square_Piece.getY()+1;

                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                    p = getPiece(x,y);
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                    }
                    if(p!=null){
                        if(p.isWhite()){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                }
                Piece rook, knight, bishop, queen;
                rook = getPiece(7,0);
                knight = getPiece(6,0);
                bishop = getPiece(5,0);
                if(candidate_Piece.getX()==4&&candidate_Piece.getY()==0&&candidate_Piece.has_moved==false&&rook!=null&&rook.has_moved==false&&knight==null&&bishop==null){
                    Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*6,Square_Width*0, active_square.getWidth(null), active_square.getHeight(null))));
                }
                rook = getPiece(0,0);
                knight = getPiece(1,0);
                bishop = getPiece(2,0);
                queen = getPiece(3,0);
                if(candidate_Piece.getX()==4&&candidate_Piece.getY()==0&&candidate_Piece.has_moved==false&&rook!=null&&rook.has_moved==false&&knight==null&&bishop==null&&queen==null){
                    Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*2,Square_Width*0, active_square.getWidth(null), active_square.getHeight(null))));
                }
            }
        }

        if(Square_Piece.PIECETYPE.equals("PAWN")){
            Piece p,p2,p3;
            int x,y;
            if(Square_Piece.isWhite()){
                p = getPiece(Square_Piece.getX(),Square_Piece.getY()-1);
                    if(Square_Piece.getY()==6){
                        if(Square_Piece.has_moved==false){
                            p2 = getPiece(Square_Piece.getX(), Square_Piece.getY()-1);
                            p3 = getPiece(Square_Piece.getX(), Square_Piece.getY()-2);
                            if(p2==null){
                                Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*5, active_square.getWidth(null), active_square.getHeight(null))));
                                if(p3==null){
                                    Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*4, active_square.getWidth(null), active_square.getHeight(null))));
                                }
                            }
                        }
                    }else{
                        if(p==null){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*(Square_Piece.getY()-1), active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                    p = getPiece(Square_Piece.getX(),Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*(Square_Piece.getY()-1), active_square.getWidth(null), active_square.getHeight(null))));
                    }         
                    x = Square_Piece.getX()+1;
                    y = Square_Piece.getY()-1;
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);
                        if(p!=null){
                            if(p.isBlack()){
                                Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                            }
                        }
                    }
                    x = Square_Piece.getX()-1;
                    y = Square_Piece.getY()-1;
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);
                        if(p!=null){
                            if(p.isBlack()){
                                Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                            }
                        }
                    }
            }else{//black pawn
                p = getPiece(Square_Piece.getX(),Square_Piece.getY()+1);
                    if(Square_Piece.getY()==1){
                        if(Square_Piece.has_moved==false){
                            p2 = getPiece(Square_Piece.getX(), Square_Piece.getY()+1);
                            p3 = getPiece(Square_Piece.getX(), Square_Piece.getY()+2);
                            if(p2==null){
                                Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*2, active_square.getWidth(null), active_square.getHeight(null))));
                                if(p3==null){
                                    Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*3, active_square.getWidth(null), active_square.getHeight(null))));
                                }
                            }
                        }
                    }else{
                        if(p==null){
                            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*(Square_Piece.getY()+1), active_square.getWidth(null), active_square.getHeight(null))));
                        }
                    }
                    p = getPiece(Square_Piece.getX(),Square_Piece.getY());
                    if(p==null){
                        Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Square_Piece.getX(),Square_Width*(Square_Piece.getY()+1), active_square.getWidth(null), active_square.getHeight(null))));
                    }         
                    x = Square_Piece.getX()+1;
                    y = Square_Piece.getY()+1;
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);
                        if(p!=null){
                            if(p.isWhite()){
                                Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                            }
                        }
                    }
                    x = Square_Piece.getX()-1;
                    y = Square_Piece.getY()+1;
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);
                        if(p!=null){
                            if(p.isWhite()){
                                Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*x,Square_Width*y, active_square.getWidth(null), active_square.getHeight(null))));
                            }
                        }
                    }
            }
        }
    }

    public boolean validation(int destination_x, int destination_y, String pieceType){
        int a,i,j,x,y;
        
        boolean ret=false;
        if(candidate_Piece.isWhite()){
            if(candidate_Piece.PIECETYPE.equals("KING")){
                for(a=0;a<9;a++){
                    if(Directions[a].equals("EAST")){
                        for(i=destination_x+1;i<=7;i++){
                            Piece p = getPiece(i, destination_y);         
                            if(p==null){
                                continue;
                            }
                            if(p!=null&&p.isWhite()){
                                if(p.PIECETYPE.equals("KING")){
                                    continue;
                                }else{
                                    ret= true;
                                    break;
                                }
                            }else if(p!=null&&p.isBlack()){
                                if((i==destination_x+1)&&p.PIECETYPE.equals("KING")){
                                    return false;
                                }

                                if(i==destination_x||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                    ret=true;
                                    break;
                                }else{
                                    return false;
                                }
    
                            }else if(p!=null&&p.isBlack()&&(i>destination_x||i<destination_x)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                return false;
                            }
                        }
                    }
                    
                    if(Directions[a].equals("WEST")){
                        for(i=destination_x-1;i>=0;i--){
                            Piece p = getPiece(i, destination_y);         
                            if(p==null){
                                continue;
                            }
                            if(p!=null&&p.isWhite()){
                                if(p.PIECETYPE.equals("KING")){
                                    continue;
                                }else{
                                    ret=true;
                                    break;
                                }
                            }else if(p!=null&&p.isBlack()){

                                if((i==destination_x-1)&&p.PIECETYPE.equals("KING")){
                                    return false;
                                }
                                if(i==destination_x||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                    ret=true;
                                    break;
                                }else{
                                    return false;
                                }
    
                            }else if(p!=null&&p.isBlack()&&(i<destination_x||i>destination_x)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                return false;
                            }
                        }
                    }
    
    
                    if(Directions[a].equals("NORTH")){
                        for(i=destination_y-1;i>=0;i--){
                            Piece p = getPiece(destination_x, i);         
                            if(p==null){
                                continue;
                            }
                            if(p!=null&&p.isWhite()){
                                if(p.PIECETYPE.equals("KING")){
                                    continue;
                                }else{
                                    ret=true;
                                    break;
                                }
                            }else if(p!=null&&p.isBlack()){
                                if((i==destination_y-1)&&p.PIECETYPE.equals("KING")){
                                    return false;
                                }

                                if(i==destination_y||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                    ret=true;
                                    break;
                                }else{
                                    return false;
                                }
    
                            }else if(p!=null&&p.isBlack()&&(i<destination_y||i>destination_y)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                return false;
                            }
                        }                   
                    }
    
    
                    if(Directions[a].equals("SOUTH")){
                        for(i=destination_y+1;i<=7;i++){
                            Piece p = getPiece(destination_x, i);         
                            if(p==null){
                                continue;
                            }
                            if(p!=null&&p.isWhite()){
                                if(p.PIECETYPE.equals("KING")){
                                    continue;
                                }else{
                                    ret=true;
                                    break;
                                }
                            }else if(p!=null&&p.isBlack()){
                                if((i==destination_y+1)&&p.PIECETYPE.equals("KING")){
                                    return false;
                                }
                                if(i==destination_y||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                    ret=true;
                                    break;
                                }else{
                                    return false;
                                }
    
                            }else if(p!=null&&p.isBlack()&&(i<destination_y||i>destination_y)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                return false;
                            }
                        }                   
                    }
                if(Directions[a].equals("NORTHEAST")){
                    for(x=destination_x+1,y=destination_y-1;x<=7&&y>=0;x++,y--){
                        Piece p = getPiece(x,y);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isWhite()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isBlack()){
                            if((x==destination_x+1&&y==destination_y-1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                                return false;
                            }
    
                            if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else {
                                return false;
                            }
    
                        }else if(p!=null&&p.isBlack()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }                
                }
    
                if(Directions[a].equals("NORTHWEST")){
                    for(x=destination_x-1,y=destination_y-1;x>=0&&y>=0;x--,y--){
                        Piece p = getPiece(x,y);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isWhite()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isBlack()){
                            if((x==destination_x-1&&y==destination_y-1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                                return false;
                            }
    
                            if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }
    
                        }else if(p!=null&&p.isBlack()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }                
                }
    
                if(Directions[a].equals("SOUTHEAST")){
                    for(x=destination_x+1,y=destination_y+1;x<=7&&y<=7;x++,y++){
                        Piece p = getPiece(x,y);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isWhite()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isBlack()){
                            if((x==destination_x+1&&y==destination_y+1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                                return false;
                            }
                            if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }
    
                        }else if(p!=null&&p.isBlack()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(  p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }                
                }
    
                if(Directions[a].equals("SOUTHWEST")){
                    for(x=destination_x-1,y=destination_y+1;x>=0&&y<=7;x--,y++){
                        Piece p = getPiece(x,y);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isWhite()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isBlack()){
                            if((x==destination_x-1&&y==destination_y+1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                                return false;
                            }
                            if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }
    
                        }else if(p!=null&&p.isBlack()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(  p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }                
                }
    
                if(Directions[a].equals("KNIGHT")){
                        Piece p;
                        x = destination_x+2;
                        y = destination_y-1;
    
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
    
                        x = destination_x + 2; 
                        y = destination_y + 1;
    
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
    
                        x = destination_x + 1; 
                        y = destination_y + 2;          
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);   
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
    
                        x = destination_x - 1; 
                        y = destination_y + 2;          
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);   
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }    
                        
                        x = destination_x - 2; 
                        y = destination_y + 1;       
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);  
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
    
                        x = destination_x - 2; 
                        y = destination_y - 1;    
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y); 
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
    
                        x = destination_x - 1; 
                        y = destination_y - 2;   
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);  
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
    
                        x = destination_x + 1; 
                        y = destination_y - 2;  
                        if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                            p = getPiece(x,y);   
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
                        }
                    }
                }
    
    
            }else{
                Piece current_piece = candidate_Piece;
                for(Piece K:White_Pieces){
                    if(K.PIECETYPE.equals("KING")){
                        x=K.getX();
                        y=K.getY();
                        for(a=0;a<9;a++){
                            if(Directions[a].equals("EAST")){
                                for(i=x+1;i<=7;i++){
                                    Piece p = getPiece(i, y);     
                                    if(p==null){
                                        if(i==destination_x&&y==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==i&&destination_y==y){
                                                return true;
                                            }
                                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                              
                                    }
                                }
                            }
                            if(Directions[a].equals("WEST")){
                                for(i=x-1;i>=0;i--){
                                    Piece p = getPiece(i, y);     
                                    if(p==null){
                                        if(i==destination_x&&y==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==i&&destination_y==y){
                                                return true;
                                            }
                                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                    
                                    }
                                }
                            }
                            if(Directions[a].equals("NORTH")){                 
                                for(i=y-1;i>=0;i--){
                                    Piece p = getPiece(x, i);         
                                    if(p==null){
                                        if(x==destination_x&&i==destination_y){
                                            break;  
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==x&&destination_y==i){
                                                return true;
                                            }
                                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                  
                                    }
                                }
                            }
    
                            if(Directions[a].equals("SOUTH")){
                                for(i=y+1;i<=7;i++){
                                    Piece p = getPiece(x, i);     
                                    if(p==null){
                                        if(x==destination_x&&i==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==x&&destination_y==i){
                                                return true;
                                            }
                                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                     
                                    }
                                }
                            }
                            if(Directions[a].equals("NORTHEAST")){
                                for(i=x+1,j=y-1;i<=7&&j>=0;i++,j--){
                                    Piece p = getPiece(i, j);     
                                    if(p==null){
                                        if(i==destination_x&&j==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==i&&destination_y==j){
                                                return true;
                                            }

                                            if((i==x+1&&j==y-1)&&p.PIECETYPE.equals("PAWN")){
                                                return false;
                                            }

                                            if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                     
                                    }
                                }
                            }
    
                            if(Directions[a].equals("NORTHWEST")){
                                for(i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
                                    Piece p = getPiece(i, j);     
                                    if(p==null){
                                        if(i==destination_x&&j==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==i&&destination_y==j){
                                                return true;
                                            }

                                            if((i==x-1&&j==y-1)&&p.PIECETYPE.equals("PAWN")){
                                                return false;
                                            }

                                            if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                      
                                    }
                                }
                            }
                            if(Directions[a].equals("SOUTHEAST")){
                                for(i=x+1,j=y+1;i<=7&&j<=7;i++,j++){
                                    Piece p = getPiece(i, j);     
                                    if(p==null){
                                        if(i==destination_x&&j==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==i&&destination_y==j){
                                                return true;
                                            }
                                            if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                   
                                    }
                                }
                            }
                            if(Directions[a].equals("SOUTHWEST")){
                                for(i=x-1,j=y+1;i>=0&&j<=7;i--,j++){
                                    Piece p = getPiece(i, j);     
                                    if(p==null){
                                        if(i==destination_x&&j==destination_y){
                                            break;
                                        }
                                        continue;
                                    }
    
                                    if(p!=null){
                                        if(p.isWhite()){
                                            if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                                continue;
                                            }
    
                                            if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                                break;
                                            }
                                            break;
                                        }else{
                                            if(destination_x==i&&destination_y==j){
                                                return true;
                                            }
                                            if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                                return false;
                                            }
                                        }                                   
                                    }
                                }
                            }
                            if(Directions[a].equals("KNIGHT")){
                                    Piece p;
                                    x = K.getX()+2;
                                    y = K.getY()-1;

                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                
                                    x = K.getX() + 2; 
                                    y = K.getY() + 1;
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                                    x = K.getX() + 1; 
                                    y = K.getY() + 2;          
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                                    x = K.getX() - 1; 
                                    y = K.getY() + 2;          
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }     
                                    x = K.getX() - 2; 
                                    y = K.getY() + 1;       
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                                    x = K.getX() - 2; 
                                    y = K.getY() - 1;    
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                                    x = K.getX() - 1; 
                                    y = K.getY() - 2;   
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                                    x = K.getX() + 1; 
                                    y = K.getY() - 2;  
                                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                        p = getPiece(x,y);
                                        if(p!=null){
                                            if(destination_x==x&&destination_y==y){
                                                return true;
                                            }

                                            if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                                return false;
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
       }else{
        if(candidate_Piece.PIECETYPE.equals("KING")){
            for(a=0;a<9;a++){
                if(Directions[a].equals("EAST")){
                    for(i=destination_x-1;i>=0;i--){
                        Piece p = getPiece(i, destination_y);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isBlack()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret= true;
                                break;
                            }
                        }else if(p!=null&&p.isWhite()){
                            if((i==destination_x-1)&&p.PIECETYPE.equals("KING")){
                                return false;
                            }
                            if(i==destination_x||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }

                        }else if(p!=null&&p.isWhite()&&(i>destination_x||i<destination_x)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }
                }
                
                if(Directions[a].equals("WEST")){
                    for(i=destination_x+1;i<=7;i++){
                        Piece p = getPiece(i, destination_y);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isBlack()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isWhite()){
                            if((i==destination_x+1)&&p.PIECETYPE.equals("KING")){
                                return false;
                            }
                            if(i==destination_x||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }

                        }else if(p!=null&&p.isWhite()&&(i<destination_x||i>destination_x)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }
                }
                if(Directions[a].equals("NORTH")){
                    for(i=destination_y+1;i<=7;i++){
                        Piece p = getPiece(destination_x, i);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isBlack()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isWhite()){
                            if((i==destination_y+1)&&p.PIECETYPE.equals("KING")){
                                return false;
                            }
                            if(i==destination_y||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }

                        }else if(p!=null&&p.isWhite()&&(i<destination_y||i>destination_y)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }                   
                }
                if(Directions[a].equals("SOUTH")){
                    for(i=destination_y-1;i>=0;i--){
                        Piece p = getPiece(destination_x, i);         
                        if(p==null){
                            continue;
                        }
                        if(p!=null&&p.isBlack()){
                            if(p.PIECETYPE.equals("KING")){
                                continue;
                            }else{
                                ret=true;
                                break;
                            }
                        }else if(p!=null&&p.isWhite()){
                            if((i==destination_y-1)&&p.PIECETYPE.equals("KING")){
                                return false;
                            }
                            if(i==destination_y||!(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                                ret=true;
                                break;
                            }else{
                                return false;
                            }

                        }else if(p!=null&&p.isWhite()&&(i<destination_y||i>destination_y)&&(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN"))){
                            return false;
                        }
                    }                   
                }
            if(Directions[a].equals("NORTHEAST")){
                for(x=destination_x-1,y=destination_y+1;x>=0&&y<=7;x--,y++){
                    Piece p = getPiece(x,y);         
                    if(p==null){
                        continue;
                    }
                    if(p!=null&&p.isBlack()){
                        if(p.PIECETYPE.equals("KING")){
                            continue;
                        }else{
                            ret=true;
                            break;
                        }
                    }else if(p!=null&&p.isWhite()){
                        if((x==destination_x-1&&y==destination_y+1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                            return false;
                        }
                        if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            ret=true;
                            break;
                        }else {
                            return false;
                        }

                    }else if(p!=null&&p.isWhite()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                        return false;
                    }
                }                
            }

            if(Directions[a].equals("NORTHWEST")){
                for(x=destination_x+1,y=destination_y+1;x<=7&&y<=7;x++,y++){
                    Piece p = getPiece(x,y);         
                    if(p==null){
                        continue;
                    }
                    if(p!=null&&p.isBlack()){
                        if(p.PIECETYPE.equals("KING")){
                            continue;
                        }else{
                            ret=true;
                            break;
                        }
                    }else if(p!=null&&p.isWhite()){
                        if((x==destination_x+1&&y==destination_y+1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                            return false;
                        }

                        if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            ret=true;
                            break;
                        }else{
                            return false;
                        }

                    }else if(p!=null&&p.isWhite()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                        return false;
                    }
                }                
            }

            if(Directions[a].equals("SOUTHEAST")){
                for(x=destination_x-1,y=destination_y-1;x>=0&&y>=0;x--,y--){
                    Piece p = getPiece(x,y);         
                    if(p==null){
                        continue;
                    }
                    if(p!=null&&p.isBlack()){
                        if(p.PIECETYPE.equals("KING")){
                            continue;
                        }else{
                            ret=true;
                            break;
                        }
                    }else if(p!=null&&p.isWhite()){
                        if((x==destination_x-1&&y==destination_y-1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                            return false;
                        }
                        if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            ret=true;
                            break;
                        }else{
                            return false;
                        }

                    }else if(p!=null&&p.isWhite()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(  p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                        return false;
                    }
                }                
            }

            if(Directions[a].equals("SOUTHWEST")){
                for(x=destination_x+1,y=destination_y-1;x<=7&&y>=0;x++,y--){
                    Piece p = getPiece(x,y);         
                    if(p==null){
                        continue;
                    }
                    if(p!=null&&p.isBlack()){
                        if(p.PIECETYPE.equals("KING")){
                            continue;
                        }else{
                            ret=true;
                            break;
                        }
                    }else if(p!=null&&p.isWhite()){
                        if((x==destination_x+1&&y==destination_y-1)&&(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("KING"))){
                            return false;
                        }
                        if((x==destination_x&&y==destination_y)||!(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                            ret=true;
                            break;
                        }else{
                            return false;
                        }

                    }else if(p!=null&&p.isWhite()&&((y<destination_y||y>destination_y)&&(x<destination_x||x>destination_x))&&(  p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN"))){
                        return false;
                    }
                }                
            }
            if(Directions[a].equals("KNIGHT")){
                    Piece p;
                    x = destination_x+2;
                    y = destination_y-1;

                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }

                    x = destination_x + 2; 
                    y = destination_y + 1;

                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }

                    x = destination_x + 1; 
                    y = destination_y + 2;          
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);   
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }

                    x = destination_x - 1; 
                    y = destination_y + 2;          
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);   
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }    
                    
                    x = destination_x - 2; 
                    y = destination_y + 1;       
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);  
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }

                    x = destination_x - 2; 
                    y = destination_y - 1;    
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y); 
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }

                    x = destination_x - 1; 
                    y = destination_y - 2;   
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);  
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }

                    x = destination_x + 1; 
                    y = destination_y - 2;  
                    if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                        p = getPiece(x,y);   
                        if(p!=null){
                            if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                return false;
                            }
                        }
                    }
                }
            } 
        }else{
            Piece current_piece = candidate_Piece;
            for(Piece K:Black_Pieces){
                if(K.PIECETYPE.equals("KING")){
                    x=K.getX();
                    y=K.getY();

                    for(a=0;a<9;a++){
                        if(Directions[a].equals("EAST")){
                            for(i=x-1;i>=0;i--){
                                Piece p = getPiece(i, y);     
                                if(p==null){
                                    if(i==destination_x&&y==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==i&&destination_y==y){
                                            return true;
                                        }
                                        if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                              
                                }
                            }
                        }
                        if(Directions[a].equals("WEST")){
                            for(i=x+1;i<=7;i++){
                                Piece p = getPiece(i, y);     
                                if(p==null){
                                    if(i==destination_x&&y==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==i&&destination_y==y){
                                            return true;
                                        }
                                        if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                    
                                }
                            }
                        }
                        if(Directions[a].equals("NORTH")){                    
                            for(i=y+1;i<=7;i++){
                                Piece p = getPiece(x, i);         
                                if(p==null){
                                    if(x==destination_x&&i==destination_y){
                                        break;  
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==x&&destination_y==i){
                                            return true;
                                        }
                                        if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                  
                                }
                            }
                        }

                        if(Directions[a].equals("SOUTH")){
                            for(i=y-1;i>=0;i--){
                                Piece p = getPiece(x, i);     
                                if(p==null){
                                    if(x==destination_x&&i==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==x&&destination_y==i){
                                            return true;
                                        }
                                        if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                     
                                }
                            }
                        }
                        if(Directions[a].equals("NORTHEAST")){
                            for(i=x-1,j=y+1;i>=0&&j<=7;i--,j++){
                                Piece p = getPiece(i, j);     
                                if(p==null){
                                    if(i==destination_x&&j==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==i&&destination_y==j){
                                            return true;
                                        }

                                        if((i==x-1&&j==y+1)&&p.PIECETYPE.equals("PAWN")){
                                            return false;
                                        }

                                        if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                     
                                }
                            }
                        }
                        if(Directions[a].equals("NORTHWEST")){
                            for(i=x+1,j=y+1;i<=7&&j<=7;i++,j++){
                                Piece p = getPiece(i, j);     
                                if(p==null){
                                    if(i==destination_x&&j==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==i&&destination_y==j){
                                            return true;
                                        }

                                        if((i==x+1&&j==y+1)&&p.PIECETYPE.equals("PAWN")){
                                            return false;
                                        }

                                        if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                      
                                }
                            }
                        }
                        if(Directions[a].equals("SOUTHEAST")){
                            for(i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
                                Piece p = getPiece(i, j);     
                                if(p==null){
                                    if(i==destination_x&&j==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==i&&destination_y==j){
                                            return true;
                                        }
                                        if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                   
                                }
                            }
                        }
                        if(Directions[a].equals("SOUTHWEST")){
                            for(i=x+1,j=y-1;i<=7&&j>=0;i++,j--){
                                Piece p = getPiece(i, j);     
                                if(p==null){
                                    if(i==destination_x&&j==destination_y){
                                        break;
                                    }
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        if(p.getX()==current_piece.getX()&&p.getY()==current_piece.getY()){
                                            continue;
                                        }

                                        if(p.getX()!=current_piece.getX()&&p.getY()!=current_piece.getY()){
                                            break;
                                        }
                                        break;
                                    }else{
                                        if(destination_x==i&&destination_y==j){
                                            return true;
                                        }
                                        if(p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                            return false;
                                        }
                                    }                                   
                                }
                            }
                        }
                        if(Directions[a].equals("KNIGHT")){
                                Piece p;
                                x = K.getX()+2;
                                y = K.getY()-1;

                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
            
                                x = K.getX() + 2; 
                                y = K.getY() + 1;
            
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
            
                                x = K.getX() + 1; 
                                y = K.getY() + 2;          
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
            
                                x = K.getX() - 1; 
                                y = K.getY() + 2;          
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }   
                                
                                x = K.getX() - 2; 
                                y = K.getY() + 1;       
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
            
                                x = K.getX() - 2; 
                                y = K.getY() - 1;    
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
            
                                x = K.getX() - 1; 
                                y = K.getY() - 2;   
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
            
                                x = K.getX() + 1; 
                                y = K.getY() - 2;  
                                if((x<=7&&x>=0)&&(y<=7&&y>=0)){
                                    p = getPiece(x,y);
                                    if(p!=null){
                                        if(destination_x==x&&destination_y==y){
                                            return true;
                                        }

                                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                            return false;
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
    return true;
}

    public boolean canCastle(int destination_x, int destination_y){
        int a,x,y,i;

        boolean ret=true;
        if(candidate_Piece.isWhite()){
            for(a=0;a<9;a++){
                if(Directions[a].equals("EAST")){
                    for(x=candidate_Piece.getX()+1;x<=7;x++){
                        Piece p = getPiece(x,destination_y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null&&p.isWhite()){
                            break;
                        }
    
                        if(p!=null&&p.isBlack()){
                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                return false;
                            }
                        }
                    }                 
                }
                if(Directions[a].equals("WEST")){
                    for(x=candidate_Piece.getX()-1;x>=0;x--){
                        Piece p = getPiece(x,destination_y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null&&p.isWhite()){
                            break;
                        }
    
                        if(p!=null&&p.isBlack()){
                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                return false;
                            }
                        }
                    }
                }
    
                if(Directions[a].equals("NORTH")){
                    for(y=candidate_Piece.getY()-1;y>=0;y--){
                        Piece p = getPiece(candidate_Piece.getX(),y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null){
                            if(p.isWhite()){
                                break;
                            }
    
                            if(p.isBlack()){
                                if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                    return false;
                                }
                            }
                        } 
                    }
                }

                if(Directions[a].equals("NORTHEAST")){
                    for(x=candidate_Piece.getX()+1, y=candidate_Piece.getY()-1;x<=7&&y>=0;x++,y--){
                        Piece p = getPiece(x,y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null){
                            if(p.isWhite()){
                                break;
                            }
    
                            if(p.isBlack()){
                                if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                    return false;
                                }
                            }
                        }
                    }
                }

                if(Directions[a].equals("NORTHWEST")){
                    for(x=candidate_Piece.getX()-1, y=candidate_Piece.getY()-1;x>=0&&y>=0;x--,y--){
                        Piece p = getPiece(x,y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null){
                            if(p.isWhite()){
                                break;
                            }
    
                            if(p.isBlack()){
                                if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                    return false;
                                }
                            }
                        }
                    }
                }
                
                if(Directions[a].equals("KNIGHT")){
                    x = candidate_Piece.getX() + 2;
                    y = candidate_Piece.getY() -1;
                    Piece p = getPiece(x, y);

                    if(p!=null){
                        if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }

                    x = candidate_Piece.getX() - 2;
                    y = candidate_Piece.getY() -1;

                    p = getPiece(x, y);

                    if(p!=null){
                        if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }

                    x = candidate_Piece.getX() + 1;
                    y = candidate_Piece.getY() -2;

                    p = getPiece(x, y);

                    if(p!=null){
                        if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }

                    x = candidate_Piece.getX() - 1;
                    y = candidate_Piece.getY() -2;

                    p = getPiece(x, y);

                    if(p!=null){
                        if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }
                }
            }
        }else{
            for(a=0;a<9;a++){
                if(Directions[a].equals("EAST")){
                    for(x=candidate_Piece.getX()-1;x>=0;x--){
                        Piece p = getPiece(x,destination_y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null&&p.isBlack()){
                            break;
                        }
    
                        if(p!=null&&p.isWhite()){
                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                return false;
                            }
                        }
                    }                 
                }
        
                if(Directions[a].equals("WEST")){
                    for(x=candidate_Piece.getX()+1;x<=7;x++){
                        Piece p = getPiece(x,destination_y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null&&p.isBlack()){
                            break;
                        }
    
                        if(p!=null&&p.isWhite()){
                            if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                return false;
                            }
                        }
                    }
                }
    
                if(Directions[a].equals("NORTH")){
                    for(y=candidate_Piece.getY()+1;y<=7;y++){
                        Piece p = getPiece(candidate_Piece.getX(),y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null){
                            if(p.isBlack()){
                                break;
                            }
    
                            if(p.isWhite()){
                                if(p.PIECETYPE.equals("ROOK")||p.PIECETYPE.equals("QUEEN")){
                                    return false;
                                }
                            }
                        } 
                    }
                }

                if(Directions[a].equals("NORTHEAST")){
                    for(x=candidate_Piece.getX()-1, y=candidate_Piece.getY()+1;x>=0&&y<=7;x--,y++){
                        Piece p = getPiece(x,y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null){
                            if(p.isBlack()){
                                break;
                            }
    
                            if(p.isWhite()){
                                if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                    return false;
                                }
                            }
                        }
                    }
                }

                if(Directions[a].equals("NORTHWEST")){
                    for(x=candidate_Piece.getX()+1, y=candidate_Piece.getY()+1;x<=7&&y<=7;x++,y++){
                        Piece p = getPiece(x,y);
    
                        if(p==null){
                            continue;
                        }
    
                        if(p!=null){
                            if(p.isBlack()){
                                break;
                            }
    
                            if(p.isWhite()){
                                if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||p.PIECETYPE.equals("QUEEN")){
                                    return false;
                                }
                            }
                        }
                    }
                }
                
                if(Directions[a].equals("KNIGHT")){
                    x = candidate_Piece.getX() + 2;
                    y = candidate_Piece.getY() +1;
                    Piece p = getPiece(x, y);

                    if(p!=null){
                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }

                    x = candidate_Piece.getX() - 2;
                    y = candidate_Piece.getY() +1;

                    p = getPiece(x, y);

                    if(p!=null){
                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }

                    x = candidate_Piece.getX() + 1;
                    y = candidate_Piece.getY() +2;

                    p = getPiece(x, y);

                    if(p!=null){
                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }

                    x = candidate_Piece.getX() - 1;
                    y = candidate_Piece.getY() +2;

                    p = getPiece(x, y);

                    if(p!=null){
                        if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                            return false;
                        }
                    }
                }
            }
        }

        if(destination_x==6){
            if(candidate_Piece.isWhite()){
                for(i=5;i<7;i++){
                    for(a=0;a<9;a++){
                        if(Directions[a].equals("NORTH")){
                            for(y=destination_y-1;y>=0;y--){
                                Piece p = getPiece(i, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isWhite()){
                                        break;
                                    }

                                    if(p.isBlack()){
                                        if(p.PIECETYPE.equals("ROOK")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHEAST")){
                            for(x=i+1,y=destination_y-1;x<=7&&y>=0;x++,y--){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isWhite()){
                                        break;
                                    }

                                    if(p.isBlack()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHWEST")){
                            for(x=i-1,y=destination_y-1;x>=0&&y>=0;x--,y--){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isWhite()){
                                        break;
                                    }

                                    if(p.isBlack()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("KNIGHT")){
                            Piece p;
                            x = i + 2;
                            y = destination_y - 1;

                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 2;
                            y = destination_y - 1;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i + 1;
                            y = destination_y - 2;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 1;
                            y = destination_y - 2;
                            
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }                          
                        }
                    }
                }
                
            }else{
                for(i=5;i<7;i++){
                    for(a=0;a<9;a++){
                        if(Directions[a].equals("NORTH")){
                            for(y=destination_y+1;y<=7;y++){
                                Piece p = getPiece(i, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        break;
                                    }

                                    if(p.isWhite()){
                                        if(p.PIECETYPE.equals("ROOK")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHEAST")){
                            for(x=i-1,y=destination_y+1;x>=0&&y<=7;x--,y++){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        break;
                                    }

                                    if(p.isWhite()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHWEST")){
                            for(x=i+1,y=destination_y+1;x<=7&&y<=7;x++,y++){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        break;
                                    }

                                    if(p.isWhite()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("KNIGHT")){
                            Piece p;
                            x = i + 2;
                            y = destination_y + 1;

                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 2;
                            y = destination_y + 1;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i + 1;
                            y = destination_y + 2;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 1;
                            y = destination_y + 2;
                            
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }                          
                        }
                    }
                }
            }
        }else if(destination_x==2){
            if(candidate_Piece.isWhite()){
                for(i=3;i>=2;i--){
                    for(a=0;a<9;a++){
                        if(Directions[a].equals("NORTH")){
                            for(y=destination_y-1;y>=0;y--){
                                Piece p = getPiece(i, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isWhite()){
                                        break;
                                    }

                                    if(p.isBlack()){
                                        if(p.PIECETYPE.equals("ROOK")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHEAST")){
                            for(x=i+1,y=destination_y-1;x<=7&&y>=0;x++,y--){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isWhite()){
                                        break;
                                    }

                                    if(p.isBlack()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHWEST")){
                            for(x=i-1,y=destination_y-1;x>=0&&y>=0;x--,y--){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isWhite()){
                                        break;
                                    }

                                    if(p.isBlack()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("KNIGHT")){
                            Piece p;
                            x = i + 2;
                            y = destination_y - 1;

                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 2;
                            y = destination_y - 1;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i + 1;
                            y = destination_y - 2;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 1;
                            y = destination_y - 2;
                            
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isBlack()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }                          
                        }
                    }
                }

            }else{
                for(i=3;i>=2;i--){
                    for(a=0;a<9;a++){
                        if(Directions[a].equals("NORTH")){
                            for(y=destination_y+1;y<=7;y++){
                                Piece p = getPiece(i, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        break;
                                    }

                                    if(p.isWhite()){
                                        if(p.PIECETYPE.equals("ROOK")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHEAST")){
                            for(x=i-1,y=destination_y+1;x>=0&&y<=7;x--,y++){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        break;
                                    }

                                    if(p.isWhite()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("NORTHWEST")){
                            for(x=i+1,y=destination_y+1;x<=7&&y<=7;x++,y++){
                                Piece p = getPiece(x, y);

                                if(p==null){
                                    continue;
                                }

                                if(p!=null){
                                    if(p.isBlack()){
                                        break;
                                    }

                                    if(p.isWhite()){
                                        if(p.PIECETYPE.equals("PAWN")||p.PIECETYPE.equals("BISHOP")||(p.PIECETYPE.equals("QUEEN"))){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        if(Directions[a].equals("KNIGHT")){
                            Piece p;
                            x = i + 2;
                            y = destination_y + 1;

                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 2;
                            y = destination_y + 1;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i + 1;
                            y = destination_y + 2;
        
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }
        
                            x = i - 1;
                            y = destination_y + 2;
                            
                            if((x<=7||x>=0)&&(y<=7||y>=7)){
                                p = getPiece(x, y);
                            }else{
                                p = null;
                            }
        
                            if(p!=null){
                                if(p.isWhite()&&p.PIECETYPE.equals("KNIGHT")){
                                    return false;
                                }
                            }                          
                        }
                    }
                }
            }
        }
        return ret;
    }

    private void adjustShapePositions(double dx, double dy) {

        Static_Shapes.get(0).adjustPosition(dx, dy);
        this.repaint();

    } 
      
    private Image loadImage(String imageFile) {
        try {
                return ImageIO.read(new File(imageFile));
        }
        catch (IOException e) {
                return NULL_IMAGE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }
       

    private void drawShapes(Graphics2D g2) {
        for (DrawingShape shape : Static_Shapes) {
            shape.draw(g2);
        }	
        for (DrawingShape shape : Piece_Graphics) {
            shape.draw(g2);
        }
    }

    private ComponentAdapter componentAdapter = new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentResized(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }	
    };

    private KeyAdapter keyAdapter = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }	
    };
}

interface DrawingShape {
    boolean contains(Graphics2D g2, double x, double y);
    void adjustPosition(double dx, double dy);
    void draw(Graphics2D g2);
}


class DrawingImage implements DrawingShape {

    public Image image;
    public Rectangle2D rect;

    public DrawingImage(Image image, Rectangle2D rect) {
            this.image = image;
            this.rect = rect;
    }

    @Override
    public boolean contains(Graphics2D g2, double x, double y) {
            return rect.contains(x, y);
    }

    @Override
    public void adjustPosition(double dx, double dy) {
            rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());	
    }

    @Override
    public void draw(Graphics2D g2) {
            Rectangle2D bounds = rect.getBounds2D();
            g2.drawImage(image, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),0, 0, image.getWidth(null), image.getHeight(null), null);
    }	
}
