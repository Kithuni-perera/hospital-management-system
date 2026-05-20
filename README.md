Readme · MD
# 🏥 Hospital Management System (HMS)
 
> A full-stack, production-grade hospital administration platform that digitises and unifies every core function of a healthcare facility — from patient registration to discharge billing — under a single, secure, intelligent system.
 
---
 
## 📋 Table of Contents
 
- [Overview](#overview)
- [Problem Statement](#problem-statement)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Role & Permissions](#roles--permissions)
- [Testing](#testing)
- [Performance Highlights](#performance-highlights)
- [Project Learnings](#project-learnings)
- [Future Roadmap](#future-roadmap)
---
 
## Overview
 
The HMS is a comprehensive digital platform built to solve real administrative and clinical pain points in healthcare environments. It brings together **patients, doctors, nurses, pharmacists, lab technicians, and administrators** into one cohesive, real-time system.
 
This is not a CRUD demo. It demonstrates concurrent request handling, a complex role-permission system, real-time data pipelines, performance caching, automated CI/CD, containerised deployment, and 85%+ test coverage.
 
**Measured outcomes from a pilot deployment:**
- ⏱ Appointment scheduling time: **~12 min → under 2 min**
- 💰 Billing errors for digitised transactions: **reduced to zero**
- 📊 Daily admin reporting time: **2 hours → instant automated dashboard**
---
 
## Problem Statement
 
Most mid-sized hospitals — especially in developing regions — operate on paper records, disconnected spreadsheets, and legacy software that cannot communicate across departments. This results in:
 
- Patient data scattered and inaccessible at the point of care
- Manual scheduling causing double-bookings and missed follow-ups
- Pharmacy and lab departments operating in isolation via paper slips
- Manual billing introducing errors and opaque pricing
- No real-time visibility for administrators on beds, staff, or revenue
**The HMS was built to eliminate every one of these failures.**
 
---
 
## Features
 
### 🗓 Intelligent Appointment Scheduling
- Conflict detection and double-booking prevention via database-level row locking
- Automated SMS/email reminders 24 hours before appointments
- Online rescheduling and cancellation with automatic waitlist management
### 📁 Electronic Medical Records (EMR)
- Chronological, structured patient records accessible to authorised clinicians in real time
- Covers allergies, diagnoses, medications, surgical history, lab results, and clinical notes
- Full version control with timestamps and editor identity on every change
### 🔬 Integrated Lab & Pharmacy Workflow
- Doctor-ordered lab tests appear instantly on the lab technician's dashboard
- Results are uploaded digitally and permanently linked to the patient record
- Prescriptions flow directly to pharmacy — no paper, no transcription errors
### 📊 Real-Time Admin Dashboard
- Live metrics: bed occupancy by ward, appointment counts, daily revenue vs. target
- Low pharmacy stock alerts and pending discharge summaries
- Data refreshes every 60 seconds; critical alerts via Server-Sent Events (SSE)
### 🧾 Automated Billing Engine
- Auto-generated itemised invoices at discharge (consultation + lab + pharmacy + ward)
- PDF invoice export and insurance claim generation
- Supports multiple payment modes
### 🔐 Multi-Role Authentication & Access Control
- 7 distinct roles: Admin, Doctor, Nurse, Pharmacist, Lab Technician, Receptionist, Patient
- 50+ granular permissions managed via a database permissions matrix
- JWT-based stateless sessions with bcrypt password hashing
---
 
## Tech Stack
 
### Frontend
| Technology | Purpose |
|---|---|
|
| Tailwind CSS | Utility-first styling for rapid, consistent UI development |
|HTTP client with JWT interceptors |
| Chart.js / Recharts | Data visualisation for admin dashboards |
| React Hook Form | Performant form handling with validation |
 
### Backend
| Technology | Purpose |
|---|---|
| RESTful API server with non-blocking I/O |
| JWT | Stateless multi-role authentication |
| bcrypt.js | Password hashing with salt rounds |
| Multer | File upload handling for lab documents |
| Nodemailer | Automated email notifications |
| Express Validator | Server-side input validation and sanitisation |
 
### Database & Caching
| Technology | Purpose |
|---|---|
| MySQL | Primary relational database (ACID-compliant) |
| Sequelize ORM | Model definitions, migrations, and query building |
| Redis | In-memory caching for frequently accessed data |

## Roles & Permissions
 
| Role | Key Capabilities |
|---|---|
| **Admin** | Full system access, staff management, reports, billing oversight |
| **Doctor** | View/write EMR, order labs, write prescriptions, view own schedule |
| **Nurse** | Ward assignments, vitals logging, medication schedules |
| **Pharmacist** | Receive prescriptions, manage inventory, record dispensing |
| **Lab Technician** | Receive test orders, upload results, track status |
| **Receptionist** | Patient registration, appointment booking, front-desk billing |
| **Patient** | View own records, book appointments, view invoices |

# Project Learnings
 
### Technical
- **Database design at scale**: Poor schema decisions become impossible bottlenecks later. Normalisation, indexing strategy, and relationship design were critical from day one.
- **Security as a foundation**: Role-based access, AES-256 encryption, audit trails, and TLS were built in from the start — not retrofitted.
- **Caching impact**: Redis dropped dashboard load time from 1.8s to 340ms.
- **Containerisation**: Docker Compose eliminated environment inconsistencies entirely, making CI/CD reliable from the first run.
### Domain
- Healthcare workflows are far more complex than they appear. Multiple hospital visits and stakeholder interviews were needed before the data model was correct.
- Usability for non-technical users is a hard engineering problem — a nurse should not need a manual to navigate the system. This required iteration, not just good intentions.

## Future Roadmap
 
- [ ] Mobile application (React Native) using existing REST API
- [ ] Telemedicine / video consultation module
- [ ] AI-assisted diagnosis suggestion from symptom input
- [ ] Multi-branch / hospital network support
- [ ] HL7 FHIR compliance for interoperability with other health systems
- [ ] Advanced analytics and predictive reporting dashboard
