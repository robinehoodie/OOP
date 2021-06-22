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
    

    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    public Piece Active_Piece;
    
    private final int rows = 8;
    private final int cols = 8;
    private Integer[][] BoardGrid;
    private String board_file_path = "images" + File.separator + "board.png";
    private String active_square_file_path = "images" + File.separator + "active_square.png";

    public void initGrid(Piece P,int row, int col){
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
                        White_Pieces.add(new Queen(col,row,true,"Queen.png",this,"QUEEN",false)); 
                }else{
                    Black_Pieces.add(new Queen(col,row,false,"Queen.png",this,"QUEEN",false));           
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

        this.setBackground(new Color(37,13,84));
        this.setPreferredSize(new Dimension(520, 520));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
        this.addKeyListener(keyAdapter);
   
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

///////////////////////////////////////IMPORTANT/////////////////////////////////////////////////////////////////////////////////////////////////////////    
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

    ////////////////////////////////////////////////////////////////////
    public boolean isValid(Piece P){
        

        return false;
    }

    /////////////////////////////////////////////////////////////////////
    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {               
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX();
            int d_Y = e.getY();  
        
            int Clicked_Row = d_Y / Square_Width;//65
        
            int Clicked_Column = d_X / Square_Width;//65
        
            boolean is_whites_turn = true;
            if (turnCounter%2 == 1){
                is_whites_turn = false;
            }
            
            Piece clicked_piece = getPiece(Clicked_Column, Clicked_Row);

            if (Active_Piece == null && clicked_piece != null && ((is_whites_turn && clicked_piece.isWhite()) || (!is_whites_turn && clicked_piece.isBlack()))){
                Active_Piece = clicked_piece;
            }else if (Active_Piece != null && Active_Piece.getX() == Clicked_Column && Active_Piece.getY() == Clicked_Row){
                Active_Piece = null;
            }else if (Active_Piece != null && Active_Piece.canMove(Clicked_Column, Clicked_Row) && ((is_whites_turn && Active_Piece.isWhite()) || (!is_whites_turn && Active_Piece.isBlack()))){
                // if piece is there, remove it so we can be there
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

                Active_Piece = null;
                turnCounter++;
            }
            drawBoard();
        }

///////////////////////////////////////IMPORTANT/////////////////////////////////////////////////////////////////////////////////////////////////////////   
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
