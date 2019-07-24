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
    	
    	ActionSequencePlan landAndStopSequencePlan = new ActionSequencePlan(new String[] {"Land","Stop"});
    	
    	assertEquals(landAndStopSequencePlan.getCurrentActionId(), "Land");
    	
    	landAndStopSequencePlan.setCurrentActionIdIndex(1);
    	
    	assertEquals(landAndStopSequencePlan.getCurrentActionId(), "Stop");
    }
}
