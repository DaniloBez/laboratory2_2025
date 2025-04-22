package View;

import Utils.JMenuBarUtil;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Головна форма додатку для автоматизації роботи складом.
 * Відображає основну інформацію про програму та забезпечує навігацію до інших форм.
 */
public class MainForm extends JFrame {
    private JMenuBar menuBar;

    /**
     * Конструктор головної форми.
     * Ініціалізує форму, встановлює параметри вікна та ініціалізує компоненти.
     */
    public MainForm() {
        setTitle("Main");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Встановлюємо сучасний вигляд (Nimbus)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ex) {
            System.out.println("Nimbus L&F не вдалося встановити.");
        }

        initComponents();
    }

    /**
     * Ініціалізація компонентів форми.
     * Створює меню, встановлює фонове зображення та додає інформаційні тексти.
     */
    private void initComponents() {
        menuBar = JMenuBarUtil.getMenuBar();
        setJMenuBar(menuBar);

        // Завантаження зображення
        ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/background.png"))); // шлях до зображення
        Image backgroundImage = backgroundIcon.getImage();

        // Панель з фоном
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);

        JPanel createPanel = new JPanel(new GridBagLayout());
        createPanel.setOpaque(false); // робимо прозорим, щоб фон було видно

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        String[] texts = {
                "Лабораторна робота №2: Автоматизоване робоче місце",
                "Роботу виконали Безух Данило та Анкудович Григорій ",
                "Необхідно автоматизувати роботу невеликого підприємства по роботі з складом.",
                "Існує декілька груп товарів (наприклад: Продовольчі, непродовольчі...).",
                "В кожній групі товарів існують конкретні товари (наприклад: борошно, гречка ...).",
                "У кожного товару є наступні властивості -",
                "назва, опис, виробник, кількість на складі, ціна за одиницю.",
                "Група товарів містить наступні властивості - назва, опис."
        };

        for (String text : texts) {
            JLabel label = new JLabel(text);
            label.setForeground(Color.WHITE);
            createPanel.add(label, gbc);
            gbc.gridy++;
        }

        backgroundPanel.add(createPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }
}

/**
 * Кастомна JPanel з можливістю відображення фонового зображення.
 */
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    /**
     * Конструктор панелі з фоном.
     * @param backgroundImage зображення для фону
     */
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        setLayout(new BorderLayout());
    }

    /**
     * Відображення фонового зображення.
     * @param g об'єкт Graphics для малювання
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
