package br.com.poaapi.contract.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("be.com.poaapi.contract"))
                .build().apiInfo(info());
    }

    private ApiInfo info(){
        return new ApiInfoBuilder()
                .title("Poa Mobilidade API")
                .description("Based on open data - DataPOA")
                .version("1.0.0")
                .contact(new Contact(
                        "Laís Monteiro", "https://github.com/mlaesh",
                        "laismont@outlook.com")).build();
    }

}
