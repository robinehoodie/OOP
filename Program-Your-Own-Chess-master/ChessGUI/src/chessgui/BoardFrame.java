package chessgui;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class BoardFrame extends JFrame  implements ActionListener{
    Component component;
    JButton Button;
    JButton resetButton;
    public BoardFrame()
    {
        
        resetButton = new JButton();
        resetButton.setBackground(new Color(255,255,255));
        resetButton.setText("Reset");
        resetButton.setSize(520, 50);
        resetButton.setLocation(0, 520);
        resetButton.addActionListener(this);
        
       
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Chess");
        this.setResizable(false);
        component = new Board();

        this.add(resetButton);
        this.add(component, BorderLayout.CENTER);
        this.setVisible(true);
        
        
        this.setLocation(600, 150);
        this.pack();
       
    }
     
         @Override
        public void actionPerformed(ActionEvent e) {
         if(e.getSource()==resetButton) {
          this.remove(component);
          component = new Board();
          this.add(component);
          SwingUtilities.updateComponentTreeUI(this);
         }
        }
}
