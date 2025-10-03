import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GardenPanel extends JPanel implements MouseListener {
    final int cellSize = 75;
    char[][] garden;
    Point Start = new Point(0 / cellSize, 0 / cellSize), Finish = new Point(730 / cellSize, 730 / cellSize);


    // внутренний класс для поиска пути
    class Path {
        ArrayList<Point> steps;

        Path() {
            steps = new ArrayList<>(20);
        }
    }

    Path currentPath = new Path();
    boolean searching = false;

    GardenPanel(int width, int height) {
        setSize(height, width);
        garden = new char[height / cellSize][width / cellSize];
        genMatrix();
        addMouseListener(this);
    }

    void genMatrix() {
        Random rnd = new Random();
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[i].length; j++) {
                int g = rnd.nextInt(100);
                if (g < 70)
                    garden[i][j] = '+';
                else
                    garden[i][j] = '-';
            }
        }
    }

    void findPath() {
        System.out.println("started...");
        currentPath = new Path();
        Path myPath = new Path();

        // список доступных клеток
        ArrayList<Point> undone = new ArrayList<>(1);
        fillunDoneList(undone);

        // список пройденных клеток - в myPath
        // текущий эл-т && предыдущий
        Point current = new Point(Start.x, Start.y);
        Point lastPoint = new Point(Start.x, Start.y);
        int counter = 0; // для отслеживания шагов в консоли

        while (!current.equals(Finish)) {
            myPath.steps.add(new Point(current.x, current.y));
            counter++;
            progress(counter, current, lastPoint);

            // если зациклился - останавливаем
            if (counter >= 100) {
                System.out.println("No ways");
                break;
            }

            // список соседей
            ArrayList<Point> neighbours = new ArrayList<>();
            findNeighbours(current, lastPoint, undone, neighbours);
            int min = minNeighbour(neighbours);
            System.out.println("neighbours now: " + neighbours);
            System.out.println("min is == " + min);

            // обработка разных случаев соседей
            if (neighbours.isEmpty()) {
                handleNoNeighbours(current, myPath, undone);
            } else if (neighbours.size() == 1) {
                handleSingleNeighbour(current, lastPoint, myPath, undone, neighbours);
            } else {
                handleMultipleNeighbours(current, lastPoint, neighbours, min);
            }
        }

        currentPath = myPath;
        searching = false;
    }

    private void progress(int counter, Point current, Point lastPoint) {
        //System.out.println("\n Step " + counter);
        //System.out.println("now current Point is...   " + current);
        //System.out.println("now lastPoint is...   " + lastPoint);
        //System.out.println("Finish is...   " + Finish);
    }

    private void handleNoNeighbours(Point current, Path myPath, ArrayList<Point> undone) {
        undone.remove(current);
        myPath.steps.remove(current);
        if (!myPath.steps.isEmpty()) {
            current.setLocation(myPath.steps.get(myPath.steps.size() - 1));
        }
    }

    private void handleSingleNeighbour(Point current, Point lastPoint, Path myPath,
                                       ArrayList<Point> undone, ArrayList<Point> neighbours) {
        if (distance(neighbours.get(0)) >= distance(current)) {
            undone.remove(current);
            myPath.steps.remove(current);
            if (!myPath.steps.isEmpty()) {
                current.setLocation(myPath.steps.get(myPath.steps.size() - 1));
            }
        } else {
            myPath.steps.add(new Point(current));
            lastPoint.setLocation(current);
            current.setLocation(neighbours.get(0));
            //System.out.println("only 1 neighbour ☺");
        }
    }

    private void handleMultipleNeighbours(Point current, Point lastPoint,
                                          ArrayList<Point> neighbours, int min) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            Point neighbor = new Point(current.x + dir[0], current.y + dir[1]);
            if (neighbours.contains(neighbor) && distance(neighbor) <= min) {
                changeCurrentPoint(current, lastPoint, neighbours, min, dir[0], dir[1]);
                break;
            }
        }
    }

    void changeCurrentPoint(Point current, Point lastPoint, ArrayList<Point> neighbours, int min, int dx, int dy) {
        if (neighbours.contains(new Point(current.x + dx, current.y + dy)) && min >= distance(new Point(current.x + dx, current.y + dy)) /* && !Objects.equals(lastPoint, new Point(current.x + dx, current.y + dy))*/) {
            lastPoint.x = current.x;
            lastPoint.y = current.y;
            current.x += dx;
            current.y += dy;
        } else
            System.out.println("Point " + new Point(current.x + dx, current.y + dy) + " doesn't fit");
    }

    int minNeighbour(ArrayList<Point> neighboirs) {
        int min = 10000;
        for (int i = 0; i < neighboirs.size(); i++) {
            if (distance(neighboirs.get(i)) <= min)
                min = distance(neighboirs.get(i));
        }
        return min;
    }

    void findNeighbours(Point current, Point lastPoint, ArrayList<Point> undone, ArrayList<Point> neighbours) {

        Integer[] dx = {-1, 1, 0, 0};
        Integer[] dy = {0, 0, -1, 1};
        for (int i = 0; i< dx.length;i++){
            checkNeighbour(current, lastPoint, neighbours, undone, dx[i], dy[i]);
        }
    }

    void checkNeighbour(Point current, Point lastPoint, ArrayList<Point> neighbours, ArrayList<Point> undone, int dx, int dy) {
        if (undone.contains(new Point(current.x + dx, current.y + dy)) &&
                current.x + dx >= 0 && current.x + dx <= garden[0].length && current.y + dy >= 0 && current.y + dy <= garden.length &&
                garden[current.y + dy][current.x + dx] != '-' && !Objects.equals(lastPoint, new Point(current.x + dx, current.y + dy)))
            neighbours.add(new Point(current.x + dx, current.y + dy));

    }

    Integer distance(Point Current) {        // возвращает расстояние от конца до точки (сумма катетов)
        return (Math.abs(Finish.x - Current.x) + Math.abs(Finish.y - Current.y));
    }


    void fillunDoneList(ArrayList<Point> undone) {
        for (int i = 0; i < garden[0].length; i++) {
            for (int j = 0; j < garden.length; j++) {
                undone.add(new Point(i, j));
            }
        }
    }

    String[] pictures = {"src/images/gardenPanel/granny.png", "src/images/gardenPanel/dog.jpg",
            "src/images/gardenPanel/steps2.png", "src/images/gardenPanel/soil.png",
            "src/images/gardenPanel/grass.png", "src/images/gardenPanel/granny.png" };
    String getColor(int i, int j) {
        if (Start.x == j && Start.y == i)
            return pictures[0];

        if (Finish.x == j && Finish.y == i)
            return pictures[1];

        if (currentPath.steps.contains(new Point(j, i)))
            return pictures[2];

        switch (garden[i][j]) {
            case '+':
                return pictures[3];
            case '-':
                return pictures[4];
        }
        return pictures[5];
    }

    @Override
    public void paint(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[i].length; j++) {
                ImageIcon image = new ImageIcon(getColor(i, j));
                g.drawImage(image.getImage(), i * cellSize, j * cellSize, cellSize, cellSize, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int I = e.getY() / cellSize;
        int J = e.getX() / cellSize;
        if (e.getButton() == MouseEvent.BUTTON1)
            Start = new Point(I, J);
        else
            Finish = new Point(I, J);
        findPath();
        repaint();
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}

