/**
 * This class contains the components for the view of the GUI.
 * 
 * @author KBrogan
 */
package FilmDatabase;

import javax.swing.*;

//@SuppressWarnings("serial")
public class FilmDatabaseView extends JPanel {
	// model
	private FilmList model;
	// list
	private JList<Film> list;

	// text fields
	private JTextField filmTitleInput;
	private JTextField filmDirectorInput;
	private JTextField filmYearInput;
	private JTextField filmGenreInput;
	private JTextField filmRatingInput;
	private JTextField searchInput;

	// buttons
	private JButton clearAllButton;
	private JButton addButton;
	private JButton removeButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton searchButton;
	private JButton clearButton;

	// selection windows
	private SelectionWindow loadWindow;
	private SelectionWindow saveWindow;


	// get/set methods
	public SelectionWindow getLoadWindow() {
		return this.loadWindow;
	}

	public SelectionWindow getSaveWindow() {
		return this.saveWindow;
	}

	public JList<Film> getList() {
		return this.list;
	}

	public void setList(JList<Film> list) {
		this.list = list;
	}

	public JTextField getFilmTitleTextField() {
		return this.filmTitleInput;
	}

	public JTextField getFilmDirectorTextField() {
		return this.filmDirectorInput;
	}

	public JTextField getFilmGenreTextField() {
		return this.filmGenreInput;
	}

	public JTextField getFilmYearTextField() {
		return this.filmYearInput;
	}

	public JTextField getFilmRatingTextField() {
		return this.filmRatingInput;
	}

	public JTextField getSearchTextField() {
		return this.searchInput;
	}

	public JButton getClearAllButton() {
		return this.clearAllButton;
	}

	public JButton getSaveButton() {
		return this.saveButton;
	}

	public JButton getLoadButton() {
		return this.loadButton;
	}

	public JButton getRemoveButton() {
		return this.removeButton;
	}

	public JButton getAddButton() {
		return this.addButton;
	}

	public JButton getSearchButton() {
		return this.searchButton;
	}

	public JButton getClearButton() {
		return this.clearButton;
	}

	/**
	 * The constructor initialises the view and it's components to add to the pane.
	 * All the components are fixed size and have no layout manager.
	 * 
	 * @param model film list to store in the database
	 */
	public FilmDatabaseView(FilmList model) {
		this.model = model;
		setLayout(null);
		this.list = new JList<Film>(this.model.getFilmList());
		this.list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setLocation(175, 10);
		scrollPane.setSize(520, 420);
		this.add(scrollPane);
		
		this.loadWindow = new SelectionWindow("Load film list from...", ".csv", "Database");
		this.saveWindow = new SelectionWindow("Save film list to...", ".csv", "Database");

		// add Enter Title text field
		this.filmTitleInput = new JTextField("Enter Title*");
		this.filmTitleInput.setLocation(10, 10);
		this.filmTitleInput.setSize(150, 25);
		this.add(filmTitleInput);
		// add Enter Director(s) text field
		this.filmDirectorInput = new JTextField("Enter Director(s)");
		this.filmDirectorInput.setLocation(10, 40);
		this.filmDirectorInput.setSize(150, 25);
		this.add(filmDirectorInput);
		// add Enter Year text field
		this.filmYearInput = new JTextField("Enter Year");
		this.filmYearInput.setLocation(10, 70);
		this.filmYearInput.setSize(150, 25);
		this.add(filmYearInput);
		// add Enter Genre text field
		this.filmGenreInput = new JTextField("Enter Genre");
		this.filmGenreInput.setLocation(10, 100);
		this.filmGenreInput.setSize(150, 25);
		this.add(filmGenreInput);
		// add Enter Rating text field
		this.filmRatingInput = new JTextField("Enter Rating");
		this.filmRatingInput.setLocation(10, 130);
		this.filmRatingInput.setSize(150, 25);
		this.add(filmRatingInput);
		// add Enter Search text field
		this.searchInput = new JTextField("Enter Search Term");
		this.searchInput.setLocation(10, 340);
		this.searchInput.setSize(150, 25);
		this.add(searchInput);

		// Add the Clear All
		this.clearAllButton = new JButton("Clear All");
		clearAllButton.setLocation(10, 160);
		clearAllButton.setSize(150, 25);
		this.add(clearAllButton);
		// Add the ADD button
		this.addButton = new JButton("Add");
		addButton.setLocation(10, 190);
		addButton.setSize(150, 25);
		this.add(addButton);
		// Add the REMOVE button
		removeButton = new JButton("Remove");
		removeButton.setLocation(10, 220);
		removeButton.setSize(150, 25);
		this.add(removeButton);
		// Add the Save button
		this.saveButton = new JButton("Save List");
		saveButton.setLocation(10, 250);
		saveButton.setSize(150, 25);
		this.add(saveButton);
		// Add the Load button
		this.loadButton = new JButton("Load List");
		loadButton.setLocation(10, 280);
		loadButton.setSize(150, 25);
		this.add(loadButton);
		// Add the Search button
		this.searchButton = new JButton("Search");
		searchButton.setLocation(10, 370);
		searchButton.setSize(150, 25);
		this.add(searchButton);
		// Add the Clear button
		this.clearButton = new JButton("Clear");
		clearButton.setLocation(10, 400);
		clearButton.setSize(150, 25);
		this.add(clearButton);

		setSize(720, 480);
		this.update();
	}

	/**
	 * Updates the view by setting the list to the current model. The status of the
	 * ADD and REMOVE buttons are also updated depending on the state of the title
	 * text field, and if the user has selected a film in the list or not.
	 */
	public void update() {
		this.list.setListData(this.model.getFilmList());
		this.addButton.setEnabled(!this.filmTitleInput.getText().isEmpty());
		this.removeButton.setEnabled(this.list.getSelectedIndex() != -1);
	}
}