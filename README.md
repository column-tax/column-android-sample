# Column Android SDK Sample App

A simple Android app that shows how to use the official Column Tax SDK.

## Setup

1. Open this project in Android Studio
2. Build and run the app
3. Get a user URL by calling the [initialize tax filing endpoint](https://docs.columntax.com/reference/express-initialize-tax-filing)
4. Paste the `user_url` into the app and tap "Launch Column Tax SDK"

## Key Features

- Uses the official Column Tax SDK (`com.columntax.columntaxsdk:column-tax-sdk:1.0.0`)
- Simple interface with URL input and launch button
- Error handling for SDK launch issues

## Differences from WebView Sample

Unlike the existing `column-android-sample` which uses a custom WebView, this app uses:
- `ColumnTaxFileActivity` class from the official SDK
- No custom file upload handling (handled by SDK)
- No custom URL routing (handled by SDK)

## Requirements

- Android API 21+
- Internet permission
- Valid Column Tax user URL

## Documentation

- [Column Mobile SDK Guide](https://docs.columntax.com/reference/mobile-sdk-guide)
- [Column Tax Documentation](https://docs.columntax.com)