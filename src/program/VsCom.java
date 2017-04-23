package program;

import java.util.Random;

import models.GameState;
import models.Vector2;
import view.Main;
import view.TicTacToe;


public class VsCom extends Thread{

    public int myId = 0;
    private TicTacToe ticTacToe;
    
    
    public VsCom(TicTacToe ttt){
        
        super("VsCom ");
        ticTacToe = ttt;
        boolean rand = new Random().nextBoolean();
        myId = rand ? GamePlay.ID1 : GamePlay.ID2;
        Computer.id = rand ? GamePlay.ID2 : GamePlay.ID1;
        TicTacToe.round = GamePlay.ID1;
        GamePlay.initializeTable();

    }
    
    @SuppressWarnings( "deprecation" )
    @Override
    public void run(){
        while ( !GamePlay.isFinalState(TicTacToe.table, myId) ) {
            setPriority( MIN_PRIORITY );

            if ( TicTacToe.round == myId) {
            
                TicTacToe.text.setText( "You" );
                GamePlay.updateTable( GamePlay.play(myId), TicTacToe.table );
                TicTacToe.round = myId == GamePlay.ID2 ? GamePlay.ID1: GamePlay.ID2;
                ticTacToe.repaint();
            
            }else{
                TicTacToe.text.setText( "Com" );
                
                try {
                    sleep( 1500 );
                } catch ( InterruptedException e ) {}
                
                Vector2 move = Computer.MiniMax(TicTacToe.table);
                        //Computer.choseMove( GamePlay.getPossibleMoves ( TicTacToe.round ,TicTacToe.table) );
                
                GamePlay.updateTable (move,TicTacToe.table );
                TicTacToe.round = myId == GamePlay.ID1 ? GamePlay.ID1: GamePlay.ID2;
                ticTacToe.repaint();
            }   
        }
        try {
            sleep(3000);
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        Main.gs = GameState.gameOver;
        stop();
    }

}
