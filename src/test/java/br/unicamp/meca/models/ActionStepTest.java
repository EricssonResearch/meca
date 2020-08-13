/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.models;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rgudwin
 */
public class ActionStepTest {
    
    
     public void setUp() {
        System.out.println("########## ActionStep TESTS ##########");
    }
    
    @Test
    public void testActionStep() {
        ActionStep as1 = new ActionStep();
        assertEquals(as1.getNumberOfParameters(),0);
        ActionStep as2 = new ActionStep("TestAction");
        assertEquals(as2.getNumberOfParameters(),0);
        assertEquals(as2.getActionId(),"TestAction");
        as2.setParameter("param", "value");
        assertEquals(as2.getNumberOfParameters(),1);
        assertEquals(as2.getParameter("param"),"value");
        as2.setParameter("param", "value2");
        assertEquals(as2.getParameter("param"),"value2");
        as2.unsetParameter("param");
        assertEquals(as2.getNumberOfParameters(),0);
    }


    
}
