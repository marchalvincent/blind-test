package org.commons.util;

import static org.junit.Assert.assertEquals;

import java.awt.image.RenderedImage;
import java.io.IOException;

import org.commons.entity.BanqueFacade;
import org.junit.Test;

public class BanqueFacadeTest {

	static private final String IMAGE = "image/al_pacino.jpeg";
	
	@Test
	public final void testGetFileName() {
		final String locFirst = "salut/test/next.png";
		final BanqueFacade locFacade = BanqueFacade.instance();
		assertEquals("next.png", locFacade.getFileName(locFirst));
		assertEquals("al_pacino.jpeg", locFacade.getFileName(IMAGE));
	}
	
	@Test
	public final void testGetFormat() {
		final String locJPG = "fr.jpg";
		final String locPNG = "fr.png";
		final String locJPEG = "test.file/myfile.JPEG";
		final String locGIF = "mygif.gif";
		assertEquals("jpg", BanqueFacade.instance().getFormat(locJPG));
		assertEquals("png", BanqueFacade.instance().getFormat(locPNG));
		assertEquals("JPEG", BanqueFacade.instance().getFormat(locJPEG));
		assertEquals("gif", BanqueFacade.instance().getFormat(locGIF));
		assertEquals("jpeg", BanqueFacade.instance().getFormat(IMAGE));
	}

	@Test
	public final void testReadWrite() throws IOException {
		final BanqueFacade locInstance = BanqueFacade.instance();
		final RenderedImage locImage = locInstance.readImage(IMAGE);
		locInstance.writeImage(IMAGE, locImage);
		final RenderedImage locSecondImage = locInstance.readImage(IMAGE);
		assertEquals(locImage.getColorModel(), locSecondImage.getColorModel());
		assertEquals(locImage.getWidth(), locSecondImage.getWidth());
		assertEquals(locImage.getHeight(), locSecondImage.getHeight());
		assertEquals(locImage.getSources(), locSecondImage.getSources());
	}

	@Test
	public final void testConvert() throws IOException {
		final BanqueFacade locFacade = BanqueFacade.instance();
		final RenderedImage locImage = locFacade.readImage(IMAGE);
		final byte[] locNewByte = locFacade.convertImage(locFacade.getFormat(IMAGE), locImage);
		final RenderedImage locSecondImage = locFacade.convertByte(locNewByte);
		assertEquals(locImage.getColorModel(), locSecondImage.getColorModel());
		assertEquals(locImage.getWidth(), locSecondImage.getWidth());
		assertEquals(locImage.getHeight(), locSecondImage.getHeight());
		assertEquals(locImage.getSources(), locSecondImage.getSources());
	}
}
