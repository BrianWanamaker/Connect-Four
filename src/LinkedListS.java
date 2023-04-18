/*Brian Wanamaker
 * Kohlberg
 * Ser120 & CSC111 
 * 5/11/222
 */
public class LinkedListS 
{
	private int numberOfItems;
	private Node head;
	private Node tail;
	public LinkedListS()
	{
		numberOfItems=0;
		head=null;
		tail=null;
	}
	public void add(char item)
	{
		Node node = new Node(item);
		if(head==null)
		{
			head=node;
			numberOfItems++;
		}
		else if(tail==null)
		{
			tail=node;
			head.setNext(tail);
			numberOfItems++;
		}
		else 
		{
			Node temp = tail;
			tail=node;
			temp.setNext(tail);
			numberOfItems++;
		}
	}
	public char get(int index)
	{
		if(index>numberOfItems)
		{
			return ' ';
		}
		else
		{
			Node node=head;
			for(int i=0;i<numberOfItems;i++)
			{
				if(i==index)
				{
					return (char) node.getItem();
				}
				else
				{
					node=node.getNext();
				}
			}
		}
		return ' ';
	}
	public void set(int index, char item)
	{
		if(index>numberOfItems)
		{
			System.out.println("Index does not exist");
		}
		else
		{
			Node node=head;
			for(int i=0;i<numberOfItems;i++)
			{
				if(i==index)
				{
					node.setItem(item);
				}
				else
				{
					node=node.getNext();
				}
			}
		}	
	}
	public void remove(int index)
	{
		if(head==null)
		{
			return;
		}
		if(index>numberOfItems)
		{
			System.out.println("Index does not exist");
		}
		else
		{
			Node node=head;
			Node prev=null;
			for(int i=0;i<numberOfItems;i++)
			{
				if(i==index)
				{
					if(node==head)
					{
						head=head.getNext();
						numberOfItems--;
					}
					else if(node == tail&&prev!=null)
					{
						tail=prev;
						numberOfItems--;
					}
					else
					{
						prev.setNext(node.getNext());
						numberOfItems--;
					}
				}
				else
				{
					if(i == index-1)
					{
						prev=node;
					}
					if(node.getNext()!=null)
					{
						node=node.getNext();
					}	
				}
			}
		}	
		
	}
	public int size()
	{
		return numberOfItems;
	}
	public void print()
	{
		if(head==null)
		{
			System.out.println("[]");
		}
		else
		{
			Node node = head;
			String str="[ ";
			for(int i=0;i<numberOfItems;i++)
			{
				if(head.getNext()==null)
				{
					str+=head;
				}
				else if(node==head)
				{	
					str+=head;
					node=head.getNext();
				}
				else
				{
					str+= ", " +node ;
					node=node.getNext();
				}
			}
			str+="]";
			System.out.println(str);
		}
	}
	
}
