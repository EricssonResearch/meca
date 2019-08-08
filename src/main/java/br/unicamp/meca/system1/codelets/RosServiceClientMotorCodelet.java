/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.net.URI;

import br.unicamp.cst.bindings.rosjava.RosServiceClientCodelet;

/**
 * @author andre
 *
 */
public abstract class RosServiceClientMotorCodelet<S, T> extends RosServiceClientCodelet<S, T> {
	
	protected String id;

	public RosServiceClientMotorCodelet(String id, String service, String messageServiceType, String host, URI masterURI) {
		super(id, service, messageServiceType, host, masterURI);
		this.id = id;
	}
	
	/**
	 * Returns the id of this RosServiceClientMotorCodelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this RosServiceClientMotorCodelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
