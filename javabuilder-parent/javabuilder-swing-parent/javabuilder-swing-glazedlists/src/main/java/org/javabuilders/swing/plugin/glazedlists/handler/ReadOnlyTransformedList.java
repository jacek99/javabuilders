package org.javabuilders.swing.plugin.glazedlists.handler;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.TransformedList;
import ca.odell.glazedlists.event.ListEvent;

/**
 * GlazedLists TransformedList prepared for easy compilations via CompilerUtils
 * @author Jacek Furmankiewicz
 *
 * @param <S>
 * @param <E>
 */
public abstract class ReadOnlyTransformedList<S,E> extends TransformedList<S, E> {

	protected ReadOnlyTransformedList(EventList<S> source) {
		super(source);
		source.addListEventListener(this);
	}

	@Override
	protected boolean isWritable() {
		return false;
	}

	@Override
	public void listChanged(ListEvent<S> listChanges) {
		updates.forwardEvent(listChanges);
	}

}
