import ActionListeners.*;
import Graphics.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class myApp extends JFrame {
    myApp() {
        // окно
        super("KURAB'E CLICKER");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Теперь окно помещено в центр.

        //--------------------- панели -------------------------
        // главное меню
        JPanel mainPanel = setPanel(true);

        // панель с описанием
        JPanel textPanel = setPanel(false);

        // панель магазина
        JPanel storePanel = setPanel(false);

        // панель стола
        JPanel deskPanel = setPanel(false);

        // панель книжного шкафа
        JPanel booksPanel = setPanel(false);

        //панель оранжереи:
        JPanel relaxPanel = setPanel(false);

        // панель сада
        JLabel grannyAtGarden = new JLabel(new ImageIcon("src/images/gardenPanel/granny.png"));
        grannyAtGarden.setBounds(0, 0, 70, 70);

        // сад пишем вручную, т.к. заполняется особым классом GardenPanel
        GardenPanel gardenPanel = new GardenPanel(800, 1200);
        gardenPanel.setLocation(0, 0);
        add(gardenPanel);
        gardenPanel.setVisible(false);

        // кнопки навигации:
        String[] buttonNames = {"Что это?", "Купить гостинцы", "Стол", "Книжная полка", "Беседка", "Садик"};
        JPanel[] destination = {textPanel, storePanel, deskPanel, booksPanel, relaxPanel, gardenPanel};

        // IN-Buttons - из глав меню
        int Y = 650;
        for (int i = 0; i < buttonNames.length; i++) {
            Y -= 80;
            setNavigationButton(buttonNames[i], 800, Y, 200, 70, mainPanel, destination[i], mainPanel);
        }

        // OUT-Buttons - вернуться в гл меню
        for (JPanel current : destination) {
            setNavigationButton("Вернуться", 500, 650, 200, 70, current, mainPanel, current);
        }

        // --------------------- Глав экран ---------------------
        // заголовок и правила в гл меню
        JLabel title = setText(mainPanel, "KURAB'E GAME", 70, 350, 10, 700, 60);
        JLabel rules = setText(mainPanel, "Нажимайте на курабье и собирайте крошки",
                35, 230, 85, 790, 40);


        // сам главный счётчик - связан с счётчиком в магазине
        JLabel mainCounter = setText(mainPanel, "Баланс:0", 40, 350, 200, 500, 50);

        JLabel main_granny = new JLabel(new ImageIcon("src/images/mainPanel/granny.png"));
        setPics(main_granny, mainPanel, 1, 365, 400, 400, "");

        // главная кнопка
        JButton mainButton = new JButton("");
        mainButton.setIcon(new ImageIcon("src/images/mainPanel/kurabie.png"));
        mainButton.setBackground(Color.white);
        mainButton.setBounds(450, 300, 300, 300);
        mainButton.setVisible(true);
        mainButton.setOpaque(false);

        mainPanel.add(mainButton);

        // падение крошек
        // 3 массива: с крошками, задержкой для таймера, с изменением по оси ординат

        String[] crumbIcons = {"src/images/crumbs/crumb1.png",
                "src/images/crumbs/crumb2.png", "src/images/crumbs/crumb3.png", "src/images/crumbs/crumb4.png",
                "src/images/crumbs/crumb5.png", "src/images/crumbs/crumb6.png", "src/images/crumbs/crumb7.png",
                "src/images/crumbs/crumb8.png", "src/images/crumbs/crumb9.png", "src/images/crumbs/crumb10.png",
                "src/images/crumbs/crumb1.png", "src/images/crumbs/crumb2.png", "src/images/crumbs/crumb3.png",};

        Integer[] delays = {5, 1, 13, 20, 4, 19, 7, 6, 17, 10, 2, 7, 4};
        Integer[] dys = {1, 3, 2, 2, 3, 1, 2, 2, 1, 3, 2, 1, 2};

        for (int i = 0; i < crumbIcons.length; i++) {

            int x = (int) (Math.random() * 1200);    // координаты крошек случайны
            int y = (int) (Math.random() * 800);

            ImageIcon crumbIcon = new ImageIcon(crumbIcons[i]); // создаём отдельно икон, чтобы далее взять её размеры
            JLabel crumb = new JLabel(crumbIcon);

            setPics(crumb, mainPanel, x, y, crumbIcon.getIconWidth(), crumbIcon.getIconHeight(), "");
            FallingCrumbsListener crumbListener = new FallingCrumbsListener(crumb, dys[i]);
            Timer timer1 = new Timer(delays[i], crumbListener);
            timer1.start();
        }

        // --------------------- info-page ---------------------
        // добавляем заголовок
        JLabel textTitle = setText(textPanel, "А ты любопытный", 40, 450, 20, 700, 60);

        //todo: перенести нахуй весь текст в тхт файл
        String[] textForPanel = {"Здравствуйте!",
                "Этот кликер изначально был моей лабораторной работой",
                "для какого-то предмета в универе.",
                "Но он так мне понравился,",
                "что я решила оставить его ещё и здесь!",
                "Больше всего мне нравится уют,",
                "который создают разные мелочи в нашей жизни.",
                "И большую часть таких мелочей можно заметить",
                "рядом с добрыми бабулями. ",
                "А печенье Курабье напоминает мне о временах,",
                "когда бабушка учила меня пить чай из блюдца :-)",
                "Так что разделите мою ностальгию и потыкайте тут"
        };



        JLabel infoGranny = new JLabel(new ImageIcon("src/images/textPanel/granny.png"));
        setPics(infoGranny, textPanel, -10, 100, 578, 578, "");

        infoGranny.setOpaque(false);
        infoGranny.setVisible(true);

        int textY = 140;
        for (String string : textForPanel){
            JLabel text = setText(textPanel, string, 20, 500, textY, 850, 30);
            textY +=25;
        }


        // --------------------- стол ---------------------
        // Затем добавляем заголовок (он будет поверх фона)
        JLabel deskTitle = setText(deskPanel, "Стол с гостинцами", 70, 250, 25, 700, 90);

        // Сначала добавляем фон (он будет в самом низу)
        JLabel back_deskPanel = new JLabel(new ImageIcon("src/images/deskPanel/back_deskPanel.jpg"));
        setPics(back_deskPanel, deskPanel, 0, 0, 1200, 800, "");
        back_deskPanel.setOpaque(false);

        // --------------------- Магазин ---------------------
        // заголовок магазин
        JLabel shopTitle = setText(storePanel, "Магазин", 70, 450, 10, 700, 60);

        // Отображение очков в магазине
        JLabel shopCounter = setText(storePanel, "Баланс:0", 30, 430, 90, 400, 30);

        //лисенер, который связывает счётчик с кнопкой (см MyButtonListener)
        MainButtonListener counterListener = new MainButtonListener(shopCounter, mainCounter);
        mainButton.addActionListener((counterListener));

        mainButton.setFocusable(false);

        JLabel storeGranny = new JLabel(new ImageIcon("src/images/storePanel/granny.png"));
        setPics(storeGranny, storePanel, 1, 365, 400, 400, "");


        // товары в магазине
        String[] things = {"src/images/storePanel/tea.png", "src/images/storePanel/kurabie_box.png",
                "src/images/storePanel/jam.png", "src/images/storePanel/honey.png"};

        String[] price = {"Ароматный чай:10", "Коробка курабье:15", "Малиновое варенье:20", "Липовый мёд:40"}; // прейскурант
        Integer[] x_coordinats = {70, 330, 600, 890};

        for (int i = 0; i < things.length; i++) {
            // товар
            ImageIcon thingIcon = new ImageIcon(things[i]);
            JLabel thing = new JLabel(thingIcon);
            setPics(thing, storePanel, x_coordinats[i], 170, thingIcon.getIconWidth(), thingIcon.getIconHeight(), price[i]);
            JLabel thing2 = new JLabel(thingIcon);
            setPics(thing2, storePanel, x_coordinats[i], 170, thingIcon.getIconWidth(), thingIcon.getIconHeight(), price[i]);

            // кнопка для покупки товара
            JButton thingButton = new JButton(price[i]);
            setButton(thingButton, storePanel, x_coordinats[i]-20, 310, thingIcon.getIconWidth()+30, 50);

            ShopButtonListener ThingButtonListener = new ShopButtonListener(thingButton, thing2, mainCounter,
                    shopCounter, deskPanel);
            thingButton.addActionListener((ThingButtonListener));

        }


        //---------книжный шкаф--------

        // Заголовок книжного шкафа
        JLabel bookTitle = new JLabel("Книжная полка");
        bookTitle.setFont(new Font("Montserrat", Font.BOLD, 50));
        bookTitle.setForeground(Color.WHITE);
        bookTitle.setBounds(600, 78, 700, 50);
        booksPanel.add(bookTitle);

        // правила книжной полки
        String[] rools = {"Бабушка была немного невнимательна", "Помоги ей расставить книги по порядку"};
        int y = 130;
        for (String rool : rools) {
            JLabel bookRules = setText(booksPanel, rool, 20, 580, y, 1000, 20);
            bookRules.setForeground(Color.white);
            y += 20;
        }

        // книги - стоят на полке

        String[] bookIcons = {"src/images/bookPanel/book1.jpg", "src/images/bookPanel/book2.jpg",
                "src/images/bookPanel/book3.jpg", "src/images/bookPanel/book4.jpg", "src/images/bookPanel/book5.jpg"};
        JLabel[] bookLables = new JLabel[bookIcons.length];
        Integer[] bookY = {317, 80, 377, 100, 300};
        String[] bookNumbers = {"4", "2", "3", "1", "5"};
        int bookX = 130;

        for (int i = 0; i < bookIcons.length; i++) {
            ImageIcon bookIcon = new ImageIcon(bookIcons[i]);
            JLabel book = new JLabel(bookIcon);
            setPics(book, booksPanel, bookX, bookY[i], bookIcon.getIconWidth(), bookIcon.getIconHeight(), bookNumbers[i]);
            bookLables[i] = book;
            bookX += 100;
        }

        // сортировка книг

        JButton genButton = new JButton("Расставить книги в алфавитном порядке");
        setButton(genButton, booksPanel, 600, 200, 400, 60);
        BooksSortListener sortBooks = new BooksSortListener(bookLables, genButton);

        genButton.addActionListener(sortBooks);
        booksPanel.add(genButton);

        // Задний план - полка с книгами
        JLabel bookShelf = new JLabel(new ImageIcon("src/images/bookPanel/back_bookPanel.png"));
        setPics(bookShelf, booksPanel, 0, 0, 1200, 800, "");


        //---------сад с комарами (релакс)--------
        ImageIcon flyIcon1 = new ImageIcon("src/images/relaxPanel/fly.png");

        // создаём 5 комаров в случайных местах
        for (int i = 0; i < 5; i++) {
            // случайные координаты с отступами от краёв
            int startX = 50 + (int) (Math.random() * (relaxPanel.getWidth() - 100));
            int startY = 50 + (int) (Math.random() * (relaxPanel.getHeight() - 100));

            FlyAnImage fly = new FlyAnImage(flyIcon1, relaxPanel.getWidth(), relaxPanel.getHeight());
            fly.setLocation(startX, startY);
            relaxPanel.add(fly);

            new Thread(fly).start(); // запускаем движение комара
        }

        // Картинка - бабушка в оранжерее
        JLabel granny_at_garden = new JLabel(new ImageIcon("src/images/relaxPanel/granny_full.png"));
        setPics(granny_at_garden, relaxPanel, 0, 40, 386, 680, "");

        // Задний план - оранжерея
        JLabel back_relaxPanel = new JLabel(new ImageIcon("src/images/relaxPanel/back_relaxPanel.jpg"));
        setPics(back_relaxPanel, relaxPanel, 0, 0, 1200, 800, "");

        //---------- сад -----------
        // кнопка генерит новые виды сада
        JButton genLabirintButton = new JButton("сгенерировать другой сад");
        setButton(genLabirintButton, gardenPanel, 1000, 100, 100, 50);
        genLabirintButton.addActionListener(e -> {
            gardenPanel.genMatrix();
            gardenPanel.repaint();
        });

        setVisible(true);
    }

    public JLabel setText(JPanel panel, String title, int fontSize, int x, int y, int width, int height) {
        JLabel name = new JLabel(title);
        name.setFont(new Font("Montserrat", Font.BOLD, fontSize));
        name.setBackground(Color.white);
        name.setBounds(x, y, width, height);
        name.setVisible(true);
        name.setOpaque(false);
        panel.add(name);
        return name;
    }

    public void setButton(JButton button, JPanel panel, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        button.setVisible(true);
        button.setOpaque(true);
        button.setBackground(Color.white);
        button.setFont(new Font("Montserrat", Font.BOLD, 15));
        button.setFocusable(false);
        panel.add(button);
    }

    public static void setPics(JLabel pic, JPanel panel, int x, int y, int width, int height, String name) {
        pic.setBounds(x, y, width, height);
        pic.setVisible(true);
        pic.setText(name);
        panel.add(pic);
    }

    public JPanel setPanel(boolean isVisible) {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1200, 800);
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setOpaque(true);
        panel.setVisible(isVisible);
        add(panel);
        return panel;
    }

    public void setNavigationButton(String text, int x, int y, int width, int height,
                                    JPanel fromPanel, JPanel toPanel, JPanel wherePanel) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(Color.white);
        button.addActionListener((ActionEvent e) -> {
            fromPanel.setVisible(false);
            toPanel.setVisible(true);
        });
        wherePanel.add(button);
    }


    public static void main(String[] args) { //функция с маленькой буквы
        new myApp();
    }
}