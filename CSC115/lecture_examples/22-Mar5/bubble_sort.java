import java.util.Random;

public class bubble_sort
{
	public static int[] createAndFill (int size)
	{
		Random r = new Random();

		int[] a = new int[size];
		for (int i=0;i<size;i++)
		{
			a[i] = r.nextInt(99);
		}
		return a;
	}

	public static void bubble_sort (int[] n)
	{
		boolean swapped = false;
		if (n.length <= 1)
			return;
		do
		{
			swapped = false;
			for (int i=1;i<n.length;i++)
			{
				if (n[i-1] > n[i])
				{
					int temp;
					swapped = true;
					temp = n[i-1];
					n[i-1] = n[i];
					n[i] = temp;
				}
			}
		} while (swapped);
	}

	public static void print_array (int[] n)
	{
		for (int i=0;i<n.length;i++)
		{
			System.out.println(n[i]);
		}
	}

	public static void main (String[] args)
	{
		int size = 10;

		if (args.length != 0)
			size = Integer.parseInt(args[0]);
	
		int[] a = createAndFill(size);
		System.out.println("before:");
		print_array(a);
		bubble_sort(a);
		System.out.println("after:");
		print_array(a);
	}
}
