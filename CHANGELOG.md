# Changelog

All notable changes to this project will be documented in this file.

---

## [1.0.0] – Layered Modular Architecture

### Added
- Refactored the entire system from monolithic to a 5-layer modular architecture:
  **Model → Data Access Layer → Service → Controller → View**.
- Restructured folders and classes following SOLID principles.
- Implemented improved retry pattern:
  - Retry each request 5 times.
  - If still failing, move URL to the end of the queue.
  - On second attempt, retry 5 times again.
  - Mark as error if still failing.
- Updated parsing layouts for multiple topics.
- Updated URLs and CI/CD configuration.
- Added `CHANGELOG.md` and `RELEASE_NOTES.md`.

### Changed
- New workflow:
  `main → prepare URLs and push to queue → dequeue URL → create Crawler → fetch summary pages → fetch articles → save to database`.

### Performance
- Time: **73 minutes** for 15 major topics.
- Error rate:
  - Summary pages: **0%**
  - Article pages: **0.07%**
- Testing:
  - 100 runs on **41×15 summary pages**
  - 3 runs on **2244×15 article pages**
- Memory usage: **180 MB (database)** + **4 KB (logs)**

---

## [0.5.0] – Monolithic Architecture Improvements

### Added
- Queue system with basic retry (5 attempts, 4s delay).
- Comments, logging, and basic unit tests.
- README with introduction and usage instructions.
- CI/CD pipeline integration.

### Changed
- Updated outdated URLs, libraries, and parsing layout.
- Workflow remains:
  `main → prepare URL → fetch URL → save as .JSON`.

### Performance
- Time: **75 minutes** (80 minutes with retry) for 15 major topics.
- Error rate before retry:
  - Summary pages: **77.6%**
  - Article pages: **0.4%**
- Error rate after retry:
  - Summary pages: **0%**
  - Article pages: **0.2%**
- Memory usage: **570 MB**

---

## [0.0.0] – Initial Prototype

### Added
- Basic HTML parsing using old layout and outdated URLs.
- Implemented simple get/set utilities and URL fetching.
- Able to perform basic operations based on remaining historical data.

### Known Issues
- No comments; missing README details.
- All logic placed in a single module → high coupling.
- Poor naming conventions for classes and attributes.
- No unit tests.
- Poor folder structure: all source code placed in one directory.
- Unable to retrieve 11-year-old webpage snapshots, limiting test coverage.

### Workflow
`main → prepare URL by topic → fetch URL → save as .JSON`

---