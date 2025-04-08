package Repository;

import Entity.ProductEntity;
import Utils.Result;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторій для управління об'єктами {@link ProductEntity}.
 */
public interface IProductRepository {

    /**
     * Зберігає новий продукт.
     *
     * @param product продукт для збереження
     * @return об'єкт класу {@link Result}
     */
    Result save(ProductEntity product);

    /**
     * Оновлює наявний продукт за унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор продукту
     * @param product продукт для оновлення
     * @return об'єкт класу {@link Result}
     */
    Result update(String id, ProductEntity product);

    /**
     * Повертає продукт за унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор продукту
     * @return Optional, що містить продукт, якщо він знайдений
     */
    Optional<ProductEntity> findById(String id);

    /**
     * Повертає всі продукти.
     *
     * @return список всіх продуктів
     */
    List<ProductEntity> findAll();

    /**
     * Видаляє продукт за унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор продукту
     * @return об'єкт класу {@link Result}
     */
    Result deleteById(String id);
}
