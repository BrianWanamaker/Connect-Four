/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel
{
	private Timer timer;
	private boolean isGameOver;
	private LinkedList<Column> cols;
	private QueueS whoPlays;
	private Ellipse redChip;
	private Ellipse yellowChip;
	private boolean isStarted;
	private ColorHolder holder;
	private int gameMode;
	private Random gen;
	private int colPlaced;
	private int rowPlaced;
	public boolean isTie;
	
	public DrawingPanel( ColorHolder holder)
	{
		gen= new Random();
		isTie=false;
		this.holder=holder;
		isStarted = false;
		setLayout(null);
		isGameOver = false;
		cols = new LinkedList<Column>();
		whoPlays = new QueueS();

		this.setFocusable(true);
		this.setDoubleBuffered(true);
		redChip = new Ellipse();
		redChip.setLocation(0, 5);
		redChip.setSize(75, 75);
		redChip.setColor(Color.RED);
		
		yellowChip = new Ellipse();
		yellowChip.setLocation(1410, 5);
		yellowChip.setSize(75, 75);
		yellowChip.setColor(Color.YELLOW);
		
		timer = new Timer(1, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
// check if win conditions have been met				
				if(checkWin()&&isStarted)
				{ 
					gameOver();
				} 
				else if(whoPlays.peek().getColor().equals("red"))
				{
					if (redChip.getXLocation() < getWidth() / 2 - 50)
					{
						redChip.setLocation(redChip.getXLocation() + 5, redChip.getYLocation());
					} 
					else
					{
						isStarted = true;
						timer.stop();
					}
				} 
				else if(whoPlays.peek().getColor().equals("yellow")&&gameMode==0)
				{
					if (yellowChip.getXLocation() > getWidth() / 2 - 50)
					{
						yellowChip.setLocation(yellowChip.getXLocation() - 5, yellowChip.getYLocation());
					} 
					else
					{
						isStarted = true;
						timer.stop();
					}
				}
// computers turn to play				
				else
				{
// randomizes computers turn					
				int x=gen.nextInt(7);
// waits 2 seconds		
					try
					{
						TimeUnit.SECONDS.sleep(1);
					} 
					catch (InterruptedException e1)
					{
						e1.printStackTrace();
					} 
					setNextFree(x);
					isStarted=true;
				} 
				repaint();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (!timer.isRunning()&&!isGameOver&&isStarted)
				{
					mouseIsDragged(e);
				}
			}
		});
	}
// 6 rows and 7 cols
// 0 for player and 1 for cpu	
	public void startGame(int mode)
	{
		cols.clear();
		gameMode=mode;
		for (int i = 0; i < 7; i++)
		{
			Column newCol = new Column();
			cols.add(newCol);
		}
		whoGoesFirst();
		timer.start();
	}
	public void gameOver()
	{
		isGameOver = true;
		timer.stop();
	}
	public void mouseIsDragged(MouseEvent e)
	{
		Point p = e.getPoint();
		Player player = whoPlays.peek();
		if (player.getColor().equals("red"))
		{
			if (isStarted&&doesCollide(redChip)==false)
			{
				double distance = Point2D.distance(redChip.getXLocation(), redChip.getYLocation(), e.getX(), e.getY());
				if (distance < 100 && distance >= 0)
				{
					redChip.setLocation((int) p.getX() - redChip.getWidth() / 2,(int) p.getY() - redChip.getHeight() / 2);
				}
			}
		} 
		else if (player.getColor().equals("yellow"))
		{
			if (isStarted&&doesCollide(yellowChip)==false)
			{
				double distance = Point2D.distance(yellowChip.getXLocation(), yellowChip.getYLocation(), e.getX(),e.getY());
				if (distance < 100 && distance >= 0)
				{
					yellowChip.setLocation((int) p.getX() - yellowChip.getWidth() / 2,(int) p.getY() - yellowChip.getHeight() / 2);
				}
			}
		} 
		repaint();
	}
// sets a random player to go first
// sets which player goes next
	public void whoGoesFirst()
	{
		int x = gen.nextInt(2);
		if (gameMode == 0)
		{
			if (x == 0)
			{
				whoPlays.add(new Human("red"));
				whoPlays.add(new Human("yellow"));
			} 
			else if (x == 1)
			{
				whoPlays.add(new Human("yellow"));
				whoPlays.add(new Human("red"));
			}
		} 
		else
		{
			if (x == 0)
			{
				whoPlays.add(new Computer("yellow"));
				whoPlays.add(new Human("red"));
			} 
			else if (x == 1)
			{
				whoPlays.add(new Human("red"));
				whoPlays.add(new Computer("yellow"));
			}
		}
		repaint();
	}
// sets next slot free within colNum	
	public void setNextFree(int colNum)
	{
		String color = whoPlays.peek().getColor();
		colPlaced=colNum;
		boolean done=false;
		for(int i=5;i>=0;i--)
		{
			if(cols.get(i).getCell(colNum)== ' ' &&!done)
			{
				if(color.equals("red"))
				{
					cols.get(i).setCell('R', colNum);
					rowPlaced=i;
					Player p = whoPlays.poll();
					whoPlays.add(p);
					redChip.setLocation(0, 5);
					timer.start();
				}
				else if(color.equals("yellow"))
				{
					cols.get(i).setCell('Y', colNum);
					rowPlaced=i;
					Player p = whoPlays.poll();
					whoPlays.add(p);
					yellowChip.setLocation(1410, 5);
					timer.start();
				}
				done=true;
			}
		}
		if(!done)
		{
			if(gameMode==1&&color.equals("yellow"))
			{
				int x= gen.nextInt(7);
				setNextFree(x);
			}
			else if(gameMode==1&&color.equals("red"))
			{
				int x= gen.nextInt(7);
				setNextFree(x);
			}
			else if(color.equals("red"))
			{
				JOptionPane.showMessageDialog(null, "Row is full!","Try Again", JOptionPane.ERROR_MESSAGE);
				redChip.setLocation(0, 5);
			}
			else if(color.equals("yellow"))
			{
				JOptionPane.showMessageDialog(null, "Row is full!","Try Again", JOptionPane.ERROR_MESSAGE);
				redChip.setLocation(1410, 5);
			}
		}
	}	
// check for win conditions, returns true if conditions are met
public boolean checkWin()
{
// checks if board is full				
	int count=0;
	for(int i=0;i<6;i++)
	{
		for(int j=0;j<7;j++)
		{
			if(cols.get(i).getCell(j)==' ')
			{
				count++;
			}
		}
	}
	if(count==0)
	{
		timer.stop();
		isTie=true;
		gameOver();
	}
// checks vertical
	int redCount=0;
	int yellowCount=0;
	for(int i=0;i<6;i++)
	{
		if(cols.get(i).getCell(colPlaced)== 'R')
		{		
			redCount++;
			yellowCount=0;
		}
		else if(cols.get(i).getCell(colPlaced)== 'Y')
		{	
			yellowCount++;
			redCount=0;
		}
		else 
		{
			redCount=0;
			yellowCount=0;
		}
		if(redCount==4||yellowCount==4)
		{
			return true;
		}
	}
// checks horizontal
	redCount=0;
	yellowCount=0;
	for(int i=0;i<7;i++)
	{
		if(cols.get(rowPlaced).getCell(i)== 'R')
		{		
			redCount++;
			yellowCount=0;
		}
		else if(cols.get(rowPlaced).getCell(i)== 'Y')
		{	
			yellowCount++;
			redCount=0;
		}
		else if(cols.get(rowPlaced).getCell(i)== ' ')
		{
			redCount=0;
			yellowCount=0;
		}
		if(redCount==4||yellowCount==4)
		{
			return true;
		}
	}
// check diagonals
	
// downwards from top right
	for (int i = 0; i < 6; i++)
	{
		for (int j =0; j < 7; j++)
		{
			if (cols.get(i).getCell(j) == 'R' && cols.get(i + 1).getCell(j + 1) == 'R' && cols.get(i + 2).getCell(j + 2) == 'R' && cols.get(i + 3).getCell(j + 3) == 'R')
			{
				return true;
			}
			else if (cols.get(i).getCell(j) == 'Y' && cols.get(i + 1).getCell(j + 1) == 'Y' && cols.get(i + 2).getCell(j + 2) == 'Y' && cols.get(i + 3).getCell(j + 3) == 'Y')
			{
				return true;
			}
		}
	}
// upwards from bottom left	
	for (int i = 5; i >3; i--)
	{
		for (int j =0; j < 7; j++)
		{
			if (cols.get(i).getCell(j) == 'R' && cols.get(i - 1).getCell(j + 1) == 'R' && cols.get(i - 2).getCell(j + 2) == 'R' && cols.get(i - 3).getCell(j + 3) == 'R')
			{
				return true;
			}
			else if (cols.get(i).getCell(j) == 'Y' && cols.get(i - 1).getCell(j + 1) == 'Y' && cols.get(i - 2).getCell(j - 2) == 'Y' && cols.get(i - 3).getCell(j + 3) == 'Y')
			{
				return true;
			}
		}
	}
// downwards from top right 	
	for (int i = 0; i <6; i++)
	{
		for (int j =6; j >3; j--)
		{
			if (cols.get(i).getCell(j) == 'R' && cols.get(i + 1).getCell(j -1) == 'R' && cols.get(i + 2).getCell(j - 2) == 'R' && cols.get(i + 3).getCell(j -3) == 'R')
			{
				return true;
			}
			else if (cols.get(i).getCell(j) == 'Y' && cols.get(i + 1).getCell(j - 1) == 'Y' && cols.get(i + 2).getCell(j - 2) == 'Y' && cols.get(i + 3).getCell(j - 3) == 'Y')
			{
				return true;
			}
		}
	}
	return false;
}
// check for collisons	
	public boolean doesCollide(Ellipse e)
	{
		if(e.getYLocation()<0)
		{
			e.setLocation(e.getXLocation(), 0);
		}
		else if(e.getXLocation()>getWidth())
		{
			e.setLocation(getWidth()-75, e.getYLocation());
		}
		else if (e.getYLocation() > 130-e.getHeight())
		{
			int option = JOptionPane.showConfirmDialog (this, "Are you sure you want to put this chip in this column?","Connect Four", 0);
// checks which column ellipse touches
			if(option==0)
			{
				if(e.getXLocation()<240)
				{
					colPlaced=0;
					setNextFree(0);
				}
				else if(e.getXLocation()>240&&e.getXLocation()<440)
				{
					colPlaced=1;
					setNextFree(1);
				}
				else if(e.getXLocation()>440&&e.getXLocation()<640)
				{
					colPlaced=2;
					setNextFree(2);
				}
				else if(e.getXLocation()>640&&e.getXLocation()<840)
				{
					colPlaced=3;
					setNextFree(3);
				}
				else if(e.getXLocation()>840&&e.getXLocation()<1040)
				{
					colPlaced=4;
					setNextFree(4);
				}
				else if(e.getXLocation()>1040&&e.getXLocation()<1240)
				{
					colPlaced=5;
					setNextFree(5);
				}
				else if(e.getXLocation()>1240)
				{
					colPlaced=6;
					setNextFree(6);
				}		
				return true;
			}
			else if(option==1||option==-1)
			{
				e.setLocation(getWidth() / 2 - 50,0);
				return false;
			}
		}
		return false;
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		
		this.setBackground(holder.getColor());
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		int tempX = 100;
		int tempY = 150;
		brush.setColor(Color.white);
// if game is not started then make the slots white		
		if (!isStarted)
		{
			for (int i = 0; i < 6; i++)
			{
				for (int j = 0; j < 7; j++)
				{
					brush.fillOval(tempX, tempY, 75, 75);
					tempX += 200;
					if (tempX > getWidth() - 150)
					{
						tempX = 100;
						tempY += 100;
					}
				}
			}
		}
// if game is started then make the slots the corresponding colors		
		else
		{
			for (int i = 0; i < 6; i++)
			{
				for (int j = 0; j < 7; j++)
				{
					if (cols.get(i).getCell(j) == ' ')
					{
						brush.setColor(Color.WHITE);
						brush.fillOval(tempX, tempY, 75, 75);
					} 
					else if (cols.get(i).getCell(j) == 'R')
					{
						brush.setColor(Color.RED);
						brush.fillOval(tempX, tempY, 75, 75);
					} 
					else if (cols.get(i).getCell(j) == 'Y')
					{
						brush.setColor(Color.YELLOW);
						brush.fillOval(tempX, tempY, 75, 75);
					}
					else if (cols.get(i).getCell(j) == 'G')
					{
						brush.setColor(Color.GREEN);
						brush.fillOval(tempX, tempY, 75, 75);
					}
					tempX += 200;
					if (tempX > getWidth() - 150)
					{
						tempX = 100;
						tempY += 100;
					}
				}
			}
		}
		redChip.paint(brush);
		yellowChip.paint(brush);
// draws border
		brush.setColor(Color.WHITE);
		Stroke stroke = new BasicStroke(20);
		brush.setStroke(stroke);
// top border		
		brush.setColor(Color.green);
		brush.drawLine(40, 130, 1440, 130);
// draws columns		
		brush.setColor(Color.GRAY);
		brush.drawLine(40, 130, 40, 740);
		brush.drawLine(240, 130, 240, 740);
		brush.drawLine(440, 130, 440, 740);
		brush.drawLine(640, 130, 640, 740);
		brush.drawLine(840, 130, 840, 740);
		brush.drawLine(1040, 130, 1040, 740);
		brush.drawLine(1240, 130, 1240, 740);
		brush.drawLine(1440, 130, 1440, 740);
		brush.drawLine(60, 740, 1420, 740);
//draws rows		
		brush.drawLine(40, 635, 1440, 635);
		brush.drawLine(40, 540, 1440, 540);
		brush.drawLine(40, 440, 1440, 440);
		brush.drawLine(40, 340, 1440, 340);
		brush.drawLine(40, 240, 1440, 240);
		if (isStarted&&whoPlays.peek()!=null)
		{
			brush.setColor(Color.WHITE);
			Player p = whoPlays.peek();
			if (p.getColor().equals("red"))
			{
				brush.drawString("Red Players Turn", getWidth() / 2 - 50, 20);
			} 
			else
			{
				brush.drawString("Yellow Players Turn", getWidth() / 2 - 50, 20);
			}
		}
		if (isGameOver)
		{
			brush.setFont(new Font("Times New Roman", Font.PLAIN, 100));
			if(isTie==true)
			{
				brush.setColor(Color.WHITE);
				brush.drawString("TIME GAME", getWidth()/2-250, 100);
			}
			else if(whoPlays.peek().getColor().equals("red"))
			{		
				brush.setColor(Color.YELLOW);
				brush.drawString("YELLOW WINS", getWidth()/2-250, 100);
			}
			else if(whoPlays.peek().getColor().equals("yellow"))
			{
				brush.setColor(Color.RED);
				brush.drawString("RED WINS", getWidth()/2-250, 100);
			}
			
		}
	}
}
