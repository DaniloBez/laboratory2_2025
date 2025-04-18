import Controller.ProductController;
import Entity.ProductEntity;
import Utils.JMenuBarUtil;
import View.MainForm;
import View.ProductForm;
import View.ProductGroupForm;

public class Main {
    public static void main(String[] args) {
        ProductController controller = ProductController.getInstance();
        controller.create(new ProductEntity("name", "description", "manufacturer", 10, 10, "241315be-19e2-4651-942a-c2bfad244d40"));


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
