FROM java

WORKDIR ./app
COPY ./target/rankr-0.0.1-SNAPSHOT.jar ./app
COPY ./target/rankr-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
EXPOSE 5432
CMD echo lol
