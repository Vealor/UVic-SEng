import java.util.LinkedList;
import java.util.Iterator;

public class ShapeTester
{
	public static void printList (LinkedList<Shape> l )
	{
		Iterator<Shape>	i = l.iterator();
		while (i.hasNext())
		{
			Shape s = i.next();
			System.out.println(s);
		}
	}

	public static void main (String[] args)
	{
		LinkedList<Shape>	l = new LinkedList<Shape>();

		l.addLast(new Square(10));
		l.addLast(new Rectangle(20,10));
		l.addLast(new Circle(5));
		l.addLast(new Square(2));
		l.addLast(new Ellipse(12.7, 18.2));

		printList(l);
	}
}
