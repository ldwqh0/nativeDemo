#

I'm use Spring boot 3.4.1 on graalvm 21.0.5

This is a simple WebApplication includes to classes

* The first class is LoginEntity

```java
package xzcode.nativedemo.request;

import java.io.Serial;
import java.io.Serializable;

public record LoginEntity(
        String acctID,
        String username,
        String password,
        String lcid
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 8219088952080888490L;
}

```

* the 2nd class is Application class

```java
package xzcode.nativedemo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import xzcode.nativedemo.request.LoginEntity;

import java.net.URI;

@SpringBootApplication
public class NativeDemoApplication implements ApplicationRunner {

    private final RestClient.Builder clientBuilder;

    public NativeDemoApplication(RestClient.Builder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    public static void main(String[] args) {
        SpringApplication.run(NativeDemoApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LoginEntity loginEntity = new LoginEntity(
                "65003cf500b386",
                "kingdee",
                "666666",
                "2052"
        );

        ResponseEntity<String> response = clientBuilder.build()
                .post()
                .uri(new URI("https://www.google.com"))
                .body(loginEntity)
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.getBody());
    }
}
```

and this is my build.gradle.kts

```
plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.4"
}

group = "com.xzcode"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
```

when I run this application with normal java application . it is run success.

but when i Build it to a native application use gradlew nativeCompile, and run the exe file on windows.
the error reported

```
org.springframework.web.client.RestClientException: No HttpMessageConverter for xzcode.nativedemo.request.LoginEntity
        at org.springframework.web.client.DefaultRestClient$DefaultRequestBodyUriSpec.writeWithMessageConverters(DefaultRestClient.java:504)
        at org.springframework.web.client.DefaultRestClient$DefaultRequestBodyUriSpec.lambda$body$0(DefaultRestClient.java:455)
        at org.springframework.web.client.DefaultRestClient$DefaultRequestBodyUriSpec.exchangeInternal(DefaultRestClient.java:563)
        at org.springframework.web.client.DefaultRestClient$DefaultRequestBodyUriSpec.exchange(DefaultRestClient.java:532)
        at org.springframework.web.client.RestClient$RequestHeadersSpec.exchange(RestClient.java:677)
        at org.springframework.web.client.DefaultRestClient$DefaultResponseSpec.executeAndExtract(DefaultRestClient.java:806)
        at org.springframework.web.client.DefaultRestClient$DefaultResponseSpec.toEntityInternal(DefaultRestClient.java:766)
        at org.springframework.web.client.DefaultRestClient$DefaultResponseSpec.toEntity(DefaultRestClient.java:755)
        at xzcode.nativedemo.NativeDemoApplication.run(NativeDemoApplication.java:40)
        at org.springframework.boot.SpringApplication.lambda$callRunner$4(SpringApplication.java:784)
        at org.springframework.util.function.ThrowingConsumer$1.acceptWithException(ThrowingConsumer.java:82)
        at org.springframework.util.function.ThrowingConsumer.accept(ThrowingConsumer.java:60)
        at org.springframework.util.function.ThrowingConsumer$1.accept(ThrowingConsumer.java:86)
        at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:796)
        at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:784)
        at org.springframework.boot.SpringApplication.lambda$callRunners$3(SpringApplication.java:772)
        at java.base@21.0.5/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        at java.base@21.0.5/java.util.stream.SortedOps$SizedRefSortingSink.end(SortedOps.java:357)
        at java.base@21.0.5/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
        at java.base@21.0.5/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        at java.base@21.0.5/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
        at java.base@21.0.5/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
        at java.base@21.0.5/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        at java.base@21.0.5/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
        at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:772)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:325)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1361)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1350)
        at xzcode.nativedemo.NativeDemoApplication.main(NativeDemoApplication.java:23)
        at java.base@21.0.5/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
```

the project repository is https://github.com/ldwqh0/nativeDemo
