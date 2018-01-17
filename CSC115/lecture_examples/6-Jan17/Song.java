import java.util.*;

public class Song
{
	public Song (String theName, String theArtist, String theAlbum, String theFilename)
	{
		name = theName;
		artist = theArtist;	
		album = theAlbum;
		filename = theFilename;
		playCount = 0;
		rating = 0;
	}

	public Song (Scanner s)
	{
		name = s.nextLine();
		artist = s.nextLine();
		album = s.nextLine();
		filename = s.nextLine();
		playCount = 0;	
		rating = 0;
	}

	public String toString()
	{
		String s = "::Song::";
		s+= name + ":" + artist + ":" + album + ":" + filename + ":";
		s+= playCount + ":" + rating;
		return s;
	}

	public void setFilename (String newfilename)
	{
		// XXX check to make sure it is valid
		// if we do this, we should also check
		// the validity of the filename in the
		// constructors
		filename = newfilename;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public void play()
	{
		// do something!!!
		playCount++;
	}

	private String name;
	private String artist;
	private String album;
	private String filename;
	private int playCount;
	private int rating;
}
