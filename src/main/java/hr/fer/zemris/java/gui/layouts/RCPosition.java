package hr.fer.zemris.java.gui.layouts;

import java.security.InvalidParameterException;

/**
 * Class that represents position in {@link CalcLayout} layout. It is defined
 * by number of row and number of column in layout.
 * 
 * @author Vilim Staroveški
 *
 */
public class RCPosition {

	/**
	 * Row position
	 */
	private int row;
	
	/**
	 * Column position
	 */
	private int column;
	
	/**
	 * Creates new {@link RCPosition} with defined row and column positions.
	 * @param row row position.
	 * @param column column position.
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Returns row position.
	 * @return row position.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns column position.
	 * @return column position.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Returns new {@link RCPosition} with position defined in parameter constraints.
	 * Works for instances of {@link RCPosition} and {@link String}. If {@link String} is 
	 * used, then valid form is:
	 * [digitR], [digitC]
	 * where digitR is row position, digitC is column position and both are {@link String}'s 
	 * parsable to {@link Integer}.
	 * @param constraints {@link Object} that defines position of new {@link RCPosition}.
	 * @return new {@link RCPosition}.
	 */
	public static RCPosition definePosition(Object constraints) {
		
		if(constraints instanceof RCPosition) {
			return (RCPosition) constraints;
		}
		
		if(constraints instanceof String) {
			String postion = (String) constraints;
			String[] arguments = postion.split(",");
			
			if(arguments.length != 2) {
				throw new InvalidParameterException("Constraint is invalid!");
			}
			int xPosition = Integer.parseInt(arguments[0].trim());
			int yPosition = Integer.parseInt(arguments[1].trim());
			
			return new RCPosition(xPosition, yPosition);
		}
		else {
			throw new InvalidParameterException("Constraint must be an instace of RCPosition or String!");
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof RCPosition)) {
			return false;
		}
		RCPosition otherPosition = (RCPosition) other;
		return (this.row == otherPosition.getRow())
				&& (this.column == otherPosition.getColumn());
	}
}
