package FilmDatabase;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SelectionWindow extends JFrame {
    private JButton button1;
	private JButton button2;

    public SelectionWindow(String title, String choice1, String choice2) {
        setLayout(null);
        setTitle(title);
        setSize(300, 90);
        setResizable(false);
        button1 = new JButton(choice1);
        button1.setLocation(15, 10);
        button1.setSize(120, 30);
        button2 = new JButton(choice2);
        button2.setLocation(150, 10);
        button2.setSize(120, 30);
        add(button1);
        add(button2);
    }

    public JButton getButton1() {
        return this.button1;
    }

    public JButton getButton2() {
        return this.button2;
    }
}