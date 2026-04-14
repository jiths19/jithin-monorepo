@echo off
echo ==================================================
echo  Stopping Eapen BookRecords Local Server...
echo ==================================================

set "PORT_PID="
for /f "tokens=5" %%a in ('netstat -aon ^| find "LISTENING" ^| find ":8080"') do set "PORT_PID=%%a"

if "%PORT_PID%"=="" (
    echo No running server found on Port 8080.
) else (
    echo Found running server ^(PID: %PORT_PID%^).
    echo Shutting it down...
    taskkill /PID %PORT_PID% /F
    timeout /t 1 /nobreak >nul
    echo Server stopped successfully!
)

pause
