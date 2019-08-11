/**
 * 
 */
package br.unicamp.meca.mind;

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
public class TestMotivationalCodelet extends MotivationalCodelet {

	public TestMotivationalCodelet(String id, double level, double priority, double urgencyThreshold,
			ArrayList<String> sensoryCodeletsIds, HashMap<String, Double> motivationalCodeletsIds)
			throws CodeletActivationBoundsException {
		super(id, level, priority, urgencyThreshold, sensoryCodeletsIds, motivationalCodeletsIds);
	}

	@Override
	public double calculateSimpleActivation(List<Memory> sensors) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calculateSecundaryDriveActivation(List<Memory> sensors, List<Drive> listOfDrives) {
		// TODO Auto-generated method stub
		return 0;
	}

}
