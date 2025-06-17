# KPO HW‑3 — Async Shopping (Orders & Payments)

Микросервисное приложение демонстрирует **асинхронную межсервисную интеграцию через Kafka** и гарантирует доставку «заказ → оплата → статус».

| Сервис                | Порт (внутр.) | Порт (хост)  | Назначение                                        |
| --------------------- | ------------- | ------------ | ------------------------------------------------- |
| **orders‑service**    | 8080          | —            | REST для заказов + Transactional Outbox (→ Kafka) |
| **payments‑service**  | 8081          | —            | REST для счетов + Inbox / Outbox, списание денег  |
| **gateway‑service**   | 8080          | **8080**     | Единая точка входа / Swagger UI / прокси          |
| **Kafka + Zookeeper** | 9092          | 9092 / 29092 | Брокер сообщений (PLAINTEXT + HOST listener)      |
| **PostgreSQL**        | 5432          | —            | Одна БД `shop` (два набора таблиц)                |
| **Kafka‑UI**          | 8085          | 8085         | Веб‑обзор топиков (`order.*`)                     |

## Запуск

```bash
docker compose up --build
```

Поднимутся контейнеры, создадутся топики `order.payment.requests` и `order.payment.results`.

* Swagger UI: [http://localhost:8080/swagger-ui/index.htm](http://localhost:8080/swagger-ui/index.htm)
* Kafka‑UI:   [http://localhost:8085](http://localhost:8085)

## REST API (через Gateway)

### /api/orders – Orders Service

| Метод       | Путь                     | Описание                                                                                                          |
| ----------- | ------------------------ | ----------------------------------------------------------------------------------------------------------------- |
| `POST JSON` | `/api/orders`            | Создать заказ `{userId, amount, description}`.<br/>Статус сразу `PENDING_PAYMENT`; оплата запускается асинхронно. |
| `GET`       | `/api/orders` *?userId=* | Список заказов пользователя                                                                                       |
| `GET`       | `/api/orders/{id}`       | Детали заказа + текущий статус                                                                                    |

### /api/accounts – Payments Service

| Метод  | Путь                            | Описание                            |
| ------ | ------------------------------- | ----------------------------------- |
| `POST` | `/api/accounts`                 | Создать счёт `{userId}`             |
| `POST` | `/api/accounts/deposit`         | Пополнить баланс `{userId, amount}` |
| `GET`  | `/api/accounts/balance?userId=` | Узнать баланс                       |

## Гарантии

* **At‑most‑once** списание денег — Inbox таблица в `payments-service` фиксирует idempotency.
* **Exactly‑once** можно получить, если включить идемпотентные продьюсеры (`enable.idempotence=true`).
* Обновление статуса заказа транзакционно связано с записью Outbox ⇒ нет "зависших" состояний.