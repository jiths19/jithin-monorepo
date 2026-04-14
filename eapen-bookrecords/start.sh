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

echo "Step 1: Building the latest application (may take a moment)..."
cd ..
./gradlew :eapen-bookrecords:application:installDist

echo "Step 2: Starting the Web Server..."
cd eapen-bookrecords/application/build/install/application/bin/
./application
