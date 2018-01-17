/**
  A hash table stores student information.
*/
public class MyHashTable
{
	private final int HASH_TABLE_SIZE=7;//a prime number
	private MyLinkedList[] table; //hash table
	private int count; //number of student objects
	
	/**
	  Create HASH_TABLE_SIZE empty MyLinkedList objects. In this case, create 7 empty lists. Set count to 0.
	*/
	public MyHashTable()
	{
	
	}
	
	/**
	  return true if the table is empty, false otherwise.
	*/
	public boolean isEmpty()
	{
		return true;
	}
	
	/**
	  insert a student "obj" into the hash table. Need to find the index of the table based on the student number. Then insert the student "obj" into the list at that index.
	*/
	public void insert(Student obj)
	{
		
	}
	
	/**
	  Based on the student number (or ID), remove the student if found and return true, return false if not found. Throw an exception if the list is empty.
	*/
	public boolean remove(String studentID)throws EmptyListException
	{
		return true;
	}
	
	/**
	  Based on the student number (or ID), return the student object if found and return null, return false if not found. Throw an exception if the list is empty.
	*/
	public Student search(String studentID)throws EmptyListException
	{
		return null;
	}
	
	/**
	  Based on the student number (or ID), calculate the table index. Throw a HashException object if the studentID is null or empty. Return the int.
	*/
	public int hashIndex(String studentID)throws HashException
	{
		return 0;
	}
	
	/**
	  A string representation of the hash table. One student per line.
	*/
	public String toString()
	{
		String output="";
		for(int i=0; i<table.length; i++)
		{
			if(table[i].size()!=0)
				output += table[i].toString()+"\n"; 
		}
		return output;
	}
	
	/**
	  return the number of student object in the table.
	*/
	public int size()
	{
		return 0;
	}
	public static void main(String[] args)
	{
		
	}
}