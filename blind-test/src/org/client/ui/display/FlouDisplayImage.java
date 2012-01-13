package org.client.ui.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.DisplayConfigurationType;

public class FlouDisplayImage implements DisplayImage {
	
	final private Configuration _configuration;
	
	protected FlouDisplayImage () {
		super();
		_configuration = ConfigurationManager.getConfiguration();
	}
	
	@Override
	public void displayImage(Graphics parGraphics, BufferedImage parImage, int parWidth, int parHeight, int parCurrentRepeat) {
		final Graphics2D par2DGraphics = (Graphics2D) parGraphics;
		int locRepeat = getRepeat();
		int locCurrentRepeat = parCurrentRepeat;
		if (locCurrentRepeat != locRepeat) {
			par2DGraphics.drawImage(fastGaussianBlur(parImage, (locRepeat - locCurrentRepeat) * 5), 0, 0, parWidth, parHeight, null);
		}
		else {
			par2DGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
		}
	}

	@Override
	public int getRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.FLOU);
		return (locType == null) ? 1 : locType.getRepeat().intValue();
	}

	@Override
	public long getTimeRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.FLOU);
		return (locType == null) ? 0L : locType.getTime().longValue();
	}
	
	public float[] gaussianKernel1D(double sigma2) {
		int radius = (int)Math.round(2*Math.sqrt(sigma2));
		// compute gaussian values
		float[] data = new float[1+2*radius];
		for(int r=-radius;r<=radius;r++)
			data[r+radius] = (float)Math.exp(-(r*r)/(2.0*sigma2));
		// normalize
		float sum=0;
		for(int i=0;i<data.length;i++) sum+=data[i];
		for(int i=0;i<data.length;i++) data[i]/=sum;
		return data;
	}
	 
	public void convolve2DSeparate(BufferedImage input, float[] kernel) {
		float r,g,b;
	 
		int width = input.getWidth(), height=input.getHeight();
		WritableRaster raster = input.getRaster();
		int kernelradius = kernel.length/2;
	 
		// horizontal
		int[][] wbuffer = new int[width][3];
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				wbuffer[x][0]=raster.getSample(x,y,0);
				wbuffer[x][1]=raster.getSample(x,y,1);
				wbuffer[x][2]=raster.getSample(x,y,2);
			}
			for(int x=0;x<width;x++) {
				r=g=b=0;
				for(int k=-kernelradius;k<=kernelradius;k++)
					if (x+k>=0 && x+k<width) {
						r+=kernel[kernelradius+k]*wbuffer[x+k][0];
						g+=kernel[kernelradius+k]*wbuffer[x+k][1];
						b+=kernel[kernelradius+k]*wbuffer[x+k][2];
					}
				raster.setSample(x, y, 0, (int)r);
				raster.setSample(x, y, 1, (int)g);
				raster.setSample(x, y, 2, (int)b);
			}
		}
	 
	 
		// vertical
		int[][] hbuffer = new int[height][3];
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				hbuffer[y][0] = raster.getSample(x,y,0);
				hbuffer[y][1] = raster.getSample(x,y,1);
				hbuffer[y][2] = raster.getSample(x,y,2);
			}
	 
			for(int y=0;y<height;y++) {
				r=g=b=0;
				for(int k=-kernelradius;k<=kernelradius;k++)
					if (y+k>=0 && y+k<height) {
						r+=kernel[kernelradius+k]*hbuffer[y+k][0];
						g+=kernel[kernelradius+k]*hbuffer[y+k][1];
						b+=kernel[kernelradius+k]*hbuffer[y+k][2];
					}
				raster.setSample(x, y, 0, (int)r);
				raster.setSample(x, y, 1, (int)g);
				raster.setSample(x, y, 2, (int)b);
			}
		}
	}
	 
	public BufferedImage fastGaussianBlur(BufferedImage input, double sigma2) {
		int factor=1;              // reduction factor
		double ds_sigma2=sigma2;   // downscale gaussian sigma2
		double us_sigma2=1.0;      // final upscale gaussian sigma2
	 
		// find best reduction factor and sigma2
		int scale=0;
		while(us_sigma2<ds_sigma2) {  
			// exit when downscale/upscale sigma2 are equivalents
	 
			// compute sigma2 for next scale
			scale++;
			double next_us_sigma2 = Math.pow(factor, 2);
			double next_ds_sigma2 = (sigma2 - next_us_sigma2) / Math.pow(4, scale); 
			if (next_ds_sigma2<=0) break;
	 
			factor*=2;
			ds_sigma2 = next_ds_sigma2;
			us_sigma2 = next_us_sigma2;
		}
	 
		// downscale (fast)
		int width = input.getWidth(), height=input.getHeight();
		BufferedImage dwnscaled = new BufferedImage(width/factor, height/factor, BufferedImage.TYPE_INT_RGB);
		dwnscaled.createGraphics().drawImage(
				input.getScaledInstance(width/factor, height/factor, BufferedImage.SCALE_AREA_AVERAGING)
				, 0, 0, null);
	 
		// convolve with gaussian 
		convolve2DSeparate(dwnscaled,gaussianKernel1D(ds_sigma2));
	 
		// upscale (fast)
		BufferedImage upscaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		upscaled.createGraphics().drawImage(
				dwnscaled.getScaledInstance(width, height, BufferedImage.SCALE_FAST)
				, 0, 0, null);
	 
		// convolve with gaussian
		convolve2DSeparate(upscaled,gaussianKernel1D(us_sigma2));
	 
		return upscaled;
	}

}
