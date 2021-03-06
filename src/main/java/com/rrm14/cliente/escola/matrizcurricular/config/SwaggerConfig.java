package com.rrm14.cliente.escola.matrizcurricular.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	public static final String CURSO             = "Curso";
	public static final String CURSO_DESCRICAO   = "Operações Referentes a Manipulação da Entidade Curso";
	public static final String MATERIA           = "Matéria";
	public static final String MATERIA_DESCRICAO = "Operações Referentes a Manipulação da Entidade Matéria";
	
	@Bean
	public Docket matrizCurricularApi() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.rrm14.cliente.escola.matrizcurricular")).build()
				.apiInfo(this.metaData())
				.tags(new Tag(CURSO, CURSO_DESCRICAO ), new Tag(MATERIA, MATERIA_DESCRICAO ));
	}
	

	@Bean
	public LinkDiscoverers discoverers() {
		List<LinkDiscoverer> listPlugins = new ArrayList<>();
		listPlugins.add(new CollectionJsonLinkDiscoverer());
		return new LinkDiscoverers(SimplePluginRegistry.create(listPlugins));
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title(Constantes.SWAGGER_TITULO)
								   .description(Constantes.SWAGGER_DESCRICAO)
								   .version(Constantes.SWAGGER_VERSAO)
								   .license("")
								   .build();
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(Constantes.SWAGGER_MAIN_RESOURCE)
		.addResourceLocations(Constantes.SWAGGER_MAIN_CLASSPATH);
		
		registry.addResourceHandler(Constantes.SWAGGER_WEBJARS_RESOURCE)
		.addResourceLocations(Constantes.SWAGGER_WEBJARS_CLASSPATH);
	}
	

}
