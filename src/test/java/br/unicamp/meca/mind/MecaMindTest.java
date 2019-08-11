/**
 * 
 */
package br.unicamp.meca.mind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.util.MindViewer;
import br.unicamp.meca.system1.codelets.ActionFromPerception;
import br.unicamp.meca.system1.codelets.IMotorCodelet;
import br.unicamp.meca.system1.codelets.ISensoryCodelet;
import br.unicamp.meca.system1.codelets.MotivationalCodelet;
import br.unicamp.meca.system1.codelets.PerceptualCodelet;

/**
 * @author andre
 *
 */
public class MecaMindTest {
	
	@BeforeClass
    public static void beforeAllTestMethods() {
    }

	@AfterClass
    public static void afterAllTestMethods() {
    }
    
    @Test
    public void testMecaMind() throws InterruptedException {
    	
    	MecaMind mecaMind = new MecaMind("MecaMind");
    	
    	/* Sensory codelets we are about to create for this Meca mind*/
    	List<ISensoryCodelet> sensoryCodelets = new ArrayList<>();
    	
    	/* Lists that will hold the codelets ids. This is important 
		 * for the MECA mind mounting algorithm be able to glue the 
		 * codelets according to the reference architecture
		 * */
		ArrayList<String> sensoryCodeletsIds = new ArrayList<>();
		
		TestSensoryCodelet testSensoryCodelet = new TestSensoryCodelet("TestSensoryCodelet");
		sensoryCodelets.add(testSensoryCodelet);
		sensoryCodeletsIds.add(testSensoryCodelet.getId());
		
		/*
		 * Now it is a good time to create the motor codelets, before the Behavioral ones,
		 * because the behavioral will need the motor ids to be positioned between them
		 * and the rest of the architecture.
		 */
		
    	List<IMotorCodelet> motorCodelets = new ArrayList<>();
    	
    	TestMotorCodelet testMotorCodelet = new TestMotorCodelet("TestMotorCodelet");
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
		
		ArrayList<String> testMotivationalCodeletIds = new ArrayList<>();
		
		TestMotivationalCodelet testMotivationalCodelet;
		
    	try {
    		testMotivationalCodelet = new TestMotivationalCodelet("TestMotivationalCodelet", 0, 0.5, 0.9, sensoryCodeletsIds, new HashMap<String, Double>());
    		testMotivationalCodeletIds.add(testMotivationalCodelet.getId());
			motivationalCodelets.add(testMotivationalCodelet);
    	
    	} catch (CodeletActivationBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*
		 * Last step is to create the behavioral codelets,
		 * They receive the ids of the perceptual codelets and
		 * motor codelets, in order to be glued to them, according
		 * to the reference architecture.		
		 */
    	
    	List<ActionFromPerception> actionFromPerceptionCodelets = new ArrayList<>();
    	
    	TestActionFromPerception testActionFromPerception = new TestActionFromPerception("TestActionFromPerception", perceptualCodeletsIds, testMotivationalCodeletIds, testMotorCodelet.getId(), null);
    	actionFromPerceptionCodelets.add(testActionFromPerception);
    	
    	
    	/*
		 * Inserting the System 1 codelets inside MECA mind
		 */
		mecaMind.setISensoryCodelets(sensoryCodelets);
		mecaMind.setIMotorCodelets(motorCodelets);
		mecaMind.setPerceptualCodelets(perceptualCodelets);
		mecaMind.setMotivationalCodelets(motivationalCodelets);
		mecaMind.setActionFromPerceptionCodelets(actionFromPerceptionCodelets);
//		mecaMind.setActionFromPlanningCodelets(actionFromPlanningCodelets);
//		mecaMind.setBehaviorCodelets(behaviorCodelets);
	    
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
//		listOfCodelets.addAll(mecaMind.getActionFromPlanningCodelets());
//		listOfCodelets.addAll(mecaMind.getBehaviorCodelets());

		MindViewer mv = new MindViewer(mecaMind, "MECA Mind Inspection - "+mecaMind.getId(), listOfCodelets);
		mv.setVisible(true);
		
		Thread.sleep(1000);
		
		//do something
		
		Thread.sleep(1000);
		
		//test something
		
//		assertEquals(messageExpected, messageActual);
		
		mecaMind.shutDown();
    	
    }

}
