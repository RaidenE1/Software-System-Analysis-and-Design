package com.example.demo.config;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.*;

@EnableOpenApi
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30).pathMapping("/")
				.enable(true)
				.apiInfo(apiInfo())
				.host("8001")
				.select()
				.apis(RequestHandlerSelectors.any())
				.build()
				.protocols(newHashSet("https", "http"))
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}


	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("demo" + " Api Doc")
				.build();
	}


	private List<SecurityScheme> securitySchemes() {
		ApiKey apiKey = new ApiKey("BASE_TOKEN", "token", In.HEADER.toValue());
		return Collections.singletonList(apiKey);
	}


	private List<SecurityContext> securityContexts() {
		return Collections.singletonList(
				SecurityContext.builder()
						.securityReferences(Collections.singletonList(new SecurityReference("BASE_TOKEN", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
						.build()
		);
	}

	@SafeVarargs
	private final <T> Set<T> newHashSet(T... ts) {
		if (ts.length > 0) {
			return new LinkedHashSet<>(Arrays.asList(ts));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		try {
			Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
			List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
			if (registrations != null) {
				for (InterceptorRegistration interceptorRegistration : registrations) {
					interceptorRegistration
							.excludePathPatterns("/swagger**/**")
							.excludePathPatterns("/webjars/**")
							.excludePathPatterns("/v3/**")
							.excludePathPatterns("/doc.html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
