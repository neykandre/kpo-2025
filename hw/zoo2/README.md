# Отчёт по мини-ДЗ 2


## a. Реализованный функционал

| Требование ДЗ | Классы / пакеты, где реализовано                                                                                                                                                                                                          |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Добавить / удалить животное | **Use‑cases:** `AddAnimalUseCase`, `RemoveAnimalUseCase` (`zoo2.application.ports.in`)  <br>**Сервис:** `AnimalManagementService` (`zoo2.application.services`)  <br>**Контроллер:** `AnimalController` (`zoo2.presentation.controllers`) |
| Добавить / удалить вольер | `AddEnclosureUseCase`, `RemoveEnclosureUseCase`  <br>`EnclosureManagementService`  <br>`EnclosureController`                                                                                                                              |
| Переместить животное между вольерами | `TransferAnimalUseCase`  <br>`AnimalTransferService`  <br>`AnimalController.move`                                                                                                                                                         |
| Просмотреть расписание кормления | `GetFeedingScheduleUseCase`  <br>`FeedingOrganizationService.getFeedingSchedule`  <br>`FeedingScheduleController#getFeedingSchedule`                                                                                                      |
| Добавить кормление в расписание | `ScheduleFeedingUseCase`  <br>`FeedingOrganizationService.scheduleFeeding`  <br>`FeedingScheduleController#scheduleFeeding`                                                                                                               |
| Просмотреть статистику зоопарка | `GetZooStatisticsUseCase`  <br>`ZooStatisticsService`  <br>`StatisticsController`                                                                                                                                                         |
| Кормление по расписанию | `FeedAnimalUseCase`, <br> `FeedingOrganizationService`, <br> `FeedingOrganizationService#feedAnimals`                                                                                                                                     |
| Сброс статуса расписаний | `FeedingOrganizationService` <br> `FeedingScheduleController#reset`                                                                                                                                                                             |
| Логирование доменных событий | `EventListener` (`zoo2.infrastructure.events`) — выводит в консоль все события                                                                                                                                                            |

---

## b. DDD и Clean Architecture

| Концепт / принцип | Где в коде |
|-------------------|-----------|
| **Entities / Aggregates** | `zoo2.domain.models.Animal`, `Enclosure`, `FeedingSchedule` |
| **Value Objects** | `zoo2.domain.vo.*` (`Species`, `Diet`, `EnclosureType`, `FeedingTime`, `FoodType`, `Gender`) |
| **Доменные события** | `zoo2.domain.events.*` (`AnimalMovedEvent`, `FeedingTimeEvent`, и др.) |
| **Domain Events bus** | `DomainEvents` + `DomainEventListener` (`zoo2.domain.utils`) |
| **Repositories (интерфейсы)** | `AnimalRepository`, `EnclosureRepository`, `FeedingScheduleRepository` (`zoo2.application.ports.out`) |
| **In‑/Out Ports** | `application.ports.in.*`, `application.ports.out.*` — граница слоя Application |
| **Application Services (Use‑cases)** | `application.services.*` (AnimalManagementService, EnclosureManagementService, AnimalTransferService, FeedingOrganizationService, ZooStatisticsService, FeedAnimalService, ScheduledFeedingService, FeedingScheduleResetService) |
| **Infrastructure адаптеры** | In‑memory репозитории (`infrastructure.persistence.*`);  <br>Слушатель событий `infrastructure.events.EventListener`;  <br>Swagger‑конфиг `infrastructure.config.swagger.SwaggerConfig` |
| **Presentation слой** | DTO (`presentation.dto.request`);  <br>REST‑контроллеры (`presentation.controllers.*`) используют ТОЛЬКО порты `in` |
| **Dependency Rule** | Слои зависят «внутрь»:  <br>Domain ни от кого, Application — только от Domain, Infrastructure и Presentation — от Application. Реализации репозиториев находятся во внешнем слое, а интерфейсы — во внутреннем. |
| **Изоляция бизнес‑логики** | Вся бизнес‑логика (проверка вместимости, диеты, изменение статусов) инкапсулирована либо в Entities (`Animal.moveTo`, `FeedingSchedule.markAsDone`), либо в Application‑сервисах. |
