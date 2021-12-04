package mms.mediaworker;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * This class just displays a 2d graphic on a Swing window. 
 * Of note though, is this class has NO XUGGLER dependencies.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 * 
 */
public class VideoFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The m onscreen picture. */
	private final ImageComponent mOnscreenPicture;

	/** The icon. */
	private final ImageIcon icon = new ImageIcon(this.getClass().getResource("/View_Icon.png"));

	/** The is mac. */
	private boolean isMac;
	/**
	 * Constructs a new VideoFrame given the film title.
	 * @param filmTitle Used to set title of JFrame
	 */
	public VideoFrame(final String filmTitle) {
		super();
		//controll to change the size of the JFrame when will display images
		if (!System.getProperty("os.name").startsWith("Mac")) {
			this.setIconImage(this.icon.getImage());
			this.isMac = false;
		}
		this.mOnscreenPicture = new ImageComponent();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().add(mOnscreenPicture);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle(filmTitle);

		this.pack();
		this.addWindowListener(new WindowAdapter() {

			//used for interrupt the Thread 
			@Override
			public void windowClosing(final WindowEvent e) {
				dispose();
				Thread.currentThread().interrupt();
			}
		});

	}

	/**
	 * start the displaying of the new image.
	 * @param newImage new BufferedImage
	 */
	public void setImage(final BufferedImage newImage) {
		this.mOnscreenPicture.setImage(newImage);
	}

	/**
	 * Private class used to update the image in the JFrame.
	 *
	 * @author Luca Pascucci
	 */
	private class ImageComponent extends JComponent {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The m image. */
		private Image mImage;

		/** The m size. */
		private final Dimension mSize;

		/**
		 * Instantiates a new image component.
		 */
		public ImageComponent() {
			super();
			this.mSize = new Dimension(0, 0);
			this.setSize(mSize);
		}

		//used invokeLater to synchronize the updating
		/**
		 * Sets the image.
		 *
		 * @param image the new image
		 */
		public void setImage(final Image image) {
			SwingUtilities.invokeLater(new ImageRunnable(image));
		}

		/**
		 * Private class that implements Runnable for synchronize the update.
		 *
		 * @author Luca Pascucci
		 */
		private class ImageRunnable implements Runnable {

			/** The more width. */
			private final Integer moreWidth = 10;

			/** The more height macosx. */
			private final Integer moreHeightMACOSX = 32;

			/** The more height windows. */
			private final Integer moreHeightWINDOWS = 34;

			/** The new image. */
			private final Image newImage;

			/**
			 * Instantiates a new image runnable.
			 *
			 * @param nextImage the next image
			 */
			public ImageRunnable(final Image nextImage) {
				super();
				this.newImage = nextImage;
			}

			public void run() {
				ImageComponent.this.mImage = newImage;
				if (!VideoFrame.this.isMac) {
					//Mac OS X need more space in the JFrame to display the image
					VideoFrame.this.setSize(mImage.getWidth(null) + moreWidth,
							mImage.getHeight(null) + moreHeightMACOSX);
				} else {
					//Windows need more space in the JFrame to display the image
					VideoFrame.this.setSize(mImage.getWidth(null) + moreWidth,
							mImage.getHeight(null) + moreHeightWINDOWS);
				}
				repaint();
			}
		}

		@Override
		public synchronized void paint(final Graphics g) {
			if (mImage != null) {
				g.drawImage(mImage, 0, 0, this);
			}
		}
	}
}
