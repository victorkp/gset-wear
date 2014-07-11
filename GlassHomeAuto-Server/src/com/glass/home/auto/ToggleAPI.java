/**
 * 
 */
package com.glass.home.auto;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

/**
 * @author Roland
 *
 */
@Api(name = "toggle", version = "v1")
public class ToggleAPI {

	private static States states;//states of each toggle-able item
	
	@ApiMethod(name = "toggle.get", path = "toggle_get", httpMethod = HttpMethod.GET)
	public States getStates()	{
		if(states == null)	{
			states = new States();
		}
		return states;
	}
	
	@ApiMethod(name="toggle.put", path = "toggle_put", httpMethod = HttpMethod.PUT)
	public States putStates(States s)	{
		states = s;
		return states;
	}
	
}
