/**
 * 
 */
package br.unicamp.meca.system1.codelets.rostopic;

import java.net.URI;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.RosTopicPublisherMotorCodelet;

/**
 * @author andre
 *
 */
public class ChatterTopicPublisher extends RosTopicPublisherMotorCodelet<std_msgs.String> {

	public ChatterTopicPublisher(String host, URI masterURI) {
		super("ChatterTopicPublisher", "chatter", std_msgs.String._TYPE, host, masterURI);
	}

	@Override
	public void fillMessageToBePublished(Memory motorMemory, std_msgs.String message) {
		if(motorMemory == null) {
			return;
		}
		
		String messageData = (String) motorMemory.getI();
		
		if(messageData == null) {
			return;
		}
		
		if(message == null) {
			return;
		}
		
		message.setData(messageData);	
		
	}

}
