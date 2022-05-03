## GitHubUsersApp
####Мобильное приложение для поиска пользователей GitHub

------------
#### Стек технологий:
##### - 100% Kotlin
##### - Архитектура MVVM
##### - Dependency injection - Koin
##### - Coroutines, Flow, StateFlow, SharedFlow
##### - Работа с сетью реализована с помощью Retrofit2.
------------
#### Демонстрация работы:
![Демонстрация работы](https://github.com/Sokolov-Dmitriy/GitHubUsersApp/blob/master/nocode/app.gif)

------------
#### Запуск приложения:
##### Для запуска необходимо указать Personal access tokens в файле (domain\src\main\java\com\sokolovds\domain\DefaultValues.kt)
```
data class Header(
        val name: String = "Authorization",
        val type: String = "Bearer",
        //create your Personal access tokens in GitHub Settings->Developer settings->Personal access tokens
        //choose only:"read:user, user:email"
        val token: String = "",
        val value: String = "$type $token"
    )
```
------------



#### Известные баги:
##### -  При вводе в строку поиска один символ, api присылает только одного пользователя
![Баг](https://github.com/Sokolov-Dmitriy/GitHubUsersApp/blob/master/nocode/bug.png)


------------
##### Разработчики:
##### - [Соколов Дмитрий](https://github.com/Sokolov-Dmitriy "Соколов Дмитрий")
