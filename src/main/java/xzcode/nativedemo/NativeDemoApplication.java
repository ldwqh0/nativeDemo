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
                .uri(new URI("http://wb2023.gnway.cc/k3cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc"))
                .body(loginEntity)
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.getBody());
    }
}
