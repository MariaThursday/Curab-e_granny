package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainButtonListener implements ActionListener {

    JLabel shopCount;
    JLabel mainCount;
    private int counter;

    public MainButtonListener(JLabel shopCount, JLabel mainCount) {
        this.mainCount = mainCount;
        this.shopCount = shopCount;
    }

    private String parsePrice(String Balance) {

        String res = "";
        int Start = Balance.indexOf(":");
        int Finish = Balance.length();

        for (int i = Start + 1; i < Finish; i++) {
            String aa = "" + Balance.charAt(i);
            res = res.concat(aa);
        }
        return res;
    }

    public int getCounter() {
        return Integer.parseInt(parsePrice(mainCount.getText()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        counter = getCounter() + 1;
        mainCount.setText("Баланс:" + counter);
        shopCount.setText("Баланс:" + counter);

    }
}
