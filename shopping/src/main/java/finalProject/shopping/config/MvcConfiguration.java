package finalProject.shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/additem").setViewName("additem");
        registry.addViewController("/remitem").setViewName("remitem");
        registry.addViewController("/pay").setViewName("pay");
        registry.addViewController("/choosepay").setViewName("choosepay");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/paymentcomplete").setViewName("paymentcomplete");
    }

}
