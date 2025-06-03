package targetProfitPolicy;

import coreSystem.CoreSystem;
import exceptions.PermissionDeniedException;
import exceptions.UnreachableTargetProfitException;

/**
 * TargetProfitServiceFee is a strategy implementation of {@link TargetProfitPolicy}
 * that adjusts the service fee to meet a specified target profit.
 */
public class TargetProfitServiceFee implements TargetProfitPolicy {

    // =============================================================
    // Methods
    // =============================================================

    /**
     * Computes the necessary service fee to reach the given target profit,
     * based on last month's income, markup percentage, delivery cost, and number of orders.
     *
     * @param coreSystem    the core system
     * @param targetProfit  the target profit to achieve
     * @return the calculated service fee per order
     * @throws UnreachableTargetProfitException if no orders exist or service fee would be negative
     * @throws PermissionDeniedException if permission to access needed data is denied
     */
    @Override
    public double computeTargetProfitPolicyParam(CoreSystem coreSystem, double targetProfit)
            throws UnreachableTargetProfitException {
        
        double totalIncomeLM = coreSystem.computeTotalIncomeLastMonth();
        double markUp = CoreSystem.getMarkUpPercentage();
        double deliveryCost = CoreSystem.getDeliveryCost();
        int numberOfOrdersLM = coreSystem.computeNumberOfOrdersLastMonth();
        double serviceFee = -1;

        if (numberOfOrdersLM != 0) {
            serviceFee = - (totalIncomeLM * markUp - numberOfOrdersLM * deliveryCost - targetProfit) / numberOfOrdersLM;
        } else {
            throw new UnreachableTargetProfitException("Target Profit is Unreachable, since there are no Orders Last Month.");
        }

        if (serviceFee >= 0) {
            return serviceFee;
        } else {
            throw new UnreachableTargetProfitException("Target Profit is Unreachable, ServiceFee must be negative to reach said Target");
        }
    }

    /**
     * Sets the service fee in the system to match the value computed for the target profit.
     *
     * @param coreSystem    the core system to update
     * @param targetProfit  the target profit to achieve
     * @throws UnreachableTargetProfitException if target is unreachable
     * @throws PermissionDeniedException if permission to set service fee is denied
     */
    @Override
    public void setParam(CoreSystem coreSystem, double targetProfit)
            throws UnreachableTargetProfitException {
        CoreSystem.setServiceFee(computeTargetProfitPolicyParam(coreSystem, targetProfit));
    }
}
