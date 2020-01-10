/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.net.URI;

import br.unicamp.cst.bindings.rosjava.RosServiceClientCodelet;

/**
 * A Wrapper of the CST's RosServiceClientCodelet implementing the IMotorCodelet interface, 
 * in order to be able to be mounted on the MecaMind. 
 * 
 * @author andre
 *
 * @param <S> Service Message Request - Ex: AddTwoIntsRequest from ROS Tutorials
 * @param <T> Service Message Response - Ex: AddTwonIntsResponse from ROS Tutorials
 */
public abstract class RosServiceClientMotorCodelet<S, T> extends RosServiceClientCodelet<S, T> implements IMotorCodelet {
	
	protected String id;

	/**
	 * Constructor for the RosServiceClientMotorCodelet.
	 * 
	 * @param id the id of this Motor Codelet, to be used in mounting MECA Mind. Also the name of this ROS node.
	 * @param service the service that this node will be a client of. Ex: "add_two_ints".
	 * @param messageServiceType the ROS message type. Ex: "rosjava_test_msgs.AddTwoInts".
	 * @param host the host IP where to run. Ex: "127.0.0.1".
	 * @param masterURI the URI of the master ROS node. Ex: new URI("http://127.0.0.1:11311").
	 */
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
