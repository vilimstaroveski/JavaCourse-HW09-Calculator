package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

/**
 * Layout that is used for implementing a simple calculator. It contains 31 positions
 * where you can put your components(works best for {@link JButton}'s). Legal positions 
 * are enumerated from (1, 1) to (5, 7) with exception of positions:
 * (1, 2), (1, 3), (1, 4), (1, 5).
 * 
 * @author Vilim Staroveški
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * List of already taken positions in layout.
	 */
	private List<RCPosition> takenPositions;
	
	/**
	 * Map that maps {@link Component} to a certain position in layout.
	 */
	private Map<RCPosition, Component> components;
	
	/**
	 * Space between components required by user.
	 */
	private int spaceBetweenComp;
	
	/**
	 * Creates new {@link CalcLayout} with defined space between components.
	 * @param spaceBetweenComp space between components.
	 */
	public CalcLayout(int spaceBetweenComp) {
		this.spaceBetweenComp = spaceBetweenComp;
		this.components = new HashMap<RCPosition, Component>();
		this.takenPositions = new ArrayList<RCPosition>();
	}

	/**
	 * Creates new {@link CalcLayout} with space between components set to 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Unused method for this layout.
	 */
	public void addLayoutComponent(String name, Component comp) {
		//not used
	}

	@Override
	public void removeLayoutComponent(Component comp) {

		for(RCPosition key : this.takenPositions) {
			if(this.components.get(key) == comp) {
				this.components.remove(key);
				this.takenPositions.remove(key);
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		
		Dimension dim = getPreferredSizeForComponents();
		Dimension containerDim = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		containerDim.width += insets.left + insets.right + 6*spaceBetweenComp + 7*dim.width;
		containerDim.height += insets.top + insets.bottom + 4*spaceBetweenComp + 5*dim.height ;
		
		//added because of window bar and 3 side borders:
		containerDim.width += 15;
		containerDim.height += 38;
		return containerDim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		
		Dimension dim = getMinimumSizeForComponents();
		Dimension containerDim = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		containerDim.width += insets.left + insets.right + 6*spaceBetweenComp + 7*dim.width + 15;
		containerDim.height += insets.top + insets.bottom + 4*spaceBetweenComp + 5*dim.height + 38;
		
		//added because of window bar and 3 side borders:
		containerDim.width += 15;
		containerDim.height += 38;
		return containerDim;
	}

	/**
	 * Returns minimum size of this layout, based on minimum sizes of components
	 * it contains.
	 * @return minimum size of this layout
	 */
	private Dimension getMinimumSizeForComponents() {
		
		int minimumWidth = 0;
		int minimumHeight = 0;
		RCPosition bigOne = new RCPosition(1, 1);
		for(RCPosition key : this.takenPositions) {
			Component comp = this.components.get(key);
			if(comp == null) {
				continue;
			}
			
			Dimension dim = comp.getMinimumSize();
			if(dim == null) {
				continue;
			}
			int wantedWidth = 0;
			int wantedHeight = 0;
			if(key.equals(bigOne)) {
				
				wantedHeight = dim.height;
				wantedWidth = Math.round( ((float)dim.width - 4*spaceBetweenComp) / 5 );
			}
			else {
				
				wantedHeight = dim.height;
				wantedWidth = dim.width;
			}
			
			if(wantedHeight > minimumHeight) {
				minimumHeight = wantedHeight;
			}
			if(wantedWidth > minimumWidth) {
				minimumWidth = wantedWidth;
			}
		}
		return new Dimension(minimumWidth, minimumHeight);
	}
	
	/**
	 * Returns preferred size of this layout, based on preferred sizes of components
	 * it contains.
	 * @return preferred size of this layout
	 */
	private Dimension getPreferredSizeForComponents() {
		
		int preferredWidth = 0;
		int preferredHeight = 0;
		RCPosition bigOne = new RCPosition(1, 1);
		for(RCPosition key : this.takenPositions) {
			Component comp = this.components.get(key);
			if(comp == null) {
				continue;
			}
			
			Dimension dim = comp.getPreferredSize();
			if(dim == null) {
				continue;
			}
			int wantedWidth = 0;
			int wantedHeight = 0;
			if(key.equals(bigOne)) {
				
				wantedHeight = dim.height;
				wantedWidth = Math.round( ((float)dim.width - 4*spaceBetweenComp) / 5 );
			}
			else {
				
				wantedHeight = dim.height;
				wantedWidth = dim.width;
			}
			
			if(wantedHeight > preferredHeight) {
				preferredHeight = wantedHeight;
			}
			if(wantedWidth > preferredWidth) {
				preferredWidth = wantedWidth;
			}
		}
		return new Dimension(preferredWidth, preferredHeight);
	}
	
	@Override
	public void layoutContainer(Container parent) {
		
		Insets insets = parent.getInsets();
        int top = insets.top;
        int bottom = parent.getHeight() - insets.bottom;
        int left = insets.left;
        int right = parent.getWidth() - insets.right;
        
        int availableSpaceHorizontal = right - left;
        int availableSpaceVertical = bottom - top;
        int availableHeightForOneComponent = (int) Math.round((availableSpaceVertical - 4*spaceBetweenComp) / 5.0);
        int availableWidthForOneComponent = (int) Math.round((availableSpaceHorizontal - 6*spaceBetweenComp) / 7.0);
        
        Dimension dimensionOfOneComponent = getMinimumSizeForComponents();
        if(availableHeightForOneComponent > dimensionOfOneComponent.height) {
        	dimensionOfOneComponent.height = availableHeightForOneComponent;
        }
        if(availableWidthForOneComponent > dimensionOfOneComponent.width) {
        	dimensionOfOneComponent.width = availableWidthForOneComponent;
        }
        RCPosition bigOne = new RCPosition(1, 1);
        for(RCPosition key : this.takenPositions) {
        	Component comp = this.components.get(key);
        	
        	if(key.equals(bigOne)) {
        		comp.setBounds(left, top, (dimensionOfOneComponent.width*5 + 4*spaceBetweenComp), dimensionOfOneComponent.height);
        	}
        	else {
        		int x = (key.getColumn() - 1) * (spaceBetweenComp + dimensionOfOneComponent.width) + left;
            	int y = (key.getRow() - 1) * (spaceBetweenComp + dimensionOfOneComponent.height) + top;
            	comp.setBounds(x, y, dimensionOfOneComponent.width, dimensionOfOneComponent.height);
        	}
        	
        }
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		RCPosition position = RCPosition.definePosition(constraints);
		if(isInvalidPosition(position)) {
			throw new InvalidParameterException("Position is invalid!");
		}
		this.components.put(position, comp);
		this.takenPositions.add(position);
	}

	/**
	 * Method that validates position for this layout. Returns true if the position
	 * is illegal. False otherwise.
	 * @param position position that is checked.
	 * @return true if the position is illegal. False otherwise.
	 */
	private boolean isInvalidPosition(RCPosition position) {
		
		if(position.getRow() < 1 || position.getRow() > 7 
				|| position.getColumn() < 1 || position.getColumn() > 7) {
			
			return true;
		}
		if(position.getRow() == 1) {
			if(position.getColumn() > 1 && position.getColumn() < 6) {
				
				return true;
			}
		}
		if(this.takenPositions.contains(position)) {
			
			return true;
		}
		return false;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
}
