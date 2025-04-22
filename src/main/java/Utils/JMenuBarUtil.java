package Utils;

import View.MainForm;
import View.ProductForm;
import View.ProductGroupForm;

import javax.swing.*;

/**
 * Утилітний клас {@code JMenuBarUtil} створює меню для перемикання між різними формами застосунку.
 */
public class JMenuBarUtil {
    /** Посилання на головну форму */
    public static MainForm mainForm;

    /** Посилання на форму товарів */
    public static ProductForm productForm;

    /** Посилання на форму груп товарів */
    public static ProductGroupForm productGroupForm;

    /**
     * Повертає панель меню з пунктами для відкриття різних форм.
     *
     * @return меню {@code JMenuBar} з пунктами "Main", "Product", "Product Group"
     */
    public static JMenuBar getMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenuItem main = new JMenuItem("Main");
        main.addActionListener(_ -> openMainForm());
        jMenuBar.add(main);

        JMenuItem product = new JMenuItem("Product");
        product.addActionListener(_ -> openProductForm());
        jMenuBar.add(product);

        JMenuItem productGroup = new JMenuItem("Product Group");
        productGroup.addActionListener(_ -> openProductGroupForm());
        jMenuBar.add(productGroup);

        return jMenuBar;
    }

    /**
     * Відкриває головну форму, приховуючи інші.
     */
    private static void openMainForm() {
        refreshData();
        mainForm.setVisible(true);
        productForm.setVisible(false);
        productGroupForm.setVisible(false);
    }


    /**
     * Відкриває форму товарів, приховуючи інші.
     */
    private static void openProductForm() {
        refreshData();
        productForm.setVisible(true);
        productGroupForm.setVisible(false);
        mainForm.setVisible(false);
    }

    /**
     * Відкриває форму груп товарів, приховуючи інші.
     */
    private static void openProductGroupForm() {
        refreshData();
        productGroupForm.setVisible(true);
        productForm.setVisible(false);
        mainForm.setVisible(false);
    }

    /**
     * Оновлює всі випадаючі списки у формах товарів і груп товарів.
     */
    private static void refreshData(){
        productForm.updateAllCombos();
        productGroupForm.updateAllCombos();
    }
}
