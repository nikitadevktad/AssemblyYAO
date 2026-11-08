
# Ассамблея ЯО

Android-приложение Ассамблеи народов Ярославской области. Оно объединяет новости, сведения об организации и контакты.

<p align="center">
  <img src="https://github.com/user-attachments/assets/c378f402-6753-4094-81a4-9f43c579b72e" width="250" />
  <img src="https://github.com/user-attachments/assets/beea94d5-7967-4074-a78c-a21d5b9eaa98" width="250" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/9bec1831-2b49-49ab-80cb-bd55da2b7a7e" width="250" />
  <img src="https://github.com/user-attachments/assets/9db8d74f-288c-4d6b-874f-792001298ce2" width="250" />
</p>

## Возможности

- Просмотр последних новостей и полного текста публикаций.
- Поиск новостей.
- Постраничная навигация по результатам.
- Офлайн-просмотр кэша не старше семи суток.
- Разделы «Главная», «Новости», «О нас» и «Контакты».
- Памятки иностранному гражданину на семи языках в формате PDF.
- Переход к звонку, электронной почте, карте и официальному сайту.

## Архитектура

- UI построен на Jetpack Compose.
- `NewsViewModel` управляет поиском, пагинацией и состоянием списка.
- `NewsDetailsViewModel` управляет экраном отдельной публикации.
- `OfflineFirstNewsRepository` получает свежие данные и использует Room при ошибке сети.
- Retrofit и Gson читают WordPress API.
- Room хранит страницы, порядок публикаций и отдельные новости.
- `AppContainer` создаёт зависимости приложения без глобальных синглтонов UI.



## Этапы разработки

1. [Создание Android-проекта](docs/steps/01-project-creation.md)
2. [Оформление главной страницы](docs/steps/02-home-design.md)
3. [Баннер с перелистыванием](docs/steps/03-home-banner.md)
4. [Получение новостей с сайта](docs/steps/04-news-api.md)
5. [Состояние загрузки новостей](docs/steps/05-news-state.md)
6. [Карточки новостей и пагинация](docs/steps/06-news-list.md)
7. [Разрешение на доступ к интернету](docs/steps/07-internet-permission.md)
8. [Подключение главной страницы](docs/steps/08-home-integration.md)
9. [Экран отдельной новости](docs/steps/09-news-details.md)
10. [Иконка и строковые ресурсы](docs/steps/10-app-resources.md)
11. [Навигация между разделами](docs/steps/11-navigation.md)
12. [Навигация к отдельной новости](docs/steps/12-add-navigation-to-news.md)
13. [Экран «О нас»](docs/steps/13-about-screen.md)
14. [Офлайн-режим и проверка качества](docs/steps/14-offline-quality.md)
15. [Памятки иностранному гражданину](docs/steps/15-foreign-citizen-memos.md)

