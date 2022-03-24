# NYT Articles
![Screenshot_20220325_032259](https://user-images.githubusercontent.com/102327017/160009289-ab19e876-1754-4278-bb8a-eea81d9b2b69.png)
![Screenshot_20220325_032326](https://user-images.githubusercontent.com/102327017/160009298-2178766c-75f3-42ff-8ead-388c517a5947.png)
![Screenshot_20220325_032410](https://user-images.githubusercontent.com/102327017/160009312-d6d6383f-15f2-47f6-99a1-63da7927ee52.png)

## To Build
- Replace you API_KEY in `secret.properties` files located at root of project (this is why needed to pass key from view layer)

## Features
Consist two screen
1. Detail
2. List with ability to search and change period

- Unit test on part of ViewModel (ArticleDetailViewModelTest), UseCase and Repository

# Architecture and Stack
- Using Clean architecture layer with MVVM (ViewModel, LiveData + Flow)
- Mix of declarative UI (Jetpack Compose) + imperative on xml to show that this case will be happen
  in day-to-day dev. (Since no existing product would like to  start new app from scratch)
- Room DB
- Detail Screen in Jetpack Compose
- UI Test with Kakao
- Hilt for Dependency Injection
- GitHub Actions for running unit test as precheck
