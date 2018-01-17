import java.util.NoSuchElementException;

public interface List<T>
{
	void add (T element);
	T elementAt (int index) throws java.util.NoSuchElementException;
	int size();
	boolean isEmpty();
	String	toString();
	
}

