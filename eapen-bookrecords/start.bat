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

echo Step 1: Building the latest application (may take a moment)...
cd ..
call gradlew.bat :eapen-bookrecords:application:installDist
if %errorlevel% neq 0 (
    echo Failed to build the project.
    pause
    exit /b 1
)

echo Step 2: Starting the Web Server...
cd eapen-bookrecords\application\build\install\application\bin\
call application.bat

pause
