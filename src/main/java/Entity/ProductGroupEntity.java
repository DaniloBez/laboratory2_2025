package Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляє групу продуктів, ідентифіковану унікальним ID.
 */
@Getter
@Setter
public class ProductGroupEntity {

    /**
     * Унікальний ідентифікатор групи продуктів.
     */
    @Getter
    private String id = UUID.randomUUID().toString();

    /**
     * Назва групи продуктів.
     */
    private String name;

    /**
     * Опис групи продуктів.
     */
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
     * Повертає рядкове представлення групи продуктів.
     *
     * @return рядок, що містить назву та опис групи продуктів
     */
    @Override
    public String toString() {
        return "Назва: " + name + ", опис: " + description;
    }
}
