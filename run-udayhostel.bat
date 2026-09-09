@echo off

echo ==========================================
echo   UDAY HOSTEL MANAGEMENT
echo ==========================================
echo.

echo Checking port 8080...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
    echo Stopping process %%a using port 8080...
    taskkill /PID %%a /F >nul 2>&1
)

echo.
echo Starting Uday Hostel Management...
echo.

java -jar target\UdayHostelManagement-0.0.1-SNAPSHOT.jar

pause