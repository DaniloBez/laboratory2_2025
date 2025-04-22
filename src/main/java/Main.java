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

        // Створення груп товарів
        ProductGroupEntity foodGroup = new ProductGroupEntity("Продовольчі товари", "Продукти харчування");
        ProductGroupEntity nonFoodGroup = new ProductGroupEntity("Непродовольчі товари", "Товари широкого вжитку");
        ProductGroupEntity electronicsGroup = new ProductGroupEntity("Електроніка", "Побутова техніка та електроніка");
        ProductGroupEntity householdGroup = new ProductGroupEntity("Господарські товари", "Товари для дому");

        groupController.create(foodGroup);
        groupController.create(nonFoodGroup);
        groupController.create(electronicsGroup);
        groupController.create(householdGroup);

        // Додавання товарів до кожної групи

        // Продовольчі товари
        controller.create(new ProductEntity("Борошно", "Пшеничне борошно вищого ґатунку", "Київхліб", 50, 25.50, foodGroup.getId()));
        controller.create(new ProductEntity("Гречка", "Гречана крупа", "Агротіка", 30, 45.75, foodGroup.getId()));
        controller.create(new ProductEntity("Цукор", "Цукор-пісок", "Цукротрест", 40, 32.20, foodGroup.getId()));
        controller.create(new ProductEntity("Олія", "Соняшникова олія", "Олійник", 25, 65.00, foodGroup.getId()));
        controller.create(new ProductEntity("Молоко", "Пастеризоване молоко 2,5%", "Галичина", 60, 28.90, foodGroup.getId()));
        controller.create(new ProductEntity("Яйця", "Яйця курячі С0", "Ясенсвіт", 100, 3.50, foodGroup.getId()));
        controller.create(new ProductEntity("Хліб", "Хліб білий нарезний", "Хлібодар", 40, 18.75, foodGroup.getId()));
        controller.create(new ProductEntity("Сіль", "Сіль кухонна", "Артемсіль", 80, 8.50, foodGroup.getId()));
        controller.create(new ProductEntity("Чай", "Чай чорний", "Ліптон", 35, 75.30, foodGroup.getId()));
        controller.create(new ProductEntity("Кава", "Кава мелена", "Jacobs", 20, 120.00, foodGroup.getId()));

        // Непродовольчі товари
        controller.create(new ProductEntity("Мило", "Туалетне мило", "Duru", 45, 15.80, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Шампунь", "Шампунь для волосся", "Head & Shoulders", 30, 85.50, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Зубна паста", "Паста для зубів", "Colgate", 50, 42.30, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Гель для душу", "Освіжаючий гель", "Fa", 40, 65.20, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Рушник", "Банний рушник", "Текстиль", 25, 120.00, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Постільна білизна", "Комплект постільної білизни", "Cotton Life", 15, 350.00, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Ганчірка", "Мікрофіброва ганчірка", "Clean", 60, 25.50, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Віник", "Віник для підлоги", "Domax", 20, 75.00, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Відбілювач", "Відбілювач для білизни", "Ace", 35, 45.80, nonFoodGroup.getId()));
        controller.create(new ProductEntity("Порошок", "Пральний порошок", "Tide", 30, 95.00, nonFoodGroup.getId()));

        // Електроніка
        controller.create(new ProductEntity("Телевізор", "Smart TV 4K", "Samsung", 10, 15000.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Смартфон", "Флагманський смартфон", "Apple", 15, 35000.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Ноутбук", "Ігровий ноутбук", "Asus", 8, 28000.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Навушники", "Бездротові навушники", "Sony", 20, 2500.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Пральна машина", "Автоматична пральна машина", "LG", 5, 12000.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Холодильник", "Двокамерний холодильник", "Bosch", 7, 18000.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Пилосос", "Робот-пилосос", "Xiaomi", 12, 8000.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Мікрохвильовка", "Мікрохвильова піч", "Panasonic", 15, 3500.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Фен", "Професійний фен", "Philips", 25, 1200.00, electronicsGroup.getId()));
        controller.create(new ProductEntity("Електрочайник", "Швидкозаварювач", "Tefal", 30, 900.00, electronicsGroup.getId()));

        // Господарські товари
        controller.create(new ProductEntity("Каструля", "Каструля нержавіюча", "Tefal", 15, 850.00, householdGroup.getId()));
        controller.create(new ProductEntity("Сковорода", "Антипригарна сковорода", "Fissler", 20, 650.00, householdGroup.getId()));
        controller.create(new ProductEntity("Набір ножів", "6 предметів", "Zepter", 10, 1200.00, householdGroup.getId()));
        controller.create(new ProductEntity("Термос", "Термос 1л", "Thermos", 25, 450.00, householdGroup.getId()));
        controller.create(new ProductEntity("Блендер", "Погружний блендер", "Braun", 12, 1500.00, householdGroup.getId()));
        controller.create(new ProductEntity("Дощовик", "Чоловічий дощовик", "Tucano", 30, 750.00, householdGroup.getId()));
        controller.create(new ProductEntity("Парасолька", "Автоматична парасолька", "Fulton", 40, 350.00, householdGroup.getId()));
        controller.create(new ProductEntity("Сумка", "Дамська сумка", "Guess", 18, 1200.00, householdGroup.getId()));
        controller.create(new ProductEntity("Гаманець", "Чоловічий гаманець", "Piquadro", 22, 850.00, householdGroup.getId()));
        controller.create(new ProductEntity("Рюкзак", "Туристичний рюкзак", "Deuter", 15, 1500.00, householdGroup.getId()));

        // Ініціалізація форм
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
