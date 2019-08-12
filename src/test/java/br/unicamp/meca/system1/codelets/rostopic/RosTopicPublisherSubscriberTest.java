/**
 * 
 */
package br.unicamp.meca.system1.codelets.rostopic;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ros.RosCore;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.system1.codelets.IMotorCodelet;
import br.unicamp.meca.system1.codelets.ISensoryCodelet;

/**
 * @author andre
 *
 */
public class RosTopicPublisherSubscriberTest {
	
	private static RosCore rosCore;
	
	@BeforeClass
    public static void beforeAllTestMethods() {
		rosCore  = RosCore.newPublic("127.0.0.1",11311);
	    rosCore.start();
    }

	@AfterClass
    public static void afterAllTestMethods() {
        rosCore.shutdown();
    }
    
    @Test
    public void testRosTopics() throws URISyntaxException, InterruptedException {
    		    
	    MecaMind mecaMind = new MecaMind("RosTopicPublisherSubscriber");
	    
	    List<IMotorCodelet> motorCodelets = new ArrayList<>();
	    
	    ChatterTopicPublisher chatterTopicPublisher = new ChatterTopicPublisher("127.0.0.1",new URI("http://127.0.0.1:11311"));	    
	    motorCodelets.add(chatterTopicPublisher);
	    
	    List<ISensoryCodelet> sensoryCodelets = new ArrayList<>();
	    
	    ChatterTopicSubscriber chatterTopicSubscriber = new ChatterTopicSubscriber("127.0.0.1",new URI("http://127.0.0.1:11311"));
	    sensoryCodelets.add(chatterTopicSubscriber);
	    
	    mecaMind.setISensoryCodelets(sensoryCodelets);
	    mecaMind.setIMotorCodelets(motorCodelets);
	    
	    mecaMind.mountMecaMind();		
		
		mecaMind.start();
		
		Thread.sleep(1000);
		
		String messageExpected = "Hello World";
		Memory motorMemory = chatterTopicPublisher.getInput(chatterTopicPublisher.getId());
		motorMemory.setI(messageExpected);
		
		Thread.sleep(1000);
		
		Memory sensoryMemory = chatterTopicSubscriber.getOutput(chatterTopicSubscriber.getId());
		
		String messageActual = (String) sensoryMemory.getI();
	    
	    assertEquals(messageExpected, messageActual);
	    
	    mecaMind.shutDown();    
    }
}
