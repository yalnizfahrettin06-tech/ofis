#!/usr/bin/env bash
set -uo pipefail
gradle --no-daemon connectedDebugAndroidTest
test_status=$?
mkdir -p emulator-screenshots
adb pull /sdcard/Pictures/EsnemeEvidence/ emulator-screenshots/
capture_status=$?
if [ "$test_status" -eq 0 ]; then
  if [ "$capture_status" -ne 0 ] || [ "$(find emulator-screenshots -name '*.png' | wc -l)" -lt 7 ]; then
    echo 'Expected seven UI screenshots were not captured.'
    exit 1
  fi
fi
exit "$test_status"
