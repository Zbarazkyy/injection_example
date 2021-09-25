package main.injeection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import main.models.*;

public class Injector {
    private Map<Class<?>, Object> instances = new HashMap<>();
    private final static Injector injector = new Injector();

    public static Injector getInjector() {
        return injector;
    }

    public Object getInstance(Class<?> interfaceClazz) {
        Object clazzImplementationInstance = null;
        Class<?> clazz = findImplementation(interfaceClazz);  // Если мы нашли Интерфейс получаем класс имплементации (найти реализацию интерфейса)
        // / If we found an Interface we get an implementation class (find an implementation of the interface)
        Field[] declaredFields = clazz.getDeclaredFields(); // Получаем поля класса
        // / Get the fields of the class
        for (Field tempField : declaredFields) {  // Пробегаемся по полям класса / Running through the fields of the class
            if (tempField.isAnnotationPresent(Inject.class)) { // Если поле класса имеет аннотацию main.injeection.Inject заходим / If the class field is annotated main.injeection.Inject, go to

// Создаем новый объект которым мы проинициализируем это поле (создать новый объект типа поля)/Create a new object with which we will initialize this field (create new object of field type)
                Object fieldInstance = getInstance(tempField.getType());  // мы вызываем рекурсию так как класс может завесить других типов / we call recursion because the class can hang other types

// создать объект interfaceClazz (который нам приходит на вход) (или имплементацию клвсса) / create an object of interfaceClazz (or implementation class)
                clazzImplementationInstance = createNewInstance(clazz); // в метод createNewInstance передаем класс имплементацию не интерфейс / Pass the implementation class not the interface to the createNewInstance method

// нам нужно засетить 'field type object' в interfaceClazz / set 'field type object' to 'interfaceClazz object'
                try {
                    tempField.setAccessible(true); // дать разрешение в доступе к приватному полю / give permission to access the private field
                    tempField.set(clazzImplementationInstance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can't initialize field value. Class: " + clazz.getName() + ". Field: " + tempField.getName());
                }
            }

        }
        if (clazzImplementationInstance == null) {
            clazzImplementationInstance = createNewInstance(clazz);
        }

        return clazzImplementationInstance;
    }

    private Object createNewInstance(Class<?> clazz) {
//         если мы уже создали объект давай пользоваться им  / if we create (hava) an object - lat's use it
        if (instances.containsKey(clazz)) {
            return instances.get(clazz);
        }
//        если не создан нужно создать новый объект  / create new object
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            instances.put(clazz, instance); // добавим в мапу реализацию интерфейса / Let's add the interface implementation to Map
            return instance;
        } catch (NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can't create instance of" + clazz);
        }
    }

    private Class<?> findImplementation(Class<?> interfaceClazz) {
        Map<Class<?>, Class<?>> interfaceImplementation = new HashMap<>();
        interfaceImplementation.put(First.class, FirstImpl.class);
        interfaceImplementation.put(Second.class, SecondHaveFieldFirst.class);
        interfaceImplementation.put(LogService.class, LogServiceImpl.class);
        if (interfaceClazz.isInterface()) {
            return interfaceImplementation.get(interfaceClazz);
        }
        return interfaceClazz;
    }

}
