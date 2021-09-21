/**
 * This class represents a Film object which is being stored in the database.
 * 
 * @author KBrogan
 */
package FilmDatabase;

public class Film {
	public enum Rating {
		G, PG, M, R13, RP13, R15, R16, RP16, R18, RP18, R;
	}

	private String title;
	private String director;
	private int yearReleased;
	private String genre;
	private Rating rating;

	// get/set methods
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getYearReleased() {
		return yearReleased;
	}

	public void setYearReleased(int yearReleased) {
		this.yearReleased = yearReleased;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	// constructor
	public Film(String title, String director, int yearReleased, String genre, Rating rating) {
		this.title = title;
		this.director = director;
		this.yearReleased = yearReleased;
		this.genre = genre;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return this.title + " [ " + this.rating + " ]" + " / Director: " + this.director + " / Year: "
				+ this.yearReleased + " / Genre: " + this.genre;
	}
}