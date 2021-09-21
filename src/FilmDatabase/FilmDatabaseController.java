/**
 * This class controls the view, and manipulates the data of the model.
 * 
 * @author KBrogan
 */
package FilmDatabase;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import FilmDatabase.Film.Rating;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//@SuppressWarnings("serial")
public class FilmDatabaseController extends JFrame {
	private FilmList model;
	private FilmDatabaseView view;
	private FilmDatabaseIoManager ioManager;

	// event handlers
	private void eventHandleListSelection() {
		// sets the view of REMOVE button to enabled if a film is selected in the view of the list
		this.view.getRemoveButton().setEnabled(this.view.getList().getSelectedIndex() != -1);
	}

	private void eventHandleKeyReleased(KeyEvent e) {
		// the view of ADD button will be disabled unless the title field is not empty
		this.view.getAddButton().setEnabled(!this.view.getFilmTitleTextField().getText().isEmpty());
	}

	private void eventHandleClearAllButton() {
		// clears all the film data entry fields and disables ADD button
		this.view.getFilmTitleTextField().setText("");
		this.view.getFilmDirectorTextField().setText("");
		this.view.getFilmYearTextField().setText("");
		this.view.getFilmGenreTextField().setText("");
		this.view.getFilmRatingTextField().setText("");
		this.view.getAddButton().setEnabled(false);
		System.out.println("All fields cleared!");
	}

	private void eventHandleAddButton() {
		// gets text from the text fields
		String titleInput = this.view.getFilmTitleTextField().getText().trim();
		String directorInput = this.view.getFilmDirectorTextField().getText().trim();
		String genreInput = this.view.getFilmGenreTextField().getText().trim();
		String yearInput = this.view.getFilmYearTextField().getText().trim();
		String ratingInput = this.view.getFilmRatingTextField().getText().trim().toUpperCase();
		int year = 0;
		Rating rating = Rating.R;

		try {
			// formats Year and Rating
			year = Integer.parseInt(yearInput);
			rating = Rating.valueOf(ratingInput);
		} catch (NumberFormatException e) {

		}
		// creates film to add to the list
		Film newFilm = new Film(titleInput, directorInput, year, genreInput, rating);

		if ((!titleInput.isEmpty()) && (!this.model.hasFilm(newFilm))) {
			this.model.addFilm(newFilm);
			System.out.println(titleInput + " added to list!");
			// resets text fields
			this.view.getFilmTitleTextField().setText("Enter Title*");
			this.view.getFilmDirectorTextField().setText("Enter Director(s)");
			this.view.getFilmYearTextField().setText("Enter Year");
			this.view.getFilmGenreTextField().setText("Enter Genre");
			this.view.getFilmRatingTextField().setText("Enter Rating");
			// disables add button after film is added to the list
			this.view.getAddButton().setEnabled(false);
		}

		this.view.update();
	}

	private void eventHandleRemoveButton() {
		int index = this.view.getList().getSelectedIndex();
		Film filmRemoved = this.model.getFilmList()[index];

		// removes selected film
		if (index != -1) {
			this.model.removeFilm(index);
		}

		this.view.update();
		System.out.println(filmRemoved.getTitle() + " removed from list!");
	}

	private void eventHandleWindowOpened() {
		this.view.update();
		// disables add button when application starts
		this.view.getAddButton().setEnabled(false);
		System.out.println("Window opened!");
	}

	private void eventHandleWindowClosing() {
		// save file on exit
		//this.ioManager.saveListToCsv(this.model);
		//System.out.println("List saved!");
	}

	private void eventHandleSaveButton() {
		this.view.getSaveWindow().setLocation(75, 100);
		this.view.getSaveWindow().setVisible(true);
	}

	private void eventHandleSaveToFileButton() {
		this.ioManager.saveListToCsv(this.model);
		this.view.getSaveWindow().setVisible(false);
		this.view.update();
	}

	private void eventHandleSaveToDbButton() {
		this.ioManager.updateFilmListTable(this.model);
		this.view.getSaveWindow().setVisible(false);
		this.view.update();
	}

	private void eventHandleLoadButton() {
		// populate the model with films loaded from a file or DB.
		//this.view.getLoadWindow().setLayout(null);
		this.view.getLoadWindow().setLocation(75, 100);
		this.view.getLoadWindow().setVisible(true);
	}

	private void eventHandleLoadFileButton() {
		this.model.clear();
		this.ioManager.loadListCsv(this.model);
		this.view.getLoadWindow().setVisible(false);
		this.view.update();
	}
	private void eventHandleLoadDbButton() {
		this.model.clear();
		this.ioManager.loadFilmListTable(this.model);
		this.view.getLoadWindow().setVisible(false);
		this.view.update();
	}

	private void eventHandleSearchButton() {
		// new FilmList to view the search results
		FilmList searchResults = new FilmList();
		// stores the search term, converted to upper-case to compare text
		String term = this.view.getSearchTextField().getText().trim().toUpperCase();

		for (int index = 0; index < this.model.getFilmList().length; index++) {
			// compares the title to the term, converted to upper-case to compare text
			if (this.model.getFilmList()[index].getTitle().toUpperCase().contains(term)) 
			{
				// adds the film to the search results list
				searchResults.addFilm(this.model.getFilmList()[index]);
			}
		}

		// sets the view to display the search results
		this.view.getList().setListData(searchResults.getFilmList());
		System.out.println("Searching database...\n" + searchResults.getFilmList().length
				+ ((searchResults.getFilmList().length) != 1 ? " results found!" : " result found!"));
	}

	private void eventHandleClearButton() {
		// sets the view to display the list and clears the search field
		this.view.getList().setListData(this.model.getFilmList());
		this.view.getSearchTextField().setText("");
		System.out.println("Search cleared!");
	}

	/**
	 * The constructor initialises the model and loads data from a text file
	 * database. The view is constructed and added to the content pane. All event
	 * handlers for the buttons, lists, fields etc are registered with the
	 * appropriate component. JFrame is fixed size and has no layout manager.
	 * 
	 * @param name text that displays at the top of the window
	 */
	public FilmDatabaseController(String name) {
		super(name);
		this.model = new FilmList();
		this.ioManager = new FilmDatabaseIoManager();
		this.ioManager.loadListCsv(this.model);
		this.view = new FilmDatabaseView(this.model);

		// event listeners for view
		this.view.getLoadWindow().getButton1().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleLoadFileButton();
			}
		});

		this.view.getLoadWindow().getButton2().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleLoadDbButton();
			}
		});

		this.view.getSaveWindow().getButton1().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleSaveToFileButton();
			}
		});

		this.view.getSaveWindow().getButton2().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleSaveToDbButton();
			}
		});


		this.view.getFilmTitleTextField().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				eventHandleKeyReleased(e);
			}
		});

		this.view.getClearAllButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleClearAllButton();
			}
		});

		this.view.getLoadButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleLoadButton();
			}
		});

		this.view.getSaveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleSaveButton();
			}
		});

		this.view.getRemoveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleRemoveButton();
			}
		});

		this.view.getAddButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleAddButton();
			}
		});

		this.view.getClearButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleClearButton();
			}
		});

		this.view.getSearchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventHandleSearchButton();
			}
		});

		this.view.getList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				eventHandleListSelection();
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				eventHandleWindowOpened();
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				eventHandleWindowClosing();
			}
		});

		// Choose to lay out components manually
		this.getContentPane().setLayout(null);

		// add the view to this content pane
		this.getContentPane().add(this.view);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(720, 480);
		setResizable(false);
	}

	public static void main(String[] args) {
		JFrame frame = new FilmDatabaseController("Film Database Application");
		frame.setVisible(true);
	}
}