package program;

import java.util.ArrayList;

import models.Mouse;
import models.Vector2;
import view.Main;
import view.TicTacToe;

public class GamePlay {

   
    public static Mouse      mouse     = new Mouse();
    private static boolean   isDragged = false;
    public final static long MAX_TIME  = 10000000000L;
    public static final int  ID1       = 5;
    public static final int  ID2       = 1;
    public static final int  XX        = 8;
    private static long      t         = 0;
    private static int       id;

    public static Vector2 play(int i) {
        id = i;
        Vector2 previousPostion = new Vector2( 0, 0 );
        //Vector2 pawn = normalize (NewGame.mouse.getPosition());
        long t1 = System.nanoTime();
        while ( t < MAX_TIME ) {
            if ( Main.timeOut ) {
                t = ( System.nanoTime() - t1 );
                if ( t % 1000000000 == 0 )
                    TicTacToe.text.setText( "Time remeining ... " + ( MAX_TIME - t ) / 1000000000 + " s." );
            }
            System.out.print( "" );
            if ( !isDragged ) {
                if ( mouse.isPressed() && mouse.intersect( getMyPawns( id ,TicTacToe.table) ) ) {
                    previousPostion = new Vector2(mouse.getPosition());
                    isDragged = true;
                }
            } else {
                if ( mouse.isReleased() ) {
                    isDragged = false;
                    Vector2 currentPosition = new Vector2 (mouse.getPosition());
                    Vector2 move = normalize( previousPostion ,currentPosition);
                    // draw pawn at his old position (previousPostion)
                    //Game.table[pawn.x][pawn.y] = Game.id;
                    if ( isMovementAllowed( move ,TicTacToe.table) ) {
                        return move;
                    }
                } else {                    
                    //hide dragged pawn 
                    //Game.table[pawn.x][pawn.y] = 0;
                }
            }
        }
        return Computer.choseMove( getPossibleMoves(id,TicTacToe.table) );
    }

    /**
     * check if the movement is allowed to be played.
     * 
     * @param mv
     *            movement
     * @return (true) if movement is allowed,(false) else.
     */
    private static boolean isMovementAllowed( Vector2 mv ,int[][] table) {
        ArrayList< Vector2> possibleMoves = getPossibleMoves(id,table);
        for ( Vector2 m : possibleMoves ) {
            if ( m.equals( mv ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * get list of possible moves.
     * @return list of possible moves.
     */
    public static ArrayList< Vector2> getPossibleMoves(int id,int[][] table) {
        
        ArrayList< Vector2> moves = new ArrayList< Vector2>();
        for ( Vector2 pion : GamePlay.getMyPawns( id ,table) ) {
            for ( int i = -1; i <= 1; i++ ) {
                for ( int j = -1; j <= 1; j++ ) {
                    Vector2 direction = new Vector2( i, j );
                    Vector2 v = pion.add ( direction );
                    if ( isValide( v ) && isAllowed( pion, direction ) ) {
                        if ( table[v.x][v.y] == XX ) {
                            moves.add( new Vector2( ( pion.toInt() ), ( v.toInt() ) ) );
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    private static boolean isValide( Vector2 v ) {
        return ( v.x >= 0 && v.x <= 2 && v.y >= 0 && v.y <= 2 );
    }    

    /**
     * Check if the direction chosed is not forbiden.
     * 
     * @param pion
     *            position of pawn relative to the table.
     * @param direction
     *            the direction that was selected.
     * @return <i>true</i> if a movement is allowed,else <i>false</i>.
     */
    private static boolean isAllowed( Vector2 pion, Vector2 direction ) {

        Vector2 v = pion.add(direction );
        int val1 = ( pion.toInt() );
        int val2 = ( v.toInt() );

        if ( ( val1 == 1 || val1 == 7 ) && ( val2 == 3 || val2 == 5 )
                || ( val2 == 1 || val2 == 7 ) && ( val1 == 3 || val1 == 5 ) ) {
            return false;
        }
        return true;
    }

    /**
     * get the move.
     * 
     * @param mouse
     *            current location
     * @param prevmouse
     *            previous location
     * @return the coordinates of the movement relative to the table.
     */
    private static Vector2 normalize( Vector2 v1, Vector2 v2 ) {

        Vector2 direction = new Vector2( normalize( v2.x - v1.x )
                , normalize( v2.y - v1.y ) );
        
        Vector2 prev = normalize( v1 );// -->[0,0]..[2,2]
        Vector2 next = prev.add( direction );// -->[0,0]..[2,2]

        return new Vector2( prev.toInt(), next.toInt() );
    }

    /**
     * get the cell that corresponds to the mouse location.
     * 
     * @param pos
     *            absolute location.
     * @return index (i,j) of the cell of the table.
     */
    private static Vector2 normalize( Vector2 pos ) {
        return new Vector2( (pos.x * 3) / TicTacToe.WIDTH, (pos.y * 3) / TicTacToe.HEIGHT );
    }

    /**
     * get the direction of the mouse relative to an axis.
     * 
     * @return -1 , 0 , 1
     */
    private static int normalize( int i ) {
        if ( i > 10 )
            return 1;
        if ( i < -10 )
            return -1;
        return 0;
    }

    /**
     * get list of positions wich refer to the player's pawns. 
     * @param id the ID of player.
     * @param table 
     * @return list wich contain 3 vector2 references to the player's pawns. 
     */
    private static Vector2[] getMyPawns( int id, int[][] table  ) {
        Vector2[] pawns = new Vector2[3];
        int k = 0;
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                if ( table[i][j] == id ) {
                    pawns[k] = new Vector2( i, j );
                    k++;
                    if ( k == 3 )
                        return pawns;
                }
            }
        }
        return pawns;
    }

    /**
     * 
     * @param move
     * @param table
     */
    public static void updateTable( Vector2 move ,int[][] table) {
        int x1 = move.x % 3, x2 = move.x / 3;
        table[move.y % 3][move.y / 3] = table[x1][x2];
        table[x1][x2] = XX;
    }

    /**
     * 
     * @param table
     * @param myId
     * @return
     */
    public static boolean isFinalState(int[][] table ,int myId ){
        int[] s = { 0, 0, 0, 0, 0, 0 ,0};

        synchronized ( table ) {
            for ( int i = 0; i < 3; i++ ) {

                s[0] += table[0][i];
                s[1] += table[1][i];
                s[2] += table[2][i];
                s[3] += table[i][i];
                s[4] += table[2 - i][i];
                s[5] += table[i][1];

            }

            if ( table[0][0] == ID2 && table[1][0] == ID2 && table[2][0] == ID2 ) {// white
                TicTacToe.text.setText( myId == ID2 ? " WIN !" : "LOSE !" );
                return true;
            }
            
            if ( table[0][2] == ID1 && table[1][2] == ID1 && table[2][2] == ID1 ) {// black
                TicTacToe.text.setText( myId == ID1 ? " WIN !" : "LOSE !" );
                return true;
            }
            
            for ( int sum : s ) {
                if ( sum == 3 * ID1 ) {
                    TicTacToe.text.setText( myId == ID1 ? " WIN !" : "LOSE !" );
                    return true;
                } else if ( sum == 3 * ID2 ) {
                    TicTacToe.text.setText( myId == ID2 ? " WIN !" : "LOSE !" );
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Initialize table (with pawns).
     */
    public static void initializeTable() {
        for ( int j = 0; j < 3; j++ ) {
            TicTacToe.table[j][0] = GamePlay.ID1;
            TicTacToe.table[j][1] = GamePlay.XX;
            TicTacToe.table[j][2] = GamePlay.ID2;
        }
    }

    public static Vector2 getPreviousMove( int[][] key, int[][] key2 ) {

        int prev = 0 , next = 0 ;
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {   
                int res = key[i][j] - key2[i][j];
                if (res != 0){
                    if (res < 0){
                        prev = new Vector2( i,j ).toInt();
                    }
                    else{
                        next = new Vector2( i,j ).toInt();
                    }
                }
            }
        }
        
        return new Vector2( prev,next );
    }
}

