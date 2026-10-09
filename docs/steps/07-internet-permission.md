# 07. Разрешение на доступ к интернету

[На главную](../../README.md) 

## Задача

Разрешить приложению выполнять сетевые запросы и пояснить уже добавленную логику.

## Что изменилось

В `AndroidManifest.xml` добавлено:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

В Mapper, моделях, API, RetrofitClient, Repository, ViewModel и пагинации добавлены комментарии.

[Предыдущий шаг](06-news-list.md) · [Следующий шаг](08-home-integration.md)
