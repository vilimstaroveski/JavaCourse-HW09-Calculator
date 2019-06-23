package hr.fer.zemris.java.gui.calc.containers;

import hr.fer.zemris.java.gui.calc.Operation;

/**
 * Container object for {@link String} representation of 
 * operators such as +, -, *, / and =.
 * 
 * @author Vilim Staroveški
 *
 */
public class OperatorContainer implements IContainer {

	/**
	 * {@link String} representation of operator inside this container.
	 */
	private String value;
	
	/**
	 * Symbol that is enumeration of type {@link Operation} used 
	 * for describing stored value.
	 */
	private Operation symbol;
	
	/**
	 * Creates new {@link OperatorContainer} and puts operator inside.
	 * @param oper {@link String} representation of operator 
	 * @param symbol {@link Operation} representation of this operator.
	 */
	public OperatorContainer(String oper, Operation symbol) {
		this.value = oper;
		this.symbol = symbol;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns symbol enumeration of this operator.
	 * @return symbol enumeration of this operator.
	 */
	public Operation getSymbol() {
		return this.symbol;
	}

}
