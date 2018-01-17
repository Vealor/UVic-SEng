import java.util.*;
import java.io.*;

public class SongTester
{
	private static Song[] storage;
	private static int songCount;

	private static final int INITIAL_SIZE = 1;

	public static void initializeStorage()
	{
		storage = new Song[INITIAL_SIZE];
		songCount = 0;
	}

	public static void growAndCopyArray()
	{
		Song[] newOne = new Song[storage.length*2];
		System.out.print("Growing from " + storage.length + " to ");
		System.out.println(storage.length*2);
		
		for (int i = 0;i<songCount;i++)	
		{
			newOne[i] = storage[i];
		}
		storage = newOne;
	}
	
	public static void addSong (Song s)
	{
		if (songCount == storage.length)
		{
			growAndCopyArray();
		}
		storage[songCount++] = s;
	}

	public static void main (String[] args) throws FileNotFoundException
	{
		File inFile = new File("songs.txt");
		Scanner sc = new Scanner(inFile);
		Song q;
		
		initializeStorage();

		while (sc.hasNext())
		{
			q = new Song(sc);
			addSong(q);
		}

		System.out.println("There are " + songCount + " songs in the library");
		for (int i=0;i<songCount;i++)
		{
			System.out.println(storage[i]);
		}

	}


}
