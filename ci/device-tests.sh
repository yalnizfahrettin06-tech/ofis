#!/usr/bin/env bash
set -uo pipefail
gradle --no-daemon connectedDebugAndroidTest
test_status=$?
mkdir -p emulator-screenshots
adb pull /sdcard/Android/data/com.esnemolasi.app.preview/files/Pictures/ emulator-screenshots/ || true
exit "$test_status"
