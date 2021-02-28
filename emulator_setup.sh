adb install -r -t emulator_hacks.apk
adb shell pm grant no.finn.android_emulator_hacks android.permission.SET_ANIMATION_SCALE
adb shell pm grant no.finn.android_emulator_hacks android.permission.WRITE_SECURE_SETTINGS
adb shell am start -n no.finn.android_emulator_hacks/no.finn.android_emulator_hacks.HackActivity
adb shell am broadcast -a com.android.intent.action.SET_LOCALE --es com.android.intent.extra.LOCALE EN
adb shell settings put secure show_ime_with_hard_keyboard 0

