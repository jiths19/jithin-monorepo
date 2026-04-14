#!/usr/bin/env bash

echo "=================================================="
echo " Starting Eapen BookRecords Local Server... "
echo "=================================================="

# Check if Java is installed
if ! command -v java &> /dev/null
then
    echo "ERROR: Java could not be found."
    echo "Please install Java 17 or later to run this application."
    echo "You can download it from: https://adoptium.net/"
    exit 1
fi

echo "Step 1: Checking for existing running sessions..."
PORT_PID=$(lsof -ti:8080)
if [ ! -z "$PORT_PID" ]; then
    echo "=================================================="
    echo "WARNING: An existing application is already running on Port 8080!"
    echo "Process Details:"
    lsof -i:8080
    echo ""
    read -p "Do you want to close the existing session and start a new one? (y/n) " -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo "Killing process (PID: $PORT_PID)..."
        kill -9 $PORT_PID
        sleep 1
    else
        echo "Aborting startup. Please try again when you are ready to terminate the existing session."
        exit 1
    fi
fi

echo "Step 2: Building the latest application (may take a moment)..."
cd ..
./gradlew :eapen-bookrecords:application:installDist

echo "Step 3: Starting the Web Server..."
cd eapen-bookrecords/application/build/install/application/bin/
./application
