package Controller;

import Entity.ProductGroupEntity;
import Service.ProductGroupService;
import Utils.Result;

import java.util.List;
import java.util.Optional;

/**
 * Контролер для управління запитами, пов'язаними з {@link ProductGroupEntity}.
 * Реалізує шаблон Singleton.
 */
public class ProductGroupController {
    /**
     * Сервіс для обробки логіки, пов'язаної з {@link ProductGroupEntity}.
     */
    private ProductGroupService productGroupService = new ProductGroupService();

    /**
     * Єдиний екземпляр контролера.
     */
    private static ProductGroupController INSTANCE;

    /**
     * Приватний конструктор для реалізації Singleton.
     */
    private ProductGroupController() {}

    /**
     * Повертає єдиний екземпляр {@code ProductGroupController}.
     *
     * @return екземпляр {@code ProductGroupController}
     */
    public static ProductGroupController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductGroupController();
        }
        return INSTANCE;
    }

    /**
     * Створює нову групу товарів.
     *
     * @param productGroupEntity об'єкт групи товарів
     * @return {@link Result} результат створення
     */
    public Result create(ProductGroupEntity productGroupEntity) {
        return productGroupService.create(productGroupEntity);
    }

    /**
     * Оновлює існуючу групу товарів.
     *
     * @param id ідентифікатор групи
     * @param productGroupEntity нові дані групи
     * @return {@link Result} результат оновлення
     */
    public Result update(String id, ProductGroupEntity productGroupEntity) {
        return productGroupService.update(id, productGroupEntity);
    }

    /**
     * Повертає групу товарів за її ідентифікатором.
     *
     * @param id ідентифікатор групи
     * @return {@link Optional} з об'єктом {@link ProductGroupEntity}, якщо знайдено
     */
    public Optional<ProductGroupEntity> findById(String id) {
        return productGroupService.findById(id);
    }

    /**
     * Повертає всі групи товарів.
     *
     * @return список усіх {@link ProductGroupEntity}
     */
    public List<ProductGroupEntity> getAll() {
        return productGroupService.getAll();
    }

    /**
     * Видаляє групу товарів за її ідентифікатором.
     *
     * @param id ідентифікатор групи
     * @return {@link Result} результат видалення
     */
    public Result delete(String id) {
        return productGroupService.delete(id);
    }
}
