# Enunciate: Getting Started

This getting started guide will attempt to walk through a mock example of Web service development integrated with [Enunciate](http://enunciate.webcohesion.com),
without trying to hide the complexities of deployment descriptors, dependencies, configuration, and packaging. Of course, some attempt to be concise needs to
be made, but at least you'll get the idea of the full development effort. And you'll see more than just "Hello, World".

We'll be writing a mock social networking app. For this tutorial, the name of our social networking app is *IfYouWannaBeCool.com*.

* [Step 1: Write the Code](#step1)
* [Step 2: Create the Project Descriptors](#step2)
* [Step 3: Invoke Enunciate](#step3)
* [Step 4: Take a Look](#step4)
* [Step 5 (Optional): Iterate](#step5)

The source code for this application can be obtained in one of two ways:

#### Option 1: Clone the Repo

The "wannabecool" sample is available as a public repository at [`stoicflame/enunciate-sample`](https://github.com/stoicflame/enunciate-sample).

#### Option 2: `enunciate-simple-archetype`

Enunciate provides a Maven archetype to seed a simple web service project. The project uses the basic modules and includes working sample service and model
code. Here's how to invoke it:

```
mvn archetype:generate \
  -DarchetypeGroupId=com.webcohesion.enunciate.archetypes \
  -DarchetypeArtifactId=enunciate-simple-archetype \
  -DarchetypeVersion=2.2.0 \
  -DgroupId=com.ifyouwannabecool \
  -DartifactId=wannabecool
```

<a name="step1"/>

## Step 1: Write the Code

We'll start by defining the domain of our app. We'll define a [`Persona`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/domain/persona/Persona.java)
with an id, an alias, an email address, a name, and a picture. The [`Name`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/domain/persona/Name.java)
is a complex structure, made up of a given name and a surname. We'll also define a [`Link`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/domain/link/Link.java)
between two personas and a [`SocialGroup`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/domain/link/SocialGroup.java)
consisting of an id, a group leader, the group members, and whether the group is exclusive.

Next, we'll define the services available for our domain data. The [`PersonaService`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/api/PersonaService.java)
will define the operations available on a persona. This includes operations for reading a persona, storing a persona, and deleting a persona. The
[`SocialGroupService`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/api/SocialGroupService.java) will
carry the operations that deal with linking and grouping personas. These operations include creating a link between two personas, creating a social group,
adding someone to a social group, and reading the social groups of a given persona.

We also define the possible exceptions that can get thrown, including a [`PermissionDeniedException`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/api/PermissionDeniedException.java)
when trying to create a link between two people, and an [`ExclusiveGroupException`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/api/ExclusiveGroupException.java)
when trying to add a persona to an exclusive group.

After having defined our service interfaces, we create our implementation classes, [`PersonaServiceImpl`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/impl/PersonaServiceImpl.java)
and [`SocialGroupServiceImpl`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/impl/SocialGroupServiceImpl.java).

#### Apply the Metadata

In order for our code to be identified as a Web service API, we need to apply some metadata in the form of Java annotations. We'll start by defining a resource
API using [JAX-RS](https://jax-rs-spec.java.net/) annotations. Our objects will be converted to XML using [JAXB](https://jaxb.java.net/).

A JAX-RS endpoint is identified by mapping it's implementation to an URI path using the `@javax.ws.rs.Path` annotation. We'll apply the `PersonaServiceImpl`
endpoint implementation to the "/persona" path and the `SocialGroupServiceImpl` to the "/group" path.

Then, using JAX-RS, we map each method to an HTTP operation and, optionally, to a subpath. We decide to apply method-level metadata on the interface that
defines the method. (Note that using interfaces isn't strictly necessary, but it may be convenient to do so if and/or when we apply some aspects or
dependency injection.)

So for the `PersonaService`, we will use an HTTP `GET` to access the `readPersona` method and will pass in the persona id as a parameter on the path. We will
use an HTTP `POST` operation to store a new persona, and an HTTP `DELETE` method to delete a person.

Then, for the `SocialGroupService`, we will use an HTTP `GET` to access the `readGroup` method and will pass in the group id as a parameter on the path.
To store a new group, we will use the HTTP `POST` method and pass in the parameters as HTTP query parameters. To add a persona to a group, we'll use a `POST`
to the group path and pass in the persona id as a query parameter.

Because we're returning objects from a few of our REST API calls, we need to also apply the `@XmlRootElement` annotation so that JAXB will know how to write
out the root of our XML tree.

So, we've got our domain defined along with some services that operate on the domain.

<a name="#step2"/>

## Step 2: Create the Project Descriptors

Now we need to create the configuration files that describe the project. This includes build files and deployment descriptors. For this sample project,
we'll use either Maven or Ant to build a war file that can be deployed to a standard J2EE servlet container.

#### The JAX-RS Application

In order to get a running Web service API, you need to choose a JAX-RS implementation. This sample uses [Jersey](https://jersey.java.net/).
Other perfectly good JAX-RS implementations include [Resteasy](http://resteasy.jboss.org/) and [CXF](http://cxf.apache.org/).

In order to describe our application to the JAX-RS implementation, we define an implementation of `javax.ws.rs.core.Application`. The particular
implementation selected for this sample application provides an abstract class, `org.glassfish.jersey.server.ResourceConfig` that makes it easy to
define our application. To that end, we create a class called [`App`](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/java/com/ifyouwannabecool/App.java)
that tells Jersey which packages to recursively scan to find our JAX-RS resource API.

#### The Build Files

For [Maven](http://maven.apache.org/), you need a [`pom.xml` file](https://github.com/stoicflame/enunciate-sample/blob/master/pom.xml). The `packaging`
of the project will be `war` and it uses the `enunciate-maven-plugin` to integrate Enunciate into the build. For development convenience, we also use
the [`cargo-maven2-plugin`](https://codehaus-cargo.github.io/cargo/Maven2+plugin.html) to make it easy to deploy our application.

For [Ant](http://ant.apache.org/), you need a [`build.xml` file](https://github.com/stoicflame/enunciate-sample/blob/master/build.xml). For convenience, this
project uses [Maven Ant Tasks](http://maven.apache.org/ant-tasks/) to manage the project dependencies (since we already have a pom.xml file), but other
dependency management tools (e.g. [Ivy](http://ant.apache.org/ivy/)) would work just as well. (Note: the Ant build for this project only generates the
Enunciate documentation; the compile and package work is left as an exercise for the reader.)

#### The Deployment Descriptor

We create a [`web.xml` file](https://github.com/stoicflame/enunciate-sample/blob/master/src/main/webapp/WEB-INF/web.xml) that tells the J2EE
container how to deploy our application. This particular example, configures Jersey as a servlet filter so that requests for the Enunciate-generated
documentation will be forwarded to the container and resolved.


<a name="step3"/>

## Step 3: Invoke Enunciate

Once we have our source code and project descriptors, the project tree looks like the following (you can see and browse it
[here](https://github.com/stoicflame/enunciate-sample/)):

```
pom.xml
build.xml
src
 └── main
     ├── java
         └── com
             └── ifyouwannabecool
                 ├── api
                 │   ├── ExclusiveGroupException.java
                 │   ├── PermissionDeniedException.java
                 │   ├── PersonaService.java
                 │   └── SocialGroupService.java
                 ├── App.java
                 ├── domain
                 │   ├── link
                 │   │   ├── Link.java
                 │   │   └── SocialGroup.java
                 │   └── persona
                 │       ├── Name.java
                 │       └── Persona.java
                 └── impl
                     ├── PersonaServiceImpl.java
                     └── SocialGroupServiceImpl.java
```

Next, simply invoke Maven:

```
user@localhost>mvn package
```

Or Ant:

```
user@localhost>ant
```

<a name="step4"/>

## Step 4: Take a Look

If you're using Maven, the result of the invocation will be a `war` file that you can deploy to your favorite J2EE application container. Alternatively,
you can invoke Cargo:

```
user@localhost>mvn cargo:run
```

If invoking cargo, you'll see the application running at [http://localhost:8080/wannabecool].

The first thing you'll notice is a nice-looking web page with a generic title divided into two sections. The first section describes the Resource API, and it
includes the two services we just wrote. The second section is the data section describing our data types. You'll notice links the XML-Schema file for our
data types, a WADL file that describes the resource API, and a link to the [Swagger UI](http://swagger.io/) for the API.

As you continue to poke around, you'll notice that the documentation is quite sparse (although some information can be gleaned from the names of the methods
and arguments).

The "Files and Libraries" page links to client-side binaries and source code that can be used to access your API. These client-side libraries were generated
by Enunciate and packaged up (along with everything else) in the war. The client-side code is clean, intuitive, and powerful, handling all the complexities
of the API.


<a name="step5"/>

## Step 5: Iterate

Even with no extra options or decorations, Enunciate does a pretty good job of documenting your API. But there's so much more you can do with only a minimal
amount of effort! Let's give our classes some extra love, then we'll talk about what we did.

You can see and browse this source code [here](https://github.com/stoicflame/enunciate-sample/tree/step5), or checkout branch "step5" from the repository.

```
src
|
|--enunciate.xml
|--LICENSE.txt
|--com
   |
   |--ifyouwannabecool
      |
      |--api
      |  |
      |  |--ExclusiveGroupException.java
      |  |--SocialGroupService.java
      |  |--PermissionDeniedException.java
      |  |--PersonaService.java
      |  |--package-info.java
      |--domain
      |  |
      |  |--link
      |  |  |
      |  |  |--Link.java
      |  |  |--SocialGroup.java
      |  |  |--package-info.java
      |  |
      |  |--persona
      |     |
      |     |--Name.java
      |     |--Persona.java
      |     |--package-info.java
      |
      |--impl
         |
         |--SocialGroupServiceImpl.java
         |--PersonaServiceImpl.java
         |--package-info.java
```


#### Add a SOAP API

Adding a SOAP API is as simple as applying the JAX-WS `@WebService` annotation to our service interfaces and to our service implementations. (Note that per the
JAX-WS specification, the implementation must reference the interface using the `endpointInterface` method on the annotation.) We also apply the `@WebFault`
annotation to our exceptions so they'll be translated to the client correctly.

#### Removed the use of the default namespace

It is considered a best practice to namespace-qualify your domain API. We did this with the use of the package-info.java files for the link API and the
persona API. Namespace-qualifying your domain API ensures maximum compatibility as the use of the default namespace is confusing to implementation vendors.
It also provides a tool to help with versioning your API.

#### Add a splash package

You'll notice we added a `package-info.java` file to the `com.ifyouwannabecool.api` package. This is where we added the introductory (i.e. "splash")
documentation for our API. We want this documentation to show up on our index page for our documentation, so we specify this package as the splash
packge in our `enunciate.xml` configuration file. For more information, check out the [user guide](https://github.com/stoicflame/enunciate/wiki/User-Guide).

#### Documentation

We've added javadocs to everything, including the endpoints, their methods and parameters, and the data types. This documentation will show up in our generated
documentation. You're free to use HTML tags as you want; they'll be applied in the resulting HTML. Javadoc block tags are recognized, but currently there is no
support for javadoc inline tags (they'll show up unparsed in the documentation).

#### Specify a title and copyright

We can specify a title and copyright to the generated documentation in the `enunciate.xml` configuration file.

#### Add another file to download

We want to be able to add a downloadable file to the documentation. In this case, we add the license file that governs the use of the API. This extra download
is specified in the `enunciate.xml` configuration file.

#### Specify the deployment configuration

We specify our hostname ("www.ifyouwannabecool.com") and the context at which the app will be deployed ("api") in the `enunciate.xml` configuration file. There
are two advantages to this. (1) The generated WSDL will have an absolute reference to our SOAP endpoints, making the formal XML contract complete. (2) Consumers
of our client code won't have to specify the URL of our endpoints if they don't want to.

#### Specify your namespace prefixes

Again, done in the `enunciate.xml` configuration file. Why would we want to do this? For aesthetics; the default "ns0", "ns1",
etc. prefixes that are automatically assigned are kind of ugly. It also gives a nice name to our wsdl and schema files.

#### Change the package structure of the client-side classes

Done (where else?) in the `enunciate.xml` configuration file. This will distinguish the generated client classes from the original server-side classes. (Makes
it easier to test, clearer that you're dealing with client-side classes, etc.)

And there are still a ton of other configuration options available to you as the API developer (which we won't go into here). These include options to
specify your own CSS for the documentation, or even your own [freemarker template](http://freemarker.org/) if you don't like the structure, etc.

When we're done polishing things up, we run our build again and deploy the war file as we did in steps 3 and 4.

Take a look now at the generated documentation and you'll notice all the new enhancements!

## Learn More

* [Enunciate Documentation](https://github.com/stoicflame/enunciate/wiki)
* [Enunciate-Specific Annotations](https://github.com/stoicflame/enunciate/wiki/Enunciate-Specific-Annotations)
* [Issue Tracker](https://github.com/stoicflame/enunciate/issues/)
* [JAXB](https://jaxb.java.net/)
* [JAX-RS](https://jax-rs-spec.java.net/)
* [JAX-WS](https://jax-ws.java.net/)
