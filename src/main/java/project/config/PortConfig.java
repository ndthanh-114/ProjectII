//package project.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.web.ServerProperties.Undertow;
//import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
//import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class PortConfig {
//
//    @Value("${server.http.port}")
//    private int httpPort;
//
//    @Bean
//    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
//        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
//        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
//
//            @Override
//            public void customize(Builder builder) {
//                builder.addHttpListener(8080, "0.0.0.0");
//            }
//
//        });
//        return factory;
//    }
//}