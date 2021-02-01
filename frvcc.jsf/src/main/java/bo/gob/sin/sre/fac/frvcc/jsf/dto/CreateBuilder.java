package bo.gob.sin.sre.fac.frvcc.jsf.dto;

import java.util.function.Consumer;

/**
 * CreateBuilder permite crear objetos de manera personalizada.
 *
 * @param <T> Tipo de objeto
 * @author vigmar.carlo
 * @version 17/03/2020
 */

public class CreateBuilder<T> {

    private T instance;

    public CreateBuilder(Class<T> clazz) {
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public CreateBuilder<T> with(Consumer<T> setter) {
        setter.accept(instance);
        return this;
    }

    public T get() {
        return instance;
    }

    public static <T> CreateBuilder<T> build(Class<T> clazz) {
        return new CreateBuilder<>(clazz);
    }

}
