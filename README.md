# Column Module sample app for Android (Kotlin)

See the full [documentation](https://docs.columntax.com) for more details.

This is a sample Android app written in Kotlin that shows an example of opening
the frontend Column Tax SDK Module in Android.

## Getting Started

1. Download and install [Android Studio](https://developer.android.com/studio) 
if you don't already have it.
1. Open this project in Android Studio.
1. Use the built-in tools to make/build and run the app. See
[the Android docs](https://developer.android.com/studio/run) for more info.
1. Once your app is up and running, create a new user via the [initialize tax filing endpoint](https://docs.columntax.com/reference/express-initialize-tax-filing)
1. Pass the `user_url` for the new user to the webview by replacing `"<user url>"` in [MainActivity.kt](https://github.com/column-tax/column-android-sample/blob/main/app/src/main/java/com/example/column/MainActivity.kt#L21)
