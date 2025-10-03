package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FallingCrumbsListener implements ActionListener {

    JComponent crumb;
    private int  dy;
    int heightPanel = 800;

    public FallingCrumbsListener(JComponent crumb, int dy) {
        this.crumb = crumb;
        this.dy = dy;
    }

    private void fall(){
        crumb.setLocation(crumb.getX(), crumb.getY() + dy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (crumb.getY() <= heightPanel) {
            fall();
        }
        if (crumb.getY() > heightPanel){
            crumb.setLocation(crumb.getX(), 0);
            fall();
        }
    }
}
