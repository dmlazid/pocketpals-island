# PocketPals Island
An original offline Android pet companion inspired by the idea of tiny pets accompanying you around your phone. No accounts, ads, analytics, or internet permission.

## First test version — 1.0.0
- Six original code-drawn pixel companions: bunny, kitty, chick, panda, frog, bear.
- Idle walking, hopping, blinking, and tap reactions.
- Feed, play, and nap reactions with saved hearts and levels. These are simple interactions, not a full hunger/sleep simulation.
- Meadow, blossom, and starlight backgrounds.
- Draggable floating pet island over other apps, with a notification Stop action.
- Android live wallpaper using the same pet and scene; touch to react.
- Saved selection, scene, hearts, and island position.

## Download
After the GitHub Actions build succeeds, open **Releases → latest test APK → PocketPals-Island.apk**. Alternatively download the PocketPals-Island-APK artifact in Actions and unzip it.

Android 8.0 or newer. Allow installation from your browser when asked. Enable Display over other apps for the island. On Xiaomi/POCO, additional background/battery settings may be needed if the phone stops it. Island startup is manual; it does not restart automatically after reboot.

Tap Set animated wallpaper to open the system preview. Home/lock-screen options are controlled by the device. This Android overlay is not Apple's Dynamic Island integration. Some protected apps may hide overlays.

## Build
JDK 17, Gradle 8.9, Android SDK 35:
```
gradle assembleDebug
```
The workflow builds and publishes a test APK on pushes to main/master, or via Run workflow. It caches the test signing key to retain update compatibility while that cache exists. Before public distribution, configure a permanent release signing key: the CI test key cache can expire, requiring uninstall/reinstall.

## Validation status
Android APK compilation succeeded in GitHub Actions run 38269482499, and the APK was published to the latest release. On-device installation, overlay behavior, and wallpaper appearance have not yet been verified. Initial version is a test build, not a verified production release.
