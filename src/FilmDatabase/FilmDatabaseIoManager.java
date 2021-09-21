/**
 * This class contains methods for reading and writing data from .csv file 
 * and SQL methods with JDBC.
 * 
 * @author KBrogan
 */
package FilmDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import FilmDatabase.Film.Rating;

public class FilmDatabaseIoManager {
	private final String filmListDataFilePath = "data/films.csv";
	private final String dbName = "film_list";
	private final String url = "jdbc:derby:filmlistdb";
	private Connection conn = null;

	/**
	 * Writes the text file films.txt of the films in FilmList.
	 * 
	 * @param fl a film list to write to .csv file.
	 */
	public void saveListToCsv(FilmList fl) {
		try {
			try (PrintWriter fileWriter = new PrintWriter(new File(filmListDataFilePath))) {
				int nFilms = 0;
				fileWriter.println("Title,Director(s),Year,Genre,Rating");

				for (Film film : fl.getFilmList()) {
					fileWriter.println(film.getTitle() + "," + film.getDirector() + "," 
						+ film.getYearReleased() + "," + film.getGenre() + "," + film.getRating());
					nFilms++;
				}

				fileWriter.close();
				System.out.println("Saved " + nFilms + " films to " 
					+ this.filmListDataFilePath);
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the text file filmListDataFileName of the films in supplied FilmList
	 * object.
	 * 
	 * @param fl an empty film list which will be populated with
	 *           films read from .csv file.
	 */
	public void loadListCsv(FilmList fl) {
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(filmListDataFilePath));
			String line;
			int nFilms = 0;
			
			// skip first line
			reader.readLine(); 

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				Film filmToAdd = new Film(tokens[0], tokens[1], Integer.parseInt(tokens[2]), 
					tokens[3], Rating.valueOf(tokens[4].toUpperCase()));
				fl.addFilm(filmToAdd);
				nFilms++;
			}

			reader.close();
			System.out.println(this.filmListDataFilePath 
			+ " loaded, contains " + nFilms + " films");
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// SQL methods
	private void establishMySQLConnection() {
		try {
			this.conn = DriverManager.getConnection(url);
			System.out.println("Connected to " + url);
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}

	/**
	 * Reads the text file filmListDataFileName of the films in supplied FilmList
	 * object.
	 * 
	 * @param fl an empty film list which will be populated with
	 *           films read from .csv file.
	 */
	public void updateFilmListTable(FilmList fl) {
		try {
			establishMySQLConnection();	
			this.conn.createStatement().executeUpdate("TRUNCATE TABLE " + dbName);
			int nFilms = 0;

			for (Film film : fl.getFilmList()) {
				this.conn.createStatement().executeUpdate("INSERT INTO " + dbName + " VALUES ('" + 
				film.getTitle() + "', '" + film.getDirector() + "', " + film.getYearReleased() + 
				", '" + film.getGenre() + "', '" + film.getRating() + "')");
				nFilms++;
			}
			this.conn.close();
			System.out.println("Saved " + nFilms + " films to " + this.url);
		}
		catch(SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	/**
	 * Reads the text file filmListDataFileName of the films in supplied FilmList
	 * object.
	 * 
	 * @param fl an empty film list which will be populated with
	 *           films read from .csv file.
	 */
	public void loadFilmListTable(FilmList fl) {
		try {
			establishMySQLConnection();
			int nFilms = 0;
			ResultSet rs = this.conn.createStatement().executeQuery("SELECT * FROM film_list");
			while(rs.next()) {
				Film filmToAdd = new Film(rs.getString(1), rs.getString(2), rs.getInt(3), 
				rs.getString(4), Rating.valueOf(rs.getString(5)));
				fl.addFilm(filmToAdd);
				nFilms++;
			}

			this.conn.close();
			System.out.println(this.dbName 
			+ " loaded, contains " + nFilms + " films");
		}
		catch(SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	// getters
	public String getFilmListDataFilePath() {
		return filmListDataFilePath;
	}

	public String dbName() {
		return filmListDataFilePath;
	}

	public String getURL() {
		return filmListDataFilePath;
	}
}