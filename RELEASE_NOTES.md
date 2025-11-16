# Release Notes

These release notes summarize the key updates, improvements, and performance changes across major versions of the project.

---

## **Version 1.0.0 – Modular Architecture Release**

### 🎉 Highlights
This release introduces a complete redesign of the system architecture, transitioning from a monolithic structure to a scalable, maintainable **5-layer modular architecture**.

### 🚀 New Features
- Added a structured 5-layer architecture:
  - Model  
  - Data Access Layer  
  - Service  
  - Controller  
  - View  
- Improved folder structure following SOLID principles.
- Enhanced retry mechanism:
  - Retry each request 5 times.
  - If still failing, move to the end of the queue.
  - Retry 5 more times on second attempt.
  - Mark as error if still unsuccessful.
- Updated parsing layout for several topics.
- Updated URLs and CI/CD pipeline.
- Added:
  - `CHANGELOG.md`
  - `RELEASE_NOTES.md`

### 🔧 Improvements
- Cleaner workflow:
  ```
  main → prepare URLs → enqueue → dequeue → Crawler → fetch summary pages → fetch articles → store in database
  ```
- Lower memory usage and higher stability.

### 📊 Performance
- **73 minutes** to process 15 major topics  
- Error rate:
  - Summary pages: **0%**
  - Article pages: **0.07%**
- Test coverage:
  - 100 runs of 41×15 summary pages
  - 3 runs of 2244×15 article pages
- **Memory usage:** 180 MB database + 4 KB logs

---

## **Version 0.5.0 – Enhanced Monolithic Architecture**

### 🚀 Highlights
A stability-focused update improving the original monolithic version.

### 🆕 New Features
- Queue-based processing flow.
- Retry mechanism added (5 attempts, 4-second delay).
- Added comments, logging, and unit tests.
- Added README with usage instructions.
- Added CI/CD pipeline.

### 📊 Performance
- **75 minutes** (80 minutes with retry) for 15 topics  
- Error rate (before retry):
  - Summary: **77.6%**
  - Article: **0.4%**
- Error rate (after retry):
  - Summary: **0%**
  - Article: **0.2%**
- **Memory usage:** 570 MB

---

## **Version 0.0.0 – Initial Prototype**

### 🚧 Overview
The first functional version of the crawler.  
Operable but highly limited in structure, testing, and maintainability.

### Key Characteristics
- Used outdated layouts and URLs.
- Basic URL fetching & parsing only.
- No available historical snapshots → cannot fully test.
- High coupling, poor naming conventions.
- No unit tests.
- All code in a single folder.

### Workflow
```
main → prepare URL by topic → fetch URL → save JSON
```

---

## 📝 Future Directions
- Implement multithreading or async crawling.
- Improve database schema.
- Add monitoring dashboards.
- Extend tests to integration and end-to-end.

---
