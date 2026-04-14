# Eapen BookRecords

A robust, multi-user local web application built dynamically for small business management. 

Features:
- **Role-Based Views**: Differentiates between regular employees conducting transactions and owners seeking summarized monthly analytics.
- **Embedded SQL Store**: Completely uncorruptible dataset leveraging SQLite and Jetbrains Exposed.
- **Dynamic Ledger Interface (HTMX)**: Next-generation UI feeling without heavy Javascript payloads.
- **Tailwind UI Framework**: Implemented custom classy "Business Green" palette over HTML components.

## Startup & Deployment Guide

This application is designed to be effortlessly deployed inside the business's local shop/office without relying on complex Cloud environments or monthly bills.

### Prerequisites
- Any modern computer running macOS, Windows, or Linux.
- **Java 17 (or newer)** installed on the target machine. You can download one for free from [Adoptium](https://adoptium.net/).

### How To Launch the Server

1. Open a terminal or command prompt inside this folder `eapen-bookrecords/`.
2. Run the provided startup script matching your Operating System:
    - **Mac / Linux:** Execute `./start.sh`
    - **Windows:** Double-click on `start.bat` (or execute it via CMD)
3. The script will automatically compile any underlying data structures and launch the web server locally! Do not close the window while you wish to use the software.

### How To Connect locally
Once the script says `Responding at http://0.0.0.0:8080`, simply open your preferred internet browser (Google Chrome, Edge, Safari, Firefox, etc.) and type this into the search bar:

`http://localhost:8080/`

**Note:** If employees use mobile devices on the same WiFi network, you can look up the host PC's Local IP address (usually starting with `192.168...`) and enter that into the mobile browser e.g., `http://192.168.1.15:8080`.

**BookRecords.db:** Note that the database file `bookrecords.db` will automatically manifest itself inside the `eapen-bookrecords/application/` directory upon initial boot relative to where gradle creates the runtime. Backup this file regularly!
