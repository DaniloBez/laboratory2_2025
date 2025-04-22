package Repository;

import Entity.ProductGroupEntity;
import Utils.FileHandler;
import Utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторій для управляння об'єктами {@link ProductGroupEntity} у JSON файлі.
 */
public class ProductGroupJSONRepository implements IProductGroupRepository {
    /**
     * Назва файлу де будуть зберігатися дані
     */
    private final String FILE_PATH = "src/main/resources/productGroups.json";

    /**
     * Об'єкт для обробки файлу
     */
    FileHandler file = new FileHandler(FILE_PATH);

    /**
     * Список усіх груп
     */
    private List<ProductGroupEntity> productGroups;

    /**
     * Клас для серіалізації/десеріалізації об'єктів
     */
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Зчитує усі групи продуктів з json файлу, і десеріалізує їх.
     * Заповнює productGroups, якщо може зчитати.
     * @return Result(true, null) або Result(false, error message)
     */
    private Result readAllProductGroups(){
        Result resultCreate = file.createFileIfNotExist();

        if(!resultCreate.isSuccess())
            return resultCreate;

        Result resultRead = file.readJSONStringFromFile();
        if (!resultRead.isSuccess())
            return resultRead;

        try {
            if(resultRead.getMessage() == null || resultRead.getMessage().isEmpty())
                productGroups = new ArrayList<>();
            else
                productGroups = new ArrayList<>(List.of(mapper.readValue(resultRead.getMessage(), ProductGroupEntity[].class)));
            return resultRead;
        }
        catch (JsonProcessingException e) {
            return new Result(false, "Помилка десеріалізації: "+ e.getMessage());
        }
    }

    /**
     * Записує усі групи продуктів у json файл.
     * @return Result(true, null) або Result(false, error message)
     */
    private Result writeAllProductGroups(){
        Result resultCreate = file.createFileIfNotExist();

        if(!resultCreate.isSuccess())
            return resultCreate;

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(productGroups);
            return file.writeJSONStringInFile(json);
        }
        catch (JsonProcessingException e) {
            return new Result(false, "Помилка серіалізації: "+ e.getMessage());
        }
    }

    /**
     * Зберігає нову групу продуктів у файл.
     *
     * @param productGroup новий об'єкт {@code ProductGroupEntity} для збереження
     * @return результат операції збереження
     */
    @Override
    public Result save(ProductGroupEntity productGroup) {
        Result result = readAllProductGroups();
        if (!result.isSuccess())
            return result;

        productGroups.add(productGroup);
        return writeAllProductGroups();
    }

    /**
     * Оновлює існуючу групу продуктів за її ID.
     *
     * @param id           ідентифікатор групи
     * @param productGroup нові дані для оновлення
     * @return результат операції оновлення
     */
    @Override
    public Result update(String id, ProductGroupEntity productGroup) {
        Result result = readAllProductGroups();
        if (!result.isSuccess()) return result;

        Optional<ProductGroupEntity> existingProductGroup = findById(id);
        if (existingProductGroup.isEmpty())
            return new Result(false, "Такої групи не існує!");

        existingProductGroup.get().update(productGroup);
        return writeAllProductGroups();
    }

    /**
     * Повертає групу продуктів за ID, якщо така існує.
     *
     * @param id ідентифікатор групи
     * @return {@code Optional} з об'єктом {@code ProductGroupEntity}, або порожній, якщо не знайдено
     */
    @Override
    public Optional<ProductGroupEntity> findById(String id) {
        Result result = readAllProductGroups();
        if (!result.isSuccess())
            return Optional.empty();

        return productGroups.stream().filter(productGroup -> productGroup.getId().equals(id)).findFirst();
    }

    /**
     * Повертає список усіх груп продуктів.
     *
     * @return список {@code List<ProductGroupEntity>} або {@code null}, якщо виникла помилка читання
     */
    @Override
    public List<ProductGroupEntity> findAll() {
        Result result = readAllProductGroups();
        if (!result.isSuccess())
            return null;

        return productGroups;
    }

    /**
     * Видаляє групу продуктів за ID.
     *
     * @param id ідентифікатор групи для видалення
     * @return результат операції видалення
     */
    @Override
    public Result deleteById(String id) {
        Result result = readAllProductGroups();
        if (!result.isSuccess()) return result;

        Optional<ProductGroupEntity> productGroup = findById(id);
        if (productGroup.isEmpty())
            return new Result(false, "Такої групи не існує!");

        productGroups.remove(productGroup.get());
        return writeAllProductGroups();
    }
}
