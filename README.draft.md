# Comparison between Openliberty, SpringBoot and Quarkus 

## Background

Evaluate JAVA application frameworks: SpringBoot, Quarkus and Openliberty.

Notes: I've worked with Spring frameworks since v2



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
|  REST API | Very Easy  | Very Easy  |  Very Easy |
|  JTA | $${\color{blue}More than JEE (e.g. readonly)}$$  | JEE Standard |  JEE Standard|
|  JPA |  JPA, Hibernate and Mybatis  |   JPA, Hibernate and Mybatis  |  JPA and Hibernate  |
|  Security | Very fine grant and complex control with AuthenticationManager and SecurityFilterChain   |  Easy to configure |  Easy to configure  |
|  Authentication (JWT) |  Need coding like JwtUtiliy  |  Maven Confiruation | Server.xml confguration   |
|  Hot reload | Need addational Configuration per IDE | Out of the box in dev mode |  Out of the box in dev mode|
|  Deployment | Runnable Jar  | Very Easy  |  Very Easy |
| Third partiy Library Integration| Configuration using maven, xml and annotation (@Bean) | Configuration using maven and annotation (@Produces)|  Configuration in server.xml and annotation (@Produces)|
|  Documentation |    |    |    |
|  Community  | Large and Very active   | Medium, very active and growing  |  Small and look like a enterprise framework  |
|  Google Search AI summarization  |    |    |   |


 
### SpringBoot  
#### What I like:
- Integration to other librarties
- Flexiablility 
- Large and active community


#### What I don't like:
- The way to restart the app while development


### Quarkus
#### What I like:
- Devlopment mode supports hot reload 
- Startup Performance 
- The way to load properties, System > Enviroment > config files > default value. https://quarkus.io/guides/config-reference 


#### What I don't like:
- Local AWS Lambda container using x86 architecture



#### Openliberty
#### What I like:
- Devlopment mode supports hot reload 
- Guides with online lib environment provided. 

#### What I don't like:
- Flexiablility
- Guides use Java EE frontend (JSF) as client. 
- Configuration file is a bit confusing e.g. microprofile-config.properties, bootstrap.properties, persistence.xml, server.xml. And files may be in different location.


## Use Case : Local development with AWS DynamoDB

## Use Case : Getting Start and Features
|  |  SpringBoot |  Quarkus |  Openliberty |
| ------------ | ------------ | ------------ | ------------ |
| AWS Integration DynamoDB |  |     |     |
| AWS Integration Lambda | AWS Library Support   |  Quarkus Nativey support |  No Related Documentation found  | 
| AWS Integration Lambda Deployment| Container  |   Both zip and container |  No Related Documentation found  |


 
### SpringBoot  
#### What I like:
- AWS provides Libraries for Integration
 
#### What I don't like:
- Upgrade AWS library may found a bit difficult if used thrid partiy (non spring) lib
 


### Quarkus
#### What I like: 
- Natively support AWS Lambda development and deployment

#### What I don't like:
- Local AWS Lambda container default using x86 architecture (That's why I need set up Ubuntu(x86) to test)




#### Openliberty
#### What I like:
- Support Eclipse JNoSQL which is very easy to integrate and can use DynamoDB as both Key-Value and Document DB
 
#### What I don't like:
- Not offically support Lambda 
 



