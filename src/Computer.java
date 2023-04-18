/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
import java.util.LinkedList;

public class Computer implements Player
{
	String color;
	public Computer(String color)
	{
		this.color=color;
	}
// 0 for human, 1 for computer
	@Override
	public int getType()
	{
		return 1;
	}
	@Override
	public String getColor()
	{
		return color;
	}
}
