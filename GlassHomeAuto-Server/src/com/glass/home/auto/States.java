/**
 * 
 */
package com.glass.home.auto;

/**
 * @author Roland
 * Holds the states of toggle-able items
 */
public class States {
	private boolean lights;//true if lights are on
	private boolean a_c;//true if AC is on
	private long motion;//records time motion last detected
	
	public States()	{
		lights = false;
		a_c = false;
		motion = 0;
	}

	/**
	 * @return whether lights are on
	 */
	public boolean isLights() {
		return lights;
	}

	/**
	 * @param lights the lights to set
	 */
	public void setLights(boolean lights) {
		this.lights = lights;
	}

	/**
	 * @return whether AC is on
	 */
	public boolean isAc() {
		return a_c;
	}

	/**
	 * @param a_c the a_c to set
	 */
	public void setAc(boolean a_c) {
		this.a_c = a_c;
	}

	/**
	 * @return the motion
	 */
	public long getMotion() {
		return motion;
	}

	/**
	 * @param motion the motion to set
	 */
	public void setMotion(long motion) {
		this.motion = motion;
	}
}
