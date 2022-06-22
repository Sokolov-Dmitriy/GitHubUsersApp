## GitHubUsersApp

### Мобильное приложение для поиска пользователей GitHub

------------
#### Стек технологий:
##### - 100% Kotlin;
##### - Архитектура MVVM;
##### - Dependency injection - [Koin](https://insert-koin.io/ "Koin");
##### - [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html "Coroutines"), [Flow](https://kotlinlang.org/docs/flow.html "Flow"), [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/ "StateFlow"), [SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/ "SharedFlow");
##### - Работа с сетью реализована с помощью [Retrofit2](https://square.github.io/retrofit/ "Retrofit2");
##### - Навигация реализована с помощью [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started "Navigation Component");
##### - Пагинация реализована с помощью [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview "Paging3").
------------
#### Демонстрация работы:
![Демонстрация работы](https://github.com/Sokolov-Dmitriy/GitHubUsersApp/blob/master/nocode/app.gif)

------------
#### Запуск приложения:
##### Для запуска желательно указать [Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token "Personal Access Token") в файле local.properties, иначе будет ограничение на 10 запросов в минуту
```
#create your Personal access tokens in GitHub Settings->Developer settings->Personal access tokens
#choose only:"read:user, user:email"
#the token is specified without quotation marks
token = ghp_...
```





------------
##### Разработчик:
##### - [Соколов Дмитрий](https://github.com/Sokolov-Dmitriy "Соколов Дмитрий")