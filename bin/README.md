# Web Crawler Project

## Description

This is a **Web Crawler** designed to collect data from https://baomoi.com/. Key features include:

* Fetching HTML content from web pages.
* Storing data to files or databases.
* Parsing and filtering data using Jsoup and Gson.

Use cases include: collecting news articles, titles, descriptions and urls.

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/daibangsamac/BaomoiCrawler.git
cd BaomoiCrawler
```

## Project Structure

```
web-crawler/
├───.github
│   └───workflows
├───.old
├───.vscode
├───backend
│   ├───cmd
│   └───internal # Source code
│       ├───config
│       ├───source
│       └───test
├───bin # bin
├───frontend
├───libs
└───output
    ├───sample
    ├───the-gioi
    ├───van-hoa
    └───xa-hoi
```

---

## Usage

### Java

# For windows
```bash
javac -cp "libs/*" -d bin $(find backend -name "*.java" || true)
java -cp "bin;libs/*" backend.cmd.Main
```
# For Linux

### Common Options

`To be added`
---

## Notes

* Only crawl **public and legal** data.

`On process`
* Avoid overloading the server with too many requests.
* Use proxies if necessary to prevent IP blocking.

---

