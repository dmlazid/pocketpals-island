# PocketPals Island — 1.1.0

An offline Android companion with six original illustrated pets, three animated scenes, pet-care reactions, and live wallpaper.

## Install
Download **PocketPals-Island.apk** from [the GitHub release](https://github.com/dmlazid/pocketpals-island/releases/tag/latest).
Install over the previous version to retain hearts and pet selection. Android 8 or newer.

## Three clearly separated modes

1. **Play rain with pet · native media** — starts genuine, audible, locally generated rain ambience with an Android MediaSession and the selected pet as media artwork. Xiaomi documents media playback as a native Hyper Island route. The system decides the compact/expanded layout. This is not free-moving pet animation inside the native island. Playback stops after 30 minutes, on audio-focus loss, headphone disconnect, or Stop.
2. **Check and try native island** — reads Xiaomi's documented focus-notification permission for this app's own package. Only if the system grants access does the app offer a 30-minute notification session using Xiaomi's documented image slots and actions. A denied or unavailable result is shown honestly; nothing bypasses the device permission.
3. **Start floating pet** — a separate small animated overlay near the camera area. Requires Display over other apps. Drag to adjust. It is explicitly not native Hyper Island.

Stop all pet sessions ends both the companion service and rain playback.
Wallpaper uses the same pet/scene selection. Lock-screen availability depends on the system wallpaper picker.

## Artwork and interaction
Bunny, kitty, chick, panda, frog, and bear use original layered drawing code with cheeks, highlights, ears, blinking, feet, and separate Feed/Play/Nap reactions.
Scenes: sunny meadow, sakura garden, moonlit pond.
Hearts and levels are local interaction counters, not a complete pet-needs simulation.

## Build
JDK 17, Gradle 8.9, Android SDK 35:
```
gradle assembleDebug
```
GitHub Actions publishes the installable test APK to Releases.
CI caches the test signing key. Before public distribution configure permanent release signing; cache expiration can break update compatibility.

## Native implementation references
- [Xiaomi island client payload and capability checks](https://dev.mi.com/xiaomihyperos/documentation/detail?pId=2131)
- [Xiaomi media notification integration](https://dev.mi.com/xiaomihyperos/documentation/detail?pId=2161)

No fake media playback, package impersonation, notification interception, or permission bypass is used.
The app requests neither internet nor microphone permission.

## Validation
The first 1.1 artwork/native-check build compiled successfully in Actions run 38271776118. The final build with optional rain playback is tracked in Actions run 38271950945.
On-device native appearance and installation have not yet been verified on the user's POCO. A successful APK build does not establish Hyper Island compatibility.
