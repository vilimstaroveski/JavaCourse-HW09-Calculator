package hr.fer.zemris.java.gui.calc.containers;

/**
 * Class that represents container for {@link String} representation
 * of numbers.
 * 
 * @author Vilim Staroveški
 *
 */
public class NumberContainer implements IContainer {

	/**
	 * Value stored in this container.
	 */
	private String value;
	
	/**
	 * Creates new {@link NumberContainer} with given {@link String}
	 * put in it.
	 * @param digit value that will be stored.
	 */
	public NumberContainer(String digit) {
		this.value = digit;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * Modifies stored value, by concatenating given parameter to 
	 * stored value.
	 * @param digit concatenated value.
	 */
	public void modify(String digit) {
		this.value += digit;
	}

	/**
	 * Puts decimal dot on the end of stored value.
	 */
	public void putDecimalDot() {
		if(value.contains(".")) {
			return;
		}
		else {
			this.value += ".";
		}
	}

	/**
	 * Converts stored value from positive to negative and other way around.
	 */
	public void changePositivity() {
		if(value.contains("-")) {
			this.value = value.substring(1);
		}
		else {
			this.value = "-".concat(this.value);
		}
	}
}
