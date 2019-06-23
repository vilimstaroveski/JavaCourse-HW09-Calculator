package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.Operation;
import hr.fer.zemris.java.gui.calc.containers.IContainer;
import hr.fer.zemris.java.gui.calc.containers.NumberContainer;
import hr.fer.zemris.java.gui.calc.containers.OperatorContainer;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

/**
 * Class inherits {@link JButton} and adds new digit to peek {@link NumberContainer} element on {@link Calculator} stack.
 * @author Vilim Staroveški
 *
 */
public class DigitButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 2378438346473066968L;

	/**
	 * Creates new {@link DigitButton} and adds new {@link DigitListener} to new instance.
	 * @param digit symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public DigitButton(String digit, Calculator parent) {
		
		 // Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(digit, null);
		
		addMouseListener(new DigitListener(digit, parent));
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link DigitListener}
	 * @author Vilim Staroveški
	 *
	 */
	private class DigitListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		/**
		 * Digit that is added to element.
		 */
		private String digit;
		
		/**
		 * Creates new {@link DigitListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 * @param digit digit that is added to element.
		 */
		public DigitListener(String digit, Calculator parent) {
			this.parent = parent;
			this.digit = digit;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(parent.getStack().size() == 0) {
				parent.getStack().push(new NumberContainer(""));
			}
			IContainer peekElem = parent.getStack().peek();
			if(peekElem instanceof OperatorContainer) {
				
				if( ((OperatorContainer)peekElem).getSymbol() == Operation.EQUALS ) {
					parent.getStack().pop();
					parent.getStack().pop();
				}
				NumberContainer numCon = new NumberContainer(digit);
				parent.getStack().push(numCon);
				parent.updateScreen();
				return;
			}
			else if(peekElem instanceof NumberContainer) {
				
				NumberContainer numConPeek = (NumberContainer) peekElem;
				numConPeek.modify(digit);
				parent.updateScreen();
				return;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
	
}
