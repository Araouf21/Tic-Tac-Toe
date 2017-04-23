package view;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import models.GameState;

@SuppressWarnings( "serial" )
public class MainMenu extends JPanel implements ActionListener {

    JButton btnVsCom ;
    JButton btnVsPlayer;

    public MainMenu() {
        setLayout(null);
        
        btnVsCom = new JButton("VS COM");
        btnVsCom.setFont(new Font("Tarzan", Font.PLAIN, 12));
        btnVsCom.setBounds(32, 146, 145, 30);
        add(btnVsCom);
        btnVsCom.addActionListener( this );
        
        btnVsPlayer = new JButton("VS PLAYER");
        btnVsPlayer.setFont(new Font("Tarzan", Font.PLAIN, 12));
        btnVsPlayer.setBounds(32, 202, 145, 30);
        add(btnVsPlayer);
        btnVsPlayer.addActionListener( this );
        
        JLabel lblTicTacToe = new JLabel("Tic \r\nTac \r\nToe");
        lblTicTacToe.setFont(new Font("Whimsy TT", Font.PLAIN, 25));
        lblTicTacToe.setBounds(32, 33, 145, 71);
        add(lblTicTacToe);
        

    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        if ( e.getSource() == btnVsCom ) {
            Main.gs = GameState.vsCom;
        } else if ( e.getSource() == btnVsPlayer ) {
            Main.gs = GameState.findPlayer;
        }
    }
}
