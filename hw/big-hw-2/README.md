# КПО  HW‑2 — File Storage & Text Analysis

Микросервисное микроприложение
* **file‑storing‑service** — приём и хранение `.txt`, генерация метаданных.
* **file‑analysis‑service** — статистика текста + SVG‑word‑cloud.
* **api‑gateway** — единая точка входа, проксирует запросы и отдаёт Swagger UI.
* **PostgreSQL** — две БД (storage / analysis).
* Сборка и запуск — **Docker Compose**.

##  Запуск

```bash
docker-compose up --build
```

Будут подняты контейнеры:

* `db`              → `localhost:5432` (файлы)
* `analysis-db` → `localhost:5433` (результаты)
* `file-service`     → internal
* `analysis-service` → internal
* `api-gateway`      → **:8080** (extern)

##  Swagger
* http://localhost:8080/swagger-ui.html

## Методы

### /files — File Storage (через Gateway)

| Метод            | Путь                   | Описание                                                                             |
| ---------------- | ---------------------- |--------------------------------------------------------------------------------------|
| `POST multipart` | `/files`               | Загрузить `.txt`. Ответ — `{id, filename}`                                           |
| `GET`            | `/files/{id}`          | Скачать содержимое                                                                   |
| `GET`            | `/files/{id}/meta`     | Метаданные. Ответ — `{id, filename}`                                                 |
| `GET`            | `/files/{id}/extended` | Расширенные метаданные. Ответ — `{id, filename, contentHash, size, duplicates[...]}` |

### /analysis — Text Analysis

| Метод  | Путь                            | Описание                                                                                                                              |
| ------ | ------------------------------- |---------------------------------------------------------------------------------------------------------------------------------------|
| `POST` | `/analysis/{fileId}?cloud=true` | Запускает анализ, опц. word‑cloud SVG (`false` по дефолту)                                                                            |
| `GET`  | `/analysis/{id}`                | JSON‑результат (+список дубликатов)                                                                                                   |
| `GET`  | `/analysis/{id}/svg`            | Отдаёт чистый SVG `image/svg+xml`<br/>В сыром запросе сразу видно картинку.<br/>В Swagger видно xml разметку, есть возможность скачать |
