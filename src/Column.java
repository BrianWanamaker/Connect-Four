/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
import java.util.LinkedList;

public class Column 
{
	private LinkedListS col;
	
    public Column()
    {
    	col = new LinkedListS();
    	initalizeCol();
    }
    public void initalizeCol()
    {
    	for(int i=0;i<7;i++)
    	{
    		col.add(' ');
    	}
    }
    public LinkedListS getCol()
    {
    	return col;
    }
    public void setCell(char type, int x)
    {
    	col.set(x, type);
    }
    public char getCell(int x)
    {
    	return col.get(x);
    }
}