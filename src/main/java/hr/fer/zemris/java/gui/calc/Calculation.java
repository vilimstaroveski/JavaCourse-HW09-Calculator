package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.containers.NumberContainer;
import hr.fer.zemris.java.gui.calc.containers.OperatorContainer;

/**
 * Class used for calculation of expressions given by {@link Calculator}.
 * @author Vilim Staroveški
 *
 */
public class Calculation {

	/**
	 * First operand in calculation.
	 */
	private double firstOperand;
	
	/**
	 * Second operand in calculation.
	 */
	private double secondOperand;
	
	/**
	 * Type of operation, expected enumeration {@link Operation}
	 */
	private Operation operationType;
	
	/**
	 * Flag, for using inverse functions or not.
	 */
	private boolean inverse;
	
	/**
	 * Creates new {@link Calculation} with 2 operands and operation type. Typically used for
	 * simple calculation such as + - * / 
	 * @param firstArg  First operand in calculation.
	 * @param secondArg Second operand in calculation.
	 * @param oper type of operation defined within {@link OperatorContainer}.
	 */
	public Calculation(NumberContainer firstArg, NumberContainer secondArg, OperatorContainer oper, boolean inverseFunction) {
		
		this.firstOperand = Double.parseDouble(firstArg.getValue());
		this.secondOperand = Double.parseDouble(secondArg.getValue());
		this.operationType = oper.getSymbol();
		this.inverse = inverseFunction;
	}
	
	/**
	 * Creates new {@link Calculation} with 1 operand and operation type. Used for functions such as
	 * sin, cos, tan, ctg, ln, log, n-th power, 1/x
	 * @param firstArg First operand in calculation.
	 * @param operationType Type of operation, expected enumeration {@link Operation}
	 * @param inverseFunction flag for using inverse functions or no.
	 */
//	public Calculation(NumberContainer firstArg, Operation operationType, boolean inverseFunction) {
//		
//		this.firstOperand = Double.parseDouble(firstArg.getValue());
//		this.inverse = inverseFunction;
//		this.operationType = operationType;
//	}
	
	/**
	 * Returns result of this {@link Calculation}.
	 * @return result of this {@link Calculation}.
	 */
	@SuppressWarnings("incomplete-switch")
	public NumberContainer getResult() {
		
		switch(operationType) {
		case PLUS:			return new NumberContainer(String.valueOf(firstOperand+secondOperand));
		case MINUS:			return new NumberContainer(String.valueOf(firstOperand-secondOperand));
		case TIMES:			return new NumberContainer(String.valueOf(firstOperand*secondOperand));
		case DIVIDE:		return new NumberContainer(String.valueOf(firstOperand/secondOperand));
		case RECIPROCAL:	return new NumberContainer(String.valueOf(1/firstOperand));
		}
		
		if(!inverse) {
			switch(operationType) {
			case SIN:				return new NumberContainer(String.valueOf(Math.sin(firstOperand)));
			case COS:				return new NumberContainer(String.valueOf(Math.cos(firstOperand)));
			case TAN:				return new NumberContainer(String.valueOf(Math.tan(firstOperand)));
			case CTG:				return new NumberContainer(String.valueOf(1/Math.tan(firstOperand)));
			case LOG:				return new NumberContainer(String.valueOf(Math.log10(firstOperand)));
			case LN:				return new NumberContainer(String.valueOf(Math.log(firstOperand)));
			case NPOWER:			return new NumberContainer(String.valueOf(Math.pow(firstOperand, secondOperand)));
			default:				return null;
			}
		}
		else {
			switch(operationType) {
			case SIN:				return new NumberContainer(String.valueOf(Math.asin(firstOperand)));
			case COS:				return new NumberContainer(String.valueOf(Math.acos(firstOperand)));
			case TAN:				return new NumberContainer(String.valueOf(Math.atan(firstOperand)));
			case CTG:				return new NumberContainer(String.valueOf(1/Math.atan(firstOperand)));
			case LOG:				return new NumberContainer(String.valueOf(Math.pow(10, firstOperand)));
			case LN:				return new NumberContainer(String.valueOf(Math.pow(Math.E, firstOperand)));
			case NPOWER:			return new NumberContainer(String.valueOf(Math.pow(firstOperand, (double)1/secondOperand)));
			default:				return null;
			}
		}
	}
	
}
