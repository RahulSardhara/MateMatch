# MateMatch 💍
MateMatch is a matrimonial matchmaking app built using **Kotlin**, **MVVM**, and **Clean Architecture**. The app integrates both **local storage** and **remote data** using **Room**, **Retrofit**, and **Paging 3**. Users can browse profiles, accept or reject matches, and view filtered lists based on status.

## 🚀 Clone the repository
   ```bash
   git clone https://github.com/RahulSardhara/MateMatch.git
   cd MateMatch

- ### ✅ Android Studio
- **Recommended Version:** Meerkat | 2024.3.1 Patch 1
- **Gradle Plugin Version:** `8.11.1`
- **Kotlin Version:** `2.1.21`
- **Minimum SDK:** `29`
- **Target SDK:** `35`
   
## 🔧 Tech Stack

| Layer         | Technology                  |   Purpose                                                                                                   |
|---------------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Language      | Kotlin                      | Modern, concise, and expressive language that integrates seamlessly with Android and improves code readability and maintainability                                           |
| UI Toolkit    | Android Views (DataBinding) | Binds data directly to the XML views                                                                                                                                         |
| Architecture  | MVVM + Clean Architecture   | Combines MVVM for UI state separation and Clean Architecture to enforce layered, testable, and scalable code — separating domain logic, data sources, and UI concerns cleanly|
| Local DB      | Room                        | Local database for offline access                                                                                                                                            |
| Remote API    | Retrofit 3 + Gson           | Type-safe network layer with JSON parsing                                                                                                                                    |
| Pagination    | Paging 3                    | Efficient data loading and pagination                                                                                                                                        |
| DI Framework  | Hilt                        | Dependency injection to reduce boilerplate and improve testability                                                                                                           |
| Build System  | Gradle (KTS)                | Kotlin-based Gradle scripts provide type safety, auto-completion, and cleaner build configuration                                                                            |
| Logging       | Timber                      | Logging for debugging and error tracking                                                                                                                                     |

## 📁 Clean Architecture Structure
com.shaadi.matematch/
├── core/         # Core Activity and Core Fragment Mapping with lockOrientation
├── data/         # Network, DB, DTOs, and mapping
├── di/           # Hilt and Module mapping
├── domain/       # Entities, UseCases, Repository interface
├── presentation/ # UI, ViewModels, State management
├── utils/        # utils, extnesion 


This project uses **Clean Architecture** to ensure separation of concerns and maintainability.
## 🧪 Why Clean Architecture?

- ✅ Easy testing (UseCases are separated)
- ✅ Modular, readable, and maintainable
- ✅ Enables reuse of business logic
- ✅ Separates logic from UI and data sources



## 🧪 Modified & Filtered API Response
Although the app uses the public API [randomuser.me] (https://randomuser.me/api/?results=10), which does not provide all the fields required in a matrimonial app, we’ve **augmented the response** to include three critical

| Field      | Source              | Description                                                                 |
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

##❤️ Match Score Logic Description

| Factor     | Weight              | Description                                                                                  |
|------------|---------------------|----------------------------------------------------------------------------------------------|
| `Age`      | 50 pts              | Users closest to a target age (e.g., 28) score higher; each year difference reduces 5 pts    |
| `City`     | 50 pts              | Users from the same city as the current user receive full city score                         |

fun calculateMatchScore(age: Int, city: String): Int {
    val ageScore = (50 - abs(28 - age) * 5).coerceAtLeast(0)  // age proximity
    val cityScore = if (city == "Mumbai") 50 else 0           // city match
    return ageScore + cityScore                               // total out of 100
}

##📶 Offline & Error Handling Strategy

| Situation      | Strategy                                     |
|----------------|----------------------------------------------|
| `No internet`  | Data fetched from Room DB                    |
| `Errors`       | Handled via ViewModel → UI event channels    |
| `No data`      | ViewStub fallback with no_data layout        |
| `API failure`  | Snackbar with error message shown            |


## 🧠 Reflection: Design Constraints
## ✅ If Images Are Not Legal (Privacy Policy)
Profile image hidden (ivProfile.setGone())

## 🧠 Reflection: Time Constraints
-** If I had more time, I would have implemented Room database encryption to secure sensitive user data and enhance app security, especially given the nature of a matrimonial app.
-** Improve UI/UX polish, including subtle animations and transitions for a smoother and more engaging user experience.
-** Analytics and crash reporting integration (e.g., Firebase Analytics and Crashlytics) to monitor app performance and user behavior in real-time.
-** Advanced match-making algorithm using machine learning or weighted scoring based on preferences like education, lifestyle, and interests.
-** Dark mode support for better usability and user comfort in low-light environments.


Fallback initials shown via tvAvatar
if (IS_IMAGE_LEGAL_POLICY) { //IS_IMAGE_LEGAL_POLICY this value is set from utils.Constant
    ivProfile.setGone()
    tvAvatar.setVisible()
    tvAvatar.text = initials
} else {
    ivProfile.setVisible()
    tvAvatar.setGone()
}

##✅ If Fully Offline Matchmaking Is Required

- **Switch to Room + PagingSource only
- **Implement search/filter queries on Room DB
- **Use WorkManager for periodic background sync

--------------------------------------------------------------------------------------
## ▶️ Key Features

- **Browse profiles from randomuser.me
- **Paginated UI with accepted/rejected states
- **Match scoring algorithm
- **Profile filtering based on status
- **Local storage using Room
- **Fallback avatar when profile images are disabled

##🙏 License
This project is open-source and was built as part of an interview assignment for Shaadi.com. It is intended solely for demonstration and evaluation purposes.
