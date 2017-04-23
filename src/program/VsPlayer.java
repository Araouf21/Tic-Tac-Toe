package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

import models.GameState;
import models.Vector2;
import view.Main;
import view.TicTacToe;

public class VsPlayer extends Thread{

    private BufferedReader myInput   = null;
    private PrintStream    myOutput  = null;
    private TicTacToe      ticTacToe = null;
    public int             myId      = 0;
    
    
    public VsPlayer( Socket s, int id1, TicTacToe ttt ) {

        super( "GamePlay " + id1 );

        try {
            // estabilate connection.
            Main.skt = s;
            ticTacToe = ttt;
            myInput = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
            myOutput = new PrintStream( s.getOutputStream() );
            myId = id1;

            if ( id1 == GamePlay.ID1 ) {
                synchronized ( myOutput ) {
                    boolean isMyRound = new Random().nextBoolean();
                    TicTacToe.round = isMyRound ? GamePlay.ID1 : GamePlay.ID2;
                    myOutput.println( !isMyRound );
                }
            } else if ( id1 == GamePlay.ID2 ) {
                synchronized ( myInput ) {
                    boolean isMyRound = myInput.readLine().equals( "true" );
                    TicTacToe.round = isMyRound ? GamePlay.ID2 : GamePlay.ID1;
                }
            }

            GamePlay.initializeTable();

            TicTacToe.text.setText( "start ... " );

        } catch ( IOException e ) {
            e.printStackTrace();
            TicTacToe.text.setText( "whoops ,something bad was happned !!! " );
        }
    }    
    

    @SuppressWarnings( "deprecation" )
    @Override
    public  void run() {
        synchronized ( TicTacToe.table ) {
            loop: while ( !GamePlay.isFinalState( TicTacToe.table ,myId) ) {
                setPriority( MIN_PRIORITY );
                if ( TicTacToe.round == myId ) {
                    send( GamePlay.play( myId ) );
                } else {
                    TicTacToe.text.setText( "wait ... " );
                    try {
                        if ( recieve() )
                            continue loop;
                    } catch ( IOException e1 ) {
                        e1.printStackTrace();
                        TicTacToe.text.setText( " WIN !!" );
                        break;
                    }

                    try {
                        Thread.sleep( 10 );
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //System.out.println( "End of game !!" );
        Main.gs = GameState.gameOver;
        disconnnect();
        stop();
    }
    
    private boolean recieve() throws IOException {
        synchronized ( myInput) {
            
                String buf = myInput.readLine();
                if ( buf != null ) {
                    //System.out.println( "[" + Game.id + "] receive <- " + buf );       
                    try {
                        Vector2 move = new Vector2( Integer.parseInt( buf.split( "," )[0] )
                                , Integer.parseInt( buf.split( "," )[1] ) );

                        TicTacToe.round = myId;
                        GamePlay.updateTable( move ,TicTacToe.table);
                        ticTacToe.repaint();
                    } catch ( NumberFormatException | IndexOutOfBoundsException ex ) {
                        ex.printStackTrace();
                    }
                }
        }
        return TicTacToe.round == myId;
    }

    private void send(Vector2 move) {
        synchronized (myOutput){
            
            TicTacToe.round = (myId == GamePlay.ID2 ? GamePlay.ID1 : GamePlay.ID2);
            GamePlay.updateTable( move ,TicTacToe.table);
            ticTacToe.repaint();
            myOutput.println( move.toString() );
            //System.out.println( "[" + Game.id + "] send -> move : " + move.toString() );
        }
    }
    
    private void disconnnect() {
        try {
            //System.out.println( "disconnection !!" );
            myOutput.close();
            myInput.close();
            Main.skt.close();
            Main.skt = null ;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        
    }
    
}
