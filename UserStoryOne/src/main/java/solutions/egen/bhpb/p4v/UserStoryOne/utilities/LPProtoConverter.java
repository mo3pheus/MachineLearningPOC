package solutions.egen.bhpb.p4v.UserStoryOne.utilities;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.joptimizer.optimizers.LPOptimizationRequest;
import com.joptimizer.optimizers.LPPrimalDualMethod;

import solutions.egen.bhpb.UserStoryOne.LinearProgrammingModule.LPProto;
import solutions.egen.bhpb.UserStoryOne.LinearProgrammingModule.LPProto.Constraint;
import solutions.egen.bhpb.UserStoryOne.LinearProgrammingModule.LPProto.Objective;

public class LPProtoConverter {
	public static void main(String[] args) throws Exception {

		System.out.println("Demonstrating common handshake/ protocol!");
		LPProto.Builder lpBuilder = LPProto.newBuilder();
		Constraint.Builder constraintBuilder = Constraint.newBuilder();
		Objective.Builder objectiveBuilder = Objective.newBuilder();

		// objective function is 5x + 8y
		objectiveBuilder.setDescription(" 5x + 7y");
		objectiveBuilder.setMaximize(true);
		objectiveBuilder.setXCoef(5.0d);
		objectiveBuilder.setYCoef(7.0d);
		objectiveBuilder.setSelected(true);
		lpBuilder.addObjectives(objectiveBuilder.build());

		// constraint 2) 4x + 9y >= 0
		List<Constraint> constraints = new ArrayList<Constraint>();
		constraintBuilder.setDescription("4x + 9y >= 0");
		constraintBuilder.setXCoef(4.0d);
		constraintBuilder.setYCoef(9.0d);
		constraints.add(constraintBuilder.build());

		// constraint 3) 3x - 9.2y >= 0
		constraintBuilder.setDescription("3x - 9.2y >= 0");
		constraintBuilder.setXCoef(3.0d);
		constraintBuilder.setYCoef(-9.2d);
		constraints.add(constraintBuilder.build());

		// constraint 4) y >= 0
		constraintBuilder.setDescription("x >= 0");
		constraintBuilder.setXCoef(0.0d);
		constraintBuilder.setYCoef(1.0d);
		constraints.add(constraintBuilder.build());

		// constraint 5) x >= 0
		constraintBuilder.setDescription("y >= 0");
		constraintBuilder.setXCoef(1.0d);
		constraintBuilder.setYCoef(0.0d);
		constraints.add(constraintBuilder.build());

		// add constraints to LPP
		lpBuilder.addAllConstraints(constraints);
		LPProto sendMe = lpBuilder.build();

		// display sendMe
		System.out.println(sendMe);

		System.out.println("#########################################");

		// parse sendME
		LPProto parsedObject = LPProto.parseFrom(sendMe.toByteArray());
		for (int i = 0; i < parsedObject.getObjectivesCount(); i++) {
			Objective objective = parsedObject.getObjectives(i);
			System.out.println(objective.getDescription());
		}

		for (int i = 0; i < parsedObject.getConstraintsCount(); i++) {
			Constraint constraint = parsedObject.getConstraints(i);
			System.out.println(constraint.getDescription());
		}

		System.out.println("#########################################");
		System.out.println(" Example of LPP solved ");
		// pass the object to the linearProgramming function and get a solution.
		byte[] solution = getLPPSolution(sendMe);
		System.out.println(LPProto.parseFrom(solution));
	}

	/**
	 * @param protoObject
	 * @return
	 * 
	 * 		This function takes a protocolBuffer java object and returns a
	 *         byte array representing the protocol buffer with a solution.
	 * @throws Exception
	 */
	public static byte[] getLPPSolution(final LPProto protoObject) throws Exception {
		// Objective function (plane)
		// Objective function
		Objective objectiveFunction = null;
		for (int i = 0; i < protoObject.getObjectivesCount(); i++) {
			if (protoObject.getObjectives(i).getSelected()) {
				objectiveFunction = protoObject.getObjectives(i);
				break;
			}
		}

		double[] c = new double[] { objectiveFunction.getXCoef(), objectiveFunction.getYCoef() };

		// Inequalities constraints
		double[][] g = new double[protoObject.getConstraintsCount()][2];
		double[] h = new double[2];
		for (int i = 0; i < protoObject.getConstraintsCount(); i++) {
			Constraint cons = protoObject.getConstraints(i);
			double[] c1 = new double[] { cons.getXCoef(), cons.getYCoef() };
			g[i] = c1;
		}

		// Bounds on variables
		double[] lb = new double[] { 0, 0 };
		double[] ub = new double[] {100000.0d, 100000.0d };

		// optimization problem
		LPOptimizationRequest or = new LPOptimizationRequest();
		or.setC(c);
		or.setG(g);
		or.setH(h);
		or.setLb(lb);
		or.setUb(ub);
		or.setDumpProblem(true);

		// optimization
		LPPrimalDualMethod opt = new LPPrimalDualMethod();

		opt.setLPOptimizationRequest(or);
		int returnCode = opt.optimize();
		System.out.println("Return code = " + returnCode);

		double[] sol = opt.getOptimizationResponse().getSolution();
		if (sol == null) {
			throw new RuntimeException("No Solution found!");
		}

		// set the solution
		LPProto.Builder lpBuilder = LPProto.newBuilder();
		lpBuilder.setOutput1(sol[0]);
		lpBuilder.setOutput2(sol[1]);

		for (int i = 0; i < protoObject.getConstraintsCount(); i++) {
			lpBuilder.setConstraints(i, protoObject.getConstraints(i));
		}

		for (int i = 0; i < protoObject.getObjectivesCount(); i++) {
			lpBuilder.setObjectives(i, protoObject.getObjectives(i));
		}

		return protoObject.toByteArray();
	}
}
