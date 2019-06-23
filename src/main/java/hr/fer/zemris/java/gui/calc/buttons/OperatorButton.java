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
 * Class inherits {@link JButton} and puts new {@link OperatorContainer} on
 * stack. If there is an pending operation on stack, it calculates that
 * operation and stores result on stack.
 * 
 * @author Vilim Staroveški
 *
 */
public class OperatorButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 3236169916099532510L;

	/**
	 * Creates new {@link OperatorButton} and adds new {@link OperatorListener}
	 * to new instance.
	 * 
	 * @param operator
	 *            symbol of this button.
	 * @param parent
	 *            {@link Calculator} for which this button is added for.
	 * @param operationType
	 *            type of operation
	 */
	public OperatorButton(String operator, Operation operationType,
			Calculator parent) {

		// Create the model
		setModel(new DefaultButtonModel());
		// initialize
		init(operator, null);

		addMouseListener(new OperatorListener(operator, operationType, parent));
	}

	/**
	 * Class implementation of {@link MouseListener} that is added to
	 * {@link OperatorListener}.
	 * 
	 * @author Vilim Staroveški
	 *
	 */
	private class OperatorListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;

		/**
		 * Symbol of operator.
		 */
		private String operator;

		/**
		 * Operation type
		 */
		private Operation operationType;

		/**
		 * Creates new {@link ChangeListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 * @param operator symbol operator.
		 * @param operationType type of operation.
		 */
		public OperatorListener(String operator, Operation operationType,
				Calculator parent) {
			this.parent = parent;
			this.operator = operator;
			this.operationType = operationType;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if (parent.getStack().size() == 0) {
				return;
			}

			IContainer peekElem = parent.getStack().peek();
			OperatorContainer opCon = new OperatorContainer(operator,
					operationType);
			if (peekElem instanceof OperatorContainer) {

				parent.getStack().pop();
				parent.getStack().push(opCon);
			} else if (peekElem instanceof NumberContainer) {

				if (parent.pendingOperation()) {

					NumberContainer secondArg = (NumberContainer) parent
							.getStack().pop();
					OperatorContainer oper = (OperatorContainer) parent
							.getStack().pop();
					NumberContainer firstArg = (NumberContainer) parent
							.getStack().pop();
					parent.getStack().push(
							new Calculation(firstArg, secondArg, oper, parent.inverseFunctions())
									.getResult());
					parent.getStack().push(opCon);
					parent.setPendingOperation(true);
					parent.updateScreen();
				} else {

					parent.getStack().push(opCon);
					parent.setPendingOperation(true);
					return;
				}
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
