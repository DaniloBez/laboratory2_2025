package Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Допоміжний клас, що дозволяє відстежувати помилки у проєкті
 */
@Getter
@AllArgsConstructor
public class Result {
    /**
     * Поле, яке представляє чи успішна відбулася операція
     */
    private final boolean success;

    /**
     * Повідомлення про помилку
     */
    private final String message;
}
