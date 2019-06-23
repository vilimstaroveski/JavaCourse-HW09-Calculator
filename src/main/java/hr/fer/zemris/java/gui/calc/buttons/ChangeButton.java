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
 * Class inherits {@link JButton} and changes the positivity of peek {@link NumberContainer} element on stack.
 * @author Vilim Staroveški
 *
 */
public class ChangeButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -2769885840973402096L;

	/**
	 * Creates new {@link ChangeButton} and adds new {@link ChangeListener} to new instance.
	 * @param change symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public ChangeButton(String change, Calculator parent) {

		// Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(change, null);
		
		addMouseListener(new ChangeListener(parent));
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link ChangeButton}.
	 * @author Vilim Staroveški
	 *
	 */
	private class ChangeListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		/**
		 * Creates new {@link ChangeListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 */
		public ChangeListener(Calculator parent) {
			this.parent = parent;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if(parent.getStack().size() == 0) {
				return;
			}
			IContainer peekElem = parent.getStack().peek();
			if(peekElem instanceof OperatorContainer) {
				if( ((OperatorContainer)peekElem).getSymbol() == Operation.EQUALS ) {
					parent.getStack().pop();
					NumberContainer numberOnPeek = (NumberContainer) peekElem;
					numberOnPeek.changePositivity();
					parent.updateScreen();
				}
				return;
			}
			else if(peekElem instanceof NumberContainer) {
				NumberContainer numberOnPeek = (NumberContainer) peekElem;
				numberOnPeek.changePositivity();
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
