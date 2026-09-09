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
- Spring Boot Mail
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