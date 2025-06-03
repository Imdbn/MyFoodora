package targetProfitPolicy;
import coreSystem.*;
import exceptions.*;

public interface TargetProfitPolicy {
	public abstract double computeTargetProfitPolicyParam(CoreSystem coreSystem,double targetProfit)throws UnreachableTargetProfitException ;
	public abstract void setParam(CoreSystem coreSystem,double targetProfit)throws UnreachableTargetProfitException ;
}