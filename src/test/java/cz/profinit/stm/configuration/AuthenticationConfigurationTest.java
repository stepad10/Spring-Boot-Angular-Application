/*
 * AuthenticationConfigurationTest
 *
 * 0.1
 *
 * Author: J. Jansky
 */
package cz.profinit.stm.configuration;

import cz.profinit.stm.oauth.PrincipalExtractorImpl;
import cz.profinit.stm.repository.user.UserRepository;
import cz.profinit.stm.service.user.AuthenticationFacade;
import cz.profinit.stm.service.user.AuthenticationFacadeImpl;
import cz.profinit.stm.service.user.UserDetailsServiceImpl;
import cz.profinit.stm.service.user.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


/**
 * Configuration bringing classes necessary for Authentication.
 */
@Configuration
@Profile("authentication")
@Import(PasswordEncoderConfiguration.class)
public class AuthenticationConfigurationTest {

    /**
     * Initialize Authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(ApplicationContext context, UserRepository userRepository) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        UserDetailsService userDetailsService = new UserDetailsServiceImpl(userRepository);
        UserDetails userDetails = new UserDetailsImpl();
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    @Bean
    AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacadeImpl();
    }

    @Bean
    PrincipalExtractorImpl principalExtractor() {
        return new PrincipalExtractorImpl();
    }

}
