/**
 * This class represents the collection of films in the database.
 * 
 * @author KBrogan
 */
package FilmDatabase;

import java.util.ArrayList;

public class FilmList {
	private ArrayList<Film> filmArray;

	public FilmList() {
		this.filmArray = new ArrayList<Film>();
	}

	/**
	 * Adds a film object to the list.
	 * 
	 * @param aFilm a film object to add to the array
	 */
	public void addFilm(Film aFilm) {
		this.filmArray.add(aFilm);
	}

	/**
	 * Removes a film object from the list.
	 * 
	 * @param index number representing the position in the list
	 */
	public void removeFilm(int index) {
		this.filmArray.remove(index);
	}

	/**
	 * Searches the array for a film object, and returns true if the list contains
	 * the film; if not, returns false.
	 * 
	 * @param aFilm a film object
	 * @return boolean representing if the array contains aFilm
	 */
	public boolean hasFilm(Film aFilm) {
		return this.filmArray.contains(aFilm);
	}

	/**
	 * Removes all objects from the list.
	 */
	public void clear() {
		this.filmArray.clear();
	}

	/**
	 * Returns an array populated with film objects.
	 * 
	 * @return Film[] an array populated with film objects
	 */
	public Film[] getFilmList() {
		Film[] list = new Film[this.filmArray.size()];

		for (int i = 0; i < list.length; i++) {
			list[i] = this.filmArray.get(i);
		}

		return list;
	}
}