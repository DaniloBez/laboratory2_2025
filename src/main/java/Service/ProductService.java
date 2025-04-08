package Service;

import Entity.ProductEntity;
import Entity.ProductGroupEntity;
import Entity.ProductJSONRepository;
import Utils.Result;
import Utils.UniqueNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервіс для роботи з продуктами.
 * Використовує репозиторій {@link ProductJSONRepository} для зберігання даних
 * та клас {@link UniqueNames} для забезпечення унікальності назв продуктів.
 */
public class ProductService {
    private final ProductJSONRepository repository = new ProductJSONRepository();

    /**
     * Ініціалізує сервіс, додаючи всі наявні назви продуктів до реєстру унікальних назв.
     */
    public ProductService() {
        for (ProductEntity productEntity : repository.findAll()) {
            UniqueNames.add(productEntity.getName());
        }
    }

    /**
     * Створює новий продукт, якщо його назва унікальна.
     *
     * @param productService Продукт, який потрібно створити.
     * @return Результат створення: успішно/неуспішно та повідомлення.
     */
    public Result create(ProductEntity productService) {
        Result checkUnique = UniqueNames.add(productService.getName());
        if (checkUnique.isSuccess())
            return repository.save(productService);
        else
            return checkUnique;
    }

    /**
     * Оновлює продукт за вказаним ідентифікатором.
     *
     * @param id Ідентифікатор продукту, який потрібно оновити.
     * @param productEntity Нові дані продукту.
     * @return Результат оновлення: успішно/неуспішно та повідомлення.
     */
    public Result update(String id, ProductEntity productEntity){
        Optional<ProductEntity> oldProduct = repository.findById(id);
        if(oldProduct.isEmpty())
            return new Result(false, "Продукт не знайдено!");

        UniqueNames.remove(oldProduct.get().getName());
        Result checkUnique = UniqueNames.add(productEntity.getName());
        if(checkUnique.isSuccess())
            return repository.update(id, productEntity);

        return checkUnique;
    }

    /**
     * Повертає продукт за ідентифікатором.
     *
     * @param id Ідентифікатор продукту.
     * @return {@link Optional} з продуктом або порожній, якщо продукт не знайдено.
     */
    public Optional<ProductEntity> getById(String id){
        return repository.findById(id);
    }

    /**
     * Повертає список усіх продуктів.
     *
     * @return Список усіх продуктів.
     */
    public List<ProductEntity> getAll(){
        return repository.findAll();
    }

    /**
     * Видаляє продукт за ідентифікатором та очищує його назву з реєстру унікальних назв.
     *
     * @param id Ідентифікатор продукту, який потрібно видалити.
     * @return Результат видалення: успішно/неуспішно та повідомлення.
     */
    public Result delete(String id){
        Optional<ProductEntity> oldProduct = repository.findById(id);
        if(oldProduct.isEmpty())
            return new Result(false, "Продукт не знайдено!");

        Result result = repository.deleteById(id);
        if (result.isSuccess())
            UniqueNames.remove(oldProduct.get().getName());

        return result;
    }

    /**
     * Повертає список продуктів, назви яких відповідають регулярному виразу.
     *
     * @param regExp Регулярний вираз для пошуку по назвах продуктів.
     * @return Список знайдених продуктів.
     */
    public List<ProductEntity> findAllByRegExp(String regExp){
        List<ProductEntity> productEntities = new ArrayList<>();
        for(ProductEntity productEntity : getAll()){
            Matcher matcher = Pattern.compile(regExp).matcher(productEntity.getName());
            if(matcher.find())
                productEntities.add(productEntity);
        }
        return productEntities;
    }
    /**
     * Змінює кількість товару на складі.
     *
     * @param id Ідентифікатор продукту.
     * @param count Кількість, на яку потрібно змінити залишок (може бути від’ємною).
     * @return Результат зміни кількості: успішно/неуспішно та повідомлення.
     */
    public Result changeCountOfProduct(String id, double count){
        Optional<ProductEntity> oldProduct = repository.findById(id);
        if(oldProduct.isEmpty())
            return new Result(false, "Продукт не знайдено!");
        if(oldProduct.get().getQuantityInStock() + count < 0)
            return new Result(false, "Стільки продуктів немає на складі!");

        oldProduct.get().setQuantityInStock(oldProduct.get().getQuantityInStock() + count);
        return repository.update(id, oldProduct.get());
    }
    /**
     * Повертає список усіх продуктів, що належать до певної групи.
     *
     * @param groupId Ідентифікатор групи продуктів.
     * @return Список продуктів цієї групи.
     */
    public List<ProductEntity> findAllByGroupId(String groupId){
        List<ProductEntity> productEntities = getAll();
        return productEntities.stream().filter(product -> product.getProductGroupId().equals(groupId)).toList();
    }
    /**
     * Видаляє всі продукти, що належать до певної групи.
     *
     * @param groupId Ідентифікатор групи продуктів.
     * @return Результат видалення: успішно/неуспішно та повідомлення.
     */
    public Result deleteAllProdutsByGroupId(String groupId){
        List<ProductEntity> productEntities = findAllByGroupId(groupId);
        for(ProductEntity productEntity : productEntities){
            Result result = repository.deleteById(productEntity.getId());
            if (!result.isSuccess())
                return result;
        }
        return new Result(true, null);
    }
}
