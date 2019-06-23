package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.containers.IContainer;
import hr.fer.zemris.java.gui.calc.containers.NumberContainer;
import hr.fer.zemris.java.gui.calc.containers.OperatorContainer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

/**
 * Class inherits {@link JButton} and clears screen of {@link Calculator} as well 
 * as removes last {@link NumberContainer} added on stack.
 * 
 * @author Vilim Staroveški
 *
 */
public class ClearButton extends JButton{

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 9061494548608404600L;

	/**
	 * Creates new {@link ClearButton} and adds new {@link ClearListener} to new instance.
	 * @param name symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public ClearButton(String name, Calculator parent) {
		
		 // Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(name, null);
		
		addMouseListener(new ClearListener(parent));
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link ClearButton}.
	 * @author Vilim Staroveški
	 *
	 */
	private class ClearListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		/**
		 * Creates new {@link ClearListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 */
		public ClearListener(Calculator parent) {

			this.parent = parent;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {

			if(parent.getStack().size() == 0) {
				return;
			}
			IContainer peekElem = parent.getStack().peek();
			if(peekElem instanceof OperatorContainer) {
				
				parent.getStack().pop();
				parent.getStack().pop();
				parent.updateScreen();
			}
			else if(peekElem instanceof NumberContainer) {
				
				parent.getStack().pop();
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
