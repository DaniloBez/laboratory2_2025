package Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляє продукт, ідентифікований унікальним ID.
 */
@Getter
@Setter
public class ProductEntity {

    /**
     * Унікальний ідентифікатор продукту.
     */
    @Getter
    private String id = UUID.randomUUID().toString();

    /**
     * Назва продукту.
     */
    private String name;

    /**
     * Опис продукту.
     */
    private String description;

    /**
     * Виробник продукту.
     */
    private String manufacturer;

    /**
     * Кількість продукту на складі.
     */
    private double quantityInStock;

    /**
     * Ціна за одиницю продукту.
     */
    private double pricePerUnit;

    /**
     * Унікальний ідентифікатор групи продуктів, до якої належить продукт.
     */
    private String ProductGroupId;

    /**
     * Конструктор, що створює новий продукт з вказаними параметрами.
     *
     * @param name            назва продукту
     * @param description     опис продукту
     * @param manufacturer    виробник продукту
     * @param quantityInStock кількість на складі
     * @param pricePerUnit    ціна за одиницю
     */
    public ProductEntity(String name, String description, String manufacturer, double quantityInStock, double pricePerUnit, String ProductGroupId) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantityInStock = quantityInStock;
        this.pricePerUnit = pricePerUnit;
        this.ProductGroupId = ProductGroupId;
    }

    /**
     * Оновлює поля відповідно до нових даних
     * @param productEntity нові дані
     */
    public void update(ProductEntity productEntity) {
        this.name = productEntity.name;
        this.description = productEntity.description;
        this.manufacturer = productEntity.manufacturer;
        this.quantityInStock = productEntity.quantityInStock;
        this.pricePerUnit = productEntity.pricePerUnit;
        this.ProductGroupId = productEntity.ProductGroupId;
    }

    /**
     * Повертає рядкове представлення продукту.
     *
     * @return рядок, що містить інформацію про продукт
     */
    @Override
    public String toString() {
        return "Назва: " + name +
                ", опис: " + description +
                ", виробник: " + manufacturer +
                ", кількість на складі: " + quantityInStock +
                ", ціна за одиницю: " + pricePerUnit;
    }
}
