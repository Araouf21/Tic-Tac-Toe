package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import models.GameState;
import program.GamePlay;

import java.awt.Font;

@SuppressWarnings( "serial" )
public class FindPlayer extends JPanel implements ActionListener {

    private JButton    invite  = new JButton( "INVITE PLAYER" );
    private JButton    join    = new JButton( "JOIN PLAYER" );
    private JTextField port    = new JTextField( "9999" );
    private JTextField ip      = new JTextField( "127.0.0.1" );
    private JButton    connect = new JButton( "START" );
    private JButton    quit    = new JButton( "BACK TO TITLE" );
    private JPanel     panel   = new JPanel();
    private int        choice  = 0;

    private boolean invite( int port ) {

        try {
            ServerSocket myServerSocket = new ServerSocket( port );
            // System.out.println( "waiting for incoming connection ... ");
            Main.skt = myServerSocket.accept();
            // System.out.println( "connetion established !!" );
            myServerSocket.close();
        } catch ( IOException e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean join( String host, int port ) {

        try {
            Main.skt = new Socket( host, port );
            // System.out.println( "connetion established !!" );
        } catch ( IOException e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private JPanel setPanel() {
        port.setFont(new Font("SketchFlow Print", Font.PLAIN, 11));
        port.setEnabled( false );
        
        ip.setFont(new Font("SketchFlow Print", Font.PLAIN, 11));
        ip.setEnabled( false );

        JPanel panel = new JPanel();
        panel.setLocation( 32, 128 );
        panel.setName( "Tic Tac Toe" );
        panel.setSize( 145, 60 );
        panel.setLayout( new GridLayout( 2, 1 ) );

        JPanel panel1 = new JPanel();
        panel1.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        panel1.setLayout( null );
        
        JLabel lblPort = new JLabel( "Port : " );
        lblPort.setFont(new Font("SketchFlow Print", Font.PLAIN, 11));
        lblPort.setBounds( 5, 0, 47, 30 );
        
        panel1.add( lblPort );
        port.setBounds( 40, 3, 95, 25 );
        panel1.add( port );

        panel.add( panel1 );

        JPanel panel2 = new JPanel();
        panel2.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        panel2.setLayout( null );
        
        JLabel lblIp = new JLabel( "IP : " );
        lblIp.setFont(new Font("SketchFlow Print", Font.PLAIN, 11));
        lblIp.setBounds( 5, 0, 39, 30 );
        
        panel2.add( lblIp );
        ip.setBounds( 40, 3, 95, 25 );
        panel2.add( ip );

        panel.add( panel2 );
        
        invite.setFont(new Font("Tarzan", Font.PLAIN, 12));
        invite.setBounds( 32, 22, 145, 30 );
        add( invite );
        invite.addActionListener( this );
        
        join.setFont(new Font("Tarzan", Font.PLAIN, 12));
        join.setBounds( 32, 75, 145, 30 );
        add( join );
        join.addActionListener( this );
        
        connect.setFont(new Font("Tarzan", Font.PLAIN, 12));
        connect.setEnabled( false );
        connect.setBounds( 32, 200, 145, 30 );
        add( connect );
        connect.addActionListener( this );

        quit.setFont(new Font("Tarzan", Font.PLAIN, 12));
        quit.setBounds(32, 250, 145, 30);
        add(quit);
        quit.addActionListener( this );
        
        panel.setVisible( false );

        return panel;
    }

    public FindPlayer() {

        panel = setPanel();
        
        this.setLayout( null );
        this.add( panel );
    }
    
    @Override
    public void actionPerformed( ActionEvent e ) {

        if ( e.getSource() == invite ) {
            choice = 1;
            panel.setVisible( true );
            port.setEnabled( true );
            ip.setEnabled( false );
            connect.setEnabled( true );
            invite.setEnabled( false );
            join.setEnabled( false );

        } else if ( e.getSource() == join ) {
            choice = 2;
            panel.setVisible( true );
            port.setEnabled( true );
            ip.setEnabled( true );
            connect.setEnabled( true );
            invite.setEnabled( false );
            join.setEnabled( false );

        } else if ( e.getSource() == connect ) {

            invite.setEnabled( true );
            join.setEnabled( true ); 
            panel.setVisible( false );
            connect.setEnabled( false );
            
            if ( choice == 1 ) {
                if ( invite( Integer.parseInt( port.getText() ) ) ) {

                    Main.id = GamePlay.ID1;
                    Main.Title = ( " White (P1)" );
                    Main.gs = GameState.vsPlayer;
                }
            } else if ( choice == 2 ) {
                if ( join( ip.getText(), Integer.parseInt( port.getText() ) ) ) {

                    Main.id = GamePlay.ID2;
                    Main.Title = ( " Black (P2)" );
                    Main.gs = GameState.vsPlayer;
                }
            }
        }else if (e.getSource() == quit){
            
            Main.gs = GameState.mainMenu;
        }
    }
}
