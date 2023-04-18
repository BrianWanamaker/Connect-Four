/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format.Field;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.html.StyleSheet;

public class ControlPanel extends JPanel  
{

	private JButton playerButton;
	private JButton cpuButton;
	private JButton settingsButton;
	private JLabel label;
	private ColorHolder holder;
	public ControlPanel(DrawingPanel drawingPanel, ColorHolder holder) 
	{
		label = new JLabel("Welcome to Connect Four! Please Choose a Game Mode!");
		
		label.setHorizontalAlignment(getWidth()/2);
		this.holder=holder;
		playerButton = new JButton();
		playerButton.setText("V.S. PLAYER");
		cpuButton = new JButton();
		cpuButton.setText("V.S. CPU");
		settingsButton = new JButton();
		settingsButton.setText("Settings");
		
		
		this.setLayout(new GridLayout(4,3));
		this.add(label);	
		this.add(playerButton);
		this.add(cpuButton);
		this.add(settingsButton);
// button for player vs player		
		playerButton.addActionListener(new ActionListener() 
	        {
	        	@Override
		        public void actionPerformed(ActionEvent e) 
		        {	
	        		playerButton.setBackground(Color.BLACK);
	        		cpuButton.setVisible(false);
	        		playerButton.setEnabled(false);
	        		drawingPanel.startGame(0);
	        		label.setText("Drag the game piece to the column you want!");
		        }
	        });
// button for player vs cpu		
		cpuButton.addActionListener(new ActionListener() 
        {
        	@Override
	        public void actionPerformed(ActionEvent e) 
	        {	
        		playerButton.setVisible(false);
        		cpuButton.setBackground(Color.BLACK);
        		cpuButton.setEnabled(false);
        		drawingPanel.startGame(1);
        		label.setText("Drag the game piece to the column you want!");
	        }
        });
// button for settings		
		settingsButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{	
				String name = JOptionPane.showInputDialog(null,"What color do you want to make the background?", null);
				StyleSheet s = new StyleSheet();
				Color c = s.stringToColor(name);
				if(c!=null)
				{
					holder.setColor(c);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Try Again","Color does not exist!", JOptionPane.ERROR_MESSAGE);
				}
				drawingPanel.repaint();

			}
		});
	}
	
}
