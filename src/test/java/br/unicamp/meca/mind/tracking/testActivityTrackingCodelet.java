/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.mind.tracking;

import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.models.ActionStep;
import br.unicamp.meca.models.ActionStepTest;
import br.unicamp.meca.system1.codelets.ActivityTrackingCodelet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rgudwin
 */
public class testActivityTrackingCodelet {
    ActivityTrackingCodelet atc;
    
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
    
}
