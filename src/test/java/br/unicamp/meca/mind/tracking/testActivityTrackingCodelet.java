/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.mind.tracking;

import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.mind.action.Test1ActivityCodelet;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.models.ActionStep;
import br.unicamp.meca.models.ActionStepTest;
import br.unicamp.meca.system1.codelets.ActivityCodelet;
import br.unicamp.meca.system1.codelets.ActivityTrackingCodelet;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rgudwin
 */
public class testActivityTrackingCodelet {
    ActivityTrackingCodelet atc;
    ActivityCodelet actc1;
    ActivityCodelet actc2;
    
    @Test
    public void testActivityTrackingCodelet() {
        atc = new ActivityTrackingCodelet("tracking",null);
        atc.accessMemoryObjects();
        atc.calculateActivation();
        // This tests the case when there is still no sequence plan to be tracked
        atc.proc();
        // Now let's introduce an empty perception list
        ArrayList<String> lop = new ArrayList();
        atc = new ActivityTrackingCodelet("tracking",lop);
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        // Now let's define an inexistent perception
        lop.add("perception");
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        // Now, still without perception, let's introduce a sequence plan
        MemoryContainer mc = new MemoryContainer();
        mc.setType(MecaMind.ACTION_SEQUENCE_PLAN_ID);
        atc.addInput(mc);
        atc.accessMemoryObjects();
        // Now there is a memory object, but still no sequence plan
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        // Now let's include a plan, but still without any action step
        ActionStep as[] = new ActionStep[3];
        ActionSequencePlan asp = new ActionSequencePlan(as);
        mc.setI(asp,0.2);
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        // Finally, let's include the action steps
        ActionStep as1 = new ActionStepTest("tracking");
        as[0] = as1;
        ActionStep as2 = new ActionStepTest("tracking");
        as[1] = as2;
        ActionStep as3 = new ActionStepTest("tracking");
        as[2] = as3;
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        assertEquals(asp.getCurrentActionIdIndex(),0);
        // Now let's introduce a Perception Memory to track
        // And now finally let's provide an existent perception, still without info
        MemoryObject per = new MemoryObject();
        per.setType("perception");
        atc = new ActivityTrackingCodelet("tracking",lop);
        atc.addInput(per);
        atc.addInput(mc);
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        assertEquals(asp.getCurrentActionIdIndex(),0);
        // Now, let's create the conditions for the codelet decide to move to its 2nd step
        per.setEvaluation(1.0);
        atc.addInput(per);
        //as[0].needsConclusion = true;
        atc.accessMemoryObjects();
        atc.calculateActivation();
        assertEquals(as[0].executed,false);
        atc.proc();
        // Now, because there are conditions for move to the second step of the plan, the index should be 1
        assertEquals(as[0].executed,true);
        // Because the ActionStep 0 was executed, now it needs conclusion
        assertEquals(as[0].needsConclusion,true);
        assertEquals(asp.getCurrentActionIdIndex(),1);
        assertEquals(as[1].executed,false);
        atc.proc();
        // Now let's play a little ... because the conclusion didn't come, this proc will not move to the 3rd action step
        assertEquals(as[1].executed,false);
        // Let's then conclude the action step 0 e see what happens
        as[0].needsConclusion = false;
        atc.proc();
        // Now, this allows the execution of action step 1
        assertEquals(as[1].executed,true);
        assertEquals(asp.getCurrentActionIdIndex(),2);
        assertEquals(as[2].executed,false);
        assertEquals(as[1].needsConclusion,true);
        as[1].needsConclusion = false;
        atc.proc();
        assertEquals(as[2].needsConclusion,true);
        assertEquals(asp.getCurrentActionIdIndex(),0);
        assertEquals(as[2].executed,true);
    }
    
    @Test 
    public void testDoConclusion() {
        // Creation of the Perception Memory
        ArrayList<String> lop = new ArrayList();
        MemoryObject per = new MemoryObject();
        per.setType("perception");
        per.setI("perception");
        lop.add("perception");
        atc = new ActivityTrackingCodelet("tracking",lop);
        atc.addInput(per);
        // Creation of the ActionSequencePlan
        MemoryContainer planContainer = new MemoryContainer();
        planContainer.setType(MecaMind.ACTION_SEQUENCE_PLAN_ID);
        ActionStep as1 = new ActionStepTest("activity1");
        ActionStep as2 = new ActionStepTest("activity2");
        ActionSequencePlan asp = new ActionSequencePlan(new ActionStep[] {as1,as2});
        planContainer.setI(asp,0.2,"plan");
        atc.addInput(planContainer);
        // Creation of the MotorMemory
        MemoryContainer motorMemory = new MemoryContainer();
        motorMemory.setType("motor");
        // Now creating the ActivityCodelets which will serve the ActionStep
        actc1 = new Test1ActivityCodelet("activity1",lop,null,"motor",null);
        actc1.addInput(per);
        actc1.addInput(planContainer);
        actc1.addOutput(motorMemory);
        actc2 = new Test1ActivityCodelet("activity2",lop,null,"motor",null);
        actc2.addInput(per);
        actc2.addInput(planContainer);
        actc2.addOutput(motorMemory);
        // Execute the 1st time step
        step();
        // Verifying if the activity1 modified the motor codelet
        assertEquals(motorMemory.getI(),"activity1 - perception");
        // now let's cause the ActivityTrackingCodelet to conclude step 1 and move to step 2
        per.setEvaluation(1.0);
        // And change the activation to a lower value than activity1 ... 
        // if activity1 is not concluded by doConclusion, it will stay at the motor codelet and ruin the output of motorCodelet
        planContainer.setI(asp,0.1,"plan");
        step();
        // Let's first verify if the plan was advanced to step 2
        assertEquals(asp.getCurrentActionIdIndex(),1);
        // Let's verify if activity2 modified the motor codelet
        assertEquals(motorMemory.getI(),"activity2 - perception");
        // Let's verify if activity1 was concluded
        assertEquals(motorMemory.getI(0),"activity1 - concluded");
        // Now let's do one more step to conclude the plan 
        step();
        // And verify the plan is back to step 1 and executed
        assertEquals(asp.getCurrentActionIdIndex(),0);
        assertEquals(asp.getCurrentActionStep().executed,true);
        assertEquals(asp.getLastExecutedActionStep().executed,true);
        // Let's do a final step and check if everything remains the same
        step();
        assertEquals(asp.getCurrentActionIdIndex(),0);
        assertEquals(asp.getCurrentActionStep().executed,true);
        assertEquals(asp.getLastExecutedActionStep().executed,true);
        
    }
    
    @Test
    public void testTwoActionStepsOfSameType() {
        // Creation of the Perception Memory
        ArrayList<String> lop = new ArrayList();
        MemoryObject per = new MemoryObject();
        per.setType("perception");
        per.setI("perception");
        lop.add("perception");
        atc = new ActivityTrackingCodelet("tracking",lop);
        atc.addInput(per);
        // Creation of the ActionSequencePlan
        MemoryContainer planContainer = new MemoryContainer();
        planContainer.setType(MecaMind.ACTION_SEQUENCE_PLAN_ID);
        ActionStep as1 = new ActionStepTest("activity1");
        ActionStep as2 = new ActionStepTest("activity1"); // The 2nd actionstep is treated by the same ActivityCodelet
        ActionSequencePlan asp = new ActionSequencePlan(new ActionStep[] {as1,as2});
        planContainer.setI(asp,0.2,"plan");
        atc.addInput(planContainer);
        // Creation of the MotorMemory
        MemoryContainer motorMemory = new MemoryContainer();
        motorMemory.setType("motor");
        // Now creating the ActivityCodelet which will serve the ActionStep
        actc1 = new Test1ActivityCodelet("activity1",lop,null,"motor",null);
        actc1.addInput(per);
        actc1.addInput(planContainer);
        actc1.addOutput(motorMemory);
        // Execute the 1st time step
        step2();
        // Verifying if the activity1 modified the motor codelet
        assertEquals(motorMemory.getI(),"activity1 - perception");
        // now let's cause the ActivityTrackingCodelet to conclude step 1 and move to step 2
        per.setEvaluation(1.0);
        // And change the activation to a lower value than activity1 ... 
        // if activity1 is not concluded by doConclusion, it will stay at the motor codelet and ruin the output of motorCodelet
        planContainer.setI(asp,0.1,"plan");
        step2();
        // Let's first verify if the plan was advanced to step 2
        assertEquals(asp.getCurrentActionIdIndex(),1);
        // The conclusion of step1 can only be verified by the log, as the motorMemory is immediately rewritten by step2
        // A "Concluding activity1" message should have appeared in the log
        // But we can still check the needsConclusion flag, which should be cleared
        assertEquals(asp.getLastExecutedActionStep().needsConclusion,false);
        // Now let's do one more step to conclude the plan 
        step2();
        // And verify the plan is back to step 1 and executed
        assertEquals(asp.getCurrentActionIdIndex(),0);
        assertEquals(asp.getCurrentActionStep().needsConclusion,false);
        assertEquals(asp.getCurrentActionStep().executed,true);
        assertEquals(asp.getLastExecutedActionStep().needsConclusion,false);
        assertEquals(asp.getLastExecutedActionStep().executed,true);
        // Let's do a final step and check if everything remains the same
        step2();
        assertEquals(asp.getCurrentActionIdIndex(),0);
        assertEquals(asp.getCurrentActionStep().needsConclusion,false);
        assertEquals(asp.getCurrentActionStep().executed,true);
        assertEquals(asp.getLastExecutedActionStep().needsConclusion,false);
        assertEquals(asp.getLastExecutedActionStep().executed,true);
    }
    
    private void step() {
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        actc1.accessMemoryObjects();
        actc1.calculateActivation();
        actc1.proc();
        actc2.accessMemoryObjects();
        actc2.calculateActivation();
        actc2.proc();
    }
    
    
     private void step2() {
        atc.accessMemoryObjects();
        atc.calculateActivation();
        atc.proc();
        actc1.accessMemoryObjects();
        actc1.calculateActivation();
        actc1.proc();
    }       
    
}
