public class Song
{
	public Song(	String theName, 
			String theArtist, 
			String theAlbum, 
			String theFilename) 
	{
		name = theName;
		artist = theArtist;
		album = theAlbum;
		filename = theFilename;
		playCount = 0;
		rating = 0;
	}

	public String toString()
	{
		String s = "::Song::";
		s += name + ":" + artist + ":" + filename;
		s += ":" + playCount + ":" + rating;
		
		return s;
	}

	public void play()
	{
		// do some magic
		
	}
	private String name;
	private String artist;
	private String album;
	private String filename;
	private int playCount;
	private int rating;
}
