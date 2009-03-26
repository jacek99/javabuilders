package org.audiolord.audio;

import java.util.Set;

public interface IAudioEngine {

	/**
	 * @return List of supported formats
	 */
	public Set<IAudioFormat> getSupportedFormats();
	
	/**
	 * @return Engine name
	 */
	public String getName();
	
	/**
	 * @return Engine description
	 */
	public String getDescription();
	
}
