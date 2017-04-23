package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import program.GamePlay;


@SuppressWarnings( "serial" )
public class TicTacToe extends JPanel {

    public final static int  WIDTH   = 200;
    public final static int  HEIGHT   = 300;
    public final static int  PAWN_W   = 20;
    public final static int  PAWN_H   = 20;
    public final static int  XPadding = ( WIDTH - 3 * PAWN_W ) / 2;
    public final static int  YPadding = ( HEIGHT - 3 * PAWN_H ) / 2;
    public static int[][]    table    = new int[3][3];
    public static int        round    = 0;

    public static JTextField text = new JTextField();
    
    public TicTacToe() {
        
        this.setSize( 200 , 325  );
        setLayout(null);
        text.setForeground(new Color(0, 0, 0));
        text.setSize(198, 20);
        text.setLocation(1, 302);
        
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setText("Tic Tac Toe");
        text.setFont(new Font("SketchFlow Print", Font.PLAIN, 12));
        
        text.setEditable(false);
        text.setBackground(Color.YELLOW);
        this.add( text);
        
    }
    
    @Override
    public void paint( Graphics g ) {
        
        g.fillRect(0,0,202,330);
        g.setColor( new Color(135, 206, 250) );
        g.fillRect( 0, 0, WIDTH, HEIGHT );
        drawTable( g );
        drawCercles( g );
        drawPawns( g );
        text.repaint();
    }
   
    private void drawPawns( Graphics g ) {
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                if ( TicTacToe.table[i][j] == GamePlay.ID2 ) {
                    g.setColor( Color.black );
                    g.fillRoundRect( i * ( PAWN_W + XPadding ), j * ( PAWN_H + YPadding ), PAWN_W, PAWN_H, 45, 45 );
                } else if ( TicTacToe.table[i][j] == GamePlay.ID1 ) {
                    g.setColor( Color.white );
                    g.fillRoundRect( i * ( PAWN_W + XPadding ), j * ( PAWN_H + YPadding ), PAWN_W, PAWN_H, 45, 45 );
                }
            }
        }
    }

    private void drawCercles( Graphics g ) {
        
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                if ( TicTacToe.table[i][j] == TicTacToe.round ) {
                    g.setColor( Color.red );
                    g.fillRoundRect( i * ( PAWN_W + XPadding ) - 3, j * ( PAWN_H + YPadding ) - 3, PAWN_W + 6, PAWN_H + 6, 45, 45 );
                 }
            }
        }
    }
    
    private void drawTable( Graphics g ) {

        g.setColor( Color.BLACK );
        g.drawLine( 0, 0, WIDTH, 0 );
        g.drawLine( 0, 0, 0, HEIGHT );
        g.drawLine( 0, 0, WIDTH, HEIGHT );
        g.drawLine( WIDTH, 0, 0, HEIGHT );
        g.drawLine( WIDTH, 0, WIDTH , HEIGHT );//
        g.drawLine( 0, HEIGHT, WIDTH, HEIGHT );
        g.drawLine( WIDTH / 2, 0, WIDTH / 2, HEIGHT );
        g.drawLine( 0, HEIGHT / 2, WIDTH, HEIGHT / 2 );

    }
}

