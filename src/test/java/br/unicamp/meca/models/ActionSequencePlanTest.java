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
	
    public void setUp() {
        System.out.println("########## Action Sequence Plan TESTS ##########");
    }

    @Test
    public void testGetCurrentActionId() {
    	
    	ActionStep as1 = new ActionStepTest("Land");
        ActionStep as2 = new ActionStepTest("Stop");
        ActionSequencePlan landAndStopSequencePlan = new ActionSequencePlan(new ActionStep[] {as1,as2});
    	
    	assertEquals(landAndStopSequencePlan.getCurrentActionStep().getActionId(), "Land");
    	
    	landAndStopSequencePlan.setCurrentActionIdIndex(1);
    	
    	assertEquals(landAndStopSequencePlan.getCurrentActionStep().getActionId(), "Stop");
    }
}
