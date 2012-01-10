package org.commons.util;

import java.util.Comparator;

/**
 * Un {@link Comparator} pour tous les objets dérivant de {@link IWithName}
 * @author pitton
 *
 */
public final class WithNameComparator implements Comparator<IWithName> {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int compare(final IWithName parName1, final IWithName parName2) {
		if(parName1 == null) {
			return (parName2 == null) ? 0 : -1;
		}
		if(parName2 == null) return 1;
		return StringUtil.compareTo(parName1.getConstName(), parName2.getConstName());
	}

	public WithNameComparator() {}
}
