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
Although the app uses the public API [randomuser.me] (https://randomuser.me/api/?results=10), which does not provide all the fields required in a matrimonial app, we’ve **augmented the response** to include three critical | Field      | Source              | Description                                                                 |
|------------|---------------------|-----------------------------------------------------------------------------|
| `education`| Generated           | Based on age — simulates real-world education levels like B.Tech, MBA       |
| `religion` | Randomly generated  | Selected from a fixed list of religions (Hindu, Muslim, Christian, etc.)    |
| `caste`    | Based on religion   | Generated from a caste list corresponding to the selected religion          |

### ✅ Justification

- **Education**: A critical filtering field in Indian matchmaking platforms.
- **Religion**: Often used to narrow down matches based on personal/family preferences.
- **Caste**: A traditional but widely used criterion in Indian matrimonial culture.

These fields are dynamically generated in the domain model mapping logic:

```kotlin
val religion = generateFakeReligion()
val caste = generateFakeCaste(religion)
val education = generateFakeEducation(age)


