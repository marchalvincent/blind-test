package org.commons.downloader;

import java.util.concurrent.Callable;

public interface Downloader extends Callable<Boolean> {

	Boolean download();
	
}
