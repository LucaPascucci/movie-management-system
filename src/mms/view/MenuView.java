package mms.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import mms.controller.MenuController;

/**
 * This class is used to create the menu of customer, admin or society. The functions depend on the type of user.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class MenuView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant NORTH_BORDER. */
	private static final int NORTH_BORDER = 20;

	/** The Constant WEST_BORDER. */
	private static final int WEST_BORDER = 55;

	/** The Constant EAST_BORDER. */
	private static final int EAST_BORDER = 55;

	/** The first button. */
	private final JButton firstButton = new JButton("");

	/** The second button. */
	private final JButton secondButton = new JButton("");

	/** The third button. */
	private final JButton thirdButton = new JButton("");

	/** The observer. */
	private MenuController observer;

	/**
	 * This constructor creates a new MenuView.
	 */
	public MenuView() {

		super();

		this.setSize(300, 190); //frame size: 300 width, 190 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.firstButton);
		layout.putConstraint(SpringLayout.WEST, this.firstButton, WEST_BORDER, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.firstButton, NORTH_BORDER, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.firstButton, -EAST_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.secondButton);
		layout.putConstraint(SpringLayout.WEST, this.secondButton, WEST_BORDER, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.secondButton, NORTH_BORDER, SpringLayout.SOUTH, this.firstButton);
		layout.putConstraint(SpringLayout.EAST, this.secondButton, -EAST_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.thirdButton);
		layout.putConstraint(SpringLayout.WEST, this.thirdButton, WEST_BORDER, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.thirdButton, NORTH_BORDER, SpringLayout.SOUTH, this.secondButton);
		layout.putConstraint(SpringLayout.EAST, this.thirdButton, -EAST_BORDER, SpringLayout.EAST, this.getContentPane());

		this.firstButton.addActionListener(this);
		this.secondButton.addActionListener(this);
		this.thirdButton.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				if (observer.getAdminControll()) {
					setTitle("Menu Admin");
					firstButton.setText("Management films");
					secondButton.setText("Management customers");
					thirdButton.setText("Statistics");

					firstButton.setIcon(new ImageIcon(this.getClass().getResource("/Film.png")));
					secondButton.setIcon(new ImageIcon(this.getClass().getResource("/Customer.png")));
					thirdButton.setIcon(new ImageIcon(this.getClass().getResource("/Stats.png")));

				} else if (observer.getUserControll()) {
					setTitle("Menu Customer");
					firstButton.setText("Film list");
					secondButton.setText("Favorites films");
					thirdButton.setText("Statistics");

					firstButton.setIcon(new ImageIcon(this.getClass().getResource("/Film.png")));
					secondButton.setIcon(new ImageIcon(this.getClass().getResource("/Favorite.png")));
					thirdButton.setIcon(new ImageIcon(this.getClass().getResource("/Stats.png")));
				} else {
					setTitle("Menu Society");
					firstButton.setText("Management society");
					secondButton.setText("Management films");
					thirdButton.setText("Management shops");

					firstButton.setIcon(new ImageIcon(this.getClass().getResource("/Society.png")));
					secondButton.setIcon(new ImageIcon(this.getClass().getResource("/Film.png")));
					thirdButton.setIcon(new ImageIcon(this.getClass().getResource("/Shop.png")));
				}
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.backToHome();
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.firstButton)) {
			this.observer.doFirst();
		}
		if (selection.equals(this.secondButton)) {
			this.observer.doSecond();
		}
		if (selection.equals(this.thirdButton)) {
			this.observer.doThird();
		}	
	}

	@Override
	public void mouseReleased(final MouseEvent e) {	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param menuController controller for this view
	 */
	public void attachObserver(final MenuController menuController) {
		this.observer = menuController;

	}

}
