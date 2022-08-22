package br.com.alura.forum.config.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {
	
	@SuppressWarnings("deprecation")
	@Bean
	public Docket forumApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
	        .paths(PathSelectors.ant("/**"))
	        .build()
	        .ignoredParameterTypes(Usuario.class)
	        .globalRequestParameters(buildParameters()
                    );
	    
	}
	
	public List<RequestParameter> buildParameters(){
		List<RequestParameter> listRequestParamters = new ArrayList<RequestParameter>();
		RequestParameter requestParamterToken = new RequestParameterBuilder()
	            .name("Authorization")
	            .description("Header para token JWT")
	            .required(false)
	            .query(q -> q.model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
	            .in(ParameterType.HEADER)
	            .build();
		
		listRequestParamters.add(requestParamterToken );
		
		return listRequestParamters;
	}
}
