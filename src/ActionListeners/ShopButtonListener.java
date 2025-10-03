package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopButtonListener implements ActionListener {
    JLabel shopCount, mainCount;
    JButton goods;
    JPanel panel;
    JComponent pic;

    public ShopButtonListener(JButton goods, JLabel pic, JLabel shopCount, JLabel mainCount, JPanel panel) {
        this.goods = goods;
        this.pic = pic;
        this.shopCount = shopCount;
        this.mainCount = mainCount;
        this.panel = panel;
    }

    static String parsePrice(String Balance) {
        String res = "";
        int Start = Balance.indexOf(":");
        int Finish = Balance.length();

        for (int i = Start + 1; i < Finish; i++) {
            String aa = "" + Balance.charAt(i);
            res = res.concat(aa);
        }
        return res;
    }

    private void changeLocation() {
        pic.setBounds(pic.getX(), pic.getY(), pic.getWidth(), pic.getHeight());

        // перемещаем товар на верхний слой
        panel.add(pic, 0); // на верхний слой - индекс 0
        pic.setVisible(true);
        //panel.revalidate();
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int mainText = Integer.parseInt(parsePrice(mainCount.getText()));
        int shopText = Integer.parseInt(parsePrice(goods.getText()));
        int answer = mainText - shopText;

        if (answer < 0) {
            shopCount.setText("Баланс:" + mainText);
            mainCount.setText("Баланс:" + mainText);
        } else {
            shopCount.setText("Баланс:" + answer);
            mainCount.setText("Баланс:" + answer);
            changeLocation();

        }
    }
}

