package Utils;

import java.io.*;

public class FileHandler {
    private final String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

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
