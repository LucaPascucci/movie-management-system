package mms.mediaworker;

import java.io.File;

import mms.controller.admin.AdminFilmController;
import mms.exception.NoVideoIconException;
import mms.exception.UnsupportedCodecException;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

/**
 * Class used to resize a video file.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class VideoResizer {

	//default settings of width and height
	/** The Constant DEFAULT_WIDTH. */
	private static final Integer DEFAULT_WIDTH = 800;

	/** The Constant DEFAULT_HEIGHT. */
	private static final Integer DEFAULT_HEIGHT = 480;

	/** The temporary file. */
	private final String temporaryFile;

	/** The media reader. */
	private final IMediaReader mediaReader;

	//Need the controller to send a notification when resizing will finish
	/** The controller. */
	private final AdminFilmController controller;

	/**
	 * Constructs a new VideoResizer given some inputs and a {@link mms.controller.admin.AdminFilmController}.
	 * @param urlOriginal	Path of the original file video
	 * @param temporary		Path of the temporary file video that will be created
	 * @param realController	Controller that invoke this Class
	 */
	public VideoResizer(final String urlOriginal, final String temporary , final AdminFilmController realController) {

		this.controller = realController;
		final String path = urlOriginal;
		this.temporaryFile = temporary;

		// create custom listeners
		final VideoListener videoListener = new VideoListener(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		final Resizer resizer = new Resizer(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// create a media reader
		this.mediaReader = ToolFactory.makeReader(path);

		this.mediaReader.addListener(resizer);

		final IMediaWriter mediaWriter = ToolFactory.makeWriter(this.temporaryFile, this.mediaReader);

		resizer.addListener(mediaWriter);

		mediaWriter.addListener(videoListener);

	}

	/**
	 * Private Class used to create IVideoResampler that will make the resize.
	 *
	 * @author Luca Pascucci
	 */
	private static class Resizer extends MediaToolAdapter {

		/** The width. */
		private final Integer width;

		/** The height. */
		private final Integer height;

		/** The video resampler. */
		private IVideoResampler videoResampler = null;

		/**
		 * Constructs a new Resizer whit width and height.
		 *
		 * @param newWidth the new width
		 * @param newHeight the new height
		 */
		public Resizer(final Integer newWidth, final Integer newHeight) {
			super();
			this.width = newWidth;
			this.height = newHeight;
		}

		@Override
		public void onVideoPicture(final IVideoPictureEvent event) {
			final IVideoPicture pic = event.getPicture();
			if (this.videoResampler == null) {
				this.videoResampler = IVideoResampler.make(this.width, this.height, pic.getPixelType(), pic.getWidth(), pic.getHeight(), pic.getPixelType());
			}
			final IVideoPicture out = IVideoPicture.make(pic.getPixelType(), this.width, this.height);
			this.videoResampler.resample(out, pic);

			final IVideoPictureEvent asc = new VideoPictureEvent(event.getSource(),
					out, event.getStreamIndex());

			super.onVideoPicture(asc);
			out.delete();
		}
	}

	/**
	 * Private class that control writing of frame video to file video.
	 *
	 * @author Luca Pascucci
	 */
	private static class VideoListener extends MediaToolAdapter {

		/** The width. */
		private final Integer width;

		/** The height. */
		private final Integer height;

		/**
		 * Constructs a new VideoListener whit width and height .
		 *
		 * @param newWidth the new width
		 * @param newHeight the new height
		 */
		public VideoListener(final Integer newWidth, final Integer newHeight) {
			super();
			this.width = newWidth;
			this.height = newHeight;
		}

		@Override
		public void onAddStream(final IAddStreamEvent event) {
			final int streamIndex = event.getStreamIndex();
			final IStreamCoder streamCoder = event.getSource().getContainer().getStream(streamIndex).getStreamCoder();
			if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				streamCoder.setWidth(this.width);
				streamCoder.setHeight(this.height);
			}
			super.onAddStream(event);
		}

	}

	/**
	 * This method start the encoding of the original file video
	 * and finished the first resizing it will call a {@link mms.mediaworker.VideoIcon}.
	 * @param finalPath	Path of the file video that will be use to display to users
	 * @param codeFilm	used to know which film has been modified
	 * @param codeShop codeShop
	 * @throws UnsupportedCodecException caused when file video use codec not supported by Xuggler
	 * @throws NoVideoIconException caused when the icon file is unreachable
	 */
	public void startEncoding(final String finalPath, final Integer codeFilm, final Integer codeShop) throws UnsupportedCodecException, NoVideoIconException {
		try {
			while (this.mediaReader.readPacket() == null) {
				// continue coding
			}
			//start a new encoding video to adding an icon 
			final VideoIcon videoIcon = new VideoIcon(this.temporaryFile, finalPath);
			videoIcon.startEncoding();
			this.controller.returnEncoding(true, codeFilm, codeShop);

			//when will catch exceptions temporary file video
		} catch (RuntimeException exc) {

			final File deleteTmp = new File(this.temporaryFile);
			deleteTmp.delete();
			this.controller.returnEncoding(false, codeFilm, codeShop);
			throw new UnsupportedCodecException();

		} catch (NoVideoIconException exc) {

			final File deleteTmp = new File(this.temporaryFile);
			deleteTmp.delete();
			this.controller.returnEncoding(false, codeFilm, codeShop);
			throw new NoVideoIconException();
		}

	}
}
