# Change Log

**Version 0** 

* Old HTML layout and outdated URLs.
* Basic implementation of `get`, `set`, and `fetch URL`.
* No comments or in-depth documentation.
* Functions mixed multiple responsibilities; class, attribute, and method names followed no consistent convention.
* **Workflow:** Run `main` → prepare URLs by topic (e.g., World, Economy, Sports) → fetch each URL and parse static HTML for information → save locally as `.JSON` files (no database).
* Since no 2014 snapshot of the original site was found, testing was not possible. However, based on the remaining data, this version likely performed its intended functions correctly.

---

**Version 1.0**

* Aimed to improve **modifiability** and **testability** while maintaining basic functionality.
* Updated folder structure and class design according to **SOLID** principles.
* Added comments, documentation, and `README.md`.
* Updated parsing logic and URLs for several topics.
* Added logging and CI/CD integration.
* **Workflow:** Run `main` → enqueue all summary page URLs → create a `Crawler` object → the crawler fetches each summary page to retrieve article URLs → fetch each article page for details → save locally → log progress.
* **Performance:** Core functions now stable. However, around **2% fault rate** when fetching URLs, and up to **70% fatal error rate** when the target URL is a summary page.

---

**Version 1.1**

* Focused on improving **availability** and **performance**.
* Implemented a **retry pattern** for URL fetching with delayed retries.

  * **Drawback:** The entire application pauses while waiting for retries; future patches will introduce **multi-threading** and more advanced recovery strategies.
* Introduced a **queue** to manage URLs to be fetched.
* **Performance:** Fault rate for fetching article pages reduced to **0.2%**, and fatal errors for summary pages decreased to **10%**.
