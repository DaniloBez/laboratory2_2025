import Utils.JMenuBarUtil;
import View.MainForm;
import View.ProductForm;
import View.ProductGroupForm;

public class Main {
    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        JMenuBarUtil.mainForm = mainForm;
        mainForm.setVisible(true);

        ProductForm productForm = new ProductForm();
        JMenuBarUtil.productForm = productForm;
        productForm.setVisible(false);

        ProductGroupForm productGroupForm = new ProductGroupForm();
        JMenuBarUtil.productGroupForm = productGroupForm;
        productGroupForm.setVisible(false);
    }
}
