package targetProfitPolicy;

import coreSystem.CoreSystem;
import exceptions.*;


public class TargetProfitMarkUp implements TargetProfitPolicy {
	@Override
	public double computeTargetProfitPolicyParam(CoreSystem coreSystem,double targetProfit)throws UnreachableTargetProfitException {
		double totalIncomeLM = coreSystem.computeTotalIncomeLastMonth();
		double deliveryCost = CoreSystem.getDeliveryCost();
		double serviceFee = CoreSystem.getServiceFee();
		int numberOfOrdersLM = coreSystem.computeNumberOfOrdersLastMonth();
		double markUp = -1;
		if(totalIncomeLM != 0) {
			markUp = (deliveryCost*numberOfOrdersLM - numberOfOrdersLM * serviceFee + targetProfit) / totalIncomeLM;
		}
		else throw new UnreachableTargetProfitException("Target Profit is Unreachable , since there was no Income Last Month.");
		if(markUp >= 0) {
			return markUp;
			
		}
		else throw new UnreachableTargetProfitException("Target Profit is Unreachable, markUp Percentage must be negative to reach said Target");
	}
	
	@Override 
	public void setParam(CoreSystem coreSystem,double targetProfit) throws UnreachableTargetProfitException {
		CoreSystem.setMarkUpPercentage(computeTargetProfitPolicyParam(coreSystem,targetProfit));
	}
}
