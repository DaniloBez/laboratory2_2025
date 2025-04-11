package Repository;

import Entity.ProductEntity;
import Utils.FileHandler;
import Utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторій для збереження, читання та оновлення об'єктів {@link ProductEntity}
 * у форматі JSON у файлі.
 *
 * Використовує клас {@link FileHandler} для роботи з файлом.
 */

public class ProductJSONRepository implements IProductRepository {
    /**
     * Шлях до файлу з даними про продукти.
     */
    private final String FILE_PATH = "src/main/resources/products.json";
    /**
     * Обробник файлу для читання/запису JSON-рядків.
     */
    FileHandler file = new FileHandler(FILE_PATH);
    /**
     * Список продуктів, прочитаних із файлу.
     */
    private List<ProductEntity> products;

    /**
     * Об'єкт для серіалізації/десеріалізації JSON.
     */
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Зчитує всі продукти з файлу JSON.
     *
     * @return Результат операції, включаючи успішність та повідомлення (JSON-рядок).
     */
    private Result readAllProducts(){
        Result resultCreate = file.createFileIfNotExist();

        if(!resultCreate.isSuccess())
            return resultCreate;

        Result resultRead = file.readJSONStringFromFile();
        if (!resultRead.isSuccess())
            return resultRead;

        try {
            if(resultRead.getMessage() == null || resultRead.getMessage().isEmpty())
                products = new ArrayList<>();
            else
                products = new ArrayList<>(List.of(mapper.readValue(resultRead.getMessage(), ProductEntity[].class)));
            return resultRead;
        }
        catch (JsonProcessingException e) {
            return new Result(false, "Помилка десеріалізації: "+ e.getMessage());
        }
    }

    /**
     * Записує поточний список продуктів до файлу JSON.
     *
     * @return Результат операції запису.
     */
    private Result writeAllProducts(){
        Result resultCreate = file.createFileIfNotExist();

        if(!resultCreate.isSuccess())
            return resultCreate;

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(products);
            return file.writeJSONStringInFile(json);
        }
        catch (JsonProcessingException e) {
            return new Result(false, "Помилка серіалізації: "+ e.getMessage());
        }
    }

    /**
     * Зберігає новий продукт у репозиторії.
     *
     * @param product Продукт для збереження.
     * @return Результат операції.
     */
    @Override
    public Result save(ProductEntity product) {
        Result result = readAllProducts();
        if (!result.isSuccess())
            return result;

        products.add(product);
        return writeAllProducts();
    }

    /**
     * Оновлює існуючий продукт за ідентифікатором.
     *
     * @param id      Ідентифікатор продукту.
     * @param product Нові дані для оновлення.
     * @return Результат операції.
     */
    @Override
    public Result update(String id, ProductEntity product) {
        Result result = readAllProducts();
        if (!result.isSuccess()) return result;

        Optional<ProductEntity> existingProduct = findById(id);
        if (existingProduct.isEmpty())
            return new Result(false, "Такого продукта не існує!");

        existingProduct.get().update(product);
        return writeAllProducts();
    }

    /**
     * Пошук продукту за ідентифікатором.
     *
     * @param id Ідентифікатор продукту.
     * @return {@link Optional} з продуктом, якщо знайдено.
     */
    @Override
    public Optional<ProductEntity> findById(String id) {
        Result result = readAllProducts();
        if (!result.isSuccess())
            return Optional.empty();

        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    /**
     * Повертає список усіх продуктів.
     *
     * @return Список продуктів або {@code null}, якщо зчитування не вдалося.
     */
    @Override
    public List<ProductEntity> findAll() {
        Result result = readAllProducts();
        if (!result.isSuccess())
            return null;

        return products;
    }

    /**
     * Видаляє продукт за ідентифікатором.
     *
     * @param id Ідентифікатор продукту для видалення.
     * @return Результат операції.
     */
    @Override
    public Result deleteById(String id) {
        Result result = readAllProducts();
        if (!result.isSuccess()) return result;

        Optional<ProductEntity> product = findById(id);
        if (product.isEmpty())
            return new Result(false, "Такого продукта не існує!");

        products.remove(product.get());
        return writeAllProducts();
    }

}
