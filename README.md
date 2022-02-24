# CHALLENGE BACKEND - Java - Spring Boot (API) 🚀

### Objetivo

Desarrollar una API para explorar el mundo de Disney, la cual permitirá conocer y modificar los personajes que lo componen y entender en qué películas participaron. Por otro lado, deberá exponer la información para que cualquier frontend pueda consumirla. 
- 👉 Utilizar Spring Boot
- 👉 No es necesario armar el Frontend
- 👉 Las rutas deberán seguir el patrón REST
- 👉 Utilizar la librería Spring Security

## DATABASE
### CHARACTER
- image
- name
- age
- weight
- history
- movies

### MOVIE
- image
- title
- creation_date
- qualification (1-5)
- characters

### GENDER
- name
- image
- movies

## SECURITY
El usuario solo podrá realizar peticiones GET sin necesidad de registrarse ni logearse. En caso de realizar otro tipo de petición se le pedirá registrarse y logearse, obteniendo un token, el cual le permitirá realizar otro tipo de peticiones.

### POST (register)
	http://localhost:8080/auth/register

Ejemplo:

    {        
        "name": "Nestor Hasin",
        "username": "username",
        "email": "nestorhasin@gmail.com",
        "password": "password"
    }

### POST (login)
	http://localhost:8080/auth/login

Ejemplo:
    
    {        
        "usernameOrEmail": "username",
        "password": "password"
    }

## API REST

### **CHARACTER**

### GET (Read)
	http://localhost:8080/characters

### GET (Read by id)
	http://localhost:8080/characters/{id}

### GET (Read by name)
    http://localhost:8080/characters?name={name}

### GET (Read by age)
    http://localhost:8080/characters?age={age}

### GET (Read by weight)
    http://localhost:8080/characters?weight={weight}

### GET (Read by movie)
    http://localhost:8080/characters?movie={id}

### POST (Create)
	http://localhost:8080/characters

Ejemplo:

    {
    "image": "image",
    "name": "name",
    "age": 10,
    "weight": 100.00,
    "history": "history"    
    }
    
### POST (Link with movie)
    http://localhost:8080/characters/{idCharacter}/movie/{idMovie}

### POST (Add movie to character)
    http://localhost:8080/characters/{id}
    
Ejemplo:

    {
        "image": "image",
        "title": "title",
	"creationDate": "2022-02-02",
	"qualification": 5
    }

### PUT (Update)
	http://localhost:8080/characters

    {
    "id": 8
    "image": "image",
    "name": "name",
    "age": 10,
    "weight": 100.00,
    "history": "history"    
    }

### DELETE (Delete by id)
	http://localhost:8080/characters/{id}

### **MOVIE**

### GET (Read)
    http://localhost:8080/movies

### GET (Read by id)
    http://localhost:8080/movies/{id}
    
### GET (Read by name)
    	http://localhost:8080/movies?name={name}

### GET (Read by gender)
    http://localhost:8080/movies?gender={gender}

### GET (Read in ascending or descending order according to creation date)
    http://localhost:8080/movies?order={ASC|DESC}

### POST (Create)
	http://localhost:8080/movies

Ejemplo:

    {    
    "image": "image",
    "title": "title",    
    "qualification": 5    
    }

### POST (Link with character)
    http://localhost:8080/movies/{idMovie}/character/{idCharacter}

### POST (Add character to movie)
    http://localhost:8080/movies/{id}/addCharacter
    
Ejemplo:

    {
        "image": "image",
        "name": "name",
        "age": 10,
        "weight": 100.00,
        "history": "history"
    }

### POST (Link with gender)
    http://localhost:8080/movies/{idMovie}/gender/{idGender}

### POST (Add character to movie)
    http://localhost:8080/movies/{id}/addGender
    
Ejemplo:

    {
        "name": "name",
	"image": "image"
    }

### PUT (Update)
	http://localhost:8080/movies
	
Ejemplo:

    {
    "id": 1,
    "image": "image",
    "title": "title",    
    "qualification": 5    
    }

### DELETE (Delete by id)
	http://localhost:8080/movies/{id}    

### **GENDER**

### POST (Create)
	http://localhost:8080/genders

Ejemplo:

    {
        "image": "image",
        "name": "name"
    }
    
### POST (Link with movie)
    http://localhost:8080/genders/{idGender}/movie/{idMovie}

### POST (Add movie to gender)
    http://localhost:8080/genders/{id}
    
Ejemplo:

    {
        "image": "image",
        "title": "title",
	"creationDate": "2022-02-02",
	"qualification": 5
    }
        
### GET (Read)
    http://localhost:8080/genders
    
### GET (Read by id)
    http://localhost:8080/genders/{id}

### PUT (Update)
    http://localhost:8080/genders

### DELETE (Delete by id)
    http://localhost:8080/genders/{id}

## E-MAIL
Para el envío de e-mail se utilizó el Servicio [SendGrid](https://app.sendgrid.com/ "SendGrid") mediante el cual, cuando un usuario se registra (http://localhost:8080/auth/register) se le envía un e-mail con un mensaje de bienvenida. El e-mail es enviado desde mi cuenta personal registrada en el sitio indicado mediante una API KEY que valida los envios. Dejo a continuación dos servicios a modo de test:

### GET (Send mail in plain text)
    http://localhost:8080/email/plain/{email}
    
### GET (Send mail in html text)
    http://localhost:8080/email/html/{email}

## DOCUMENTATION
Se documenta el proyecto mediante las siguiente herramientas:

### Postman

Check src/main/resources/postman_collection.json

### Swagger

Check http://localhost:8080/open-api/swagger-ui-custom.html y http://localhost:8080/open-api/api-docs

## TESTS
Se realizaron utilizando JUnit y Mockito bajo el contexto de Spring.
