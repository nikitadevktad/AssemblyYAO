
# Шаг 12. Навигация к отдельной новости
[На главную](../../README.md) 


# Что сделано 

1. Подключён `AppNavigation` в `MainActivity`.
2. Создан маршрут `news/{newsId}` для отдельной новости.
3. Настроена передача ID выбранной новости через `NavController`.
4. Добавлен аргумент `newsId` типа `NavType.IntType`.
5. Реализовано получение ID через `NavBackStackEntry`.
6. Добавлен обработчик `onNewsClick` в `HomeScreen`.
7. Связаны обработчики нажатий `HomeScreen` и `HomeNewsScreen`.
8. Подключён существующий `NewsDetailsScreen` к `NavHost`.
9. Добавлена проверка `newsId` на `null`.

### Логика перехода

1. Пользователь нажимает на карточку новости.
2. `NewsCard` вызывает обработчик нажатия.
3. `HomeNewsScreen` передаёт ID выбранной новости.
4. `HomeScreen` передаёт событие в `AppNavigation`.
5. `NavController` выполняет переход по маршруту.
6. `NavHost` извлекает `newsId` типа `Int`.
7. `NewsDetailsScreen` получает ID новости.
8. `ViewModel` загружает данные через `Repository`.
9. `NewsDetailsContent` отображает новость.

[Предыдущий шаг](11-navigation.md)