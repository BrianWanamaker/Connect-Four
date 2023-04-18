/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
import java.util.LinkedList;

public class Human implements Player
{
	String color;
	public Human(String color)
	{
		this.color=color;
	}
//	0 for player, 1 for computer
	@Override
	public int getType()
	{
		return 0;
	}
	@Override
	public String getColor()
	{
		return color;
	}

}
