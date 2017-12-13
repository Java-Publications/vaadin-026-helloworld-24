
<center>
<a href="https://vaadin.com">
 <img src="https://vaadin.com/images/hero-reindeer.svg" width="200" height="200" /></a>
</center>

# Backend meets Frontend: getting started with Vaadin and Speedment
Here we will bootstrap the infrastructure for the persistence layer and the ORM speedment. 


## How to start
To play with this you only have to start the main - method from the class  ```org.rapidpm.microservice.Main``` and 
go with your browser to the address: [http://localhost:7080/](http://localhost:7080/)

Ok, you got your first Vaadin "Hello World".

If you have any questions
 -> please ping me at Twitter [https://twitter.com/SvenRuppert](https://twitter.com/SvenRuppert)


## some of the used libs
* [https://github.com/Dynamic-Dependency-Injection/dynamic-cdi](https://github.com/Dynamic-Dependency-Injection/dynamic-cdi)
* [https://github.com/JavaMicroService/rapidpm-microservice](https://github.com/JavaMicroService/rapidpm-microservice)
* [https://github.com/functional-reactive/functional-reactive-lib](https://github.com/functional-reactive/functional-reactive-lib)

## more to read and see
* german article [http://bit.ly/jaxenter-kolumne-vaadin](http://bit.ly/jaxenter-kolumne-vaadin)
* engl. article : not translated until now, **[ping me](https://twitter.com/SvenRuppert)** if you need it


## Screencast (german)
<!--
[![Watch the video](https://img.youtube.com/vi/5UDyR-zhv0Y/0.jpg)](https://www.youtube.com/embed/5UDyR-zhv0Y?rel=0 "Watch the video")
-->
not created until now, **[ping me](https://twitter.com/SvenRuppert)** if you need it

>The complete playlist of german screencasts you can find here: 
>[http://bit.ly/vaadin-video-list-ger](http://bit.ly/vaadin-video-list-ger)

## Screencast (english)
not translated until now, **[ping me](https://twitter.com/SvenRuppert)** if you need it




## a few comments:

### Postgres via Docker 
docker run -it --rm --name postgres -p 5432:5432 postgres:latest 
user:   postgres 
passwd: postgres

### Flayway MAven Targets
mvn flyway:migrate

switch to oracle for JavaFX
mvn speedment:tool

after creating the json file with the tool
mvn speedment:clear
mvn speedment:reload
mvn speedment:generate
