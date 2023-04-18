/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
public class QueueS
{
	private int numberOfItems;
	private Node head;
	private Node tail;
	public QueueS()
	{
		
	}
	public void add(Player item)
	{
		Node node = new Node(item);
		if(head==null&&item!=null)
		{
			head=node;
			numberOfItems++;
		}
		else if(tail==null&&item!=null)
		{
			tail=node;
			head.setNext(tail);
			numberOfItems++;
		}
		else if(item!=null)
		{
			Node temp = tail;
			tail=node;
			temp.setNext(tail);
			numberOfItems++;
		}
		else
		{
			System.out.println("Item is null");
		}
	}
// returns the front item, does not remove it	
	public Player peek()
	{
		if(head.getItem()!=null)
		{
			return (Player) head.getItem();
		}
		else
		{
			return null;
		}
		
	}
// returns item at front, and removes it	
	public Player poll()
	{
		Node temp = head;
		head=head.getNext();
		numberOfItems--;
		return (Player) temp.getItem();
	}
	public int size()
	{
		return numberOfItems;
	}
	public void print()
	{
		if(head==null&&tail==null)
		{
			System.out.println("[]");
		}
		else
		{
			Node node = head;
			String str="[";
			for(int i=0;i<numberOfItems;i++)
			{
				if(head.getNext()==null)
				{
					str+=head.getItem();
				}
				else if(node==head)
				{	
					str+=head.getItem();
					node=head.getNext();
				}
				else
				{
					str+= ", " +node.getItem() ;
					node=node.getNext();
				}
			}
			str+="]";
			System.out.println(str);
		}
	}
}
