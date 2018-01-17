import java.util.*;
import java.io.*;

public class SongTester
{
	public static void main (String[] args) throws FileNotFoundException
	{
		Song s = new Song(	"hell's bells", 
					"ac/dc",
					"back in black", 
					"hells_bells.mp3");

		Song t = new Song(	"the monster", 
					"eminem", 
					"the marshal mathers lp 2", 
					"the_monster.mp3");

		Song g = new Song("roar", "katy perry", "prism", "roar.mp3");

		System.out.println(s);
		System.out.println(t);
		System.out.println(g);

		File inFile = new File("songs.txt");
		Scanner sc = new Scanner(inFile);

		while (sc.hasNext())
		{
			Song q = new Song(sc);
			System.out.println(q);
		}
		
	}
}
