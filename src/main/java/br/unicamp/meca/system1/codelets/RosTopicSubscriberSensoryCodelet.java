/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.net.URI;

import br.unicamp.cst.bindings.rosjava.RosTopicSubscriberCodelet;

/**
 * A Wrapper of the CST's RosTopicSubscriberCodelet implementing the ISensoryCodelet interface, 
 * in order to be able to be mounted on the MecaMind.
 * 
 * @author andre
 *
 * @param <T> The ROS Message Type - Ex: std_msgs.String from ROS standard messages
 */
public abstract class RosTopicSubscriberSensoryCodelet<T> extends RosTopicSubscriberCodelet<T> implements ISensoryCodelet {

	protected String id;
	
	/**
	 * 
	 * @param id the id of this Sensory Codelet, to be used in mounting MECA Mind. Also the name of this ROS node.
	 * @param topic the name of the ROS topic this node will be subscribing to.
	 * @param messageType the ROS message type. Ex: "std_msgs.String".
	 * @param host the host IP where to run. Ex: "127.0.0.1" .
	 * @param masterURI the URI of the master ROS node. Ex: new URI("http://127.0.0.1:11311").
	 */
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
