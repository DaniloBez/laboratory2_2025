package Repository;

import Entity.ProductGroupEntity;
import Utils.Result;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторій для управління об'єктами {@link ProductGroupEntity}.
 */
public interface IProductGroupRepository {

    /**
     * Зберігає нову групу продуктів.
     *
     * @param productGroup продукт для збереження
     * @return об'єкт класу {@link Result}
     */
    Result save(ProductGroupEntity productGroup);

    /**
     * Оновлює наявну групу продуктів за унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор групи продукту
     * @param productGroup група продуктів для оновлення
     * @return об'єкт класу {@link Result}
     */
    Result update(String id, ProductGroupEntity productGroup);

    /**
     * Повертає групу продуктів за унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор групи продуктів
     * @return Optional, що містить групу продуктів, якщо вона знайдена
     */
    Optional<ProductGroupEntity> findById(String id);

    /**
     * Повертає всі групи продуктів.
     *
     * @return список всіх груп продуктів
     */
    List<ProductGroupEntity> findAll();

    /**
     * Видаляє групу продуктів за унікальним ідентифікатором.
     *
     * @param id унікальний ідентифікатор групи продуктів
     * @return об'єкт класу {@link Result}
     */
    Result deleteById(String id);
}
