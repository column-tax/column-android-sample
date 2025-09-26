# Column Tax Android SDK Sample

This sample Android app demonstrates how to integrate the official Column Tax Android SDK into your application.

## What this sample does

- Provides a text input for entering a Column Tax user URL
- Validates input and enables/disables the launch button dynamically
- Launches the Column Tax SDK using the official `ColumnTaxFileActivity`
- Handles the complete tax filing flow through the SDK

## Getting Started

### Prerequisites
- [Android Studio](https://developer.android.com/studio) installed
- Android device or emulator (API level 21+)
- Valid Column Tax user URL (see setup step 4)

### Setup Instructions

1. **Open the project**
   ```bash
   # Clone or download this repository
   # Open in Android Studio
   ```

2. **Build and run**
   - Use Android Studio's built-in tools to build the project
   - Run on your device or emulator
   - See [Android documentation](https://developer.android.com/studio/run) for detailed instructions

3. **Get a user URL**
   - Call Column Tax's [initialize tax filing endpoint](https://docs.columntax.com/reference/express-initialize-tax-filing)
   - This will return a `user_url` specific to your user

4. **Test the integration**
   - Launch the app
   - Paste your `user_url` into the text field
   - Tap "Launch Column Tax SDK"
   - The Column Tax interface will open within your app

## Technical Details

### SDK Integration
- **SDK Version**: `com.columntax.columntaxsdk:column-tax-sdk:1.0.3`
- **Main Class**: `ColumnTaxFileActivity` handles the entire tax filing flow
- **Architecture**: Simple Activity-based integration with minimal setup

### App Features
- **Dynamic UI**: Button changes color and state based on URL input
- **Error Handling**: Graceful handling of SDK launch issues
- **Modern Android**: Built with AGP 8.13.0, targeting Android API 34

### Requirements
- **Minimum SDK**: Android API 21 (Android 5.0)
- **Target SDK**: Android API 34
- **Permissions**: Internet access for API communication
- **Dependencies**: AndroidX libraries, Column Tax SDK

## Code Structure

```
app/src/main/java/com/example/columnsdksample/
└── MainActivity.kt          # Main app logic and SDK integration

app/src/main/res/
├── layout/activity_main.xml # Simple UI with URL input and button
└── values/strings.xml       # App strings
```

## Support

- [Column Tax Documentation](https://docs.columntax.com)
- [Mobile SDK Guide](https://docs.columntax.com/reference/mobile-sdk-guide)