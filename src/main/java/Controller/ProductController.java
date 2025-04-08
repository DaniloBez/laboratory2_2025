package Controller;

import Entity.ProductEntity;
import Service.ProductService;
import Utils.Result;

import java.util.List;
import java.util.Optional;

/**
 * Контролер для керування операціями над продуктами.
 * Використовує {@link ProductService} для виконання бізнес-логіки.
 * Реалізований як Singleton.
 */
public class ProductController {
    /**
     * Сервіс для обробки логіки, пов'язаної з {@link ProductEntity}.
     */
    private ProductService productService = new ProductService();

    /**
     * Єдиний екземпляр контролера.
     */
    private static ProductController INSTANCE;

    /**
     * Приватний конструктор для реалізації Singleton.
     */
    private ProductController() {}

    /**
     * Повертає єдиний екземпляр {@code ProductController}.
     *
     * @return екземпляр {@code ProductController}
     */
    public static ProductController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductController();
        }
        return INSTANCE;
    }

    /**
     * Створює новий продукт.
     *
     * @param productEntity об'єкт продукту
     * @return {@link Result} результат створення
     */
    public Result create(ProductEntity productEntity) {
        return productService.create(productEntity);
    }

    /**
     * Оновлює існуючий продукт.
     *
     * @param id ідентифікатор продукту
     * @param productEntity нові дані продукту
     * @return {@link Result} результат оновлення
     */
    public Result update(String id, ProductEntity productEntity) {
        return productService.update(id, productEntity);
    }

    /**
     * Повертає продукт за його ідентифікатором.
     *
     * @param id ідентифікатор продукту
     * @return {@link Optional} з об'єктом {@link ProductEntity}, якщо знайдено
     */
    public Optional<ProductEntity> findById(String id) {
        return productService.getById(id);
    }

    /**
     * Повертає список усіх продуктів.
     *
     * @return список {@link ProductEntity}
     */
    public List<ProductEntity> getAll() {
        return productService.getAll();
    }

    /**
     * Видаляє продукт за його ідентифікатором.
     *
     * @param id ідентифікатор продукту
     * @return {@link Result} результат видалення
     */
    public Result delete(String id) {
        return productService.delete(id);
    }

    /**
     * Повертає список продуктів, що належать до вказаної групи.
     *
     * @param groupId ідентифікатор групи
     * @return список продуктів групи
     */
    public List<ProductEntity> findAllByGroupId(String groupId) {return productService.findAllByGroupId(groupId);}

    /**
     * Видаляє всі продукти, що належать до вказаної групи.
     *
     * @param groupId ідентифікатор групи
     * @return {@link Result} результат видалення
     */
    public Result deleteAllProdutsByGroupId(String groupId) {return productService.deleteAllProdutsByGroupId(groupId);}

    /**
     * Змінює кількість конкретного продукту на складі.
     *
     * @param id ідентифікатор продукту
     * @param count кількість для зміни (може бути від’ємною)
     * @return {@link Result} результат зміни кількості
     */
    public Result changeCountOfProduct(String id, double count) {return productService.changeCountOfProduct(id, count);}

    /**
     * Повертає список продуктів, назви яких відповідають регулярному виразу.
     *
     * @param regExp регулярний вираз для пошуку
     * @return список відповідних продуктів
     */
    public List<ProductEntity> findAllByRegExp(String regExp) {return productService.findAllByRegExp(regExp);}

}
