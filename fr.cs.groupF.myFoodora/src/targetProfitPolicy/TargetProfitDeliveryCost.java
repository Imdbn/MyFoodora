package targetProfitPolicy;

import coreSystem.CoreSystem;
import exceptions.*;

public class TargetProfitDeliveryCost implements TargetProfitPolicy {
	
	@Override
	public double computeTargetProfitPolicyParam (CoreSystem coreSystem , double targetProfit) throws UnreachableTargetProfitException{
		double totalIncomeLM = coreSystem.computeTotalIncomeLastMonth();
		double markUp = CoreSystem.getMarkUpPercentage();
		double serviceFee = CoreSystem.getServiceFee();
		int numberOfOrdersLM = coreSystem.computeNumberOfOrdersLastMonth();
		double deliveryCost = -1;
		if(numberOfOrdersLM != 0) {
			deliveryCost = (totalIncomeLM*markUp + numberOfOrdersLM * serviceFee - targetProfit) / numberOfOrdersLM;
		}
		else throw new UnreachableTargetProfitException("Target Profit is Unreachable , since there are no Orders Last Month.");
		if(deliveryCost >= 0) {
			return deliveryCost;
			
		}
		else throw new UnreachableTargetProfitException("Target Profit is Unreachable, Delivery Cost must be negative to reach said Target");
	}
	@Override 
	public void setParam(CoreSystem coreSystem,double targetProfit) throws UnreachableTargetProfitException {
		CoreSystem.setDeliveryCost(computeTargetProfitPolicyParam(coreSystem,targetProfit));
	}
}
