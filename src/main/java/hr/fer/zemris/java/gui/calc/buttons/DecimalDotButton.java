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
 * Class inherits {@link JButton} and adds decimal dot to peek {@link NumberContainer} element on stack.
 * @author Vilim Staroveški
 *
 */
public class DecimalDotButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 2154180520957029513L;

	/**
	 * Creates new {@link DecimalDotButton} and adds new {@link DecimalDotListener} to new instance.
	 * @param decimalDot symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public DecimalDotButton(String decimalDot, Calculator parent) {
		
		// Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(decimalDot, null);
		
		addMouseListener(new DecimalDotListener(parent));
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link DecimalDotButton}.
	 * @author Vilim Staroveški
	 *
	 */
	private class DecimalDotListener implements MouseListener {
		
		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		/**
		 * Creates new {@link DecimalDotListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 */
		public DecimalDotListener(Calculator parent) {
			this.parent = parent;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if(parent.getStack().size() == 0) {
				parent.getStack().push(new NumberContainer("0."));
			}
			
			IContainer peekElem = parent.getStack().peek();
			if(peekElem instanceof NumberContainer) {
				NumberContainer numberOnPeek = (NumberContainer) peekElem;
				numberOnPeek.putDecimalDot();
				parent.updateScreen();
				return;
			}
			else if(peekElem instanceof OperatorContainer) {
				if( ((OperatorContainer)peekElem).getSymbol() == Operation.EQUALS ) {
					parent.getStack().pop();
					parent.getStack().pop();
				}
				NumberContainer newNumCon = new NumberContainer("0.");
				parent.getStack().push(newNumCon);
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
