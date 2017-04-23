package program;

import java.util.ArrayList;
import java.util.Random;

import models.Vector2;

public class Computer {
    
    private static class Tree {
        
        ArrayList<Tree> succ;
        int value ;
        int id;
        int [][] key;
    
        public Tree( int[][] key , int id) {

            this.id = id;
            this.key = key;
            this.succ = new ArrayList<Tree> ();
        }

    }
    
    public static int id   ;
    
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    private static int power(int x,int y){
        
        int t = 1;
        for (int i=0; i < y ;i++)
            t *= x;
        
        return t;
    }
    
    /**
     * 
     * @param state
     * @return
     */
    static Long stateToKey(int[][] state){
        
        Long temp = 0L;
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                temp += state[j][i] * power(10,(3-i) * 3 - j - 1);
            }
        }
        return temp;
    }
    
    /**
     * 
     * @param key
     * @return
     */
    private static int[][] cloneTable(int[][] t){
        
        int[][] r = new int [3][3];
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {   
                r[i][j] = t[i][j];
            }
        }
        return r;
    }
    
    /**
     * 
     * @param s
     * @return
     */
    private static ArrayList< int [][] > getChild( int[][] s ,int id) {
        
        ArrayList< int [][]> list = new ArrayList< int [][]>();
        int [][] state = cloneTable (s);

        ArrayList<Vector2> moves = GamePlay.getPossibleMoves(id ,state);
        for (Vector2 move : moves ){
            
            int[][] table = cloneTable(state);
            GamePlay.updateTable( move, table );
            list.add(table);
        }
        
        return list;
    }
    
    /**
     * chose one move randomly from list of possible moves. 
     * @return vector2 refer to move wich can be played next time.
     */
    public static Vector2 choseMove(ArrayList< Vector2> possibleMoves ) {
        return  possibleMoves.get( new Random().nextInt( possibleMoves.size() ) );
    }
        
    /**
     * search the best move to play it.
     * @param state
     * @return move as vector2.
     */
    public static Vector2 MiniMax(int[][] state){

        Random rand = new Random() ;
        Tree tr = new Tree( state, id );
        evaluateTree(tr,rand.nextInt( 3 ) + 1);
        
        for (Tree t : tr.succ){
            if (t.value == tr.value){
                return GamePlay.getPreviousMove(tr.key , t.key );
            }
        }
        return choseMove( GamePlay.getPossibleMoves(id, state ) );
    }

    /**
     * evaluate all node in tree.(SSS*)
     */
    private static void evaluateTree(Tree tr,int d ){
        
        if ( GamePlay.isFinalState( tr.key , tr.id ) ) {
            tr.value = 100 * ( tr.id == id ? 1 : -1 );
        } else if (d == 0){
            tr.value = GamePlay.getPossibleMoves( tr.id, tr.key ).size();
        }else{
            ArrayList< int [][]> list = getChild( tr.key, tr.id );
            int p = d - 1, id2 = ( tr.id == GamePlay.ID2 ) ? GamePlay.ID1 : GamePlay.ID2;

            for ( int i = 0 ; i<list.size() ; i++) {
                tr.succ.add( new Tree( list.get( i ), id2 ) );
                evaluateTree( tr.succ.get( i ), p );
            }

            if ( tr.id != id ) {
                tr.value = max( tr.succ );
            } else {
                tr.value = min( tr.succ );
            }
        }
    }
    
    private static Integer min(ArrayList<Tree> vector){
        Integer number = vector.get( 0 ).value;
        for (int i = 1 ; i < vector.size(); i++){
            if (number > vector.get(i).value){
                number = vector.get( i ).value;
            }
        }
        return number;
    }
    
    private static Integer max(ArrayList<Tree> vector){
        Integer number = vector.get( 0 ).value;
        for (int i = 1 ; i < vector.size(); i++){
            if (number < vector.get(i).value){
                number = vector.get( i ).value;
            }
        }
        return number;
    }    
    
}
