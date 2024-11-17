## aviateur22/lalamiam-v2-backend
## Techno

JAVA 17

## Build du projet

### Variable d'environment attentdu pour le build du project

#### Base de données
> DATABASE_URL
 
> DATABASE_USER

> DATABASE_PASSWORD

#### JWT
> JWT_SECRET

#### Captcha
>CAPTCHA_SECRET

#### Mail 
>MAILER_ACCOUNT

>MAILER_PASSWORD

### Clean Architecture

 ### CORE Module

- Dependencies of:
  - Junit-Jupiter: 5.9
  - Mockito: 5.3

###  INFRA module

- Dependencies of:
    - SpringBoot: 3.1
    - SringBootSecurity: 3.1
    - SrpingBootAnnotation: 3.1
    - Spring data jpa: 3.1
    - Hibernate: 6.0
    - PostgreSQL: 42.6
    - Junit-Jupiter: 5.9
    - Mockito: 5.3
    - java-jw: 4.4.0
    - passay: 1.6.4