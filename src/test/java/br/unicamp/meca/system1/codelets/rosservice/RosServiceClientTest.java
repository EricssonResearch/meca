/**
 * 
 */
package br.unicamp.meca.system1.codelets.rosservice;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ros.RosCore;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.system1.codelets.IMotorCodelet;

/**
 * @author andre
 *
 */
public class RosServiceClientTest {

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
    public void testRosService() throws URISyntaxException, InterruptedException {
    	
		AddTwoIntService addTwoIntService = new AddTwoIntService();
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
		NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic("127.0.0.1",new URI("http://127.0.0.1:11311"));
		nodeMainExecutor.execute(addTwoIntService, nodeConfiguration);	
		
		Thread.sleep(2000);
		
		MecaMind mecaMind = new MecaMind("RosServiceClient");
		
		List<IMotorCodelet> motorCodelets = new ArrayList<>();
		
		AddTwoIntServiceClient addTwoIntServiceClient = new AddTwoIntServiceClient("127.0.0.1",new URI("http://127.0.0.1:11311"));
		motorCodelets.add(addTwoIntServiceClient);
    
		mecaMind.setIMotorCodelets(motorCodelets);
	    
	    mecaMind.mountMecaMind();		
		
		mecaMind.start();
		
		Thread.sleep(5000);
		
		Memory motorMemory = addTwoIntServiceClient.getInput(addTwoIntServiceClient.getId());
		
		Integer expectedSum = 5;
		
		Integer[] numsToSum = new Integer[] {2,3};
		motorMemory.setI(numsToSum);
		
		Thread.sleep(2000);
		
		assertEquals(expectedSum, addTwoIntServiceClient.getSum());
		
		nodeMainExecutor.shutdownNodeMain(addTwoIntService);
		
		mecaMind.shutDown();
    
    }
    
    @Test
    public void testRosServiceCallTwice() throws URISyntaxException, InterruptedException {
    	
		AddTwoIntService addTwoIntService = new AddTwoIntService();
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
		NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic("127.0.0.1",new URI("http://127.0.0.1:11311"));
		nodeMainExecutor.execute(addTwoIntService, nodeConfiguration);	
		
		Thread.sleep(2000);
		
		MecaMind mecaMind = new MecaMind("RosServiceClient");
		
		List<IMotorCodelet> motorCodelets = new ArrayList<>();
		
		AddTwoIntServiceClient addTwoIntServiceClient = new AddTwoIntServiceClient("127.0.0.1",new URI("http://127.0.0.1:11311"));
		motorCodelets.add(addTwoIntServiceClient);
    
		mecaMind.setIMotorCodelets(motorCodelets);
	    
	    mecaMind.mountMecaMind();		
		
		mecaMind.start();
		
		Thread.sleep(5000);
		
		Memory motorMemory = addTwoIntServiceClient.getInput(addTwoIntServiceClient.getId());
		
		Integer expectedSum = 5;
		
		Integer[] numsToSum = new Integer[] {2,3};
		motorMemory.setI(numsToSum);
		
		Thread.sleep(2000);
		
		assertEquals(expectedSum, addTwoIntServiceClient.getSum());
		
		expectedSum = 6;
		
		numsToSum = new Integer[] {3,3};
		motorMemory.setI(numsToSum);
		
		Thread.sleep(2000);
		
		assertEquals(expectedSum, addTwoIntServiceClient.getSum());
		
		nodeMainExecutor.shutdownNodeMain(addTwoIntService);
		
		mecaMind.shutDown();
    
    }
}
