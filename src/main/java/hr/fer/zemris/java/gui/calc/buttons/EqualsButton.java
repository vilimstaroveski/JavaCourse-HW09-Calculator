package hr.fer.zemris.java.gui.calc.buttons;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hr.fer.zemris.java.gui.calc.Calculation;
import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.Operation;
import hr.fer.zemris.java.gui.calc.containers.IContainer;
import hr.fer.zemris.java.gui.calc.containers.NumberContainer;
import hr.fer.zemris.java.gui.calc.containers.OperatorContainer;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

/**
 * Class inherits {@link JButton} and puts result of expression placed on {@link Calculator}'s stack
 * @author Vilim Staroveški
 *
 */
public class EqualsButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -8966791621710821819L;

	/**
	 * Creates new {@link EqualsButton} and adds new {@link EqualsListener} to new instance.
	 * @param equal symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public EqualsButton(String equal, Calculator parent) {

		// Create the model
	    setModel(new DefaultButtonModel());
	    // initialize
	    init(equal, null);
	    
	    addMouseListener(new EqualsListener(parent));
	}

	/**
	 * Class implementation of {@link MouseListener} that is added to {@link EqualsButton}.
	 * @author Vilim Staroveški
	 *
	 */
	private class EqualsListener implements MouseListener {
		
		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;

		/**
		 * Creates new {@link EqualsListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 */
		public EqualsListener(Calculator parent) {
			this.parent = parent;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if(parent.getStack().size() < 3) {
				return;
			}
			IContainer peekElem = parent.getStack().peek();
			if(peekElem instanceof OperatorContainer) {
				return;
			}
			else if(peekElem instanceof NumberContainer) {
				
				NumberContainer secondArg = (NumberContainer) parent.getStack().pop();
				OperatorContainer oper = (OperatorContainer) parent.getStack().pop();
//				oper.setInverse(parent.inverseFunctions());
				NumberContainer firstArg = (NumberContainer) parent.getStack().pop();
				parent.getStack().push(new Calculation(firstArg, secondArg, oper, parent.inverseFunctions()).getResult());
				parent.getStack().push(new OperatorContainer("=", Operation.EQUALS));
				parent.setPendingOperation(false);
				parent.updateScreen();
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
