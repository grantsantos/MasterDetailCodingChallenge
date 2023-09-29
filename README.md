# MasterDetailCodingChallenge

This is a simple master-detail project that utilizes the Jetpack Libraries (Paging, Room, Navigation, etc) and other libraries like Retrofit, Hilt, and more.

API used: Itunes Search API with specific endpoint: https://itunes.apple.com/search?term=star&country=au&media=movie&;all

Architecture:

I used the app architecture recommended by google: https://developer.android.com/topic/architecture#recommended-app-arch 
The reason for this is to promote the separation of concerns principle. Separation of concerns means, you separate your app into multiple layers
which are, UI, Domain and Data. This makes the app easier to test if not more testable, it also makes it easier to find where the problem as they are divided in three different layers, and thus
making the project easier to scale. 

Dependency Injection:
I used Dagger Hilt for easy dependency management. I mainly use this library as it is well documented and supported by Google.

Persistence:
I used Room persistence library in saving and retrieving data in local database SQLite. 
