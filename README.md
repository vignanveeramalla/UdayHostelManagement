# Uday Hostel Management System

A full-stack Hostel Management System developed using Spring Boot, MySQL, Spring Data JPA, Spring Security, JWT Authentication, REST APIs, and a responsive HTML/CSS/JavaScript frontend.

## Project Overview

Uday Hostel Management System is designed to manage hostel operations digitally through an admin management portal and a public student-facing website.

The system helps hostel management handle students, rooms, monthly hostel payments, complaints, enquiries, facilities, administrators, and dashboard statistics.

## Features

### Admin Management

- Admin registration
- Admin login
- JWT-based authentication
- Secure admin-only APIs
- Password encryption using BCrypt

### Student Management

- Add student
- View all students
- View student by ID
- Update student
- Delete student
- Search students by name
- Search students by room
- Search students by college
- Filter by college and room
- Fee filtering
- Pagination and sorting
- Room capacity validation
- Room overbooking protection

### Room Management

- Add rooms
- View all rooms
- View room by room number
- Update room
- Delete room
- Room availability checking
- Room capacity validation
- Prevent deletion of occupied rooms
- Validated hostel room numbers

### Payment Management

- Add payment
- View payments
- Update payment
- Delete payment
- Search payments by student
- Filter payments by status
- Student payment summary
- Monthly payment summary
- Monthly unpaid payment tracking
- Monthly payment history
- Expected fee calculation
- Total collection tracking
- Pending fee calculation

### Complaint Management

- Public complaint submission
- Admin complaint management
- View complaints
- Filter complaints by student
- Filter complaints by status
- Update complaint status
- Delete complaints

### Enquiry Management

- Public enquiry submission
- Admin enquiry management
- View enquiries
- Search enquiries by email
- Filter enquiries by status
- Reply to enquiries
- Enquiry status management
- Email reply functionality

### Facilities Management

- Add facilities
- View facilities
- Update facilities
- Delete facilities
- Facility availability status
- Public facilities display

### Dashboard

- Total students
- Total rooms
- Occupied rooms
- Available rooms
- Total fees
- Total paid
- Pending fees
- Total complaints
- Open complaints
- Pending enquiries
- Monthly payment overview
- Monthly expected fee
- Monthly collection
- Monthly pending amount
- Paid, partial and unpaid student counts

## Technologies Used

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- Resend Mail API
- Maven

### Database

- MySQL 8

### Frontend

- HTML5
- CSS3
- JavaScript
- REST API integration
- Responsive design

### Development Tools

- Eclipse / Spring Tools
- MySQL
- Postman
- Git
- GitHub

## Architecture

The backend follows a layered architecture:

```text
Client / Frontend
       |
       v
   Controller
       |
       v
    Service
       |
       v
   Repository
       |
       v
    Entity
       |
       v
 MySQL Database     
 ```                                                                               


## Live Application

### Public Website

https://udayboyshostel.com/

### Admin Management Portal

https://uday-boys-hostel-management.vercel.app/

### Backend API

https://udayhostelmanagement-production.up.railway.app/

## Deployment

- Frontend: Vercel
- Backend: Railway
- Database: MySQL
- Email Service: Resend API
- Source Code: GitHub

## Authentication

The application uses JWT-based authentication for administrator access.

Public users can:

- View hostel information
- View facilities
- View rooms
- Submit complaints
- Submit enquiries
- Contact the hostel

Administrators can securely manage:

- Students
- Rooms
- Payments
- Complaints
- Enquiries
- Facilities
- Dashboard statistics

## API Security

Admin management APIs are protected using Spring Security and JWT authentication.

Public endpoints are available for selected operations such as:

- Public room viewing
- Public facilities viewing
- Complaint submission
- Enquiry submission
- Administrator login


## REST API Endpoints

### Authentication

| Method | Endpoint | Access |
|---|---|---|
| POST | `/auth/login` | Public |

### Students

| Method | Endpoint | Access |
|---|---|---|
| GET | `/students` | Admin |
| GET | `/students/{studentId}` | Admin |
| POST | `/students` | Admin |
| PUT | `/students/{studentId}` | Admin |
| DELETE | `/students/{studentId}` | Admin |

### Rooms

| Method | Endpoint | Access |
|---|---|---|
| GET | `/rooms` | Public |
| GET | `/rooms/{roomNo}` | Admin |
| POST | `/rooms` | Admin |
| PUT | `/rooms/{roomNo}` | Admin |
| DELETE | `/rooms/{roomNo}` | Admin |

### Payments

| Method | Endpoint | Access |
|---|---|---|
| GET | `/payments` | Admin |
| GET | `/payments/{paymentId}` | Admin |
| POST | `/payments` | Admin |
| PUT | `/payments/{paymentId}` | Admin |
| DELETE | `/payments/{paymentId}` | Admin |
| GET | `/payments/monthly-summary` | Admin |
| GET | `/payments/monthly-unpaid` | Admin |
| GET | `/payments/monthly-history` | Admin |

### Complaints

| Method | Endpoint | Access |
|---|---|---|
| POST | `/complaints` | Public |
| GET | `/complaints` | Admin |
| PUT | `/complaints/{complaintId}` | Admin |
| DELETE | `/complaints/{complaintId}` | Admin |

### Enquiries

| Method | Endpoint | Access |
|---|---|---|
| POST | `/enquiries` | Public |
| GET | `/enquiries` | Admin |
| PUT | `/enquiries/{enquiryId}/reply` | Admin |
| DELETE | `/enquiries/{enquiryId}` | Admin |

### Facilities

| Method | Endpoint | Access |
|---|---|---|
| GET | `/facilities` | Public |
| POST | `/facilities` | Admin |
| PUT | `/facilities/{facilityId}` | Admin |
| DELETE | `/facilities/{facilityId}` | Admin |

### Dashboard

| Method | Endpoint | Access |
|---|---|---|
| GET | `/dashboard/**` | Admin |


## Project Structure

```text
UdayHostelManagement
|
+-- src
|   +-- main
|       +-- java
|       |   +-- com.udayhostel
|       |       +-- controller
|       |       +-- service
|       |       +-- repository
|       |       +-- entity
|       |       +-- security
|       |       +-- config
|       |
|       +-- resources
|           +-- application.properties
|
+-- frontend
|   +-- public
|   +-- admin
|
+-- pom.xml
+-- README.md
+-- .env.example
```