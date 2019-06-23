package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.Operation;
import hr.fer.zemris.java.gui.calc.StackOperation;
import hr.fer.zemris.java.gui.calc.containers.IContainer;
import hr.fer.zemris.java.gui.calc.containers.NumberContainer;
import hr.fer.zemris.java.gui.calc.containers.OperatorContainer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

/**
 * Class inherits {@link JButton} and makes an stack operation such as push and pop.
 * @author Vilim Staroveški
 *
 */
public class StackButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 104623065603588408L;

	/**
	 * Creates new {@link StackButton} and adds new {@link StackListener} to new instance.
	 * @param name symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 * @param operation operation type.
	 */
	public StackButton(String name, StackOperation operation, Calculator parent) {

		 // Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(name, null);
		
		addMouseListener(new StackListener(operation, parent));
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link StackButton}.
	 * @author Vilim Staroveški
	 *
	 */
	private class StackListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		private StackOperation operation;
		
		/**
		 * Creates new {@link StackListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 * @param operation type of operation.
		 */
		public StackListener(StackOperation operation, Calculator parent) {

			this.parent = parent;
			this.operation = operation;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {

			if(operation == StackOperation.PUSH) {
				
				if(parent.getStack().size() == 0) {
					return;
				}
				IContainer peekElem = parent.getStack().peek();
				if(peekElem instanceof OperatorContainer) {
					
					OperatorContainer peekOperation = (OperatorContainer) parent.getStack().pop();
					NumberContainer peekNumber = (NumberContainer) parent.getStack().peek();
					
					parent.getMemory().push(peekNumber);
					parent.getStack().push(peekOperation);
				}
				else if(peekElem instanceof NumberContainer) {
					parent.getMemory().push((NumberContainer) parent.getStack().peek());
				}
			}
			else {
				
				if(parent.getMemory().size() == 0) {
					return;
				}
				IContainer peekElem = parent.getStack().peek();
				if(peekElem instanceof OperatorContainer) {
					
					if( ((OperatorContainer)peekElem).getSymbol() == Operation.EQUALS ) {
						OperatorContainer oper = (OperatorContainer) parent.getStack().pop();
						parent.getStack().pop();
						parent.getStack().push(parent.getMemory().pop());
						parent.getStack().push(oper);
						parent.updateScreen();
						return;
					}
					parent.getStack().push(parent.getMemory().pop());
					parent.updateScreen();
					return;
				}
				else if(peekElem instanceof NumberContainer) {
					
					parent.getStack().pop();
					parent.getStack().push(parent.getMemory().pop());
					parent.updateScreen();
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
