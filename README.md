# 🎯 SNBT Math Mentor – 38 Hari

Aplikasi Android personal mentor matematika UTBK SNBT 2026.

## Tech Stack
- **Language**: Kotlin 100%
- **UI**: Jetpack Compose + Material 3 + Dark Mode
- **Architecture**: MVVM + Clean Architecture + Repository Pattern
- **DI**: Hilt
- **Database**: Room (offline-first)
- **Navigation**: Jetpack Compose Navigation
- **State**: StateFlow + SharedFlow
- **Async**: Kotlin Coroutines + Flow
- **Notifications**: WorkManager (daily reminder 07.00 WIB)
- **Images**: Coil
- **Preferences**: DataStore

## Fitur
- Splash Screen + Onboarding (sekali)
- Home Dashboard (progress bar, streak, quick stats)
- Daily Learning Screen (materi, ringkasan, video YouTube, latihan soal, quiz, timer)
- Progress & History Screen
- Error Log Screen
- Notifikasi harian 07.00 WIB
- Dark Mode
- Reset progress

## Cara Build
1. Buka di Android Studio Hedgehog atau lebih baru
2. Sync Gradle
3. Run di emulator/device API 26+

## Struktur Folder
```
app/src/main/java/com/snbt/mathmentor/
├── data/
│   ├── local/
│   │   ├── dao/         - Room DAOs
│   │   ├── database/    - Room Database
│   │   ├── entity/      - Room Entities
│   │   └── RoadmapDataSource.kt
│   └── repository/      - Repository Implementation
├── di/                  - Hilt Modules
├── domain/
│   ├── model/           - Domain Models
│   └── repository/      - Repository Interface
├── presentation/
│   ├── navigation/      - NavHost & Routes
│   ├── screens/         - All Screens
│   ├── theme/           - Material3 Theme
│   └── viewmodel/       - ViewModels
├── util/                - UserPreferences
└── worker/              - WorkManager
```

## RAM Optimization
- LazyColumn digunakan untuk semua list (tidak render semua item sekaligus)
- ViewModel hanya aktif selama lifecycle-nya
- StateFlow menggunakan WhileSubscribed(5000) untuk auto-cancel
- Room query menggunakan Flow (lazy evaluation)
- Tidak menggunakan `largeHeap` di Manifest
- ProGuard aktif di release build untuk minify

## Roadmap 38 Hari
| Fase | Hari | Materi |
|------|------|--------|
| 1 | 1-8 | Aritmetika & Aljabar Dasar |
| 2 | 9-12 | Geometri Dasar |
| 3 | 13-22 | Aljabar & Geometri Lanjutan |
| 4 | 23-28 | Statistika & Peluang |
| 5 | 29-35 | Penalaran Matematika |
| 6 | 36-38 | Tryout & Final Review |
