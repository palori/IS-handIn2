package dtu.is31380;

import java.awt.Font;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TemperatureGUI implements ChangeListener {

  private JFrame frame;
  private JPanel panel;
  private JSpinner spinner;
  private JLabel label;
  private Vector<TemperatureGUIListener> listenerList;
  
  public TemperatureGUI() {
    listenerList=new Vector<TemperatureGUIListener>();
    frame=new JFrame("Temperature GUI");
    panel=new JPanel();
    frame.getContentPane().add(panel);
    label=new JLabel("Temperature setpoint");
    label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
    panel.add(label);
    spinner=new JSpinner(new SpinnerNumberModel(20,10,30,1));
    spinner.setFont(new Font(spinner.getFont().getName(), Font.BOLD, 20));
    panel.add(spinner);
    spinner.addChangeListener(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void stateChanged(ChangeEvent arg0) {
    SpinnerNumberModel numModel = (SpinnerNumberModel)spinner.getModel();
    double ts=Double.valueOf(numModel.getValue().toString());
    for (TemperatureGUIListener l:listenerList) {
      l.setpointChanged(ts);
    }
  }
  
  public void addListener(TemperatureGUIListener l) {
    if (!listenerList.contains(l)) {
      listenerList.add(l);
    }
  }
  
  public void removeListener(TemperatureGUIListener l) {
    if (listenerList.contains(l)) {
      listenerList.remove(l);
    }
  }
  
  public static void main(String args[]) {
    new TemperatureGUI();
  }
  
}
