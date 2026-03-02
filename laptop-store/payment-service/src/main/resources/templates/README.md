# ${artifactId}

${description}

## Getting Started

#### Prerequisites

* Java SE Development Kit 11+ - https://www.oracle.com/java/technologies/downloads/
* Apache Maven 3.8.1+ as package manager - https://maven.apache.org/install.html

#### Dependencies

```xml

<dependency>
    <groupId>${project.parent.groupId}</groupId>
    <artifactId>${project.parent.artifactId}</artifactId>
    <version>${project.parent.version}</version>
</dependency>
```

### Installation

Go to the project directory

```shell
    cd ${artifactId}
```

Build the project.

```shell
    mvn clean package
```

### Run Locally

This service can be run as a Spring Boot application via Maven.
Go to the service directory

```shell
    cd ${artifactId}
```

Run the service via Spring Boot Maven plugin

```shell
    mvn spring-boot:run
```

## Contributing

Please see [CONTRIBUTING.md](CONTRIBUTING.md)
