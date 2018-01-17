public class SongTester
{
	public static void main (String[] args)	

	{
                           // name          artist   album    
		Song s = new Song (	"back in black", 
					"ac/dc", 
					"back in black", 
					"back_in_black.mp3");

		Song t = s;
		System.out.println(s);
		s.play();

	}
}
