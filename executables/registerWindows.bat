@echo off
setlocal
set "CUSTOM_CONFIG=..\saves\.customConfig"
set "CUSTOM_TOKEN=..\saves\.customToken"


cd "executables"

set /p username="Enter your git username: "
set /p mail="Enter your git mail: "
set /p token="Enter your login token: "

if not exist %CUSTOM_CONFIG% type null > "%CUSTOM_CONFIG%"
type null >  "%CUSTOM_TOKEN%"

git config --file "%CUSTOM_CONFIG%" user.name "%username%"
git config --file "%CUSTOM_CONFIG%" user.mail "%mail%"
git config --file "%CUSTOM_CONFIG%" credential.helper store
echo https://%username%:%token%@github.com > %CUSTOM_TOKEN%

cd ".."
