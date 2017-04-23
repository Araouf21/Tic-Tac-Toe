package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.GameState;

@SuppressWarnings( "serial" )
public class GameOver extends JPanel implements ActionListener{

    
    private JButton btn = new JButton("BACK TO TITLE");
    public JLabel lblWin = new JLabel();
    
    public GameOver() {
        
        setLayout(null);
        
        btn.setFont(new Font("Tarzan", Font.PLAIN, 12));
        btn.setBounds(32, 236, 145, 30);
        add(btn);
        btn.addActionListener( this );
        
        JLabel lbl = new JLabel(" YOU ");
        lbl.setFont(new Font("Tarzan", Font.PLAIN, 50));
        lbl.setBounds(32, 41, 145, 60);
        add(lbl);
        
        lblWin.setFont(new Font("Tarzan", Font.PLAIN, 30));
        lblWin.setBounds(53, 112, 101, 54);
        add(lblWin);

    }

    @Override
    public void actionPerformed( ActionEvent event ) {
  
        if (event.getSource() ==  btn ){
            Main.gs = GameState.mainMenu;
        }
    }
}
