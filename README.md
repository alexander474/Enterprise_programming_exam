# Enterprise Programming Exam

## PG5100 2019

[![Build Status](https://travis-ci.com/alexander474/Enterprise_programming_exercise.svg?token=Jcye5ttDhAMRpUM3Ca28&branch=master)](https://travis-ci.com/alexander474/Enterprise_programming_exercise)

All requirements have been completed and some extra functionality has been added. When I used inspiration or copied code, I specified the source in the current file or above the methods. All the pom.xml files that were created with inspiration from the [Course Repository](https://github.com/arcuri82/testing_security_development_enterprise_systems)

[Exam text](/PG5100exam.pdf)

## Run Application

Run "LocalApplicationRunner" and it will run on [localhost:8080](http://localhost:8080)

- Administrator
  - Email: admin@admin.com
  - Password: a
- User
  - Email: foo@bar.com
  - Password: a



## Run Tests

```bash
> mvn clean install
```

If the dependencies have been downloaded then you can just run:

```bash
> mvn verify
```



## Different choices i made in the exam

The exam states that the ratings are some kind of "stars", but I chose not to display visual stars as that would just be design work but rather use the data type Integer/int to represent stars where the valid values would be from 1-5 as stated in the exam. When the average is displaying the value i'm using Double with no limit on the decimals because this is not stated in the exam.

I got the movie information from imdb.com Top 250. I just picked some of the first items displayed.



## Extras

#### Administrator

I have added an administrator option to the user. The administrator has access to enable/disable user accounts and create new items. This is the tests for the administrator extra:

```java
public void testDisableAndEnableUser()
```

```java
public void testCreateItem()
```



## Coverage

Frontend: 91%

Backend: 97%

Total: 95%

