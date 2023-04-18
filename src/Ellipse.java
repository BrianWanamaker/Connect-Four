
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ellipse extends RectangleShape 
{
	public Ellipse() 
	{
		super(new Ellipse2D.Double());
	}
	
	public Ellipse(int x, int y) 
	{
		super(new java.awt.geom.Ellipse2D.Double(), x, y);
	}
	
	public Ellipse(Color color) 
	{
		super(new java.awt.geom.Ellipse2D.Double(), color);
	}
}
