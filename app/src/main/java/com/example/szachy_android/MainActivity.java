package com.example.szachy_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rits.cloning.Cloner;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Context context;
    private int WavailibeRow=0;
    private int WavailibeCol=0;
    private int BavailibeRow=0;
    private int BavailibeCol=0;
    public static boolean turn=true;
    public static Piece LastTaken;
    private boolean isPieceSelected=false;
    private boolean isMoveSelected=false;
    public static boolean wasLegal=false;
    private boolean playable = true;
    private Piece[][] board = new Piece[8][8];
    private Piece piece = board[0][0];
    private ImageButton[][] whiteTakenBoard= new ImageButton[2][8];
    private ImageButton[][] blackTakenBoard= new ImageButton[2][8];
    private ImageButton[][] buttons = new ImageButton[8][8];
    boolean isKingInCheck = false;
    private ArrayList<String> tilesToColor = new ArrayList<String>();
    private ArrayList<String> legalMoves = new ArrayList<String>();
    public TextView txt;
    public static void add_pieces(Piece board[][]){
        //white pieces
        board[7][7] = new Piece('R',true,7,7,false);
        board[7][0] = new Piece('R',true,7,0,false);
        board[7][3] = new Piece('Q',true,7,3,false);
        board[7][4] = new Piece('K',true,7,4,false);
        board[7][1] = new Piece('N',true,7,1,false);
        board[7][6] = new Piece('N',true,7,6,false);
        board[7][2] = new Piece('B',true,7,2,false);
        board[7][5] = new Piece('B',true,7,5,false);
        //black pieces
        board[0][0] = new Piece('R',false,0,0,false);
        board[0][7] = new Piece('R',false,0,7,false);
        board[0][3] = new Piece('Q',false,0,3,false);
        board[0][4] = new Piece('K',false,0,4,false);
        board[0][1] = new Piece('N',false,0,1,false);
        board[0][6] = new Piece('N',false,0,6,false);
        board[0][2] = new Piece('B',false,0,2,false);
        board[0][5] = new Piece('B',false,0,5,false);

        for(int i = 0; i< board.length; i++){
            //black pawns
            board[1][i] = new Piece('P',false,1,i,false);
            //white pawns
            board[6][i] = new Piece('P',true,6,i,false);
        }
    }
    public void calculate_legal_moves(Piece piece, Piece[][] board){
        if(piece.getColor()==turn){
            if(piece.getType()=='P' && piece.getColor()==true){
                //takes left
                if(piece.getPosY()-1 >= 0){
                    if(board[0][0].check_legal(piece,board,piece.getPosY()-1,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //takes right
                if(piece.getPosY()+1 < 8){
                    if(board[0][0].check_legal(piece,board, piece.getPosY()+1,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //goes forward 2 squares
                if(!piece.isDidMove()){
                    int x = piece.getPosY();
                    int y = piece.getPosX()-1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        y = piece.getPosX()-2;
                        if(board[0][0].check_legal(piece, board, x,y, turn)){
                            tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        }
                    }
                }
                //goes forward 1 square
                else{
                    int x = piece.getPosY();
                    int y = piece.getPosX()-1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
            }
            else if(piece.getType()=='P' && piece.getColor()==false){
                //if takes left
                if(piece.getPosY()-1 > 0 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-1,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //takes right
                if(piece.getPosY()+1 < 8 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+1,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                if(!piece.isDidMove()){
                    int x = piece.getPosY();
                    int y = piece.getPosX()+1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        y = piece.getPosX()+2;
                        if(board[0][0].check_legal(piece, board, x,y, turn)){
                            tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        }
                    }
                }
                else{
                    int x = piece.getPosY();
                    int y = piece.getPosX()+1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
            }
            if(piece.getType()=='N'){
                //right up
                if(piece.getPosY()+1 < 8 && piece.getPosX()-2 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+1,piece.getPosX()-2, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()-2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left up
                if(piece.getPosY()-1 >= 0 && piece.getPosX()-2 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-1,piece.getPosX()-2, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()-2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //right sideways up
                if(piece.getPosY()+2 < 8 && piece.getPosX()-1 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+2,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()+2;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left sideways up
                if(piece.getPosY()-2 >= 0 && piece.getPosX()-1 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-2,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()-2;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //right down
                if(piece.getPosY()+1 < 8 && piece.getPosX()+2 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+1,piece.getPosX()+2, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()+2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left down
                if(piece.getPosY()-1 >= 0 && piece.getPosX()+2 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-1,piece.getPosX()+2, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()+2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //right sideways down
                if(piece.getPosY()+2 < 8 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+2,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()+2;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left sideways down
                if(piece.getPosY()-2 >= 0 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-2,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()-2;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
            }
            if(piece.getType()=='B'){
                int i = 1;
                int j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()-j;
                    int pies = piece.getPosX()-i;
                    if(pies >= 0 && kot >= 0){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()-i;
                    if((pies >= 0) && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()+i;
                    if(pies < 8 && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    //X
                    int kot = piece.getPosY()-j;
                    //Y
                    int pies = piece.getPosX()+i;
                    if((pies < 8) && (kot >= 0)){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
            }
            if(piece.getType()=='R'){
                int i,j;
                i=1;
                //right
                while(i!=8){
                    int pies = piece.getPosY()+i;
                    if(pies < 8){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                i=1;
                //left
                while(i!=8){
                    int pies = piece.getPosY()-i;
                    if(pies >= 0){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                j=1;
                //up
                while(j!=8){
                    int kot = piece.getPosX()-j;
                    if(kot >= 0){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
                j=1;
                //down
                while(j!=8){
                    int kot = piece.getPosX()+j;
                    if(kot < 8){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
            }
            if(piece.getType()=='Q'){
                int i = 1;
                int j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()-j;
                    int pies = piece.getPosX()-i;
                    if(pies >= 0 && kot >= 0){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()-i;
                    if((pies >= 0) && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()+i;
                    if(pies < 8 && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    //X
                    int kot = piece.getPosY()-j;
                    //Y
                    int pies = piece.getPosX()+i;
                    if((pies < 8) && (kot >= 0)){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i=1;
                //right
                while(i!=8){
                    int pies = piece.getPosY()+i;
                    if(pies < 8){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                i=1;
                //left
                while(i!=8){
                    int pies = piece.getPosY()-i;
                    if(pies >= 0){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                j=1;
                //up
                while(j!=8){
                    int kot = piece.getPosX()-j;
                    if(kot >= 0){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
                j=1;
                //down
                while(j!=8){
                    int kot = piece.getPosX()+j;
                    if(kot < 8){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
            }
            if(piece.getType()=='K'){
                int pies = piece.getPosY()+1;
                int kot = piece.getPosX()+1;
                if(pies < 8 && kot < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()+1;
                kot = piece.getPosX()-1;
                if(pies < 8 && kot >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-1;
                kot = piece.getPosX()-1;
                if(pies >= 0 && kot >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-1;
                kot = piece.getPosX()+1;
                if(pies >= 0 && kot < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-1;
                kot = piece.getPosX();
                if(pies >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()+1;
                kot = piece.getPosX();
                if(pies < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()+2;
                kot = piece.getPosX();
                if(pies < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-2;
                kot = piece.getPosX();
                if(pies >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY();
                kot = piece.getPosX()-1;
                if(kot >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY();
                kot = piece.getPosX()+1;
                if(kot < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
            }
        }
    }
    public static void fill_board(Piece board[][]){
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                board[i][j] = new Piece('l',false,i,j,true);
            }
        }
    }
    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        GridLayout gridLayout = findViewById(R.id.chessboard);
        gridLayout.setColumnCount(8);
        gridLayout.setRowCount(8);
        gridLayout.removeAllViews();
        txt = (TextView) findViewById(R.id.textView);
        fill_board(board);
        add_pieces(board);
        GridLayout gridBlack = findViewById(R.id.BlacksTaken);
        GridLayout gridWhite = findViewById(R.id.WhitesTaken);
        gridBlack.removeAllViews();
        gridWhite.removeAllViews();
        for(int i=0; i < 8; i++){
            for(int j=0; j < 8; j++){
                ImageButton imageButton = new ImageButton(this,i,j);
                Piece piece = board[j][i];
                if(piece.getType()=='P' && piece.getColor()){
                    imageButton.setImageResource(R.drawable.wpawn);
                }
                else if(piece.getType()=='N' && piece.getColor()){
                    imageButton.setImageResource(R.drawable.wknight);
                }
                else if(piece.getType()=='B' && piece.getColor()){
                    imageButton.setImageResource(R.drawable.wbishop);
                }
                else if(piece.getType()=='K' && piece.getColor()){
                    imageButton.setImageResource(R.drawable.wking);
                }
                else if(piece.getType()=='Q' && piece.getColor()){
                    imageButton.setImageResource(R.drawable.wqueen);
                }
                else if(piece.getType()=='R' && piece.getColor()){
                    imageButton.setImageResource(R.drawable.wrook);
                }

                if(piece.getType()=='P' && !piece.getColor()){
                    imageButton.setImageResource(R.drawable.bpawn);
                }
                else if(piece.getType()=='N' && !piece.getColor()){
                    imageButton.setImageResource(R.drawable.bknight);
                }
                else if(piece.getType()=='B' && !piece.getColor()){
                    imageButton.setImageResource(R.drawable.bbishop);
                }
                else if(piece.getType()=='K' && !piece.getColor()){
                    imageButton.setImageResource(R.drawable.bking);
                }
                else if(piece.getType()=='Q' && !piece.getColor()){
                    imageButton.setImageResource(R.drawable.bqueen);
                }
                else if(piece.getType()=='R' && !piece.getColor()){
                    imageButton.setImageResource(R.drawable.brook);
                }
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(i);
                param.rowSpec = GridLayout.spec(j);
                param.width = 130;
                param.height = 130;
                imageButton.setLayoutParams (param);
                buttons[j][i] = imageButton;
                if(i%2==0 && j%2==0 || i%2!=0 && j%2!=0){
                    imageButton.setBackgroundColor(R.color.white);
                }
                else{
                    imageButton.setBackgroundColor(R.color.green);
                }
                gridLayout.addView(imageButton);
            }
        }
        for(int i=0; i < 2; i++){
            for(int j=0; j < 8; j++){
                ImageButton img = new ImageButton(this);
                img.setImageResource(android.R.color.transparent);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(j);
                param.rowSpec = GridLayout.spec(i);
                param.width = 110;
                param.height = 90;
                img.setLayoutParams(param);
                gridBlack.addView(img);
                ImageButton img2 = new ImageButton(this);
                img2.setImageResource(android.R.color.transparent);
                GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
                param2.setGravity(Gravity.CENTER);
                param2.columnSpec = GridLayout.spec(j);
                param2.rowSpec = GridLayout.spec(i);
                param2.width = 110;
                param2.height = 90;
                img2.setLayoutParams(param2);
                blackTakenBoard[i][j] = img;
                gridWhite.addView(img2);
                whiteTakenBoard[i][j] = img2;
            }
        }
    }
    class ImageButton extends androidx.appcompat.widget.AppCompatImageView {
        private int xPos,yPos;
        public ImageButton(Context context, int xPos, int yPos) {
            super(context);
            this.xPos = xPos;
            this.yPos = yPos;
            ImageButton imageButton = this;
            setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    if (!isPieceSelected && playable && board[imageButton.yPos][imageButton.xPos].getType() != 'l' && board[imageButton.yPos][imageButton.xPos].getColor() == turn) {
                        if(legalMoves.size()!=0){
                            legalMoves.clear();
                        }
                        for(int i=0; i < board.length; i++){
                            for(int j=0; j < board.length; j++){
                                Piece piece2 = board[i][j];
                                calculate_legal_moves(piece2, board);
                            }
                        }
                        Cloner cloner = new Cloner();
                        legalMoves = cloner.deepClone(tilesToColor);
                        tilesToColor.clear();
                        int XofPiece = imageButton.xPos;
                        int YofPiece = imageButton.yPos;
                        piece = board[YofPiece][XofPiece];
                        isPieceSelected = true;
                        calculate_legal_moves(piece, board);
                        if(turn) {
                            isKingInCheck = piece.isKingAttacked(board[0][0].findWhiteKing(board), board);
                            String message = "";
                            if (isKingInCheck && legalMoves.size() == 0) {
                                message = "The game has ended! It's checkmate, black wins!";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                playable = false;
                            } else if (!isKingInCheck && legalMoves.size() == 0) {
                                message = "The game has ended! It's stalemate, it's a draw!";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                playable = false;
                            } else if (isKingInCheck) {
                                message = "The white king is in check! Get him out of danger";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            String message = "";
                            isKingInCheck = piece.isKingAttacked(board[0][0].findBlackKing(board),board);
                            if(isKingInCheck && legalMoves.size()==0){
                                message = "The game has ended! It's checkmate, white wins!";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                playable = false;
                            }
                            else if(!isKingInCheck && legalMoves.size()==0){
                                message = "The game has ended! It's stalemate, it's a draw!";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                playable = false;
                            }
                            else if(isKingInCheck){
                                message = "The black king is in check! Get him out of danger";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else if (playable && isPieceSelected && !isMoveSelected) {
                        wasLegal = false;
                        int XofTarget = imageButton.xPos;
                        int YofTarget = imageButton.yPos;
                        LastTaken = board[YofTarget][XofTarget];
                        Piece.move_piece(piece, XofTarget, YofTarget, turn, board, turn);
                        isPieceSelected = false;
                        isMoveSelected = false;
                        if(LastTaken.getType()!='l' && wasLegal){
                            if(LastTaken.getColor() && piece.getColor() != LastTaken.getColor()){
                                if(BavailibeCol == 7){
                                    BavailibeCol=0;
                                    BavailibeRow=1;
                                }
                                else{
                                    BavailibeCol++;
                                }
                                ImageButton imageButton2 = blackTakenBoard[BavailibeRow][BavailibeCol];
                                if(LastTaken.getType()=='P'){
                                    imageButton2.setImageResource(R.drawable.wpawn);
                                }
                                else if(LastTaken.getType()=='N'){
                                    imageButton2.setImageResource(R.drawable.wknight);
                                }
                                else if(LastTaken.getType()=='B' && LastTaken.getColor()){
                                    imageButton2.setImageResource(R.drawable.wbishop);
                                }
                                else if(LastTaken.getType()=='K'){
                                    imageButton2.setImageResource(R.drawable.wking);
                                }
                                else if(LastTaken.getType()=='Q'){
                                    imageButton2.setImageResource(R.drawable.wqueen);
                                }
                                else if(LastTaken.getType()=='R'){
                                    imageButton2.setImageResource(R.drawable.wrook);
                                }
                                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                                param.setGravity(Gravity.CENTER);
                                param.columnSpec = GridLayout.spec(BavailibeCol);
                                param.rowSpec = GridLayout.spec(BavailibeRow);
                                param.width = 110;
                                param.height = 90;
                                imageButton2.setLayoutParams(param);
                                blackTakenBoard[BavailibeRow][BavailibeCol] = imageButton2;
                            }
                            else if(!LastTaken.getColor() && piece.getColor() != LastTaken.getColor()){
                                if(WavailibeCol == 7){
                                    WavailibeCol=0;
                                    WavailibeRow=1;
                                }
                                else{
                                    WavailibeCol++;
                                }
                                ImageButton imageButton2 = whiteTakenBoard[WavailibeRow][WavailibeCol];
                                if(LastTaken.getType()=='P'){
                                    imageButton2.setImageResource(R.drawable.bpawn);
                                }
                                else if(LastTaken.getType()=='N'){
                                    imageButton2.setImageResource(R.drawable.bknight);
                                }
                                else if(LastTaken.getType()=='B' && LastTaken.getColor()){
                                    imageButton2.setImageResource(R.drawable.bbishop);
                                }
                                else if(LastTaken.getType()=='K'){
                                    imageButton2.setImageResource(R.drawable.bking);
                                }
                                else if(LastTaken.getType()=='Q'){
                                    imageButton2.setImageResource(R.drawable.bqueen);
                                }
                                else if(LastTaken.getType()=='R'){
                                    imageButton2.setImageResource(R.drawable.brook);
                                }
                                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                                param.columnSpec = GridLayout.spec(WavailibeCol);
                                param.rowSpec = GridLayout.spec(WavailibeRow);
                                param.setGravity(Gravity.CENTER);
                                param.width = 110;
                                param.height = 90;
                                imageButton2.setLayoutParams(param);
                                whiteTakenBoard[WavailibeRow][WavailibeCol] = imageButton2;

                            }
                        }
                        piece = null;
                        for(int i=0; i < 8; i++){
                            for(int j=0; j < 8; j++){
                                Piece piece = board[j][i];
                                ImageButton imageButton = buttons[j][i];
                                if(piece.getType()=='P' && piece.getColor()){
                                    imageButton.setImageResource(R.drawable.wpawn);
                                }
                                else if(piece.getType()=='N' && piece.getColor()){
                                    imageButton.setImageResource(R.drawable.wknight);
                                }
                                else if(piece.getType()=='B' && piece.getColor()){
                                    imageButton.setImageResource(R.drawable.wbishop);
                                }
                                else if(piece.getType()=='K' && piece.getColor()){
                                    imageButton.setImageResource(R.drawable.wking);
                                }
                                else if(piece.getType()=='Q' && piece.getColor()){
                                    imageButton.setImageResource(R.drawable.wqueen);
                                }
                                else if(piece.getType()=='R' && piece.getColor()){
                                    imageButton.setImageResource(R.drawable.wrook);
                                }
                                else if(piece.getType()=='P' && !piece.getColor()){
                                    imageButton.setImageResource(R.drawable.bpawn);
                                }
                                else if(piece.getType()=='N' && !piece.getColor()){
                                    imageButton.setImageResource(R.drawable.bknight);
                                }
                                else if(piece.getType()=='B' && !piece.getColor()){
                                    imageButton.setImageResource(R.drawable.bbishop);
                                }
                                else if(piece.getType()=='K' && !piece.getColor()){
                                    imageButton.setImageResource(R.drawable.bking);
                                }
                                else if(piece.getType()=='Q' && !piece.getColor()){
                                    imageButton.setImageResource(R.drawable.bqueen);
                                }
                                else if(piece.getType()=='R' && !piece.getColor()){
                                    imageButton.setImageResource(R.drawable.brook);
                                }
                                else{
                                    imageButton.setImageResource(android.R.color.transparent);
                                }
                                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                                param.setGravity(Gravity.CENTER);
                                param.columnSpec = GridLayout.spec(i);
                                param.rowSpec = GridLayout.spec(j);
                                param.width = 130;
                                param.height = 130;
                                imageButton.setLayoutParams (param);
                                buttons[j][i] = imageButton;
                                if(i%2==0 && j%2==0 || i%2!=0 && j%2!=0){
                                    imageButton.setBackgroundColor(R.color.white);
                                }
                                else{
                                    imageButton.setBackgroundColor(R.color.green);
                                }
                            }
                        }
                        String msg;
                        if(turn)
                            msg = "Whites turn!";
                        else
                            msg = "Blacks turn!";
                        txt.setText(msg);
                    }
                }
            });
        }
        public ImageButton(Context context) {
            super(context);

        }
            public void setPosXinGrid(int x){
            this.xPos = x;
        }
        public void setPosYinGrid(int y){
            this.yPos = y;
        }
        public int getPosXOnGrid(){
            return this.xPos;
        }
        public int getPosYOnGrid(){
            return this.yPos;
        }
    }
}