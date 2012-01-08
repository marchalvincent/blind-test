package org.server.monitor;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.server.concurrent.BlindTestExecutor;

public final class MonitorRunnable {

	final private MonitorReadWriter _monitor;
	final private Thread _thread;
	final private BlindTestExecutor _pool;

	public MonitorRunnable() {
		_monitor = MonitorReadWriter.instance();
		_thread = init(_monitor);
		_pool = new BlindTestExecutor(4, 1200, TimeUnit.SECONDS);
	}

	public final void start() {
		if (_thread.isAlive()) {
			final InfoProvider locFileProvider = InfoProviderManager
					.getFileProvider();
			final String locFormat = String.format("Le thread "
					+ _thread.getName()
					+ " est déjà lancé. On ne peut le relancer.");
			final RuntimeException locException = new RuntimeException(
					locFormat);
			locFileProvider
					.appendMessage(Level.SEVERE, locFormat, locException);
			throw locException;
		}
		_thread.start();
	}

	private final Thread init(final MonitorReadWriter parMonitorReader) {
		return new Thread(new Runnable() {

			@Override
			public final void run() {
				while (true) {
					// On catch l'exception car si on kill le thread,
					// on coupera le moniteur alors qu'il sera en train de lire.
					try {
						final String locValue = _monitor.readLine();
						final String[] locSplitValue = locValue.split(" ");
						final String locCommandName = locSplitValue[0];
						final EnumMonitorCommand locCommand = EnumMonitorCommand.getCommand(locCommandName);
						if (locCommand == null) {
							showCommand(locCommandName);
							continue;
						}
						final boolean locHasArgument = locCommand
								.hasArguments();
						if (locCommand == null || (locHasArgument && locHasArgument == (locSplitValue.length < 1))) {
							showCommand(locCommandName);
							continue;
						}
						invoke(locCommandName, locSplitValue, locCommand);
					} catch (NoSuchElementException locException) {
					}
				}
			}
		}, "Console Monitor Reader");
	}

	private final void showCommand(final String parCommandName) {
		final String locMessage = String
				.format("La commande \"%s\" est invalide. La liste des commandes disponible est : %s",
						parCommandName, EnumMonitorCommand.getCommands()
								.toString());
		_monitor.println(locMessage);
	}

	private final void invoke(final String locCommandName,
			final String[] parArguments, final EnumMonitorCommand parEnumCommand) {
		final boolean locHasArgument = parEnumCommand.hasArguments();
		Future<String> locResultat = null;
		if (locHasArgument == false) {
			final MonitorCommand locCommand = parEnumCommand.createCommand(
					locCommandName, null);
			locResultat = _pool.submit(locCommand);
		} else {
			final String[] locArguments = Arrays.copyOfRange(parArguments, 1,
					parArguments.length);
			final MonitorCommand locCommand = parEnumCommand.createCommand(
					locCommandName, locArguments);
			locResultat = _pool.submit(locCommand);
		}
		assert (locResultat != null);
		try {
			_monitor.println(locResultat.get());
		} catch (Exception e) {
		}
	}
}
