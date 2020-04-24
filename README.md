# Enunciate: Sample Web Application

Welcome!

Here's a sample web application you can use to see how to apply Enunciate to a simple multi-module project.

This project provides examples of a REST endpoint (see `PersonsEndpoint`) that provides both XML and JSON endpoints.
It also provides a SOAP service (see `PersonService`).

### Clone

```bash
git clone https://github.com/stoicflame/enunciate-sample.git
```

### Build

```bash
mvn install
```

### Run

```bash
mvn -f enunciate-sample-webapp/pom.xml cargo:run
```

### See

[http://localhost:8080/enunciate-sample-webapp/](http://localhost:8080/enunciate-sample-webapp/)

## Learn More

* [Enunciate Documentation](https://github.com/stoicflame/enunciate/wiki)
* [Enunciate-Specific Annotations](https://github.com/stoicflame/enunciate/wiki/Enunciate-Specific-Annotations)
* [Issue Tracker](https://github.com/stoicflame/enunciate/issues/)
* [JAXB](https://jaxb.java.net/)
* [JAX-RS](https://jax-rs-spec.java.net/)
* [JAX-WS](https://jax-ws.java.net/)
