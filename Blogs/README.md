# Blogging Platform API

RESTful API for a personal blogging platform with basic CRUD operations.

**Project page:** [https://roadmap.sh/projects/blogging-platform-api](https://roadmap.sh/projects/blogging-platform-api)

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 (in-memory database)

## Requirements

- Java 17+
- Maven (or use the included `mvnw` wrapper)

## How to Run

```bash
cd Blogs
./mvnw spring-boot:run
```

The API starts at: `http://localhost:8080`

H2 console (optional): `http://localhost:8080/h2-console`  
- JDBC URL: `jdbc:h2:mem:testdb`  
- Username: `sa`  
- Password: `shasha`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/posts` | Create a blog post |
| `GET` | `/posts` | Get all blog posts |
| `GET` | `/posts?term=tech` | Search posts by title, content, or category |
| `GET` | `/posts/{id}` | Get a single blog post |
| `PUT` | `/posts/{id}` | Update a blog post |
| `DELETE` | `/posts/{id}` | Delete a blog post |

### Create / Update body

```json
{
  "title": "My First Blog Post",
  "content": "This is the content of my first blog post.",
  "category": "Technology",
  "tags": ["Tech", "Programming"]
}
```

### Example requests

```bash
# Create
curl -X POST http://localhost:8080/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"My First Blog Post","content":"This is the content.","category":"Technology","tags":["Tech","Programming"]}'

# Get all
curl http://localhost:8080/posts

# Search
curl "http://localhost:8080/posts?term=tech"

# Get one (replace {id} with a real UUID)
curl http://localhost:8080/posts/{id}

# Update
curl -X PUT http://localhost:8080/posts/{id} \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Title","content":"Updated content.","category":"Technology","tags":["Tech"]}'

# Delete
curl -X DELETE http://localhost:8080/posts/{id}
```
