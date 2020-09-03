/**
 * 
 */
package br.unicamp.meca.mind;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.util.MindViewer;
import br.unicamp.meca.mind.action.Test1ActivityCodelet;
import br.unicamp.meca.mind.action.Test2ActivityCodelet;
import br.unicamp.meca.mind.action.Test3ActivityCodelet;
import br.unicamp.meca.mind.behavior.Test1AndTest2BehaviorCodelet;
import br.unicamp.meca.mind.motivational.TestMotivationalFromPerceptionCodelet;
import br.unicamp.meca.mind.motivational.TestMotivationalFromPlanningCodelet;
import br.unicamp.meca.mind.motor.TestMotorCodelet;
import br.unicamp.meca.mind.perceptual.TestPerceptualCodelet;
import br.unicamp.meca.mind.sensory.TestPerceptionSensoryCodelet;
import br.unicamp.meca.mind.sensory.TestPlanningSensoryCodelet;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.system1.codelets.ActivityCodelet;
import br.unicamp.meca.system1.codelets.ActivityTrackingCodelet;
import br.unicamp.meca.system1.codelets.BehaviorCodelet;
import br.unicamp.meca.system1.codelets.IMotorCodelet;
import br.unicamp.meca.system1.codelets.ISensoryCodelet;
import br.unicamp.meca.system1.codelets.MotivationalCodelet;
import br.unicamp.meca.system1.codelets.PerceptualCodelet;

/**
 * @author andre
 *
 */
public class MecaMindTest {

	private static MecaMind mecaMind;

	private static MindViewer mv;

	private static TestPerceptionSensoryCodelet testPerceptionSensoryCodelet;

	private static TestPlanningSensoryCodelet testPlanningSensoryCodelet;

	private static TestMotorCodelet testMotorCodelet;
	
	private static List<PerceptualCodelet> perceptualCodelets;
	
	private static List<ISensoryCodelet> sensoryCodelets;
	
	private static List<MotivationalCodelet> motivationalCodelets;
	
	private static List<ActivityCodelet> activityCodelets;

	@BeforeClass
	public static void setup() throws InterruptedException {

		mecaMind = new MecaMind("MecaMind");

		/* Sensory codelets we are about to create for this Meca mind*/
		sensoryCodelets = new ArrayList<>();

		/* Lists that will hold the codelets ids. This is important 
		 * for the MECA mind mounting algorithm be able to glue the 
		 * codelets according to the reference architecture
		 * */
		ArrayList<String> sensoryPerceptionCodeletsIds = new ArrayList<>();
		
		testPerceptionSensoryCodelet = new TestPerceptionSensoryCodelet("TestPerceptionSensoryCodelet");
		sensoryCodelets.add(testPerceptionSensoryCodelet);
		sensoryPerceptionCodeletsIds.add(testPerceptionSensoryCodelet.getId());


		/*
		 * Now it is a good time to create the motor codelets, before the Behavioral ones,
		 * because the behavioral will need the motor ids to be positioned between them
		 * and the rest of the architecture.
		 */

		List<IMotorCodelet> motorCodelets = new ArrayList<>();

		testMotorCodelet = new TestMotorCodelet("TestMotorCodelet");
		motorCodelets.add(testMotorCodelet);

		/*
		 * Then, we create the Perceptual codelet. 
		 * This codelet must receive the ids of the sensory codelets,
		 * in order to be glued to them, receiving  their inputs.
		 */
		perceptualCodelets = new ArrayList<>();
		ArrayList<String> perceptualPerceptionCodeletsIds = new ArrayList<>();

		TestPerceptualCodelet testPerceptualCodelet = new TestPerceptualCodelet("TestPerceptualCodelet", sensoryPerceptionCodeletsIds);
		perceptualPerceptionCodeletsIds.add(testPerceptualCodelet.getId());
		perceptualCodelets.add(testPerceptualCodelet);
		

		/*
		 * Next step is to create the motivational codelets.
		 * This codelets must receive the ids of the sensory codelets,
		 * in order to be glued to them, receiving  their inputs.
		 */
		motivationalCodelets = new ArrayList<>();

		ArrayList<String> testMotivationalFromPerceptionCodeletIds = new ArrayList<>();

		TestMotivationalFromPerceptionCodelet testMotivationalFromPerceptionCodelet;

		try {
			testMotivationalFromPerceptionCodelet = new TestMotivationalFromPerceptionCodelet("TestMotivationalFromPerceptionCodelet", 0, 0.45, 0.9, sensoryPerceptionCodeletsIds, new HashMap<String, Double>());
			testMotivationalFromPerceptionCodeletIds.add(testMotivationalFromPerceptionCodelet.getId());
			motivationalCodelets.add(testMotivationalFromPerceptionCodelet);

		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}

		/*
		 * Last step is to create the behavioral codelets,
		 * They receive the ids of the perceptual codelets and
		 * motor codelets, in order to be glued to them, according
		 * to the reference architecture.		
		 */
		
		activityCodelets = new ArrayList<>();

		Test1ActivityCodelet test1ActivityCodelet = new Test1ActivityCodelet("Test1Activity", perceptualPerceptionCodeletsIds, testMotivationalFromPerceptionCodeletIds, testMotorCodelet.getId(), null);
		activityCodelets.add(test1ActivityCodelet);
	

		/*
		 * Inserting the System 1 codelets inside MECA mind
		 */
		mecaMind.setISensoryCodelets(sensoryCodelets);
		mecaMind.setIMotorCodelets(motorCodelets);
		mecaMind.setPerceptualCodelets(perceptualCodelets);
		mecaMind.setMotivationalCodelets(motivationalCodelets);
		mecaMind.setActivityCodelets(activityCodelets);
	

	}

	@AfterClass
	public static void tearDown() {

		mv.setVisible(false);
		mecaMind.shutDown();     
	}

	@Test
	public void testMecaMindMountActivityReactWin() throws InterruptedException {
		
		/*
		 * After passing references to the codelets, we call the method 'MecaMind.mountMecaMind()', which
		 * is responsible for wiring the MecaMind altogether according to the reference architecture, including
		 * the creation of memory objects and containers which glue them together. This method is of pivotal
		 * importance and inside it resides all the value from the reference architecture created - the idea is 
		 * that the user only has to create the codelets, put them inside lists of differente types and call
		 * this method, which transparently glue the codelets together accordingly to the MECA reference 
		 * architecture.
		 */		
		mecaMind.mountMecaMind();		

		/*
		 * Starting the mind
		 */
		mecaMind.start();

		/*
		 * Instead of inserting the sensory codelets in the
		 * CST visualization tool, let's insert the behaviroal
		 * codelets, which activation has a pivotal role.
		 */
		List<Codelet> listOfCodelets = new ArrayList<>();
		listOfCodelets.addAll(mecaMind.getActivityCodelets());

		mv = new MindViewer(mecaMind, "MECA Mind Inspection - "+mecaMind.getId(), listOfCodelets);
		mv.setVisible(true);

		Thread.sleep(1000);

		//do something    	    

		String contentInTheEnvironment = "Something";

		testPerceptionSensoryCodelet.setSensoryContents(contentInTheEnvironment);

		Thread.sleep(1000);

		//test something

		String messageExpected = "Test1Activity - A black dog";

		MemoryContainer motorMemory = (MemoryContainer) testMotorCodelet.getInput("TestMotorCodelet");

		String messageActual = (String) motorMemory.getI();

//		System.out.println(messageActual);

		assertEquals(messageExpected, messageActual);	
	}

	@Test
	public void testMecaMindMountActivityFromPlanningWin() throws InterruptedException {
		
		ArrayList<String> sensoryPlanningCodeletsIds = new ArrayList<>();
		testPlanningSensoryCodelet = new TestPlanningSensoryCodelet("TestPlanningSensoryCodelet");
		sensoryCodelets.add(testPlanningSensoryCodelet);
		sensoryPlanningCodeletsIds.add(testPlanningSensoryCodelet.getId());
		
		ArrayList<String> perceptualPlanningCodeletsIds = new ArrayList<>();
		TestPerceptualCodelet testPlanningCodelet = new TestPerceptualCodelet("TestPlanningCodelet", sensoryPlanningCodeletsIds);
		perceptualPlanningCodeletsIds.add(testPlanningCodelet.getId());
		perceptualCodelets.add(testPlanningCodelet);
		
		ArrayList<String> testMotivationalFromPlanningCodeletIds = new ArrayList<>();

		TestMotivationalFromPlanningCodelet testMotivationalFromPlanningCodelet;

		try {

			testMotivationalFromPlanningCodelet = new TestMotivationalFromPlanningCodelet("TestMotivationalFromPlanningCodelet",  0, 0.5, 0.9, sensoryPlanningCodeletsIds, new HashMap<String, Double>());
			testMotivationalFromPlanningCodeletIds.add(testMotivationalFromPlanningCodelet.getId());
			motivationalCodelets.add(testMotivationalFromPlanningCodelet);    		
		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}
		
		Test2ActivityCodelet test2ActivityCodelet = new Test2ActivityCodelet("Test2Activity", perceptualPlanningCodeletsIds, testMotivationalFromPlanningCodeletIds, testMotorCodelet.getId(), null);
		activityCodelets.add(test2ActivityCodelet);

		Test3ActivityCodelet test3ActivityCodelets = new Test3ActivityCodelet("Test3Activity", perceptualPlanningCodeletsIds, testMotivationalFromPlanningCodeletIds, testMotorCodelet.getId(), null);
		activityCodelets.add(test3ActivityCodelets);
		
		ActivityTrackingCodelet activityTrackingCodelet = new ActivityTrackingCodelet("ActivityTrackingCodelet", perceptualPlanningCodeletsIds) {
			
			@Override
			public void trackActionSequencePlan(ArrayList<Memory> perceptualMemories, ActionSequencePlan actionSequencePlan) {
				
				if(actionSequencePlan == null || actionSequencePlan.getActionStepSequence() == null) {
					return;
				}
							
				actionSequencePlan.setCurrentActionIdIndex(0);
				
				//In this test, will never go on to the second action. We could have limit on perceptual memory to move on, though.
			}
		};
		
	
		List<BehaviorCodelet> behaviorCodelets = new ArrayList<>();

		Test1AndTest2BehaviorCodelet test1AndTest2BehaviorCodelet = new Test1AndTest2BehaviorCodelet("Test1AndTest2BehaviorCodelet", perceptualPlanningCodeletsIds, testMotivationalFromPlanningCodeletIds, null);
		behaviorCodelets.add(test1AndTest2BehaviorCodelet);

		mecaMind.setBehaviorCodelets(behaviorCodelets);
		mecaMind.setActivityTrackingCodelet(activityTrackingCodelet);
		
		/*
		 * After passing references to the codelets, we call the method 'MecaMind.mountMecaMind()', which
		 * is responsible for wiring the MecaMind altogether according to the reference architecture, including
		 * the creation of memory objects and containers which glue them together. This method is of pivotal
		 * importance and inside it resides all the value from the reference architecture created - the idea is 
		 * that the user only has to create the codelets, put them inside lists of differente types and call
		 * this method, which transparently glue the codelets together accordingly to the MECA reference 
		 * architecture.
		 */		
		mecaMind.mountMecaMind();		

		/*
		 * Starting the mind
		 */
		mecaMind.start();

		/*
		 * Instead of inserting the sensory codelets in the
		 * CST visualization tool, let's insert the behaviroal
		 * codelets, which activation has a pivotal role.
		 */
		List<Codelet> listOfCodelets = new ArrayList<>();
		listOfCodelets.addAll(mecaMind.getActivityCodelets());
		listOfCodelets.addAll(mecaMind.getBehaviorCodelets());

		mv = new MindViewer(mecaMind, "MECA Mind Inspection - "+mecaMind.getId(), listOfCodelets);
		mv.setVisible(true);

		Thread.sleep(1000);

		
		//do something    	    

		String contentInTheEnvironment = "Something";

		testPerceptionSensoryCodelet.setSensoryContents(null);

		testPlanningSensoryCodelet.setSensoryContents(contentInTheEnvironment);

		Thread.sleep(1000);

		//test something

		String messageExpected = "Test2Activity - A black dog";

		MemoryContainer motorMemory = (MemoryContainer) testMotorCodelet.getInput("TestMotorCodelet");

		String messageActual = (String) motorMemory.getI();

//		System.out.println(messageActual);

		assertEquals(messageExpected, messageActual);	
	}
}
