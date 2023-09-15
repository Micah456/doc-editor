package component;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class StatsDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatsDialog(ArrayList<String> data) {
		
		setLayout(new GridLayout(data.size()/2,2));
		setTitle("Advanced Stats");
		for(int i = 0; i < data.size(); i++) {
			this.add(new JLabel(data.get(i)));
			i++;
			this.add(new JLabel(data.get(i)));
		}
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
