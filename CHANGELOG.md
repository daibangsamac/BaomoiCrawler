# Changelog

## [1.1] - 2025-11-03
### Added
- Implemented retry pattern for URL fetching
- Introduced URL queue system
### Improved
- Reduced fault rate from 2% → 0.2%
- Reduced fatal error on summary pages from 70% → 10%
### Known Issues
- Application halts during retry (to be fixed with multithreading)

## [1.0] - 2025-10-14
### Added
- Refactored codebase following SOLID principles and comments
- Added CI/CD pipeline and logging
### Changed
- Updated parsing logic and URLs
- Improved folder and class structure
