package Utils;

import java.io.*;

/**
 * Клас {@code FileHandler} відповідає за створення, читання та запис JSON-рядків у файл.
 */
public class FileHandler {
    private final String filePath;

    /**
     * Створює об'єкт {@code FileHandler} для роботи з файлом за вказаним шляхом.
     *
     * @param filePath шлях до файлу
     */
    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Створює файл, якщо він не існує.
     *
     * @return результат створення файлу
     */
    public Result createFileIfNotExist() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    return new Result(false, "Помилка при створені файлу!");
                }

                return new Result(true, "Файл успішно створено!");
            } catch (IOException e) {
                return new Result(false, "Помилка при створені файлу: " + e.getMessage());
            }
        }

        return new Result(true, "Файл вже існує!");
    }

    /**
     * Записує JSON-рядок у файл.
     *
     * @param jsonString JSON-рядок для запису
     * @return результат запису
     */
    public Result writeJSONStringInFile(String jsonString) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(jsonString);
            writer.close();

            return new Result(true, "Данні успішно записано!");
        }
        catch (IOException e) {
            return new Result(false, "Помилка при записі даних: " + e.getMessage());
        }
    }

    /**
     * Зчитує JSON-рядок із файлу.
     *
     * @return результат зчитування (JSON або null, якщо файл порожній)
     */
    public Result readJSONStringFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder jsonString = new StringBuilder();
            if (reader.ready()) {
                while (true) {
                    String line = reader.readLine();
                    if (line == null)
                        break;

                    jsonString.append(line);
                }
                return new Result(true, jsonString.toString());
            }
            else
                return new Result(true, null);
        } catch (IOException e) {
            return new Result(false, "Помилка при читанні файлу: " + e.getMessage());
        }
    }
}
