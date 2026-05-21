# Favorites API Documentation

## Overview

The Favorites API allows users to mark educational content as favorites, manage their favorite list, and check if specific content is favorited.

## Base URL

```
/favorites
```

## Endpoints

### 1. Add Favorite

Add a content to user's favorites.

**Endpoint:** `POST /favorites`

**Request Body:**
```json
{
  "userId": 1,
  "contentId": 2
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "userId": 1,
  "contentId": 2,
  "content": {
    "id": 2,
    "name": "Angular Avançado",
    "description": "Domine técnicas avançadas de Angular incluindo RxJS, Signals e arquitetura",
    "imageUrl": "https://angular.io/assets/images/logos/angular/angular.svg"
  },
  "createdAt": "2026-05-21T13:30:00"
}
```

**Error Responses:**
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Content not found
- `409 Conflict` - Content already favorited by user

---

### 2. Get User Favorites

List all favorites of a specific user with content details.

**Endpoint:** `GET /favorites/user/{userId}`

**Path Parameters:**
- `userId` (Long) - User ID

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "userId": 1,
    "contentId": 2,
    "content": {
      "id": 2,
      "name": "Angular Avançado",
      "description": "Domine técnicas avançadas de Angular incluindo RxJS, Signals e arquitetura",
      "imageUrl": "https://angular.io/assets/images/logos/angular/angular.svg"
    },
    "createdAt": "2026-05-21T13:30:00"
  },
  {
    "id": 2,
    "userId": 1,
    "contentId": 5,
    "content": {
      "id": 5,
      "name": "Git e GitHub Essencial",
      "description": "Controle de versão profissional com Git e colaboração no GitHub",
      "imageUrl": "https://git-scm.com/images/logos/downloads/Git-Icon-1788C.png"
    },
    "createdAt": "2026-05-21T14:15:00"
  }
]
```

---

### 3. Check if Favorited

Check if a user has favorited a specific content.

**Endpoint:** `GET /favorites/user/{userId}/content/{contentId}`

**Path Parameters:**
- `userId` (Long) - User ID
- `contentId` (Long) - Content ID

**Success Response (200 OK):**
```json
{
  "isFavorited": true
}
```

---

### 4. Count User Favorites

Get the total number of favorites for a user.

**Endpoint:** `GET /favorites/user/{userId}/count`

**Path Parameters:**
- `userId` (Long) - User ID

**Success Response (200 OK):**
```json
{
  "count": 5
}
```

---

### 5. Get Favorite by ID

Get a specific favorite by its ID.

**Endpoint:** `GET /favorites/{id}`

**Path Parameters:**
- `id` (Long) - Favorite ID

**Success Response (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "contentId": 2,
  "content": {
    "id": 2,
    "name": "Angular Avançado",
    "description": "Domine técnicas avançadas de Angular incluindo RxJS, Signals e arquitetura",
    "imageUrl": "https://angular.io/assets/images/logos/angular/angular.svg"
  },
  "createdAt": "2026-05-21T13:30:00"
}
```

**Error Response:**
- `404 Not Found` - Favorite not found

---

### 6. Remove Favorite by ID

Remove a favorite by its ID.

**Endpoint:** `DELETE /favorites/{id}`

**Path Parameters:**
- `id` (Long) - Favorite ID

**Success Response (204 No Content)**

**Error Response:**
- `404 Not Found` - Favorite not found

---

### 7. Remove Favorite by User and Content

Remove a specific favorite of a user.

**Endpoint:** `DELETE /favorites/user/{userId}/content/{contentId}`

**Path Parameters:**
- `userId` (Long) - User ID
- `contentId` (Long) - Content ID

**Success Response (204 No Content)**

**Error Response:**
- `404 Not Found` - Favorite not found

---

## Error Response Format

All error responses follow this format:

```json
{
  "timestamp": "2026-05-21T13:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Favorito com ID 999 não encontrado"
}
```

## Usage Examples

### cURL Examples

**Add a favorite:**
```bash
curl -X POST http://localhost:8080/favorites \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "contentId": 2}'
```

**Get user favorites:**
```bash
curl http://localhost:8080/favorites/user/1
```

**Check if favorited:**
```bash
curl http://localhost:8080/favorites/user/1/content/2
```

**Remove favorite:**
```bash
curl -X DELETE http://localhost:8080/favorites/1
```

### JavaScript/Fetch Examples

**Add a favorite:**
```javascript
const response = await fetch('http://localhost:8080/favorites', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    userId: 1,
    contentId: 2
  })
});

const favorite = await response.json();
console.log(favorite);
```

**Get user favorites:**
```javascript
const response = await fetch('http://localhost:8080/favorites/user/1');
const favorites = await response.json();
console.log(favorites);
```

**Check if favorited:**
```javascript
const response = await fetch('http://localhost:8080/favorites/user/1/content/2');
const { isFavorited } = await response.json();
console.log('Is favorited:', isFavorited);
```

## Business Rules

1. **Unique Favorites**: A user cannot favorite the same content twice. Attempting to do so will return a 409 Conflict error.

2. **Content Validation**: The content must exist before it can be favorited. If the content doesn't exist, a 404 Not Found error is returned.

3. **Automatic Timestamps**: The `createdAt` timestamp is automatically set when a favorite is created.

4. **Cascade Information**: When retrieving favorites, the complete content information is included in the response.

## OpenAPI/Swagger Documentation

The API is fully documented with OpenAPI 3.0 annotations. Access the interactive documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Architecture

The Favorites feature follows Clean Architecture principles:

- **Domain Layer**: `Favorite` entity, `FavoriteService`, custom exceptions
- **Adapter Layer**: 
  - Input: `FavoriteController`, DTOs, `FavoriteMapper`
  - Output: `FavoriteRepository` (in-memory storage)
- **Exception Handling**: Global exception handler for consistent error responses

## Testing

The implementation includes comprehensive unit tests for:
- Service layer business logic
- Controller endpoints
- Mapper conversions
- Repository operations

Run tests with:
```bash
mvn test
```

---

**Made with Bob** 🤖