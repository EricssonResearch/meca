package br.unicamp.meca.models;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.models.ActionStep;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ActionStepTest extends ActionStep {
    
    public ActionStepTest() {
        
    }
    
    public ActionStepTest(String s) {
        super(s);
    }
    
     public void setUp() {
        System.out.println("########## ActionStep TESTS ##########");
    }
     
    @Override 
    public boolean stopCondition(List<Memory> perceptions) {
        return(false);
    }
    
}