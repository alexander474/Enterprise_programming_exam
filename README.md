# Enterprise Programming Exam

## PG5100 2019

[![Build Status](https://travis-ci.com/alexander474/Enterprise_programming_exercise.svg?token=Jcye5ttDhAMRpUM3Ca28&branch=master)](https://travis-ci.com/alexander474/Enterprise_programming_exercise)

All requirements is completed and some extra functionalities has been added.

### How to run

Run "LocalApplicationRunner" and it will run on localhost:8080

- Administrator
  - Email: admin@admin.com
  - Password: a
- User
  - Email: foo@bar.com
  - Password: a

### Testing

```
mvn clean install

if dependencies is installed then you could run:
mvn verify
```



## Extras

- Administrator
  - Can enable/disable users in the application
  - Can create new items
  - Testing:
    - testDisableAndEnableUser()
    - testCreateItem()
- 



#### Coverage

Frontend: 91%

Backend: 97%

Total: 95%

