package view;

import java.awt.BorderLayout;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.GameState;
import program.GamePlay;

import java.awt.Toolkit;


@SuppressWarnings( "serial" )
public class Main extends JFrame implements Runnable{

    public static String     Title = "Tic Tac Toe";
    public static FindPlayer ng    = null;
    public static Socket     skt   = null;
    public static int        id    = 0;
    private static TicTacToe        game;
    private static GameOver         go;
    private static MainMenu         mm;

    private static JPanel prev ;
    public static GameState gs = GameState.mainMenu;
    public static boolean timeOut = true;
    
    public static void main(String[] args) {
        new Main().run();
    }
    
    public Main() {
        //setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\RAOUF\\Desktop\\table.JPG"));

        setResizable(false);
        setSize( 207, 352 );
        
        mm = new MainMenu();
        this.setTitle( Title );
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add( mm );

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo( null );
        this.setVisible( true );
        this.addMouseListener( GamePlay.mouse );
        prev = mm;
        mm.setLayout(null);
    }
        
    @Override
    public void run() {
        while ( true ) {
            switch ( gs ) {
            
                case mainMenu:
                    mm = new MainMenu();
                    remove( prev );
                    getContentPane().add( mm );
                    repaint();
                    setResizable( true );
                    setTitle( gs +"");
                    prev = mm;
                    gs = GameState.refresh;
                    break;
            
                case findPlayer:
                    ng = new FindPlayer();
                    remove( prev );
                    getContentPane().add( ng );
                    repaint();
                    setResizable( true );
                    setTitle( gs+"" );
                    prev = ng;
                    gs = GameState.refresh;
                    break;

                case vsPlayer :
                    timeOut = true;
                    game = new TicTacToe();
                    new program.VsPlayer( skt, id, game ).start();
                    gs = GameState.play;
                    setTitle( "vs player" );
                    break;
                    
                case vsCom:
                    timeOut = false;
                    game = new TicTacToe();
                    new program.VsCom(game).start();
                    gs = GameState.play;
                    Title = "Tic Tac Toe";
                    break;
                    
                case play:
                    remove( prev );
                    getContentPane().add( game );
                    repaint();
                    setResizable( true );
                    setTitle( Title );
                    prev = game;
                    gs = GameState.refresh;
                    break;

                case gameOver:
                    go = new GameOver();
                    go.lblWin.setText(TicTacToe.text.getText());
                    remove( prev );
                    getContentPane().add( go );
                    repaint();
                    setResizable( true );
                    setTitle( gs +"");
                    prev = go;
                    gs = GameState.refresh;
                    break;
                    
                case refresh:
                    setResizable( false );
                    gs = GameState.pause;
                    break;
                    
                default:
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
