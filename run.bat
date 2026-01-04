@echo off
title TermChat Ultimate Launcher
color 0A

:: Clear the screen for a clean start
cls

echo ===================================================
echo   TermChat - One Click Build ^& Launch Sequence
echo ===================================================

:: 1. Cleanup old build files
echo [1/4] Cleaning old build files...
if exist bin rmdir /s /q bin
mkdir bin

:: 2. Compile all source code
echo [2/4] Compiling Java source code...
javac -d bin src/com/termchat/shared/*.java src/com/termchat/server/*.java src/com/termchat/client/*.java

:: Check if compilation failed
if %errorlevel% neq 0 (
    color 0C
    echo ---------------------------------------------------
    echo [ERROR] Compilation failed! Check for code typos.
    echo ---------------------------------------------------
    pause
    exit /b
)

:: 3. Create the Executable JAR
echo [3/4] Building TermChat_Client.jar...
:: 'cfe' flag: c=create, f=file name, e=entry point (main class)
jar cfe TermChat_Client.jar com.termchat.client.TermClient -C bin .

:: 4. Launch Sequence
echo [4/4] Launching TermChat Ecosystem...
echo ---------------------------------------------------

:: Start the Server in a new window with a different title
echo Starting Server in background...
start "TermChat Server [ACTIVE]" cmd /k java -cp bin com.termchat.server.TermServer

:: Give the server 2 seconds to bind to the port
timeout /t 2 /nobreak > nul

:: Start the Client in this current window
echo Starting your Client...
echo ---------------------------------------------------
java -jar TermChat_Client.jar

echo.
echo Chat session ended.
pause