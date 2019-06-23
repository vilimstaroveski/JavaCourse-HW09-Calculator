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
 * Class inherits {@link JButton} and calculates function defined in this class for peek 
 * {@link NumberContainer} element on {@link Calculator}'s stack
 * @author Vilim Staroveški
 *
 */
public class FunctionButton extends JButton {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 3175161919185762181L;
	
	/**
	 * Symbol of function
	 */
	private String symbol;
	
	/**
	 * Symbol of inverse function
	 */
	private String inversSymbol;
	
	/**
	 * Type of operation.
	 */
	private OperatorContainer funcType;

	/**
	 * Creates new {@link FunctionButton} and adds new {@link FunctionListener} to new instance.
	 * @param symbol  Symbol of function
	 * @param inversSymbol Symbol of inverse function
	 * @param funcType type of function of this class
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public FunctionButton(String symbol, String inversSymbol, Operation funcType, Calculator parent) {
		
		// Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(symbol, null);
        
        this.symbol = symbol;
        this.inversSymbol = inversSymbol;
		this.funcType = new OperatorContainer(symbol, funcType);
				
		addMouseListener(new FunctionListener(this.funcType, parent));
	}

	/**
	 * Sets button text decided by inverse function flag.
	 * @param inversEnabled flag that tells if inverse function is used.
	 */
	public void invers(boolean inversEnabled) {

		if(inversEnabled) {
			this.setText(inversSymbol);
		}
		else {
			this.setText(symbol);
		}
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link FunctionButton}
	 * @author Vilim Staroveški
	 *
	 */
	private class FunctionListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		/**
		 * Type of function.
		 */
		private OperatorContainer funcType;
		
		/**
		 * Creates new {@link FunctionListener}
		 * @param parent {@link Calculator} for which this button is added for.
		 * @param funcType type of function.
		 */
		public FunctionListener(OperatorContainer funcType, Calculator parent) {
			this.funcType = funcType;
			this.parent = parent;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if(parent.getStack().size() == 0) {
				return;
			}
			
			IContainer peekElem = parent.getStack().peek();
			
			if(this.funcType.getSymbol() == Operation.NPOWER) {
				if(peekElem instanceof OperatorContainer) {
					
					parent.getStack().pop();
					parent.getStack().push(new OperatorContainer("NPOWER", Operation.NPOWER));
					return;
				}
				else if(peekElem instanceof NumberContainer) {
					
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
						parent.getStack().push(new OperatorContainer("NPOWER", Operation.NPOWER));
						parent.setPendingOperation(true);
						parent.updateScreen();
					} else {

						parent.getStack().push(new OperatorContainer("NPOWER", Operation.NPOWER));
						parent.setPendingOperation(true);
						return;
					}
					
				}
			}
			
			if(peekElem instanceof OperatorContainer) {
				
				OperatorContainer opCon = (OperatorContainer) parent.getStack().pop();
				NumberContainer numCon = (NumberContainer) parent.getStack().pop();
				parent.getStack().push(new Calculation(numCon, new NumberContainer("5"), this.funcType, parent.inverseFunctions()).getResult());
				parent.getStack().push(opCon);
				parent.updateScreen();
				return;
			}
			else if(peekElem instanceof NumberContainer) {
				
				NumberContainer firstArg = (NumberContainer) parent.getStack().pop();
				parent.getStack().push(new Calculation(firstArg, new NumberContainer("5"), this.funcType, parent.inverseFunctions()).getResult());
				parent.getStack().push(new OperatorContainer("=", Operation.EQUALS));
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
