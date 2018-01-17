public class SongTester
{
	public static void main (String[] args)
	{	
		
		Song s = new Song (	"back in black", 
					"ac/dc", 
					"back in black", 
					"bib.mp3");
		
		Song t = s;			//ON MIDTERM, only one "song" because only one instance of Song	
		
		
		System.out.println(s);
		s.play();
		
	}
}
