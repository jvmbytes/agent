## jvmbytes Inst Demo

```bash
git clone http://github.com/jvmbytes/agent.git

cd agent && mvn clean package

java -javaagent:inst-agent/target/inst-agent-1.0.0.jar=feature \
   -jar inst-demo/target/inst-demo-1.0.0.jar

jvmbytes inst agent loaded
inst:sun.instrument.InstrumentationImpl@7440e464
feature:feature
.
```