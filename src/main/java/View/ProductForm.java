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
import java.util.Objects;

/**
 * Клас для створення форми управління продуктами.
 * Має вкладки для створення, оновлення, видалення, перегляду однієї групи та перегляду всіх продуктів.
 */
public class ProductForm extends JFrame {
    private final ProductController productController;
    private final ProductGroupController productGroupController;

    // Компоненти для вкладки "Створити"
    private JComboBox<ProductGroupEntity> createComboBoxGroup;
    private JTextField createNameField, createDescField, createManufacturerField, createQuantityInStockField, createPriceField;
    private JButton createButton;

    // Компоненти для вкладки "Оновити"
    private JComboBox<ProductGroupEntity> updateComboBoxGroup;
    private JComboBox<ProductEntity> updateComboBoxProduct;
    private JTextField updateNameField, updateDescField, updateManufacturerField, updateQuantityInStockField, updatePriceField;
    private JButton updateButton;

    // Компоненти для вкладки "Видалити"
    private JComboBox<ProductEntity> deleteComboBoxProduct;
    private JComboBox<ProductGroupEntity> deleteComboBoxGroup;
    private JButton deleteButton;

    // Компоненти для вкладки "Перегляд" (інформація про об'єкт)
    private JComboBox<ProductEntity> viewComboBox;
    private JComboBox<ProductGroupEntity> viewComboBoxGroup;
    private JTextArea viewTextArea;

    // Компоненти для вкладки "Пошук за regexp товарів"
    private JTextArea viewAllTextArea;
    private JButton refreshButton;
    private JTextField filterField;

    // Компоненти для вкладки "Додавання/списання товару"
    private JComboBox<ProductEntity> changeCountComboBox;
    private JComboBox<ProductGroupEntity> changeCountComboBoxGroup;
    private JTextField changeCountField, currentCountField;
    private JButton changeCountButton;

    // Компоненти для вкладки "Перегляд за групою"
    private JComboBox<ProductGroupEntity> viewByGroupComboBox;
    private JTextArea viewByGroupTextArea;

    /**
     * Конструктор, що ініціалізує форму, встановлює вигляд та компоненти.
     */
    public ProductForm() {
        productController = ProductController.getInstance();
        productGroupController = ProductGroupController.getInstance();

        setTitle("Форма управління продуктами товарів");
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
        createPanel.add(new JLabel("Оберіть групу до якої належить продукт:"), gbc);
        gbc.gridx = 1;
        createComboBoxGroup = new JComboBox<>();
        createPanel.add(createComboBoxGroup, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        createPanel.add(new JLabel("Назва продукту:"), gbc);
        gbc.gridx = 1;
        createNameField = new JTextField(20);
        createPanel.add(createNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        createPanel.add(new JLabel("Опис продукту:"), gbc);
        gbc.gridx = 1;
        createDescField = new JTextField(20);
        createPanel.add(createDescField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        createPanel.add(new JLabel("Мануфактура:"), gbc);
        gbc.gridx = 1;
        createManufacturerField = new JTextField(20);
        createPanel.add(createManufacturerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        createPanel.add(new JLabel("Кількість на складі:"), gbc);
        gbc.gridx = 1;
        createQuantityInStockField = new JTextField(20);
        createPanel.add(createQuantityInStockField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        createPanel.add(new JLabel("Ціна за одиницю:"), gbc);
        gbc.gridx = 1;
        createPriceField = new JTextField(20);
        createPanel.add(createPriceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        createButton = new JButton("Створити продукт");
        createPanel.add(createButton, gbc);

        createButton.addActionListener(e -> handleCreate());

        // --------------------- Вкладка "Оновити" ---------------------
        JPanel updatePanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        updatePanel.add(new JLabel("Оберіть продукт для оновлення:"), gbc);
        gbc.gridx = 1;
        updateComboBoxProduct = new JComboBox<>();
        updatePanel.add(updateComboBoxProduct, gbc);

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

        gbc.gridx = 0;
        gbc.gridy = 3;
        updatePanel.add(new JLabel("Нова мануфактура:"), gbc);
        gbc.gridx = 1;
        updateManufacturerField = new JTextField(20);
        updatePanel.add(updateManufacturerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        updatePanel.add(new JLabel("Нова кількість на складі:"), gbc);
        gbc.gridx = 1;
        updateQuantityInStockField = new JTextField(20);
        updatePanel.add(updateQuantityInStockField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        updatePanel.add(new JLabel("Нова ціна за одиницю:"), gbc);
        gbc.gridx = 1;
        updatePriceField = new JTextField(20);
        updatePanel.add(updatePriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        updatePanel.add(new JLabel("Оберіть нову групу для продукта:"), gbc);
        gbc.gridx = 1;
        updateComboBoxGroup = new JComboBox<>();
        updatePanel.add(updateComboBoxGroup, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        updateButton = new JButton("Оновити продукт");
        updatePanel.add(updateButton, gbc);

        updateButton.addActionListener(e -> handleUpdate());

        // --------------------- Вкладка "Видалити" ---------------------
        JPanel deletePanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        deletePanel.add(new JLabel("Оберіть групу до якої належить:"), gbc);
        gbc.gridx = 1;
        deleteComboBoxGroup = new JComboBox<>();
        deletePanel.add(deleteComboBoxGroup, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        deletePanel.add(new JLabel("Оберіть продукт для видалення:"), gbc);
        gbc.gridx = 1;
        deleteComboBoxProduct = new JComboBox<>();
        deletePanel.add(deleteComboBoxProduct, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        deleteButton = new JButton("Видалити продукт");
        deletePanel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> handleDelete());
        deleteComboBoxGroup.addActionListener(e -> {
            ProductGroupEntity selectedGroup = (ProductGroupEntity) deleteComboBoxGroup.getSelectedItem();
            if (selectedGroup != null) {
                updateProductComboById(selectedGroup.getId());
            }
        });

        // --------------------- Вкладка "Перегляд товару" ---------------------
        JPanel viewPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        viewPanel.add(new JLabel("Оберіть групу:"), gbc);
        gbc.gridx = 1;
        viewComboBoxGroup = new JComboBox<>();
        viewPanel.add(viewComboBoxGroup, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        viewPanel.add(new JLabel("Оберіть продукт для перегляду:"), gbc);
        gbc.gridx = 1;
        viewComboBox = new JComboBox<>();
        viewPanel.add(viewComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        viewTextArea = new JTextArea(7, 30);
        viewTextArea.setEditable(false);
        JScrollPane scrollView = new JScrollPane(viewTextArea);
        viewPanel.add(scrollView, gbc);

        // Додаємо слухач подій для комбобоксу груп
        viewComboBoxGroup.addActionListener(e -> {
            ProductGroupEntity selectedGroup = (ProductGroupEntity) viewComboBoxGroup.getSelectedItem();
            if (selectedGroup != null) {
                updateViewComboByGroupId(selectedGroup.getId());
            }
        });

        // Додаємо слухач подій для комбобоксу продуктів
        viewComboBox.addActionListener(e -> handleViewSingle());

        // --------------------- Вкладка "Пошук за regexp товарів" ---------------------
        JPanel viewAllPanel = new JPanel(new BorderLayout());

        // Панель для вводу фільтра
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel inputLabel = new JLabel("Введіть regexp:");
        filterField = new JTextField();

        JPanel inputGroup = new JPanel(new BorderLayout(5, 0));
        inputGroup.add(inputLabel, BorderLayout.WEST);
        inputGroup.add(filterField, BorderLayout.CENTER);

        inputPanel.add(inputGroup, BorderLayout.NORTH);

        // Текстова область
        viewAllTextArea = new JTextArea();
        viewAllTextArea.setEditable(false);
        JScrollPane scrollAll = new JScrollPane(viewAllTextArea);

        // Кнопка оновлення
        refreshButton = new JButton("Оновити список продуктів");
        refreshButton.addActionListener(e -> {
            displayFilteredProducts();
        });
        filterField.addActionListener(e -> displayFilteredProducts());

        // Додаємо компоненти
        viewAllPanel.add(inputPanel, BorderLayout.NORTH);
        viewAllPanel.add(scrollAll, BorderLayout.CENTER);
        viewAllPanel.add(refreshButton, BorderLayout.SOUTH);

    // --------------------- Вкладка "Додавання/списання товару" ---------------------
        JPanel changeCountPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        changeCountPanel.add(new JLabel("Оберіть групу:"), gbc);
        gbc.gridx = 1;
        changeCountComboBoxGroup = new JComboBox<>();
        changeCountPanel.add(changeCountComboBoxGroup, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        changeCountPanel.add(new JLabel("Оберіть продукт:"), gbc);
        gbc.gridx = 1;
        changeCountComboBox = new JComboBox<>();
        changeCountPanel.add(changeCountComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        changeCountPanel.add(new JLabel("Поточна кількість товару:"), gbc);
        gbc.gridx = 1;
        currentCountField = new JTextField(20);
        currentCountField.setEditable(false);
        changeCountPanel.add(currentCountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        changeCountPanel.add(new JLabel("Зміна кількості (введіть +або- число):"), gbc);
        gbc.gridx = 1;
        changeCountField = new JTextField(20);
        changeCountPanel.add(changeCountField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        changeCountButton = new JButton("Змінити кількість");
        changeCountPanel.add(changeCountButton, gbc);

        changeCountComboBoxGroup.addActionListener(e -> {
            ProductGroupEntity selectedGroup = (ProductGroupEntity) changeCountComboBoxGroup.getSelectedItem();
            if (selectedGroup != null) {
                updateChangeCountComboByGroupId(selectedGroup.getId());
            }
        });

        changeCountComboBox.addActionListener(e -> {
            ProductEntity selectedProduct = (ProductEntity) changeCountComboBox.getSelectedItem();
            if (selectedProduct != null) {
                currentCountField.setText(String.valueOf(selectedProduct.getQuantityInStock()));
            } else {
                currentCountField.setText("");
            }
        });

        changeCountButton.addActionListener(e -> handleChangeCount());

        // --------------------- Вкладка "Перегляд за групою" ---------------------
        JPanel viewByGroupPanel = new JPanel(new BorderLayout());

        // Панель для вибору групи
        JPanel groupSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        groupSelectionPanel.add(new JLabel("Оберіть групу:"));
        viewByGroupComboBox = new JComboBox<>();
        groupSelectionPanel.add(viewByGroupComboBox);

        // Текстова область для виводу товарів
        viewByGroupTextArea = new JTextArea();
        viewByGroupTextArea.setEditable(false);
        JScrollPane scrollByGroup = new JScrollPane(viewByGroupTextArea);

        // Додаємо слухача для комбобоксу
        viewByGroupComboBox.addActionListener(e -> displayProductsByGroup());

        // Додаємо компоненти
        viewByGroupPanel.add(groupSelectionPanel, BorderLayout.NORTH);
        viewByGroupPanel.add(scrollByGroup, BorderLayout.CENTER);

        // --------------------- Додаємо всі вкладки до таб-панелі ---------------------
        tabbedPane.addTab("Створити", createPanel);
        tabbedPane.addTab("Оновити", updatePanel);
        tabbedPane.addTab("Видалити", deletePanel);
        tabbedPane.addTab("Перегляд товару", viewPanel);
        tabbedPane.addTab("Перегляд за групою", viewByGroupPanel);
        tabbedPane.addTab("Пошук за regexp", viewAllPanel);
        tabbedPane.addTab("Додавання/списання товару", changeCountPanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Відображає список товарів вибраної групи з підрахунком загальної вартості.
     * Використовує {@link ProductEntity#fullName()} для форматування інформації про кожен товар.
     */
    private void displayProductsByGroup() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) viewByGroupComboBox.getSelectedItem();
        if (selectedGroup == null) {
            viewByGroupTextArea.setText("Оберіть групу для перегляду");
            return;
        }

        List<ProductEntity> products = productController.findAllByGroupId(selectedGroup.getId());
        StringBuilder sb = new StringBuilder();

        if (products.isEmpty()) {
            sb.append("У групі '").append(selectedGroup.getName()).append("' немає товарів");
        } else {
            sb.append("Товари групи '").append(selectedGroup.getName()).append("':\n\n");
            double totalValue = 0;

            for (ProductEntity product : products) {
                sb.append(product.fullName()).append("\n\n");
                totalValue += product.getQuantityInStock() * product.getPricePerUnit();
            }

            sb.append(String.format("Загальна вартість товарів у групі: %.2f", totalValue));
        }

        viewByGroupTextArea.setText(sb.toString());
    }

    /** Обробник зміни кількості товару */
    private void handleChangeCount() {
        ProductEntity selectedProduct = (ProductEntity) changeCountComboBox.getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Будь ласка, оберіть продукт");
            return;
        }

        try {
            double quantityChange = Double.parseDouble(changeCountField.getText());
            Result result = productController.changeCountOfProduct(
                    selectedProduct.getId(),
                    quantityChange
            );

            if (result.isSuccess()) {
                JOptionPane.showMessageDialog(this, "Кількість товару успішно змінена");
                // Оновлюємо поточне значення
                currentCountField.setText(String.valueOf(
                        selectedProduct.getQuantityInStock() + quantityChange
                ));
                // Оновлюємо всі комбобокси
                updateAllCombos();
                changeCountField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Помилка: " + result.getMessage());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Будь ласка, введіть коректне число");
        }
    }

    /**
     * Обробляє створення нового продукту.
     */
    private void handleCreate() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) updateComboBoxGroup.getSelectedItem();
        if (selectedGroup == null) return;

        String name = createNameField.getText().trim();
        String description = createDescField.getText().trim();
        String manufacturer = createManufacturerField.getText().trim();
        String quantityText = createQuantityInStockField.getText().trim();
        String priceText = createPriceField.getText().trim();

        // Валідація обов'язкових текстових полів
        if (!validateRequiredTextFields(name, description, manufacturer)) {
            return;
        }

        // Валідація кількості
        Double quantityInStock = validatePositiveNumber(quantityText, "Кількість товару повинна бути більше 0.");
        if (quantityInStock == null) return;

        // Валідація ціни
        Double price = validatePositiveNumber(priceText, "Ціна товару повинна бути більше 0.");
        if (price == null) return;

        ProductEntity newProduct = new ProductEntity(name, description, manufacturer,
                quantityInStock, price, selectedGroup.getId());
        Result result = productController.create(newProduct);
        JOptionPane.showMessageDialog(this, result.getMessage());
        clearCreateFields();
        updateAllCombos();
    }



    /**
     * Обробляє оновлення вибраного продукту.
     *
     * <p>Якщо група залишається незмінною - всі поля обов'язкові.
     * При зміні групи - інші поля стають необов'язковими (можна лише перенести продукт в іншу групу).</p>
     *
     * <p>Ціна та кількість товару повинні бути більше 0.</p>
     */
    private void handleUpdate() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) updateComboBoxGroup.getSelectedItem();
        ProductEntity selectedProduct = (ProductEntity) updateComboBoxProduct.getSelectedItem();
        if (selectedGroup == null || selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Оберіть продукт для оновлення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newName = updateNameField.getText().trim();
        String newDesc = updateDescField.getText().trim();
        String newManufacturer = updateManufacturerField.getText().trim();
        String quantityText = updateQuantityInStockField.getText().trim();
        String priceText = updatePriceField.getText().trim();

        boolean groupChanged = !Objects.equals(selectedGroup.getId(), selectedProduct.getProductGroupId());

        if (!groupChanged && !validateRequiredTextFields(newName, newDesc, newManufacturer, quantityText, priceText)) {
            return;
        }

        Double newQuantityInStock = validateQuantity(quantityText, groupChanged, selectedProduct);
        if (newQuantityInStock == null) return;

        Double newPrice = validatePrice(priceText, groupChanged, selectedProduct);
        if (newPrice == null) return;

        String finalName = newName.isEmpty() && groupChanged ? selectedProduct.getName() : newName;
        String finalDesc = newDesc.isEmpty() && groupChanged ? selectedProduct.getDescription() : newDesc;
        String finalManufacturer = newManufacturer.isEmpty() && groupChanged ?
                selectedProduct.getManufacturer() : newManufacturer;

        ProductEntity updatedProduct = new ProductEntity(
                finalName, finalDesc, finalManufacturer,
                newQuantityInStock, newPrice, selectedGroup.getId()
        );

        Result result = productController.update(selectedProduct.getId(), updatedProduct);
        JOptionPane.showMessageDialog(this, result.getMessage());
        clearUpdateFields();
        updateAllCombos();
    }

    /**
     * Валідує кількість товару. Якщо поле порожнє при зміні групи - повертає поточне значення.
     */
    private Double validateQuantity(String quantityText, boolean groupChanged, ProductEntity product) {
        if (quantityText.isEmpty() && groupChanged) {
            return product.getQuantityInStock();
        }
        return validatePositiveNumber(quantityText, "Кількість товару повинна бути більше 0.");
    }

    /**
     * Валідує ціну товару. Якщо поле порожнє при зміні групи - повертає поточну ціну.
     */
    private Double validatePrice(String priceText, boolean groupChanged, ProductEntity product) {
        if (priceText.isEmpty() && groupChanged) {
            return product.getPricePerUnit();
        }
        return validatePositiveNumber(priceText, "Ціна товару повинна бути більше 0.");
    }

    /**
     * Валідує обов'язкові текстові поля.
     */
    private boolean validateRequiredTextFields(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Будь ласка, заповніть всі обов'язкові поля.",
                        "Помилка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Валідує позитивне числове значення.
     */
    private Double validatePositiveNumber(String numberText, String errorMessage) {
        try {
            double value = Double.parseDouble(numberText);
            if (value <= 0) {
                JOptionPane.showMessageDialog(this, errorMessage, "Помилка", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Невірний формат числового поля.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    /**
     * Обробка видалення вибраного продукта.
     * Підтверджує видалення продукта та викликає метод видалення продукта в контролері.
     */
    private void handleDelete() {
        ProductGroupEntity selectedGroup = (ProductGroupEntity) deleteComboBoxGroup.getSelectedItem();
        if (selectedGroup == null) {
            JOptionPane.showMessageDialog(this, "Оберіть групу для видалення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Оновлюємо список продуктів для вибраної групи
        updateProductComboById(selectedGroup.getId());

        ProductEntity selectedProduct = (ProductEntity) deleteComboBoxProduct.getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Оберіть продукт для видалення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Ви впевнені, що хочете видалити продукт " + selectedProduct.getName() + "?", "Підтвердження",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Result result = productController.delete(selectedProduct.getId());
            JOptionPane.showMessageDialog(this, result.getMessage());
            updateAllCombos();
        }
    }

    /**
     * Обробка перегляду інформації про вибраний продукт.
     * Виводить деталі продукта у текстову область.
     */
    private void handleViewSingle() {
        ProductEntity selectedProduct = (ProductEntity) viewComboBox.getSelectedItem();
        if (selectedProduct != null) {
            viewTextArea.setText(selectedProduct.fullName() +
                    "\nГрупа: " + productGroupController.findById(selectedProduct.getProductGroupId()).get());
        } else {
            viewTextArea.setText("");
        }
    }

    /**
     * Відображає відфільтровані продукти з використанням {@link ProductEntity#fullName()}.
     */
    private void displayFilteredProducts() {
        String filterText = filterField.getText().trim();
        List<ProductEntity> products = productController.findAllByRegExp(filterText);

        StringBuilder sb = new StringBuilder();

        if (products == null) {
            sb.append("Помилка: Невірний синтаксис регулярного виразу");
        } else if (products.isEmpty()) {
            sb.append("Не знайдено продуктів");
            if (!filterText.isEmpty() && !"*".equals(filterText)) {
                sb.append(" за вказаним фільтром: ").append(filterText);
            }
        } else {
            sb.append("Список продуктів");
            if (!filterText.isEmpty() && !"*".equals(filterText)) {
                sb.append(" (фільтр: ").append(filterText).append(")");
            }
            sb.append(" (").append(products.size()).append("):\n\n");

            products.forEach(product -> sb.append(String.format(
                    "Назва: %s\nОпис: %s\nВиробник: %s\nКількість: %.2f\nЦіна: %.2f\nГрупа: %s\n\n",
                    product.getName(),
                    product.getDescription(),
                    product.getManufacturer(),
                    product.getQuantityInStock(),
                    product.getPricePerUnit(),
                    productGroupController.findById(product.getProductGroupId()).get()
            )));
        }

        viewAllTextArea.setText(sb.toString());
    }

    /**
     * Оновлення випадаючих списків на основі актуальних даних продуктів.
     */
    public void updateAllCombos() {
        updateAllGroupCombos();
        updateAllProductCombos();
    }

    /**
     * Оновлює комбобокс продуктів для перегляду, фільтруючи по ID групи.
     */
    private void updateViewComboByGroupId(String groupId) {
        List<ProductEntity> products = productController.findAllByGroupId(groupId);
        viewComboBox.removeAllItems();

        for (ProductEntity product : products) {
            viewComboBox.addItem(product);
        }
    }

    /**
     * Оновлює комбобокс продуктів для зміни кількості, фільтруючи по ID групи.
     */
    private void updateChangeCountComboByGroupId(String groupId) {
        List<ProductEntity> products = productController.findAllByGroupId(groupId);
        changeCountComboBox.removeAllItems();

        for (ProductEntity product : products) {
            changeCountComboBox.addItem(product);
        }
    }

    /**
     * Оновлює комбобокс продуктів для видалення, фільтруючи по ID групи.
     *
     * @param groupId ID групи продуктів для фільтрації
     */
    private void updateProductComboById(String groupId) {
        // Зберігаємо поточний вибраний продукт
        ProductEntity selectedProduct = (ProductEntity) deleteComboBoxProduct.getSelectedItem();
        String selectedProductId = selectedProduct != null ? selectedProduct.getId() : null;

        List<ProductEntity> products = productController.findAllByGroupId(groupId);
        deleteComboBoxProduct.removeAllItems();

        for (ProductEntity product : products) {
            deleteComboBoxProduct.addItem(product);
            // Відновлюємо вибраний продукт, якщо він існує в новому списку
            if (selectedProductId != null && selectedProductId.equals(product.getId())) {
                deleteComboBoxProduct.setSelectedItem(product);
            }
        }

        // Якщо не вдалося відновити вибраний продукт, вибираємо перший елемент
        if (deleteComboBoxProduct.getSelectedIndex() == -1 && deleteComboBoxProduct.getItemCount() > 0) {
            deleteComboBoxProduct.setSelectedIndex(0);
        }
    }

    /**
     * Оновлює всі комбобокси груп продуктів (для створення, оновлення та видалення).
     * Завантажує актуальний список груп з контролера.
     */
    private void updateAllGroupCombos() {
        List<ProductGroupEntity> groups = productGroupController.getAll();
        createComboBoxGroup.removeAllItems();
        updateComboBoxGroup.removeAllItems();
        deleteComboBoxGroup.removeAllItems();
        viewComboBoxGroup.removeAllItems();
        changeCountComboBoxGroup.removeAllItems();
        viewByGroupComboBox.removeAllItems();

        for (ProductGroupEntity group : groups) {
            createComboBoxGroup.addItem(group);
            updateComboBoxGroup.addItem(group);
            deleteComboBoxGroup.addItem(group);
            viewComboBoxGroup.addItem(group);
            changeCountComboBoxGroup.addItem(group);
            viewByGroupComboBox.addItem(group);
        }
    }

    /**
     * Оновлює всі комбобокси продуктів (для оновлення, перегляду та зміни кількості).
     * Завантажує повний список продуктів з контролера.
     */
    private void updateAllProductCombos() {
        List<ProductEntity> products = productController.getAll();
        updateComboBoxProduct.removeAllItems();
        viewComboBox.removeAllItems();
        changeCountComboBox.removeAllItems();

        for (ProductEntity product : products) {
            updateComboBoxProduct.addItem(product);
            viewComboBox.addItem(product);
            changeCountComboBox.addItem(product);
        }
    }


    /**
     * Очищає поля для створення нового продукту.
     */
    private void clearCreateFields() {
        createNameField.setText("");
        createDescField.setText("");
        createManufacturerField.setText("");
        createQuantityInStockField.setText("");
        createPriceField.setText("");
    }

    /**
     * Очищає поля для оновлення продукту.
     */
    private void clearUpdateFields() {
        createNameField.setText("");
        createDescField.setText("");
        createManufacturerField.setText("");
        createQuantityInStockField.setText("");
        createPriceField.setText("");
    }
}