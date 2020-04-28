package nehe.tenantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableDiscoveryClient
@EnableFeignClients("nehe.tenantservice")
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableSwagger2
public class TenantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantServiceApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration()
	{
		return  new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/**"))
				.apis(RequestHandlerSelectors.basePackage("nehe.tenantservice"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Tenant Service",
				"Documentation for tenant service",
				"1.0",
				"https://notermsofservice.com",
				new springfox.documentation.service.Contact("Nehemiah","www.nowebsite.com","kamolunehemiah@gmail.com"),
				"No license",
				"https://nolicense.com",
				Collections.emptyList());
	}

}
