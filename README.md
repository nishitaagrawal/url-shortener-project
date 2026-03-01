# URL Shortener Service

A simple REST-based URL Shortener built using Java and Spring Boot.
This service accepts a URL, generates a shortened URL, and redirects users to the original URL when the short link is accessed.
The application also provides a metrics API that returns the top 3 domains that have been shortened the most.

# Features
* Shorten a URL via REST API
* Return the same short URL for repeated requests of the same URL
* Redirect users from short URL to the original URL
* Metrics API to return top 3 domains shortened
* In-memory storage for URL mappings
* Thread-safe implementation
* Logging
* Custom exception handling
* Unit test
* Docker for containerizing the application
  
# Tech Stack
* Java 17
* Spring Boot
* Maven
* Lombok
* Docker

# Project Architecture
The project follows a layered architecture to ensure maintainability and readability.
src/main/java/com/example/urlshortener
controller   -> REST API endpoints
service      -> Business logic
repository   -> In-memory storage layer
util         -> Utility classes (Base62 encoding)
dto          -> Request/response models
exception    -> Custom exception handling

# Design Decisions
### URL Generation
Short URLs are generated using **Base62 encoding** of an incrementing numeric ID.
Example:
ID → Base62
1  → a
2  → b
61 → Z
62 → aa

This approach ensures:
* Short URL length
* Unique identifiers
* Fast encoding
* No external dependency

### Data Storage
All URL mappings are stored in memory using:
```
ConcurrentHashMap
```
This ensures thread-safe operations in concurrent environments.

### Domain Metrics

Whenever a URL is shortened, the domain name is extracted and a counter is incremented.
The metrics API returns the **top 3 domains with highest counts**.

Example:

```
Udemy: 6
YouTube: 4
Wikipedia: 2
```
---

# API Endpoints
## 1. Shorten URL
POST `/api/shorten`
Request:
```json
{
  "url": "https://youtube.com/watch?v=example"
}
```
Response:
```json
{
  "shortUrl": "http://localhost:8080/abc12"
}
```
Behavior:
* If the same URL is shortened again, the same short URL is returned.
---

## 2. Redirect to Original URL
GET `/{shortCode}`
Example:
```
http://localhost:8080/abc12
```
Behavior:
* Redirects to the original URL using HTTP 302 response.
---

## 3. Metrics API
GET `/api/metrics/top-domains`
Example response:
```json
[
  {
    "domain": "udemy.com",
    "count": 6
  },
  {
    "domain": "youtube.com",
    "count": 4
  },
  {
    "domain": "wikipedia.org",
    "count": 2
  }
]
```

Application will start at:

```
http://localhost:8080
```
---

# Running with Docker
Build Docker image
```
docker build -t url-shortener .
```
Run container
```
docker run -p 8080:8080 url-shortener
```
---

# Example Usage
Shorten URL
```
POST /api/shorten
```
Redirect
```
GET /{shortCode}
```
Metrics
```
GET /api/metrics/top-domains
```

# Author
Nishita Agrawal
