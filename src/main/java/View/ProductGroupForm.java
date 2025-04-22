package View;

import Controller.ProductController;
import Controller.ProductGroupController;
import Entity.ProductEntity;
import Entity.ProductGroupEntity;
import Utils.JMenuBarUtil;
import Utils.Result;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Клас для створення форми управління групами товарів.
 * Має вкладки для створення, оновлення, видалення, перегляду однієї групи та перегляду всіх груп товарів.
 */
public class ProductGroupForm extends JFrame {
    private final ProductGroupController groupController;
    private final ProductController productController;

    // Компоненти для вкладки "Створити"
    private JTextField createNameField, createDescField;
    private JButton createButton;

    // Компоненти для вкладки "Оновити"
    private JComboBox<ProductGroupEntity> updateComboBox;
    private JTextField updateNameField, updateDescField;
    private JButton updateButton;

    // Компоненти для вкладки "Видалити"
    private JComboBox<ProductGroupEntity> deleteComboBox;
    private JButton deleteButton;

    // Компоненти для вкладки "Перегляд" (інформація про об'єкт)
    private JComboBox<ProductGroupEntity> viewComboBox;
    private JTextArea viewTextArea;

    // Компоненти для вкладки "Перегляд усіх об'єктів"
    private JTextArea viewAllTextArea;
    private JButton refreshButton;

    /**
     * Конструктор, що ініціалізує форму, встановлює вигляд та компоненти.
     */
    public ProductGroupForm() {
        groupController = ProductGroupController.getInstance();
        productController = ProductController.getInstance();

        setTitle("Форма управління групами товарів");
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
        updateAllCombos();
    }

    /**
     * Ініціалізує всі компоненти форми.
     */
    private void initComponents() {
        JMenuBar menuBar = JMenuBarUtil.getMenuBar();
        setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();

        // --------------------- Вкладка "Створити" ---------------------
        JPanel createPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        createPanel.add(new JLabel("Назва групи:"), gbc);
        gbc.gridx = 1;
        createNameField = new JTextField(20);
        createPanel.add(createNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        createPanel.add(new JLabel("Опис групи:"), gbc);
        gbc.gridx = 1;
        createDescField = new JTextField(20);
        createPanel.add(createDescField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        createButton = new JButton("Створити групу");
        createPanel.add(createButton, gbc);

        createButton.addActionListener(e -> handleCreate());

        // --------------------- Вкладка "Оновити" ---------------------
        JPanel updatePanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        updatePanel.add(new JLabel("Оберіть групу для оновлення:"), gbc);
        gbc.gridx = 1;
        updateComboBox = new JComboBox<>();
        updatePanel.add(updateComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        updatePanel.add(new JLabel("Нова назва:"), gbc);
        gbc.gridx = 1;
        updateNameField = new JTextField(20);
        updatePanel.add(updateNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        updatePanel.add(new JLabel("Новий опис:"), gbc);
        gbc.gridx = 1;
        updateDescField = new JTextField(20);
        updatePanel.add(updateDescField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        updateButton = new JButton("Оновити групу");
        updatePanel.add(updateButton, gbc);

        updateButton.addActionListener(e -> handleUpdate());

        // --------------------- Вкладка "Видалити" ---------------------
        JPanel deletePanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        deletePanel.add(new JLabel("Оберіть групу для видалення:"), gbc);
        gbc.gridx = 1;
        deleteComboBox = new JComboBox<>();
        deletePanel.add(deleteComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        deleteButton = new JButton("Видалити групу");
        deletePanel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> handleDelete());

        // --------------------- Вкладка "Перегляд" ---------------------
        JPanel viewPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        viewPanel.add(new JLabel("Оберіть групу для перегляду:"), gbc);
        gbc.gridx = 1;
        viewComboBox = new JComboBox<>();
        viewPanel.add(viewComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        viewTextArea = new JTextArea(5, 30);
        viewTextArea.setEditable(false);
        JScrollPane scrollView = new JScrollPane(viewTextArea);
        viewPanel.add(scrollView, gbc);

        viewComboBox.addActionListener(e -> handleViewSingle());

        // --------------------- Вкладка "Перегляд усіх" ---------------------
        JPanel viewAllPanel = new JPanel(new BorderLayout());
        viewAllTextArea = new JTextArea();
        viewAllTextArea.setEditable(false);
        JScrollPane scrollAll = new JScrollPane(viewAllTextArea);
        viewAllPanel.add(scrollAll, BorderLayout.CENTER);

        refreshButton = new JButton("Оновити список груп");
        refreshButton.addActionListener(e -> refreshAllGroups());
        viewAllPanel.add(refreshButton, BorderLayout.SOUTH);

        // --------------------- Додаємо всі вкладки до таб-панелі ---------------------
        tabbedPane.addTab("Створити", createPanel);
        tabbedPane.addTab("Оновити", updatePanel);
        tabbedPane.addTab("Видалити", deletePanel);
        tabbedPane.addTab("Перегляд", viewPanel);
        tabbedPane.addTab("Перегляд усіх", viewAllPanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Обробка створення нової групи.
     * Валідує введені дані та викликає метод створення групи в контролері.
     */
    private void handleCreate() {
        String name = createNameField.getText().trim();
        String description = createDescField.getText().trim();
        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Будь ласка, заповніть всі поля.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ProductGroupEntity newGroup = new ProductGroupEntity(name, description);
        Result result = groupController.create(newGroup);
        JOptionPane.showMessageDialog(this, result.getMessage());
        clearCreateFields();
        updateAllCombos();
    }

    /**
     * Обробка оновлення вибраної групи.
     * Валідує введені дані та викликає метод оновлення групи в контролері.
     */
    private void handleUpdate() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) updateComboBox.getSelectedItem();
        if (selectedGroup == null) {
            JOptionPane.showMessageDialog(this, "Оберіть групу для оновлення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String newName = updateNameField.getText().trim();
        String newDesc = updateDescField.getText().trim();
        if (newName.isEmpty() || newDesc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Будь ласка, заповніть всі поля оновлення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ProductGroupEntity updatedGroup = new ProductGroupEntity(newName, newDesc);
        Result result = groupController.update(selectedGroup.getId(), updatedGroup);
        JOptionPane.showMessageDialog(this, result.getMessage());
        clearUpdateFields();
        updateAllCombos();
    }

    /**
     * Обробка видалення вибраної групи.
     * Підтверджує видалення групи та викликає метод видалення групи в контролері.
     */
    private void handleDelete() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) deleteComboBox.getSelectedItem();
        if (selectedGroup == null) {
            JOptionPane.showMessageDialog(this, "Оберіть групу для видалення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Ви впевнені, що хочете видалити групу " + selectedGroup.getName() + "?" +
                        "\nБудуть видалені такі продукти: " + productController.findAllByGroupId(selectedGroup.getId()), "Підтвердження",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productController.deleteAllProdutsByGroupId(selectedGroup.getId());
            Result result = groupController.delete(selectedGroup.getId());
            JOptionPane.showMessageDialog(this, result.getMessage());
            updateAllCombos();
        }
    }

    /**
     * Обробка перегляду інформації про вибрану групу.
     * Виводить деталі групи у текстову область.
     */
    private void handleViewSingle() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) viewComboBox.getSelectedItem();

        if (selectedGroup != null) {
            List<ProductEntity> products = productController.findAllByGroupId(selectedGroup.getId());
            double sum = 0;
            for (ProductEntity product : products)
                sum += product.getQuantityInStock() * product.getPricePerUnit();
            viewTextArea.setText(selectedGroup.fullName() + "\nЗагальна ціна продуктів: " + sum);
        } else {
            viewTextArea.setText("");
        }
    }

    /**
     * Оновлення текстової області для перегляду усіх груп.
     * Отримує всі групи через контролер та відображає їх.
     */
    private void refreshAllGroups() {
        List<ProductGroupEntity> groups = groupController.getAll();
        StringBuilder sb = new StringBuilder();
        for (ProductGroupEntity group : groups) {
            List<ProductEntity> products = productController.findAllByGroupId(group.getId());
            double sum = 0;
            for (ProductEntity product : products)
                sum += product.getQuantityInStock() * product.getPricePerUnit();

            sb.append(group.fullName()).append("\nЗагальна ціна продуктів: ").append(sum).append("\n\n");
        }

        viewAllTextArea.setText(sb.toString());
    }

    /**
     * Оновлення випадаючих списків на основі актуальних даних груп.
     */
    public void updateAllCombos() {
        List<ProductGroupEntity> groups = groupController.getAll();
        updateComboBox.removeAllItems();
        deleteComboBox.removeAllItems();
        viewComboBox.removeAllItems();

        for (ProductGroupEntity group : groups) {
            updateComboBox.addItem(group);
            deleteComboBox.addItem(group);
            viewComboBox.addItem(group);
        }
        refreshAllGroups();
    }

    /**
     * Очищає поля для створення нової групи.
     */
    private void clearCreateFields() {
        createNameField.setText("");
        createDescField.setText("");
    }

    /**
     * Очищає поля для оновлення групи.
     */
    private void clearUpdateFields() {
        updateNameField.setText("");
        updateDescField.setText("");
    }
}
