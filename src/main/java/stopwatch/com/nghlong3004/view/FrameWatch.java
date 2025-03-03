package stopwatch.com.nghlong3004.view;

import javax.swing.JFrame;

public class FrameWatch extends JFrame{

  private static final long serialVersionUID = -3848530282863874056L;
  
  public FrameWatch() {
    add(new PanelWatch());
    setSize(400, 200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
}
