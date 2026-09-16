# Hotel Management System

A Java desktop application for managing hotel rooms and staff, built with Swing for the GUI. Developed as a coursework project to practice object-oriented design, file I/O, and input validation.

## Features
- Add and manage hotel managers with validated credentials (password complexity rules, confirmation matching)
- Add rooms (Standard, Double, King, or Connected) tied to a responsible manager
- Bulk upload managers or rooms from a text file, with per-record error reporting for invalid data
- Generate a statistics report: managers sorted alphabetically with their assigned rooms listed underneath, and all rooms sorted by price

## Built With
- Java (Swing for the GUI)
- Object-oriented design: inheritance (`Room` → `ConnectedRoom`), interfaces (`Comparable`), custom checked exceptions
- File I/O for bulk data import/export

## What I Learned
- Handling malformed input gracefully instead of letting bad data crash the program
- Structuring validation logic so errors are reported clearly rather than failing silently
- Designing a small multi-class system where objects reference each other (rooms linked to managers by ID)

## How to Run
1. Clone the repo
2. Open the `hotelsystem` folder as a project in NetBeans (or compile manually with `javac`)
3. Run `HotelSystemWindow.java`

## Sample Data
`r.txt` contains sample room data for testing the bulk upload feature, including some intentionally invalid rows to test error handling.
