/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class App extends JFrame 
{
	public App() 
	{
		super("Connect Four");
		this.setLayout(new BorderLayout());
		
		ColorHolder holder= new ColorHolder();
		DrawingPanel drawingPanel = new DrawingPanel(holder);
		this.add(drawingPanel, BorderLayout.CENTER);
		ControlPanel controlPanel = new ControlPanel(drawingPanel,holder);
		this.add(controlPanel, BorderLayout.NORTH);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1500, 900);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public static void main(String[] args) 
	{
		new App();
	}
}
