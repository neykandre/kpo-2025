# Список терминов семинара
###### Нужно написать определения с примером из жизни или кода
- Что такое Docker? зачем он нужен?
- Как поднять бд в докере?
- Как подключить бд к приложению?
- Что такое Repository?
- +1 уникальный факт связанный с темами выше или семинаром

# Краткая инструкция по Docker, базам данных и Repository с примерами на Java

---

## Что такое Docker? Зачем он нужен?

**Docker** — это платформа для создания, распространения и запуска приложений в изолированных средах, называемых контейнерами. Контейнеры позволяют упаковать приложение вместе со всеми его зависимостями и запускать его на любой машине, где установлен Docker.

### Пример из жизни:
Представь, что ты собрал проект, который работает только на твоем компьютере. На другом ПК — ошибки. Docker помогает упаковать всё нужное (приложение, библиотеки, конфигурации) в один контейнер, и он будет работать одинаково везде.

### Пример команды:
```bash
docker run hello-world
```
Запускает тестовый контейнер, проверяющий установку Docker.

---

## Как поднять БД в Docker?

Чтобы запустить, например, PostgreSQL в Docker:

```bash
docker run \
  --name my-postgres \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=mydb \
  -p 5432:5432 \
  -d postgres
```

- `-e` задаёт переменные окружения для настройки БД
- `-p` пробрасывает порт
- `-d` запускает в фоновом режиме

Теперь БД доступна по адресу: `localhost:5432`

---

## Как подключить БД к приложению?

Для подключения PostgreSQL к Java-приложению, например, через Spring Boot, нужно указать параметры подключения в `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Зависимость в `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
```

---

## Что такое Repository?

**Repository** в контексте Spring Data — это интерфейс, через который приложение взаимодействует с базой данных. Он скрывает реализацию операций с данными (CRUD).

### Пример:
```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
```

Spring автоматически реализует методы (`save`, `findAll`, `findByName` и др.) на основе имени метода.

---

## Уникальный факт

Docker позволяет создавать **docker-compose** файлы, чтобы запускать сразу несколько сервисов, например: БД + backend. Это особенно полезно для микросервисов.

### Пример `docker-compose.yml`:
```yaml
version: '3.8'
services:
  db:
    image: postgres
    environment:
      POSTGRES_DB: mydb
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
    ports:
      - "5432:5432"
```
Запустить можно командой:
```bash
docker-compose up
```

---

> Эта инструкция — основа для запуска проектов на Java с базой данных в Docker.

