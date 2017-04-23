package models;

public class Vector2 {

    public int x;
    public int y;
    
    public Vector2( int i, int j ) {
        x = i;
        y = j;
    }
    
    public Vector2( Vector2 position ) {
       x = position.x;
       y = position.y;
    }

    public int toInt(){
        return x + y*3; 
    }
    
    public Vector2 add( Vector2 v2 ) {
        return new Vector2( x + v2.x, y + v2.y );
    }

    @Override
    public boolean equals(Object obj){
        Vector2 o = ((Vector2) obj);
        return (o.x == this.x && o.y == this.y);
    }
    
    @Override
    public String toString(){
        return x + "," + y;
    }
}
