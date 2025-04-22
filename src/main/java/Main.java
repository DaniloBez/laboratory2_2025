import Controller.ProductController;
import Controller.ProductGroupController;
import Entity.ProductEntity;
import Entity.ProductGroupEntity;
import Utils.JMenuBarUtil;
import View.MainForm;
import View.ProductForm;
import View.ProductGroupForm;

public class Main {
    public static void main(String[] args) {
        ProductController controller = ProductController.getInstance();
        ProductGroupController groupController = ProductGroupController.getInstance();
        ProductGroupEntity groupEntity = new ProductGroupEntity("Test group", "Test description");
        groupController.create(groupEntity);
        controller.create(new ProductEntity("name", "description", "manufacturer", 10, 10, groupEntity.getId()));


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
