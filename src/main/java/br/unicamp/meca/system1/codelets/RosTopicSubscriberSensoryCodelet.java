/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.net.URI;

import br.unicamp.cst.bindings.rosjava.RosTopicSubscriberCodelet;

/**
 * @author andre
 *
 */
public abstract class RosTopicSubscriberSensoryCodelet<T> extends RosTopicSubscriberCodelet<T> {

	protected String id;
	
	public RosTopicSubscriberSensoryCodelet(String id, String topic, String messageType, String host, URI masterURI) {
		super(id, topic, messageType, host, masterURI);
		this.id = id;	
	}

	/**
	 * Returns the id of this RosTopicSubscriberSensoryCodelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this RosTopicSubscriberSensoryCodelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
