/**
 * 
 */
package br.unicamp.meca.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author andre
 *
 */
public class ActionSequencePlanTest {
    
    ActionStep as1 = new ActionStepTest("Land");
    ActionStep as2 = new ActionStepTest("Stop");
    ActionSequencePlan landAndStopSequencePlan = new ActionSequencePlan(new ActionStep[] {as1,as2});
	
    public void setUp() {
        System.out.println("########## Action Sequence Plan TESTS ##########");
    }
    
    @Test
    public void testGetCurrentActionIdIndex() {
        landAndStopSequencePlan.setCurrentActionIdIndex(0);
        int currentStep = landAndStopSequencePlan.getCurrentActionIdIndex();
        assertEquals(currentStep,0);
        landAndStopSequencePlan.setCurrentActionIdIndex(1);
        currentStep = landAndStopSequencePlan.getCurrentActionIdIndex();
        assertEquals(currentStep,1);
    }

    @Test
    public void testGetCurrentActionId() {
        landAndStopSequencePlan.setCurrentActionIdIndex(0);                
    	assertEquals(landAndStopSequencePlan.getCurrentActionStep().getActionId(), "Land");
        
    	landAndStopSequencePlan.setCurrentActionIdIndex(1);
    	assertEquals(landAndStopSequencePlan.getCurrentActionStep().getActionId(), "Stop");
    }
    
    @Test
    public void testGetCurrentActionStep() {
        landAndStopSequencePlan.setCurrentActionIdIndex(0);
        ActionStep as = landAndStopSequencePlan.getCurrentActionStep();
        assertEquals(as,as1);
        landAndStopSequencePlan.setCurrentActionIdIndex(1);
        as = landAndStopSequencePlan.getCurrentActionStep();
        assertEquals(as,as2);
    }
    
    @Test
    public void testLastActionStep() {
        landAndStopSequencePlan.setCurrentActionIdIndex(0);
        ActionStep lastAS = landAndStopSequencePlan.getLastExecutedActionStep();
        assertEquals(lastAS,as2);
        assertEquals(lastAS.executed,false);
        assertEquals(lastAS.needsConclusion,false);
        
        landAndStopSequencePlan.setCurrentActionIdIndex(1);
        lastAS = landAndStopSequencePlan.getLastExecutedActionStep();
        assertEquals(lastAS,as1);
        assertEquals(lastAS.executed,false);
        assertEquals(lastAS.needsConclusion,false);
    }
    
    @Test
    public void testGoToNextAction() {
        landAndStopSequencePlan.setCurrentActionIdIndex(0);
        ActionStep as = landAndStopSequencePlan.getCurrentActionStep();
        assertEquals(as,as1);
        assertEquals(as1.executed,false);
        
        landAndStopSequencePlan.gotoNextAction();
        as = landAndStopSequencePlan.getCurrentActionStep();
        assertEquals(as,as2);
        assertEquals(as1.executed,true);
        assertEquals(as2.executed,false);
        landAndStopSequencePlan.gotoNextAction();
        as = landAndStopSequencePlan.getCurrentActionStep();
        assertEquals(as,as1);
        assertEquals(as2.executed,true);
    }
    
    @Test 
    public void testResetPlan() {
        as1.executed = true;
        as2.executed = true;
        landAndStopSequencePlan.resetPlan();
        for (ActionStep ass : landAndStopSequencePlan.getActionStepSequence()) {
            assertEquals(ass.executed,false);
            assertEquals(ass.needsConclusion,false);
        }
    }
    
    @Test
    public void testToString() {
       String s = landAndStopSequencePlan.toString();
       assertEquals(s,"{Land, Stop}");
    }
}
