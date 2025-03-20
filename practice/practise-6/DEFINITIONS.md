# Список терминов семинара
###### Нужно написать определения с примером из жизни или кода
- Поведенческие паттерны
- Цепочка обязанностей (Chain of responsibility)
- Команда (Command)
- Интерпретатор (Interpreter)
- Итератор (Iterator)
- Посредник (Mediator)
- Хранитель (Memento)
- Наблюдатель (Observer)
- Состояние (State)
- Стратегия (Strategy)
- Шаблонный метод (Template method)
- Посетитель  (Visitor)
- +1 уникальный факт связанный с темами выше или семинаром

# Поведенческие паттерны в Java

## Поведенческие паттерны (Behavioral Patterns)

Поведенческие паттерны проектирования описывают способы взаимодействия объектов и классов. Они помогают сделать код более гибким и удобным для расширения.

📌 **Пример из жизни:**\
Оркестр, где дирижер (паттерн-посредник) координирует действия музыкантов, не давая им общаться напрямую.

---

## Цепочка обязанностей (Chain of Responsibility)

**Описание:** Позволяет передавать запрос по цепочке обработчиков, где каждый обработчик либо выполняет действие, либо передает дальше.

📌 **Пример из жизни:**\
В техподдержке звонок клиента сначала попадает к оператору первой линии. Если он не может помочь, запрос передается на вторую линию, затем на третью и так далее.

📝 **Реализация в Java:**
```java
abstract class Handler {
    protected Handler next;
    public void setNext(Handler next) { this.next = next; }
    public abstract void handleRequest(String request);
}

class ConcreteHandlerA extends Handler {
    public void handleRequest(String request) {
        if (request.equals("A")) {
            System.out.println("Handled by A");
        } else if (next != null) {
            next.handleRequest(request);
        }
    }
}
```

---

## Команда (Command)

**Описание:** Инкапсулирует запрос в виде объекта, позволяя параметризовать клиентов с разными запросами и ставить их в очередь.

📌 **Пример из жизни:**\
Удаленный пульт для телевизора. Каждая кнопка – это команда (включить, выключить, переключить канал).

📝 **Реализация в Java:**
```java
interface Command {
    void execute();
}

class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.turnOn(); }
}
```

---

## Интерпретатор (Interpreter)

**Описание:** Описывает грамматику языка и интерпретирует выражения этого языка.

📌 **Пример из жизни:**\
Переводчик с одного языка на другой.

📝 **Реализация в Java:**
```java
interface Expression {
    int interpret();
}

class NumberExpression implements Expression {
    private int number;
    public NumberExpression(int number) { this.number = number; }
    public int interpret() { return number; }
}
```

---

## Итератор (Iterator)

**Описание:** Позволяет последовательно обходить элементы коллекции без раскрытия ее внутренней структуры.

📌 **Пример из жизни:**\
Листание страниц книги.

📝 **Реализация в Java:**
```java
interface Iterator {
    boolean hasNext();
    Object next();
}

class ConcreteIterator implements Iterator {
    private List<String> items;
    private int index = 0;
    public ConcreteIterator(List<String> items) { this.items = items; }
    public boolean hasNext() { return index < items.size(); }
    public Object next() { return items.get(index++); }
}
```

---

## Посредник (Mediator)

**Описание:** Координирует взаимодействие между объектами, устраняя их прямую зависимость.

📌 **Пример из жизни:**\
Диспетчер аэропорта управляет самолетами, чтобы они не сталкивались друг с другом.

📝 **Реализация в Java:**
```java
interface Mediator {
    void sendMessage(String message, Colleague colleague);
}
```

---

## Хранитель (Memento)

**Описание:** Позволяет сохранять и восстанавливать состояние объекта.

📌 **Пример из жизни:**\
Сохранение игры.

📝 **Реализация в Java:**
```java
class Memento {
    private String state;
    public Memento(String state) { this.state = state; }
    public String getState() { return state; }
}
```


---

## Наблюдатель (Observer)

**Описание:** Позволяет объектам подписываться на события другого объекта и автоматически получать уведомления.

📌 **Пример из жизни:**\
Подписка на новости.

📝 **Реализация в Java:**
```java
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(String message);
}

class NewsSubscriber implements Observer {
    private String name;
    public NewsSubscriber(String name) { this.name = name; }
    public void update(String message) {
        System.out.println(name + " received update: " + message);
    }
}

class NewsPublisher {
    private List<Observer> subscribers = new ArrayList<>();
    public void addObserver(Observer observer) { subscribers.add(observer); }
    public void removeObserver(Observer observer) { subscribers.remove(observer); }
    public void notifyObservers(String news) {
        for (Observer observer : subscribers) {
            observer.update(news);
        }
    }
}
```

---

## Состояние (State)

**Описание:** Позволяет объекту менять свое поведение в зависимости от внутреннего состояния.

📌 **Пример из жизни:**\
Светофор, меняющий цвет.

📝 **Реализация в Java:**
```java
interface State {
    void handle();
}

class RedLight implements State {
    public void handle() { System.out.println("Stop!"); }
}

class GreenLight implements State {
    public void handle() { System.out.println("Go!"); }
}

class TrafficLight {
    private State state;
    public void setState(State state) { this.state = state; }
    public void change() { state.handle(); }
}
```

---

## Стратегия (Strategy)

**Описание:** Определяет семейство алгоритмов и делает их взаимозаменяемыми.

📌 **Пример из жизни:**\
Способы оплаты (карта, наличные, криптовалюта).

📝 **Реализация в Java:**
```java
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " using Credit Card"); }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " using PayPal"); }
}

class ShoppingCart {
    private PaymentStrategy strategy;
    public void setPaymentStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    public void checkout(int amount) { strategy.pay(amount); }
}
```

---

## Шаблонный метод (Template Method)

**Описание:** Определяет скелет алгоритма, позволяя подклассам переопределять отдельные шаги.

📌 **Пример из жизни:**\
Рецепт приготовления блюда.

📝 **Реализация в Java:**
```java
abstract class Recipe {
    public final void cook() {
        prepareIngredients();
        cookDish();
        serve();
    }
    abstract void prepareIngredients();
    abstract void cookDish();
    void serve() { System.out.println("Serving the dish"); }
}

class PastaRecipe extends Recipe {
    void prepareIngredients() { System.out.println("Preparing pasta and sauce"); }
    void cookDish() { System.out.println("Cooking pasta"); }
}
```

---

## Посетитель (Visitor)

**Описание:** Позволяет добавлять новые операции к объектам без изменения их структуры.

📌 **Пример из жизни:**\
Налоговый инспектор проверяет разные виды отчетов.

📝 **Реализация в Java:**
```java
interface Visitor {
    void visit(Book book);
    void visit(Electronic electronic);
}

interface Item {
    void accept(Visitor visitor);
}

class Book implements Item {
    public void accept(Visitor visitor) { visitor.visit(this); }
}

class Electronic implements Item {
    public void accept(Visitor visitor) { visitor.visit(this); }
}

class PriceCalculator implements Visitor {
    public void visit(Book book) { System.out.println("Calculating price for Book"); }
    public void visit(Electronic electronic) { System.out.println("Calculating price for Electronic"); }
}
```

---

## Уникальный факт

✅ **Интересный факт про паттерны:**\
Паттерн «Наблюдатель» используется во многих библиотеках событий, включая `Java Swing` и `RxJava`, что делает его одним из самых популярных паттернов в разработке пользовательских интерфейсов.
