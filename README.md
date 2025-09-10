# CUET Today - University Community Platform

A Spring Boot web application for the CUET (Chittagong University of Engineering and Technology) community, allowing students, faculty, and alumni to connect, share posts, and access resources.

## Features

### User Management
- **Three User Types**: Students, Faculty, and Alumni
- **User Registration & Login**: Secure authentication system
- **User Profiles**: Different profile fields for each user type
  - Students: Name, Email, Batch
  - Faculty: Name, Email, Research Area
  - Alumni: Name, Email, Research Area, Current Workplace, Description

### Community Posts
- Create, read, update, and delete posts
- Text-based posts with title and description
- User authentication required for post management
- Users can only edit/delete their own posts

### Resource Library
- Three categories: Academic, Higher Study, Others
- Share resources with title, description, and URL
- Filter resources by category
- User authentication required for resource management
- Users can only edit/delete their own resources

## Technology Stack

- **Backend**: Spring Boot 3.1.0
- **Database**: MySQL with JDBC
- **Frontend**: HTML, CSS, JavaScript (No frameworks)
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- MySQL Server
- IntelliJ IDEA Ultimate (recommended) or any Java IDE
- Maven (usually included with IDE)

## Setup Instructions

### 1. Database Setup

1. Install and start MySQL Server
2. Create a database:
   ```sql
   CREATE DATABASE cuet_today;
   ```
3. Run the schema script located at `src/main/resources/schema.sql`

### 2. Application Configuration

1. Open `src/main/resources/application.properties`
2. Update the database configuration:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cuet_today
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

### 3. Running the Application

1. Open the project in IntelliJ IDEA Ultimate
2. Make sure Java 17 is configured as the project SDK
3. Run the main class: `CuetTodayApplication.java`
4. The application will start on `http://localhost:8080`

### 4. Alternative: Command Line

```bash
# Navigate to project directory
cd cuet-today

# Run with Maven
mvn spring-boot:run
```

## Project Structure

```
src/
├── main/
│   ├── java/com/cuet/today/
│   │   ├── controller/          # REST Controllers
│   │   ├── dao/                 # Data Access Objects
│   │   ├── model/               # Entity classes
│   │   ├── service/             # Business logic
│   │   └── CuetTodayApplication.java
│   └── resources/
│       ├── static/
│       │   ├── css/style.css    # Styling
│       │   └── js/script.js     # JavaScript functionality
│       ├── templates/           # HTML templates
│       ├── application.properties
│       └── schema.sql           # Database schema
└── pom.xml                      # Maven dependencies
```

## Database Schema

### Tables
- `users` - Base user information
- `students` - Student-specific data
- `faculty` - Faculty-specific data  
- `alumni` - Alumni-specific data
- `posts` - Community posts
- `resources` - Resource library items

## API Endpoints

### User Management
- `POST /api/users/register/student` - Register student
- `POST /api/users/register/faculty` - Register faculty
- `POST /api/users/register/alumni` - Register alumni
- `POST /api/users/login` - User login
- `POST /api/users/logout` - User logout
- `GET /api/users/current` - Get current user

### Posts
- `GET /api/posts/all` - Get all posts
- `POST /api/posts/create` - Create new post
- `GET /api/posts/{id}` - Get post by ID
- `PUT /api/posts/update/{id}` - Update post
- `DELETE /api/posts/delete/{id}` - Delete post

### Resources
- `GET /api/resources/all` - Get all resources
- `GET /api/resources/category/{category}` - Get resources by category
- `POST /api/resources/create` - Create new resource
- `GET /api/resources/{id}` - Get resource by ID
- `PUT /api/resources/update/{id}` - Update resource
- `DELETE /api/resources/delete/{id}` - Delete resource

## Usage

1. **Home Page**: View platform overview and statistics
2. **Registration**: Choose user type and fill required information
3. **Login**: Access with email and password
4. **Posts Section**: 
   - View all community posts
   - Create new posts (requires login)
   - Edit/delete your own posts
5. **Resources Section**:
   - Browse resources by category
   - Add new resources (requires login)
   - Edit/delete your own resources

## Security Features

- Session-based authentication
- User authorization for CRUD operations
- Users can only modify their own content
- Input validation and sanitization

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is developed for educational purposes as part of the CUET community platform.

## Support

For issues and questions, please contact the development team or create an issue in the repository.