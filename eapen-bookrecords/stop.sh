#!/usr/bin/env bash

echo "=================================================="
echo " Stopping Eapen BookRecords Local Server... "
echo "=================================================="

PORT_PID=$(lsof -ti:8080)
if [ -z "$PORT_PID" ]; then
    echo "No running server found on Port 8080."
    exit 0
else
    echo "Found running server (PID: $PORT_PID)."
    echo "Shutting it down..."
    kill -9 $PORT_PID
    sleep 1
    echo "Server stopped successfully!"
fi
