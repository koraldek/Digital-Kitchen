package pl.krasnowski.DigitalKitchen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.Locale;

@Configuration
//@EnableCaching
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    @SessionScope
    User user() {
        return new User();
    }

    @Bean
    Kitchen guestDummyKitchen() {
        Kitchen dummyKitchen = new Kitchen();
        dummyKitchen.setName("dummy guest Kitchen name");
        return dummyKitchen;
    }

    @Bean
    Kitchen loggedDummyKitchen() {
        Kitchen dummyKitchen = new Kitchen();
        dummyKitchen.setName("Logged user Kitchen name");
        return dummyKitchen;
    }


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }


//    /*
//    CACHE BEANS
//     */
//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheCacheManager() {
//        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
//        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        cmfb.setShared(true);
//
//        return cmfb;
//    }
}