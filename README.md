# NYT Articles

## To Build
- Replace you API_KEY in `secret.properties` files located at root of project (this is why needed to pass key from view layer)

## Features
Consist two screen
1. Detail
2. List with ability to search and change period

# Architecture and Stack
- Using Clean architecture layer with MVVM (ViewModel, LiveData + Flow)
- Mix of declarative UI (Jetpack Compose) + imperative on xml to show that this case will be happen
  in day-to-day dev. (Since no existing product would like to  start new app from scratch)
- Room DB
- Detail Screen in Jetpack Compose
- UI Test with Kakao
- Unit test on ViewModel (ArticleDetailViewModelTest), UseCase and Repository
- Hilt for Dependency Injection
- GitHub Actions for running unit test as precheck

