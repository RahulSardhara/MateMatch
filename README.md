# MateMatch 💍
MateMatch is a matrimonial matchmaking app built using **Kotlin**, **MVVM**, and **Clean Architecture**. The app integrates both **local storage** and **remote data** using **Room**, **Retrofit**, and **Paging 3**. Users can browse profiles, accept or reject matches, and view filtered lists based on status.

## 🔧 Tech Stack

| Layer         | Technology |
|---------------|------------|
| Language      | Kotlin     |
| UI Toolkit    | Android Views (DataBinding) |
| Architecture  | MVVM + Clean Architecture |
| Local DB      | Room       |
| Remote API    | Retrofit   |
| Pagination    | Paging 3   |
| DI Framework  | Hilt       |
| Build System  | Gradle (KTS) |
| Logging       | Timber     |

## 📁 Clean Architecture Structure

This project uses **Clean Architecture** to ensure separation of concerns and maintainability.
## 🧪 Why Clean Architecture?

- ✅ Easy testing (UseCases are separated)
- ✅ Modular, readable, and maintainable
- ✅ Enables reuse of business logic
- ✅ Separates logic from UI and data sources

- ### ✅ Android Studio
- **Recommended Version:** Meerkat | 2024.3.1 Patch 1
- **Gradle Plugin Version:** `8.11.1`
- **Kotlin Version:** `2.1.21`
- **Minimum SDK:** `29`
- **Target SDK:** `35`

## 🧪 Modified & Filtered API Response
Although the app uses the public API [randomuser.me]((https://randomuser.me/api/?results=10), which does not provide all the fields required in a matrimonial app, we’ve **augmented the response** to include three critical fields:
| Field      | Source       | Description                                                                 |
|------------|--------------|-----------------------------------------------------------------------------|
| `education`| Generated    | Based on age — simulates real-world education levels such as Bachelor's, Master's, Ph.D. |
| `religion` | Generated    | Randomly assigned from major Indian religions (Hindu, Muslim, Christian, etc.) |
| `caste`    | Generated    | Randomly assigned based on religion (Brahmin, Rajput, Kayastha, Yadav, Maratha,etc.) |

