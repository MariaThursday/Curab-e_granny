package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class FlyAnImage extends JLabel implements Runnable {
    private final ImageIcon icon;
    private final Random random = new Random();
    private final int panelWidth;
    private final int panelHeight;

    // Параметры движения
    private int baseX, baseY, amplitude, speed, directionX;
    private double frequency, prevX, prevY; // Для расчета угла поворота
    private int t = 0;

    public FlyAnImage(ImageIcon icon, int panelWidth, int panelHeight) {
        this.icon = icon;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;

        // Случайная начальная позиция
        this.baseX = random.nextInt(panelWidth);
        this.baseY = random.nextInt(panelHeight);
        this.prevX = baseX;
        this.prevY = baseY;

        // Параметры движения
        this.amplitude = 10 ;
        this.frequency = 0.02 + random.nextDouble() * 0.06;
        this.speed = 4;
        this.directionX = random.nextBoolean() ? 1 : -1;

        setIcon(icon);
        setSize(icon.getIconWidth(), icon.getIconHeight());
        setLocation(baseX, baseY);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // cохр предыдущие координаты для расчета угла
            prevX = baseX;
            prevY = baseY;

            // Обновляем позицию
            baseX += directionX * speed;
            int currentY = baseY + (int)(amplitude*Math.sin(t * frequency));

            // Проверка границ по X
            if (baseX < 0) {
                baseX = panelWidth;
                prevX = baseX + panelWidth; // Для плавного перехода
            } else if (baseX > panelWidth) {
                baseX = 0;
                prevX = baseX - panelWidth;
            }

            // Проверка границ по Y
            if (currentY < 0) {
                currentY = panelHeight;
                prevY = currentY + panelHeight;
            } else if (currentY > panelHeight) {
                currentY = 0;
                prevY = currentY - panelHeight;
            }

            baseY = currentY;

            SwingUtilities.invokeLater(() -> {
                setLocation(baseX, baseY);
                repaint();
            });
            t++;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Рассчитываем угол через тангенс (направление движения)
        double deltaX = baseX - prevX;
        double deltaY = baseY - prevY;
        double angle = Math.atan2(deltaY, deltaX);

        // Поворачиваем изображение по направлению движения
        AffineTransform transform = new AffineTransform(); // создаем объект трансформации
        transform.translate(icon.getIconWidth()/2.0, icon.getIconHeight()/2.0); //перемещаем систему координат так, чтобы начало совпало с центром изображения.
        transform.rotate(angle);
        transform.translate(-icon.getIconWidth()/2.0, -icon.getIconHeight()/2.0);

        g2d.drawImage(icon.getImage(), transform, this);
        g2d.dispose();
    }
}