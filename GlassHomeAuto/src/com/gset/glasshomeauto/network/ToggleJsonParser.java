package com.gset.glasshomeauto.network;

import org.json.JSONObject;


public class ToggleJsonParser {

	/**
	 * The fields that are expected in the JSON response
	 */
	private static class JSONFields {
		public static final String LIGHTS = "lights";
		public static final String A_C = "a_c";
		public static final String Motion = "motion";
	}

	private boolean lights;
	private boolean a_c;
	private long motion;

	public ToggleJsonParser(String json) {

		try {
			JSONObject obj = new JSONObject(json);

			lights = obj.getBoolean(JSONFields.LIGHTS);
			a_c = obj.getBoolean(JSONFields.A_C);
			motion = obj.getLong(JSONFields.Motion);
			

			/*// If there are no notes, then it won't even be in the JSON
			if(obj.has(JSONFields.NOTES)){
				mNotes = obj.getString(JSONFields.NOTES);
			} else {
				mNotes = "";
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getLights(){
		return lights;
	}

	public boolean getA_C(){
		return a_c;
	}

	public long getMotion(){
		return motion;
	}

}
