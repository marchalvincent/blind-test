package org.commons.util;

import java.util.Comparator;

/**
 * Un {@link Comparator} pour tous les objets dérivant de {@link IWithId}
 * @author pitton
 *
 */
public final class WithIdComparator implements Comparator<IWithId> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int compare(final IWithId parId1, final IWithId parId2) {
		if(parId1 == null) return (parId2 == null) ? 0 : -1;
		if(parId2 == null) return 1;
		
		return parId1.getId() - parId2.getId();
	}

	public WithIdComparator() {}
	
}
