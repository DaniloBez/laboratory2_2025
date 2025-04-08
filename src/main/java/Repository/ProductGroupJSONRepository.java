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


    @Override
    public Result save(ProductGroupEntity productGroup) {
        Result result = readAllProductGroups();
        if (!result.isSuccess())
            return result;

        productGroups.add(productGroup);
        return writeAllProductGroups();
    }

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

    @Override
    public Optional<ProductGroupEntity> findById(String id) {
        Result result = readAllProductGroups();
        if (!result.isSuccess())
            return Optional.empty();

        return productGroups.stream().filter(productGroup -> productGroup.getId().equals(id)).findFirst();
    }

    @Override
    public List<ProductGroupEntity> findAll() {
        Result result = readAllProductGroups();
        if (!result.isSuccess())
            return null;

        return productGroups;
    }

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
