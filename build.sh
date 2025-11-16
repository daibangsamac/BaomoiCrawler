#!/bin/bash

# Stop on first error
set -e

echo "===== Clean old build ====="
rm -rf bin build
mkdir -p bin

# Compile Java sources
echo "===== Compile Java sources ====="
javac -cp "libs/*" -d bin $(find src/main -name "*.java" || true)

# Create JAR file on Root
echo "===== Create executable JAR ====="
jar cfe BaoMoiCrawler.jar manifest.txt -C bin .

echo "===== Build complete! ====="
echo "Run with: java -cp "BaoMoiCrawler.jar:libs/*" src.main.Main"

pause