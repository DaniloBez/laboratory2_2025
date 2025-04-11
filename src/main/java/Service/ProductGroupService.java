package Service;

import Entity.ProductGroupEntity;
import Repository.IProductGroupRepository;
import Repository.ProductGroupJSONRepository;
import Utils.Result;
import Utils.UniqueNames;

import java.util.List;
import java.util.Optional;

/**
 * Сервіс для управління об'єктами {@link ProductGroupEntity},
 * який використовує реалізацію {@link IProductGroupRepository} для збереження даних.
 */
public class ProductGroupService {
    /**
     * Репозиторій для збереження та обробки об'єктів {@link ProductGroupEntity}.
     */
    IProductGroupRepository repository = new ProductGroupJSONRepository();

    public ProductGroupService(){
        for(ProductGroupEntity productGroupEntity : repository.findAll()){
            UniqueNames.add(productGroupEntity.getName());
        }
    }

    /**
     * Створює нову групу товарів, якщо її назва унікальна.
     *
     * @param productGroupEntity Об'єкт {@link ProductGroupEntity}, який потрібно створити.
     * @return {@link Result} з успішним результатом або повідомленням про помилку, якщо назва неунікальна.
     */
    public Result create(ProductGroupEntity productGroupEntity) {
        Result checkUnique = UniqueNames.add(productGroupEntity.getName());
        if (checkUnique.isSuccess())
            return repository.save(productGroupEntity);
        else
            return checkUnique;
    }

    /**
     * Оновлює групу товарів за ідентифікатором. Перевіряє унікальність нової назви.
     *
     * @param id Ідентифікатор існуючої групи.
     * @param productGroupEntity Нові дані групи товарів.
     * @return {@link Result} з успішним оновленням або повідомленням про помилку.
     */
    public Result update(String id, ProductGroupEntity productGroupEntity) {
        Optional<ProductGroupEntity> oldGroup = repository.findById(id);
        if (oldGroup.isEmpty())
            return new Result(false, "Групу не знайдено!");

        UniqueNames.remove(oldGroup.get().getName());
        Result checkUnique = UniqueNames.add(productGroupEntity.getName());
        if (checkUnique.isSuccess())
            return repository.update(id, productGroupEntity);

        return checkUnique;
    }

    /**
     * Повертає групу товарів за її ідентифікатором.
     *
     * @param id Ідентифікатор групи товарів.
     * @return {@link Optional} з об'єктом {@link ProductGroupEntity}, якщо знайдено.
     */
    public Optional<ProductGroupEntity> findById(String id) {
        return repository.findById(id);
    }

    /**
     * Повертає список усіх груп товарів.
     *
     * @return Список об'єктів {@link ProductGroupEntity}.
     */
    public List<ProductGroupEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Видаляє групу товарів за ідентифікатором. Також видаляє її назву зі списку унікальних імен.
     *
     * @param id Ідентифікатор групи товарів.
     * @return {@link Result} з інформацією про успішність видалення.
     */
    public Result delete(String id) {
        Optional<ProductGroupEntity> oldGroup = repository.findById(id);
        if (oldGroup.isEmpty())
            return new Result(false, "Групу не знайдено!");

        Result result = repository.deleteById(id);
        if (result.isSuccess())
            UniqueNames.remove(oldGroup.get().getName());

        return result;
    }
}
