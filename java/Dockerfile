FROM azul/zulu-openjdk:17

WORKDIR /app

COPY . /app

RUN javac Main.java

EXPOSE 8080

ENTRYPOINT ["java", "Main.java"]