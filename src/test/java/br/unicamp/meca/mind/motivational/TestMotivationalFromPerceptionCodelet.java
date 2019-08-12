/**
 * 
 */
package br.unicamp.meca.mind.motivational;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.motivational.Drive;
import br.unicamp.meca.system1.codelets.MotivationalCodelet;

/**
 * @author andre
 *
 */
public class TestMotivationalFromPerceptionCodelet extends MotivationalCodelet {

	public TestMotivationalFromPerceptionCodelet(String id, double level, double priority, double urgencyThreshold,
			ArrayList<String> sensoryCodeletsIds, HashMap<String, Double> motivationalCodeletsIds)
			throws CodeletActivationBoundsException {
		super(id, level, priority, urgencyThreshold, sensoryCodeletsIds, motivationalCodeletsIds);
	}

	@Override
	public double calculateSimpleActivation(List<Memory> sensoryMemories) {
		
		double activation = 0.1d;
		
		if (sensoryMemories != null && sensoryMemories.size() > 0) {
			
			for(Memory memory : sensoryMemories) {
				if(memory.getI() != null && memory.getName().equals("TestPerceptionSensoryCodelet")) {
					
					//if there is something, we want to interact with it
					activation = 0.95d;						
				}
			}
		}
		
		if(activation<0.1d)
			activation=0.1d;

		if(activation>1.0d)
			activation=1.0d;

		
		return activation;
	}

	@Override
	public double calculateSecundaryDriveActivation(List<Memory> sensors, List<Drive> listOfDrives) {
		return 0;
	}

}
