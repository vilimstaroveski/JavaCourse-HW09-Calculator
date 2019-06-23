package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

/**
 * Class inherits {@link JButton} and resets {@link Calculator} in to initial state.
 * @author Vilim Staroveški
 *
 */
public class ResetButton extends JButton{

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -6173868080596095292L;

	/**
	 * Creates new {@link ResetButton} and adds new {@link ResetListener} to new instance.
	 * @param name symbol of this button.
	 * @param parent {@link Calculator} for which this button is added for.
	 */
	public ResetButton(String name, Calculator parent) {

		 // Create the model
        setModel(new DefaultButtonModel());
        // initialize
        init(name, null);
		
		addMouseListener(new ResetListener(parent));
	}
	
	/**
	 * Class implementation of {@link MouseListener} that is added to {@link ResetButton}.
	 * @author Vilim Staroveški
	 *
	 */
	private class ResetListener implements MouseListener {

		/**
		 * {@link Calculator} for which this button is added for.
		 */
		private Calculator parent;
		
		/**
		 * Creates new {@link ResetListener}.
		 * @param parent {@link Calculator} for which this button is added for.
		 */
		public ResetListener(Calculator parent) {

			this.parent = parent;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {

			parent.reset();
			parent.updateScreen();
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
