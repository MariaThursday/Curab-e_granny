package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooksSortListener implements ActionListener {

    int N, Count = 1;
    JLabel[] array;
    JButton button;

    Timer timer;
    int i = 0; // внешний индекс сортировки
    int j = 0; // внутренний индекс

    boolean isSorting = false;

    public BooksSortListener(JLabel[] components, JButton button) {
        this.N = components.length; // число книг
        this.array = new JLabel[N];
        System.arraycopy(components, 0, array, 0, N);
        this.button = button;

        timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i < N - 1) {
                    if (j < N - i - 1) {
                        int val1 = toInt(array[j]);
                        int val2 = toInt(array[j + 1]);
                        if (val1 > val2) {
                            changeLocation(array[j], array[j + 1]);
                            changeArray(array, j, j + 1);
                        }
                        j++;
                    } else {
                        j = 0;
                        i++;
                    }
                } else {
                    timer.stop();
                    isSorting = false;
                    i = 0;
                    j = 0;
                }
            }
        });
    }

    // внешние изменения - сортировка картинок
    private void changeLocation(JComponent book1, JComponent book2) {
        int x1 = book1.getX();
        int x2 = book2.getX();
        book1.setLocation(x2, book1.getY());
        book2.setLocation(x1, book2.getY());
    }

    // изменения в массиве - перестановка
    private void changeArray(JLabel[] books, int i, int j) {
        JLabel mid = books[i];
        books[i] = books[j];
        books[j] = mid;
    }

    // переводит текст картинки в число ("5" -> 5 ) - нужно для сортировки
    public int toInt(JLabel comp) {
        return Integer.parseInt(comp.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isSorting) {
            if (Count % 2 == 0) {
                isSorting = true;
                i = 0;
                j = 0;
                timer.start();
            }
            Count++;
        }
    }
}
