package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляє групу продуктів, ідентифіковану унікальним ID.
 */
@Getter
@NoArgsConstructor
public class ProductGroupEntity {

    /**
     * Унікальний ідентифікатор групи продуктів.
     */
    private final String id = UUID.randomUUID().toString();

    /**
     * Назва групи продуктів.
     */
    @Setter
    private String name;

    /**
     * Опис групи продуктів.
     */
    @Setter
    private String description;

    /**
     * Конструктор, що створює нову групу продуктів з вказаною назвою та описом.
     *
     * @param name        назва групи продуктів
     * @param description опис групи продуктів
     */
    public ProductGroupEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Оновлює поля відповідно до нових даних
     * @param productGroupEntity нові дані
     */
    public void update(ProductGroupEntity productGroupEntity) {
        this.name = productGroupEntity.getName();
        this.description = productGroupEntity.getDescription();
    }

    /**
     * Повертає повну назву групи без id
     * @return повна назва
     */
    public String fullName() {
        return "Назва: " + name + "\n" + "Опис: " + description;
    }

    /**
     * Повертає рядкове представлення групи продуктів.
     *
     * @return рядок, що містить назву та опис групи продуктів
     */
    @Override
    public String toString() {
        return name;
    }
}
