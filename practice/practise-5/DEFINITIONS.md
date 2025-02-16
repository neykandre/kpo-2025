# Порождающие паттерны в Java

## Порождающие паттерны (Creational Patterns)
Порождающие паттерны упрощают создание объектов, делая код более гибким и удобочитаемым. Они скрывают процесс создания объектов и позволяют изменять способ их инициализации без изменения клиентского кода.

📌 **Пример из жизни:**  
Автомобильный завод может использовать разные линии сборки (паттерны) для производства машин (объектов), чтобы упростить создание автомобилей разных моделей.

---

## Singleton (Одиночка)
**Описание:** Паттерн гарантирует, что у класса есть только один экземпляр, и предоставляет к нему глобальную точку доступа.

📌 **Пример из жизни:**  
В операционной системе должен быть только один диспетчер задач.

📝 **Реализация в Java:**
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

---

## Factory (Фабрика)
**Описание:** Паттерн предоставляет общий интерфейс для создания объектов, позволяя подклассам решать, какой класс инстанцировать.

📌 **Пример из жизни:**  
Фабрика мороженого производит разные виды мороженого.

📝 **Реализация в Java:**
```java
interface Product {
    void use();
}

class ConcreteProductA implements Product {
    public void use() { System.out.println("Using Product A"); }
}

class ConcreteProductB implements Product {
    public void use() { System.out.println("Using Product B"); }
}

class ProductFactory {
    public static Product createProduct(String type) {
        switch (type) {
            case "A": return new ConcreteProductA();
            case "B": return new ConcreteProductB();
            default: throw new IllegalArgumentException("Unknown product type");
        }
    }
}
```

---

## Factory Method (Фабричный метод)
**Описание:** Этот паттерн делегирует создание объектов подклассам.

📌 **Пример из жизни:**  
Разные рестораны готовят бургеры по разным рецептам.

📝 **Реализация в Java:**
```java
abstract class Creator {
    abstract Product factoryMethod();
}

class ConcreteCreatorA extends Creator {
    public Product factoryMethod() {
        return new ConcreteProductA();
    }
}

class ConcreteCreatorB extends Creator {
    public Product factoryMethod() {
        return new ConcreteProductB();
    }
}
```

---

## Abstract Factory (Абстрактная фабрика)
**Описание:** Паттерн создает семейства связанных объектов без указания их конкретных классов.

📌 **Пример из жизни:**  
Мебельный магазин продает наборы мебели в разных стилях.

📝 **Реализация в Java:**
```java
interface Chair { void sit(); }
interface Table { void use(); }

class ModernChair implements Chair { public void sit() { System.out.println("Sitting on a modern chair"); } }
class ClassicChair implements Chair { public void sit() { System.out.println("Sitting on a classic chair"); } }

class ModernTable implements Table { public void use() { System.out.println("Using a modern table"); } }
class ClassicTable implements Table { public void use() { System.out.println("Using a classic table"); } }

interface FurnitureFactory {
    Chair createChair();
    Table createTable();
}

class ModernFurnitureFactory implements FurnitureFactory {
    public Chair createChair() { return new ModernChair(); }
    public Table createTable() { return new ModernTable(); }
}

class ClassicFurnitureFactory implements FurnitureFactory {
    public Chair createChair() { return new ClassicChair(); }
    public Table createTable() { return new ClassicTable(); }
}
```

---

## Builder (Строитель)
**Описание:** Позволяет пошагово создавать сложные объекты.

📌 **Пример из жизни:**  
Конструктор Лего: можно поэтапно добавлять детали.

📝 **Реализация в Java:**
```java
class Car {
    private String engine;
    private int wheels;
    private String color;

    static class Builder {
        private Car car = new Car();

        public Builder setEngine(String engine) { car.engine = engine; return this; }
        public Builder setWheels(int wheels) { car.wheels = wheels; return this; }
        public Builder setColor(String color) { car.color = color; return this; }

        public Car build() { return car; }
    }
}
```

---

## Prototype (Прототип)
**Описание:** Позволяет создавать новые объекты путем копирования уже существующих.

📌 **Пример из жизни:**  
Ксерокс: можно создать копию документа.

📝 **Реализация в Java:**
```java
class Prototype implements Cloneable {
    String field;

    public Prototype(String field) { this.field = field; }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Prototype(this.field);
    }
}
```

---

## Уникальный факт
✅ **Интересный факт про Singleton:**  
В многопоточной среде обычная реализация `Singleton` может привести к созданию нескольких экземпляров, если два потока одновременно вызывают `getInstance()`. Чтобы избежать этого, используется **"double-checked locking"**, что делает код потокобезопасным.

📝 **Исправленный Singleton:**
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

