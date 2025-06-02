package targetProfitPolicy;
import exceptions.*;

public class TargetProfitPolicyFactory {
    public TargetProfitPolicyFactory() {
    }

    public static TargetProfitPolicy createTargetProfitPolicy(TargetProfitPolicyType targetProfitPolicyType) throws UndefinedPolicyException{
        switch (targetProfitPolicyType) {
            case DELIVERYCOST:
                return new TargetProfitDeliveryCost();
            case MARKUP:
                return new TargetProfitMarkUp();
            case SERVICEFEE:
                return new TargetProfitServiceFee();
            default:
                throw new UndefinedPolicyException("Unknown policy type: " + targetProfitPolicyType);
        }
    }
}