@echo off
echo ==================================================
echo  Starting Eapen BookRecords Local Server...
echo ==================================================

:: Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java could not be found.
    echo Please install Java 17 or later to run this application.
    echo You can download it from: https://adoptium.net/
    pause
    exit /b 1
)

echo Step 1: Checking for existing running sessions...
set "PORT_PID="
for /f "tokens=5" %%a in ('netstat -aon ^| find "LISTENING" ^| find ":8080"') do set "PORT_PID=%%a"

if not "%PORT_PID%"=="" (
    echo ==================================================
    echo WARNING: An existing application is already running on Port 8080!
    echo Process PID: %PORT_PID%
    echo.
    set /p confirm="Do you want to close the existing session and start a new one? (y/n) "
)

if /i "%confirm%"=="y" (
    echo Killing process ^(PID: %PORT_PID%^)...
    taskkill /PID %PORT_PID% /F
    timeout /t 1 /nobreak >nul
) else if not "%PORT_PID%"=="" (
    echo Aborting startup. Please try again when you are ready to terminate the existing session.
    pause
    exit /b 1
)

echo Step 2: Building the latest application (may take a moment)...
cd ..
call gradlew.bat :eapen-bookrecords:application:installDist
if %errorlevel% neq 0 (
    echo Failed to build the project.
    pause
    exit /b 1
)

echo Step 3: Starting the Web Server...
cd eapen-bookrecords\application\build\install\application\bin\
call application.bat

pause
