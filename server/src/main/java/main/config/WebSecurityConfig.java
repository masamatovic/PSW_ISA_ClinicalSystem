package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import main.security.RestAuthenticationEntryPoint;
import main.security.TokenAuthenticationFilter;
import main.security.TokenUtils;
import main.service.CustomUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    // Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.

    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.

    @Bean

    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

  // Neautorizovani pristup zastcenim resursima

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

    // Definisemo nacin autentifikacije

    @Autowired

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Autowired
    TokenUtils tokenUtils;
    
    @Override

	protected void configure(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()



				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()



				// svim korisnicima dopusti da pristupe putanjama /auth/**, /h2-console/** i

				// /api/foo 

				.authorizeRequests().antMatchers("/auth/**").permitAll()



				.anyRequest().authenticated().and()



				.cors().and()



				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),

						BasicAuthenticationFilter.class);



		http.csrf().disable();

	}



	// Generalna bezbednost aplikacije

	@Override

	public void configure(WebSecurity web) throws Exception {

		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanjess

		//web.ignoring().antMatchers(HttpMethod.POST, "/auth/**");

		//web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");

		//web.ignoring().antMatchers(HttpMethod.POST, "/auth/register");



		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",

				"/**/*.css", "/**/*.js");

	}
}