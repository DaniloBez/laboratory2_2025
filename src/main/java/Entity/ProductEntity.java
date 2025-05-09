package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляє продукт, ідентифікований унікальним ID.
 */
@Getter
@NoArgsConstructor
public class ProductEntity {

    /**
     * Унікальний ідентифікатор продукту.
     */
    private String id = UUID.randomUUID().toString();

    /**
     * Назва продукту.
     */
    @Setter
    private String name;

    /**
     * Опис продукту.
     */
    @Setter
    private String description;

    /**
     * Виробник продукту.
     */
    @Setter
    private String manufacturer;

    /**
     * Кількість продукту на складі.
     */
    @Setter
    private double quantityInStock;

    /**
     * Ціна за одиницю продукту.
     */
    @Setter
    private double pricePerUnit;

    /**
     * Унікальний ідентифікатор групи продуктів, до якої належить продукт.
     */
    @Setter
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
     * Повертає повну назву товару без id.
     * @return повна назва
     */
    public String fullName() {
        return "Назва: " + name + "\n" +
                "Опис: " + description + "\n" +
                "Виробник: " + manufacturer + "\n" +
                "Кількість на складі: " + quantityInStock + "\n" +
                "Ціна за одиницю: " + pricePerUnit + "\n" +
                "Загальна вартість товару на складі: " + quantityInStock * pricePerUnit;
    }

    /**
     * Повертає рядкове представлення продукту.
     *
     * @return рядок, що містить інформацію про продукт
     */
    @Override
    public String toString() {
        return name;
    }
}
