package hr.fer.zemris.java.gui.calc;


import hr.fer.zemris.java.gui.calc.buttons.ChangeButton;
import hr.fer.zemris.java.gui.calc.buttons.ClearButton;
import hr.fer.zemris.java.gui.calc.buttons.DecimalDotButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.calc.buttons.EqualsButton;
import hr.fer.zemris.java.gui.calc.buttons.FunctionButton;
import hr.fer.zemris.java.gui.calc.buttons.OperatorButton;
import hr.fer.zemris.java.gui.calc.buttons.ResetButton;
import hr.fer.zemris.java.gui.calc.buttons.StackButton;
import hr.fer.zemris.java.gui.calc.containers.IContainer;
import hr.fer.zemris.java.gui.calc.containers.NumberContainer;
import hr.fer.zemris.java.gui.calc.containers.OperatorContainer;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Simple calculator program. It supports operations:
 * +, -, *, /,
 * functions:
 * 1/x, sin, cos, tan, ctg, log, ln, n-th root as well as 
 * their inverse functions.
 * @author Vilim Staroveški
 *
 */
public class Calculator extends JFrame {
	
	/**
	 * Stack of this calculator
	 */
	private Stack<IContainer> stack;
	
	/**
	 * Memory of this calculator.
	 */
	private Stack<NumberContainer> stackAsMemory;
	
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Screen
	 */
	private JLabel screen;
	
	/**
	 * Flag that tells if an operation is being defined.
	 */
	private boolean pendingOperation;
	
	/**
	 * {@link JCheckBox} for transforming functions into inverse function.
	 */
	private JCheckBox invers;
	
	/**
	 * Flag that tells if inverse functions are active or not.
	 */
	private boolean inversEnabled;
	
	/**
	 * Creates new {@link Calculator}.
	 */
	public Calculator() {
		setLocation(400, 400);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		stack = new Stack<IContainer>();
		this.pendingOperation = false;
		this.inversEnabled = false;
		this.stackAsMemory = new Stack<NumberContainer>();
		
		initGUI();
	}

	/**
	 * Initialises GUI for new {@link Calculator}
	 */
	private void initGUI() {
		getContentPane().setLayout(new CalcLayout(5));
		
		//setting the screen
		this.screen = new JLabel();
		this.screen.setBackground(Color.YELLOW);
		this.screen.setOpaque(true);
		this.screen.setText("0");
		this.screen.setHorizontalAlignment(JTextField.RIGHT);
		getContentPane().add(screen, "1, 1");
		
		
		//setting the functions for 1 arg --> they take 
		//argument that is currently on screen and do something 
		//with it and then put result on screen
		//argument they need is screen and invers
		FunctionButton[] funcButtons = new FunctionButton[8];
		
		FunctionButton sin = new FunctionButton("sin", "asin", Operation.SIN, this);
		funcButtons[0] = sin;
		getContentPane().add(sin, "2, 2");
		
		FunctionButton rec = new FunctionButton("1/X", "1/X", Operation.RECIPROCAL, this);
		funcButtons[1] = rec;
		getContentPane().add(rec, "2, 1");
		
		FunctionButton log = new FunctionButton("log", "10^", Operation.LOG, this);
		funcButtons[2] = log;
		getContentPane().add(log, "3, 1");
		
		FunctionButton ln = new FunctionButton("ln", "e^", Operation.LN, this);
		funcButtons[3] = ln;
		getContentPane().add(ln, "4, 1");
		
		FunctionButton nPow = new FunctionButton("x^n", "n\u221Ax", Operation.NPOWER, this);
		funcButtons[4] = nPow;
		getContentPane().add(nPow, "5, 1");
		
		FunctionButton cos = new FunctionButton("cos", "acos", Operation.COS, this);
		funcButtons[5] = cos;
		getContentPane().add(cos, "3, 2");
		
		FunctionButton tan = new FunctionButton("tan", "atan", Operation.TAN, this);
		funcButtons[6] = tan;
		getContentPane().add(tan, "4, 2");
		
		FunctionButton ctg = new FunctionButton("ctg", "actg", Operation.CTG, this);
		funcButtons[7] = ctg;
		getContentPane().add(ctg, "5, 2");
		
		//another type of upper functions, only difference
		//is that they put screen on "0" or left the value
		//and do the main work with stack
		//argument they need is stack and screen
		getContentPane().add(new StackButton("Push", StackOperation.PUSH, this), "3, 7");
		getContentPane().add(new StackButton("Pop", StackOperation.POP, this), "4, 7");
		getContentPane().add(new ClearButton("clr", this), "1, 7");
		getContentPane().add(new ResetButton("res", this), "2, 7");
		
		//setting the digit section, they take care of 
		//setting correct value to the screen
		//argument they need is screen 
		getContentPane().add(new DigitButton("7", this), "2, 3");
		getContentPane().add(new DigitButton("8", this), "2, 4");
		getContentPane().add(new DigitButton("9", this), "2, 5");
		getContentPane().add(new DigitButton("4", this), "3, 3");
		getContentPane().add(new DigitButton("5", this), "3, 4");
		getContentPane().add(new DigitButton("6", this), "3, 5");
		getContentPane().add(new DigitButton("1", this), "4, 3");
		getContentPane().add(new DigitButton("2", this), "4, 4");
		getContentPane().add(new DigitButton("3", this), "4, 5");
		getContentPane().add(new DigitButton("0", this), "5, 3");
		
		getContentPane().add(new ChangeButton("+/-", this), "5, 4");
		getContentPane().add(new DecimalDotButton(".", this), "5, 5");
		
		//setting the operators for 2 args
		//they need stack to get first value
		getContentPane().add(new OperatorButton("/", Operation.DIVIDE, this), "2, 6");
		getContentPane().add(new OperatorButton("*", Operation.TIMES, this), "3, 6");
		getContentPane().add(new OperatorButton("-", Operation.MINUS, this), "4, 6");
		getContentPane().add(new OperatorButton("+", Operation.PLUS, this), "5, 6");
		
		//equals button
		getContentPane().add(new EqualsButton("=", this), "1, 6");
		
		//setting the invers check box
		this.invers = new JCheckBox("Inv");
		this.invers.setSelected(false);
		this.invers.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				inversEnabled = !inversEnabled;
				for(FunctionButton func : funcButtons) {
					func.invers(inversEnabled);
				}
				repaint();
			}
		});
		getContentPane().add(invers, "5, 7");
		
		setMinimumSize(getMinimumSize());
	}
	
	/**
	 * Method called on program start.
	 * @param args command line arguments, not used in this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.setVisible(true);
		});
	}

	/**
	 * Returns stack of this {@link Calculator}
	 * @return
	 */
	public Stack<IContainer> getStack() {
		return this.stack;
	}

	/**
	 * Updates screen with new values.
	 */
	public void updateScreen() {
		
		if(stack.size() == 0) {
			screen.setText("0");
			return;
		}
		IContainer peek = stack.peek();
		if(peek instanceof NumberContainer) {
			screen.setText(peek.getValue());
			return;
		}
		else if(peek instanceof OperatorContainer) {
			NumberContainer numCon = (NumberContainer) stack.get(stack.size() - 2);
			screen.setText(numCon.getValue());
		}
	}
	
	/**
	 * Returns flag for pending operation.
	 * @return flag for pending operation.
	 */
	public boolean pendingOperation() {
		return this.pendingOperation;
	}
	
	/**
	 * Sets flag for pending operation.
	 * @param state new state of the flag.
	 */
	public void setPendingOperation(boolean state) {
		this.pendingOperation = state;
	}

	/**
	 * Returns flag for inverse functions.
	 * @return flag for inverse functions.
	 */
	public boolean inverseFunctions() {
		return this.invers.isSelected();
	}
	
	/**
	 * Resets this {@link Calculator} into initial state.
	 */
	public void reset() {
		stack.clear();
		stackAsMemory.clear();
		updateScreen();
	}

	/**
	 * Returns memory of this {@link Calculator}
	 * @return memory of this {@link Calculator}
	 */
	public Stack<NumberContainer> getMemory() {
		return this.stackAsMemory;
	}
}
