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
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.util.MindViewer;
import br.unicamp.meca.mind.action.Test1ActionFromPerceptionCodelet;
import br.unicamp.meca.mind.action.Test1ActionFromPlanningCodelet;
import br.unicamp.meca.mind.action.Test2ActionFromPlanningCodelets;
import br.unicamp.meca.mind.behavior.Test1AndTest2BehaviorCodelet;
import br.unicamp.meca.mind.motivational.TestMotivationalFromPerceptionCodelet;
import br.unicamp.meca.mind.motivational.TestMotivationalFromPlanningCodelet;
import br.unicamp.meca.mind.motor.TestMotorCodelet;
import br.unicamp.meca.mind.perceptual.TestPerceptualCodelet;
import br.unicamp.meca.mind.sensory.TestPerceptionSensoryCodelet;
import br.unicamp.meca.mind.sensory.TestPlanningSensoryCodelet;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.system1.codelets.ActionFromPerceptionCodelet;
import br.unicamp.meca.system1.codelets.ActionFromPlanningCodelet;
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

	@BeforeClass
	public static void setup() throws InterruptedException {

		mecaMind = new MecaMind("MecaMind");

		/* Sensory codelets we are about to create for this Meca mind*/
		List<ISensoryCodelet> sensoryCodelets = new ArrayList<>();

		/* Lists that will hold the codelets ids. This is important 
		 * for the MECA mind mounting algorithm be able to glue the 
		 * codelets according to the reference architecture
		 * */
		ArrayList<String> sensoryCodeletsIds = new ArrayList<>();

		testPerceptionSensoryCodelet = new TestPerceptionSensoryCodelet("TestPerceptionSensoryCodelet");
		sensoryCodelets.add(testPerceptionSensoryCodelet);
		sensoryCodeletsIds.add(testPerceptionSensoryCodelet.getId());

		testPlanningSensoryCodelet = new TestPlanningSensoryCodelet("TestPlanningSensoryCodelet");
		sensoryCodelets.add(testPlanningSensoryCodelet);
		sensoryCodeletsIds.add(testPlanningSensoryCodelet.getId());

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
		List<PerceptualCodelet> perceptualCodelets = new ArrayList<>();
		ArrayList<String> perceptualCodeletsIds = new ArrayList<>();

		TestPerceptualCodelet testPerceptualCodelet = new TestPerceptualCodelet("TestPerceptualCodelet", sensoryCodeletsIds);
		perceptualCodeletsIds.add(testPerceptualCodelet.getId());
		perceptualCodelets.add(testPerceptualCodelet);

		/*
		 * Next step is to create the motivational codelets.
		 * This codelets must receive the ids of the sensory codelets,
		 * in order to be glued to them, receiving  their inputs.
		 */
		List<MotivationalCodelet> motivationalCodelets = new ArrayList<>();

		ArrayList<String> testMotivationalFromPerceptionCodeletIds = new ArrayList<>();

		TestMotivationalFromPerceptionCodelet testMotivationalFromPerceptionCodelet;

		try {
			testMotivationalFromPerceptionCodelet = new TestMotivationalFromPerceptionCodelet("TestMotivationalFromPerceptionCodelet", 0, 0.45, 0.9, sensoryCodeletsIds, new HashMap<String, Double>());
			testMotivationalFromPerceptionCodeletIds.add(testMotivationalFromPerceptionCodelet.getId());
			motivationalCodelets.add(testMotivationalFromPerceptionCodelet);

		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}

		ArrayList<String> testMotivationalFromPlanningCodeletIds = new ArrayList<>();

		TestMotivationalFromPlanningCodelet testMotivationalFromPlanningCodelet;

		try {

			testMotivationalFromPlanningCodelet = new TestMotivationalFromPlanningCodelet("TestMotivationalFromPlanningCodelet",  0, 0.5, 0.9, sensoryCodeletsIds, new HashMap<String, Double>());
			testMotivationalFromPlanningCodeletIds.add(testMotivationalFromPlanningCodelet.getId());
			motivationalCodelets.add(testMotivationalFromPlanningCodelet);    		
		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}

		/*
		 * Last step is to create the behavioral codelets,
		 * They receive the ids of the perceptual codelets and
		 * motor codelets, in order to be glued to them, according
		 * to the reference architecture.		
		 */

		List<ActionFromPerceptionCodelet> actionFromPerceptionCodelets = new ArrayList<>();

		Test1ActionFromPerceptionCodelet test1ActionFromPerceptionCodelet = new Test1ActionFromPerceptionCodelet("Test1ActionFromPerceptionCodelet", perceptualCodeletsIds, testMotivationalFromPerceptionCodeletIds, testMotorCodelet.getId(), null);
		actionFromPerceptionCodelets.add(test1ActionFromPerceptionCodelet);

		List<ActionFromPlanningCodelet> actionFromPlanningCodelets = new ArrayList<>();

		Test1ActionFromPlanningCodelet test1ActionFromPlanningCodelet = new Test1ActionFromPlanningCodelet("Test1ActionFromPlanningCodelet", perceptualCodeletsIds, testMotorCodelet.getId(), null);
		actionFromPlanningCodelets.add(test1ActionFromPlanningCodelet);

		Test2ActionFromPlanningCodelets test2ActionFromPlanningCodelets = new Test2ActionFromPlanningCodelets("Test2ActionFromPlanningCodelets", perceptualCodeletsIds, testMotorCodelet.getId(), null);
		actionFromPlanningCodelets.add(test2ActionFromPlanningCodelets);

		List<BehaviorCodelet> behaviorCodelets = new ArrayList<>();

		ActionSequencePlan test1Test2ActionSequence = new ActionSequencePlan(new String[] {"Test1ActionFromPlanningCodelet","Test2ActionFromPlanningCodelets"});

		Test1AndTest2BehaviorCodelet test1AndTest2BehaviorCodelet = new Test1AndTest2BehaviorCodelet("Test1AndTest2BehaviorCodelet", perceptualCodeletsIds, testMotivationalFromPlanningCodeletIds, null, test1Test2ActionSequence);
		behaviorCodelets.add(test1AndTest2BehaviorCodelet);

		/*
		 * Inserting the System 1 codelets inside MECA mind
		 */
		mecaMind.setISensoryCodelets(sensoryCodelets);
		mecaMind.setIMotorCodelets(motorCodelets);
		mecaMind.setPerceptualCodelets(perceptualCodelets);
		mecaMind.setMotivationalCodelets(motivationalCodelets);
		mecaMind.setActionFromPerceptionCodelets(actionFromPerceptionCodelets);
		mecaMind.setActionFromPlanningCodelets(actionFromPlanningCodelets);
		mecaMind.setBehaviorCodelets(behaviorCodelets);

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
		listOfCodelets.addAll(mecaMind.getActionFromPerceptionCodelets());
		listOfCodelets.addAll(mecaMind.getActionFromPlanningCodelets());
		listOfCodelets.addAll(mecaMind.getBehaviorCodelets());

		mv = new MindViewer(mecaMind, "MECA Mind Inspection - "+mecaMind.getId(), listOfCodelets);
		mv.setVisible(true);

		Thread.sleep(1000);

	}

	@AfterClass
	public static void tearDown() {

		mv.setVisible(false);
		mecaMind.shutDown();     
	}

	@Test
	public void testMecaMindMountActionFromPerceptionWin() throws InterruptedException {

		//do something    	    

		String contentInTheEnvironment = "Something";

		testPerceptionSensoryCodelet.setSensoryContents(contentInTheEnvironment);

		testPlanningSensoryCodelet.setSensoryContents(null);

		Thread.sleep(1000);

		//test something

		String messageExpected = "Test1ActionFromPerception - A black dog";

		MemoryContainer motorMemory = (MemoryContainer) testMotorCodelet.getInput("TestMotorCodelet");

		String messageActual = (String) motorMemory.getI();

//		System.out.println(messageActual);

		assertEquals(messageExpected, messageActual);	
	}

	@Test
	public void testMecaMindMountActionFromPlanningWin() throws InterruptedException {

		//do something    	    

		String contentInTheEnvironment = "Something";

		testPerceptionSensoryCodelet.setSensoryContents(null);

		testPlanningSensoryCodelet.setSensoryContents(contentInTheEnvironment);

		Thread.sleep(1000);

		//test something

		String messageExpected = "Test1ActionFromPlanning - A black dog";

		MemoryContainer motorMemory = (MemoryContainer) testMotorCodelet.getInput("TestMotorCodelet");

		String messageActual = (String) motorMemory.getI();

//		System.out.println(messageActual);

		assertEquals(messageExpected, messageActual);	
	}
}
