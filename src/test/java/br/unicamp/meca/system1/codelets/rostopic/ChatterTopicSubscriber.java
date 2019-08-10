/**
 * 
 */
package br.unicamp.meca.system1.codelets.rostopic;

import java.net.URI;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.RosTopicSubscriberSensoryCodelet;

/**
 * @author andre
 *
 */
public class ChatterTopicSubscriber extends RosTopicSubscriberSensoryCodelet<std_msgs.String> {

	public ChatterTopicSubscriber(String host, URI masterURI) {
		super("ChatterTopicSubscriber", "chatter", std_msgs.String._TYPE, host, masterURI);
	}

	@Override
	public void fillMemoryWithReceivedMessage(std_msgs.String message, Memory sensoryMemory) {
		if(message == null) {
			sensoryMemory.setI(null);
			return;
		}
		
		String messageData = message.getData();
		
		if(messageData == null) {
			sensoryMemory.setI(null);
			return;
		}
		
		System.out.println("I heard: \"" + messageData + "\"");
		sensoryMemory.setI(messageData);
	}

}
