@echo off
SETLOCAL ENABLEEXTENSIONS
SETLOCAL ENABLEDELAYEDEXPANSION

SET JAVA_HOME=C:\Program Files\Java\jdk1.8

SET PATH=%JAVA_HOME%\bin;%PATH%
java -version
javac -version

echo ===== Clean old build =====
IF EXIST bin rmdir /s /q bin
mkdir bin

echo ===== Compile Java sources =====
IF EXIST filelist.txt DEL filelist.txt
FOR /R src/main %%f IN (*.java) DO (
    ECHO %%f >> filelist.txt
)
javac -cp "libs/*" -d bin @filelist.txt

echo ===== Create executable JAR =====
jar cfm BaoMoiCrawler.jar manifest.txt -C bin .

echo ===== Build complete! =====
echo Run with: java -jar BaoMoiCrawler.jar

pause