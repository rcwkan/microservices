# Comparison between Openliberty, SpringBoot and Quarkus 

## Background

Evaluate JAVA application frameworks: SpringBoot, Quarkus and Openliberty.
 



## Enviroment 
- IDE: Eclipse
- JDK: OpenJDK 21, GraalVM 21
- Maven
- Architecture: arm, x86
- OS: Windows (x86), Ubuntu(x86), MacOS (arm)
- AWS Lambda & DynamoDB



## Use Case : Getting Start and Features
|  |  SpringBoot |  Quarkus |  Openliberty |
| ------------ | ------------ | ------------ | ------------ | 
| Getting Start Tutorials|   Easy  |   Easy  |    Very Easy (if run in provied Lab)|
| Configuration|   Easy to use | $${\color{blue}Robust}$$ |  A bit confusing |
|  JTA | $${\color{blue}More than JEE (e.g. readonly)}$$  | JEE Standard |  JEE Standard|
|  JPA |  JPA, Hibernate and Mybatis  |   JPA, Hibernate and Mybatis  |  JPA and Hibernate  |
|  Security | Very fine grant and complex control with AuthenticationManager and SecurityFilterChain   |  Easy to configure |  Easy to configure  |
|  Authentication (JWT) |  Need coding like JwtUtiliy  |  Maven Confiruation | Server.xml confguration   |
|  Hot reload | Need addational Configuration per IDE | Out of the box in dev mode |  Out of the box in dev mode|
|  Deployment | Runnable Jar  | Very Easy  |  Very Easy |
| Third partiy Library Integration| Configuration using maven, xml and annotation (@Bean) | Configuration using maven and annotation (@Produces)|  Configuration in server.xml and annotation (@Produces)|
|  Documentation |    |    |    |
|  Community  | Large and Very active   | Medium, very active and growing  |  Small and look like a enterprise framework  |
 


 
### SpringBoot  
#### What I like:
- Integration to other librarties
- Flexiablility 
- Large and active community


#### What I don't like:
- The way to restart the app while development (enable spring-boot-devtools auto refresh may result to unnamed module of loader org.springframework.boot.devtools.restart.classloader.RestartClassLoader)


### Quarkus
#### What I like:
- Devlopment mode supports hot reload 
- Startup Performance 
- The way to load properties, System > Enviroment > config files > default value. https://quarkus.io/guides/config-reference 


#### What I don't like:
 

 
#### Openliberty
#### What I like:
- Devlopment mode supports hot reload 
- Guides with online Lab environment provided. 

#### What I don't like:
- Flexiablility
- Guides use Java EE frontend (JSF) as client. 
- Configuration file is a bit confusing e.g. microprofile-config.properties, bootstrap.properties, persistence.xml, server.xml. And files may be in different location.


## Use Case : Local development with AWS DynamoDB & Lambda
 



