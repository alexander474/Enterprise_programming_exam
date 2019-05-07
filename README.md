# Enterprise Programming Exam

## PG5100 2019

[![Build Status](https://travis-ci.com/alexander474/Enterprise_programming_exercise.svg?token=Jcye5ttDhAMRpUM3Ca28&branch=master)](https://travis-ci.com/alexander474/Enterprise_programming_exercise)

All requirements is completed and some extra functionalities has been added. When i used inspiration or copied code i specified the source in the current file or above methods. All the pom.xml files that is created is created with inspiration from the [Course Repository](https://github.com/arcuri82/testing_security_development_enterprise_systems)

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

If the dependencies is downloaded the you could just run:

```bash
> mvn verify
```



## Different choices i did take in the exam 

As the text states that the ratings is "stars" i did not choose to display visual stars as that would just be design work but rather use the data type Integer/int to represent stars where the valid values would be from 1-5 as stated in the exam. When the average is displaying the value i'm using Double with no limit on the decimals because this is not stated in the exam.

I did also got item information (information about movies) from imdb.com top 250. I did just pick 20 items that is displayed by default. 



## Extras

#### Administrator

I have added administrator option to the user. The administrator gets access to enable/disable user accounts and create new items. This is tested: 

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

