package models;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import view.TicTacToe;

public class Mouse implements MouseListener , MouseMotionListener{

    private boolean isPressed;
    private boolean isReleased;
    private Vector2 location = new Vector2( 0, 0 );
    private boolean isActive;

    public boolean isActive(){
        return isActive;
    }
    
    public boolean isPressed() {
        return isPressed;
    }

    public boolean isReleased() {
        return isReleased;
    }
    
    public Vector2 getPosition() {
        return location;
    }
    
    public boolean intersect( Vector2[] pions ) {
        
        for (Vector2 pion : pions){                      
            int x =  pion.x * (TicTacToe.PAWN_W + TicTacToe.XPadding); 
            int y =  pion.y * (TicTacToe.PAWN_H + TicTacToe.YPadding); 

            //mouse on pawn  
            if ( x <= location.x && location.x < x + 20
                 && y <= location.y && location.y < y + 20 ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        location.x = e.getX();
        location.y = e.getY() - 20;
    }

    @Override
    public void mouseEntered( MouseEvent e ) {
        isActive = true;
        location.x = e.getX();
        location.y = e.getY() - 20;
    }

    @Override
    public void mouseExited( MouseEvent e ) {
        isActive = false;
        location.x = e.getX();
        location.y = e.getY() - 20;
    }

    @Override
    public void mousePressed( MouseEvent e ) {
        location.x = e.getX();
        location.y = e.getY() - 20;
        if (e.getButton() == MouseEvent.BUTTON1){
            isPressed = true;
            isReleased = false;
            //System.out.println("##### Yes mouse is Pressed("+isPressed+")!! at "+location.toString());
        }
    }

    @Override
    public void mouseReleased( MouseEvent e ) {
        location.x = e.getX();
        location.y = e.getY() - 20;
        if (e.getButton() == MouseEvent.BUTTON1){
            isReleased = true;
            isPressed = false;
            //System.out.println("##### Yes mouse is Released("+isReleased+")!!at "+location.toString());
        }
    }

    @Override
    public void mouseDragged( MouseEvent arg0 ) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved( MouseEvent arg0 ) {
        // TODO Auto-generated method stub
        
    }
    
    
}
