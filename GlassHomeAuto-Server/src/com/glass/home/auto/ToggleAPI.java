/**
 * 
 */
package com.glass.home.auto;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author Roland
 *
 */
@Api(name = "toggle", version = "v1")
public class ToggleAPI {

	private static States states;//states of each toggle-able item
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static Entity dataStates;
	private static Key key = KeyFactory.createKey("toggler", 0);//database key
	
	@ApiMethod(name = "toggle.get", path = "toggle_get", httpMethod = HttpMethod.GET)
	public States getStates()	{
		if(states == null)	{
			try {
				dataStates = datastore.get(key);
			} catch (EntityNotFoundException e) {
				System.out.println("FAIL TO GET KEY");
				e.printStackTrace();
			}
			states = new States();
			states.setLights((Boolean) dataStates.getProperty("lights"));
			states.setAc((Boolean) dataStates.getProperty("ac"));
			states.setMotion((Long) dataStates.getProperty("motion"));
		}
		return states;
	}
	
	@ApiMethod(name="toggle.put", path = "toggle_put", httpMethod = HttpMethod.PUT)
	public States putStates(States s)	{
		if(s.getMotion() == 1){//arduino sets motion to 1 when motion detected
			s.setMotion(System.currentTimeMillis());
		}
		states = s;
		dataStates = new Entity("Toggle States", key);
		dataStates.setProperty("lights", states.isLights());
		dataStates.setProperty("ac", states.isAc());
		dataStates.setProperty("motion", states.getMotion());
		datastore.put(dataStates);
		
		return states;
	}
	
}
