@echo off
setlocal
set "LOGIN=%cd%\executables\logInWindows.bat"
set "REGISTER=%cd%\executables\registerWindows.bat"
set "CUSTOM_CONFIG_FILE=%cd%\saves\.customConfig"
set "CUSTOM_TOKEN_FILE=%cd%\saves\.customToken"


set "callerDirectory"="%cd%"
cd "%~dp0"

git --version >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo "Git is not available!"
    exit /b 1
)

if not exist "%cd%\saves" mkdir "%cd%\saves"

if exist "%CUSTOM_CONFIG_FILE%" if exist "%CUSTOM_TOKEN_FILE%" goto credentials_present

rem first register
call "%REGISTER%"

:credentials_present

:loop
echo "1) Reregister"
echo "2) Log In"
echo "3) Exit"
set /p selection="Select an action: "
if "%selection%" == "1" (
    call "%REGISTER%"
) else if "%selection%" == "2" (
    call "%LOGIN%"
) else if "%selection%" == "3" (
    goto loop_end
) else (
    echo Invalid action: "%selection%" (expected 1, 2 or 3)
)
cls
GOTO loop

:loop_end
cd "%callerDirectory%"