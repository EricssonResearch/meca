/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.mind.action;

import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.mind.motor.TestMotorCodelet;
import br.unicamp.meca.mind.perceptual.TestPerceptualCodelet;
import br.unicamp.meca.mind.sensory.TestPerceptionSensoryCodelet;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.models.ActionStep;
import br.unicamp.meca.models.ActionStepTest;
import br.unicamp.meca.system1.codelets.ActivityCodelet;
import br.unicamp.meca.system1.codelets.MotorCodelet;
import br.unicamp.meca.system1.codelets.PerceptualCodelet;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rgudwin
 */
public class TestActivityCodelet {
    
    SensoryCodelet sc;
    PerceptualCodelet pc;
    ActivityCodelet ac;
    
    @Test
    public void testAccessMemoryObjects() {
        ArrayList<String> lsc = new ArrayList();
        lsc.add("m1");
        lsc.add("m2");
        lsc.add("m3");
        ac = new Test1ActivityCodelet("teste",lsc,lsc,null,null);
        ac.accessMemoryObjects();
        assertEquals(ac.getPerceptionMemories().size(),0);
        MemoryObject mo = new MemoryObject();
        mo.setType("m1");
        ac.addInput(mo);
        mo = new MemoryObject();
        mo.setType("m2");
        ac.addInput(mo);
        mo = new MemoryObject();
        mo.setType("m3");
        ac.addInput(mo);
        ac.accessMemoryObjects();
        assertEquals(ac.getPerceptionMemories().size(),3);
        mo = new MemoryObject();
        mo.setType("m1Drive");
        ac.addInput(mo);
        mo = new MemoryObject();
        mo.setType("m2Drive");
        ac.addInput(mo);
        mo = new MemoryObject();
        mo.setType("m3Drive");
        ac.addInput(mo);
        ac.accessMemoryObjects();
        assertEquals(ac.getDriveMemories().size(),3);
        ac = new Test1ActivityCodelet("teste",null,null,"motor","soar");
        mo = new MemoryObject();
        mo.setType("motor");
        ac.addOutput(mo);
        ac.accessMemoryObjects();
        assertEquals(ac.getMotorMemory(),mo);
        mo = new MemoryObject();
        mo.setType("soar");
        ac.addBroadcast(mo);
        ac.accessMemoryObjects();
        assertEquals(ac.getBroadcastMemory(),mo);
        MemoryContainer mc = new MemoryContainer();
        mc.setType(MecaMind.ACTION_SEQUENCE_PLAN_ID);
        ac.addInput(mc);
        ac.accessMemoryObjects();
        assertEquals(ac.getActionSequencePlanMemoryContainer(),mc);
    }

    @Test
    public void testActivityCodelet() {
        sc = new TestPerceptionSensoryCodelet("sensory");
        ArrayList<String> lsc = new ArrayList();
        lsc.add("sensory");
        pc = new TestPerceptualCodelet("perception",lsc);
        lsc = new ArrayList();
        ac =  new Test1ActivityCodelet("teste",null,null,null,null);
        ac.accessMemoryObjects();
        ac.proc();
        ac.calculateActivation();
        
        ac = new Test1ActivityCodelet("teste",lsc,lsc,null,null);
        ac.accessMemoryObjects();
        ac.proc();
        ac.calculateActivation();
        
        ac = new Test1ActivityCodelet("teste",lsc,lsc,"motor",null);
        ac.accessMemoryObjects();
        ac.proc();
        ac.calculateActivation();
        
        MotorCodelet mc = new TestMotorCodelet("motor");
        ac.accessMemoryObjects();
        ac.proc();
        ac.calculateActivation();
           
    }
    
    @Test
    public void testCalculateActivation() {
        ArrayList<String> lsc = new ArrayList();
        lsc.add("m1");
        lsc.add("m2");
        lsc.add("m3");
        ac = new Test1ActivityCodelet("teste",null,lsc,null,null);
        MemoryObject mo = new MemoryObject();
        mo.setType("m1Drive");
        mo.setEvaluation(0.5);
        ac.addInput(mo);
        mo = new MemoryObject();
        mo.setType("m2Drive");
        mo.setEvaluation(0.6);
        ac.addInput(mo);
        mo = new MemoryObject();
        mo.setType("m3Drive");
        ac.addInput(mo);
        mo.setEvaluation(0.8);
        ac.accessMemoryObjects();
        ac.calculateActivation();
        double presumedActivation = (0.5+0.6+0.8)/3;
        assertEquals(ac.getActivation(),presumedActivation,0);
        MemoryContainer mc = new MemoryContainer();
        mc.setType(MecaMind.ACTION_SEQUENCE_PLAN_ID);
        ActionStep as[] = new ActionStep[1];
        ActionSequencePlan asp = new ActionSequencePlan(as);
        mc.setI(asp,0.2);
        ac.addInput(mc);
        ac.accessMemoryObjects();
        ac.calculateActivation();
        // Because the ActionStep is null, the evaluation is 0
        assertEquals(ac.getActivation(),0,0);
        as[0] = new ActionStepTest("teste");
        // Now that there is an action step with the same name as the ActivityCodelet, it should be 0.2
        ac.calculateActivation();
        assertEquals(ac.getActivation(),0.2,0);
    }
    
}
