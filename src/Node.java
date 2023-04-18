/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
public class Node
{
	Object item;
	Node next;
	public Node(Object item)
	{
		this.item=item;
	}
	public Object getItem()
	{
		if(item!=null)
		{
			return item;
		}
		else
		{
			return null;
		}
		
	}
	public void setItem(Object item)
	{
		this.item = item;
	}
	public Node getNext()
	{
		return next;
	}
	public void setNext(Node next)
	{
		this.next = next;
	}
	public String toString()
	{
		return "" + item;
	}
}
