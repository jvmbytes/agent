**jvmbytes inst agent** allows you to config once and use **Instrumentation** for multiple times and in multiple libraries.

**jvmbytes inst agent** export the **Instrumentation** instance to multiple bytes libraries，
which can be included in application classpath and loaded by **AppClassloader**, 
so that it's not need to update java command config, 
and which is always hard to change in production environment. 

## usage

1. add `inst-loader` dependency in pom.xml:
```xml
<dependency>
    <groupId>com.jvmbytes.agent</groupId>
    <artifactId>inst-loader</artifactId>
    <version>1.0.1</version>
</dependency>
```

2. load **Instrumentation** instance through `com.jvmbytes.agent.inst.InstLoader.loadInst()`.

3. download inst-agent-1.0.1.jar
```shell script
wget https://search.maven.org/remotecontent?filepath=com/jvmbytes/agent/inst-agent/1.0.1/inst-agent-1.0.1.jar
```

4. add `-javaagent:inst-agent-1.0.1.jar` for java process
```shell script
java -javaagent:inst-agent-1.0.1.jar -jar app.jar
```

> see [demo](inst-demo/README.md)
