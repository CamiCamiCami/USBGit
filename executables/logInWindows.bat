@echo off
setlocal
set "CUSTOM_CONFIG=..\saves\.customConfig"
set "CUSTOM_TOKEN=..\saves\.customToken"
set "SAVED_CONFIG=..\saves\.savedConfig"
set "SAVED_TOKEN=..\saves\.savedToken"
set "LOCAL_CONFIG=%USERPROFILE%\.gitconfig"
set "LOCAL_TOKEN=%USERPROFILE%\.git-credentials"


cd "executables"

if exist "%LOCAL_CONFIG%" copy  "%LOCAL_CONFIG%" "%SAVED_CONFIG%" > nul
if exist "%LOCAL_TOKEN%" copy  "%LOCAL_TOKEN%" "%SAVED_TOKEN%" > nul

copy  "%CUSTOM_CONFIG%" "%LOCAL_CONFIG%" > nul
copy  "%CUSTOM_TOKEN%" "%LOCAL_TOKEN%" > nul

echo "Logged in"
echo "Press Enter to log out..."
pause > nul

if exist "%SAVED_CONFIG%" (
  copy  "%SAVED_CONFIG%" "%LOCAL_CONFIG%" > nul
  del  "%SAVED_CONFIG%" > nul
) else (
  del  "%LOCAL_CONFIG%" > nul
)

if exist "%SAVED_TOKEN%" (
  copy  "%SAVED_TOKEN%" "%LOCAL_TOKEN%" > nul
  del  "%SAVED_TOKEN%" > nul
) else (
  del  "%LOCAL_TOKEN%" > nul
)

cd ".."