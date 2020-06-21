https://devcolibri.com/%D0%BA%D0%B0%D0%BA-%D1%81%D0%B4%D0%B5%D0%BB%D0%B0%D1%82%D1%8C-%D1%81%D0%B2%D0%BE%D0%B9-maven-repository-%D0%BD%D0%B0-github/

Abstract generic реализация для Spring Boot CRUD сервисов, контроллеров
реализован mapper для конвертирования объектов

Подключение библиотеки:

<repositories>
    <repository>
        <id>[name-project]-mvn-repo</id>
        <url>https://raw.github.com/NNikolay/crud-service/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>by.nhorushko</groupId>
    <artifactId>crud-abstract-generic</artifactId>
    <version>{version}</version>
     <exclusions>
                    <exclusion>
                        <groupId>org.springframework.data</groupId>
                        <artifactId>spring-data-jpa</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-web</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>javax.servlet</groupId>
                        <artifactId>servlet-api</artifactId>
                    </exclusion>
                </exclusions>
</dependency>

