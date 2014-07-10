/**
 * 
 */
package com.gset.glasshomeauto.network;

/**
 * @author Roland
 *
 */
public class States {
	private static boolean lights;
	private static boolean a_c;
	private static long motion;
	/**
	 * @return the lights
	 */
	public static boolean isLights() {
		return lights;
	}
	/**
	 * @param lights the lights to set
	 */
	public static void setLights(boolean lights) {
		States.lights = lights;
	}
	/**
	 * Switches state of lights
	 */
	public static void switchLights() {
		States.lights = !States.lights;
	}
	/**
	 * @return the a_c
	 */
	public static boolean isA_c() {
		return a_c;
	}
	/**
	 * @param a_c the a_c to set
	 */
	public static void setA_c(boolean a_c) {
		States.a_c = a_c;
	}
	/**
	 * Switches state of AC
	 */
	public static void switchA_c() {
		States.a_c = !States.a_c;
	}
	/**
	 * @return the motion
	 */
	public static long getMotion() {
		return motion;
	}
	/**
	 * @param motion the motion to set
	 */
	public static void setMotion(long motion) {
		States.motion = motion;
	}
	
	
}
