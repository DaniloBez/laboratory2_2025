package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас {@code UniqueNames} забезпечує збереження унікальних імен.
 * Дозволяє додавати нові імена, якщо їх ще немає у списку,
 * та видаляти наявні імена.
 */
public class UniqueNames {
    /**
     * Список для зберігання унікальних імен.
     */
    private static final List<String> names = new ArrayList<>();

    /**
     * Додає ім'я до списку, якщо воно ще не міститься в ньому.
     *
     * @param name Ім'я для додавання
     * @return {@code Result} з {@code success=true}, якщо ім’я успішно додано;
     *         інакше {@code success=false} з повідомленням про помилку.
     */
    public static Result add(String name) {
        if (!names.contains(name)) {
            names.add(name);
            return new Result(true, null);
        }

        return new Result(false, "Таке ім'я вже існує!");
    }

    /**
     * Видаляє ім'я зі списку, якщо воно є.
     *
     * @param name Ім'я для видалення
     */
    public static void remove(String name) {
        names.remove(name);
    }
}
