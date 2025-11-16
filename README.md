# Baomoi Crawler Project

## Description

This is a **Web Crawler** designed to collect data from https://baomoi.com/. Key features include:

* Fetching HTML content from web pages.
* Storing data to files or databases.
* Parsing and filtering data using Jsoup and Gson.

Use cases include: collecting news articles, titles, descriptions and urls.

---

## Project Structure

```
E:.
├───.github
│   └───workflows
├───.old
├───.vscode
├───bin # compile folder
├───libs # contain external libraries
├───output # Store Data
└───src
    ├───main
    │   ├───controller
    │   ├───dao
    │   ├───model
    │   ├───service
    │   ├───util
    │   └───view
    └───test
        └───main
            ├───controller
            ├───dao
            ├───model
            └───service
---

# Usage

## Installation

1. Clone the repository:

```bash
git clone https://github.com/daibangsamac/BaomoiCrawler.git
cd BaomoiCrawler
```
## Java

# For windows

execute build.bat
execute run.bat

# For Linux

execute build.sh
```
java -cp "BaoMoiCrawler.jar:libs/*" src.main.Main
```

# Notes

* Only crawl **public and legal** data.

---

