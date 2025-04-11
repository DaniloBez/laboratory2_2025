package Utils;

import View.MainForm;
import View.ProductForm;
import View.ProductGroupForm;

import javax.swing.*;

public class JMenuBarUtil {
    public static MainForm mainForm;
    public static ProductForm productForm;
    public static ProductGroupForm productGroupForm;

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

    private static void openMainForm() {
        mainForm.setVisible(true);
        productForm.setVisible(false);
        productGroupForm.setVisible(false);
    }

    private static void openProductForm() {
        productForm.setVisible(true);
        productGroupForm.setVisible(false);
        mainForm.setVisible(false);
    }

    private static void openProductGroupForm() {
        productGroupForm.setVisible(true);
        productForm.setVisible(false);
        mainForm.setVisible(false);
    }
}
