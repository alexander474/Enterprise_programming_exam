# Enterprise Programming Exam

## PG5100 2019

[![Build Status](https://travis-ci.com/alexander474/Enterprise_programming_exercise.svg?token=Jcye5ttDhAMRpUM3Ca28&branch=master)](https://travis-ci.com/alexander474/Enterprise_programming_exercise)

All requirements is completed and some extra functionalities has been added. When i used inspiration or copied code i specified the source in the current file or above methods. All the pom.xml files that is created is created with inspiration from the course repository

[Course Repository]: https://github.com/arcuri82/testing_security_development_enterprise_systems	"Course Repository"



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

#### Administrator

I have added administrator option to the user. The administrator gets access to enable/disable user accounts and create new items. This is tested by testDisableAndEnableUser() and testCreateItem().



#### Coverage

Frontend: 91%

Backend: 97%

Total: 95%

