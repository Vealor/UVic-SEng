/**
  A singly linked list of HNode (hash node). Each node contains student number (as a string), a student object, and an HNode pointer points to the next node.
*/

public class MyLinkedList
{
	private HNode head;
	private int count;
	
	//construct an empty list.
	public MyLinkedList()
	{
		head=null;
		count=0;
	}
	
	//return true if the list is empty, false otherwise
	public boolean isEmpty()
	{
		return head==null;
	}
	
	//return the number of HNodes in the list
	public int size()
	{
		return count;
	}
	
	/**search the HNode by student number (key), throw EmptyListException if the list is empty. Return the student object if found, null otherwise.
	*/
	public Student search(String str)throws EmptyListException
	{
		if(head==null)
			throw new EmptyListException("List is empty.");
		HNode current=head;
		while(current!=null)
		{
			if(str.equals(current.key))
				return current.value;
			current=current.next;
		}
		return null;
	}
	
	//insert a student object into the list
	public void insert(Student obj)
	{
		String sNum=obj.getStudentNum();
		if(head==null||search(sNum)==null)
		{
			HNode newNode=new HNode(sNum,obj,head);
			head=newNode;
			count++;
		}
	}
	
	/**
	  remove a student object from the list by student number. Throw an EmptyListException if the list is empty. Return null if not found.
	*/
	public boolean remove(String sNum)throws EmptyListException
	{
		if(head==null)
			throw new EmptyListException("List is empty.");
		if(sNum.equals(head.key))
		{
			head=head.next;
			count--;
			return true;
		}
		HNode current=head;
		while(current.next!=null)
		{
			if(sNum.equals(current.next.key))
			{
				current.next=current.next.next;
				count--;
				return true;
			}
			current=current.next;
		}
		return false;
	}
	
	/**
	  return a string representation of the list. One student per line.
	*/
	public String toString()
	{
		String result="";
		HNode current=head;
		while (current!=null){
			result += current.value.toString();
			if(current.next!=null)
				result +="\n";
			current=current.next;
		}
		return result;
	}
	
	/**
	  HNode stands for hash node. 
	*/
	private class HNode
	{
		String key;//stores student number, e.g. V00123456
		Student value; //stores student object (student number, mark and letter grade)
		HNode next;
		public HNode(String k, Student obj, HNode nextNode)
		{
			key=k;
			value=obj;
			next=nextNode;
		}
	}
	
	public static void main(String[] args)
	{
		MyLinkedList list=new MyLinkedList();
		System.out.println(list);
		System.out.println("size="+list.size());
		list.insert(new Student("V00111111",11));
		System.out.println(list);
		System.out.println("size="+list.size());
		list.insert(new Student("V00222222",22));
		if(list.size()==2)
			System.out.println("Passed size test");
		list.insert(new Student("V00333333",33));
		list.insert(new Student("V00444444",44));
		list.insert(new Student("V00555555",55));
		
		System.out.println("\nAfter insert five, the list is:\n"+list);
		list.remove("V00555555");
		System.out.println("\nAfter remove 5\n"+list);
		System.out.println("size="+list.size());
		list.remove("V00111111");
		System.out.println("\nAfter remove 1\n"+list);
		System.out.println("size="+list.size());
		list.remove("V00333333");
		System.out.println("\nAfter remove 3\n"+list);
		System.out.println("size="+list.size());
		list.remove("V00444444");
		System.out.println("\nAfter remove 4\n"+list);
		System.out.println("size="+list.size());
		list.remove("V00222222");
		System.out.println("\nAfter remove 2\n"+list);
		System.out.println("size="+list.size());
	}
}