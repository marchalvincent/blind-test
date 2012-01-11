package org.commons.entity;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Une facade pour toutes les opérations un peu complexe sur les {@link RenderedImage}
 * @author pitton
 *
 */
public final class BanqueFacade {
	
	static private final BanqueFacade INSTANCE = new BanqueFacade();
	
	static public final BanqueFacade instance() {
		return INSTANCE;
	}
	
	final public String getFormat(final String parFile) {
		final int locIndexOf = parFile.lastIndexOf(".");
		return parFile.substring(locIndexOf + 1, parFile.length());
	}
	
	final public String getFileName(final String parPath) {
		final int locIndexOf = parPath.lastIndexOf(File.separator);
		if(locIndexOf == -1) return parPath;
		
		return parPath.substring(locIndexOf + 1, parPath.length());
	}
	
	final public BanqueFacade writeImage(final String parFile, final RenderedImage parImage) throws IOException {
		ImageIO.write(parImage, getFormat(parFile), new File(parFile));
		return this;
	}
	
	final public RenderedImage readImage(final String parFile) throws IOException {
		return ImageIO.read(new File(parFile));
	}
	
	final public byte[] convertImage(final String parFormat, final RenderedImage parImage) throws IOException {
		final ByteArrayOutputStream locStream = new ByteArrayOutputStream();
		ImageIO.write(parImage, parFormat, locStream);
		return locStream.toByteArray();
	}
	
	final public RenderedImage convertByte(final byte[] parByte) throws IOException {
		final ByteArrayInputStream locArrayStream = new ByteArrayInputStream(parByte);
		return ImageIO.read(locArrayStream);
	}
	
	private BanqueFacade() {
		super();
	}
}
