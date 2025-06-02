package fidelityCards;

import exceptions.UndefinedFidelityCardException;


public class FidelityCardFactory {
	
	public static FidelityCard createFidelityCard(FidelityCardType fidelityCard) throws UndefinedFidelityCardException {
		switch (fidelityCard) {
        case BASIC:
            return new BasicFidelityCard();
        case LOTTERY:
            return new LotteryFidelityCard();
        case POINT :
            return new PointFidelityCard();
        default :
        	throw new UndefinedFidelityCardException("Sorry but the Fidelity Card you are trying to create doesn't exist.");
            
    }
}
}