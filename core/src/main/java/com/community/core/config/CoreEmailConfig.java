package com.community.core.config;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.MessageCreator;
import org.broadleafcommerce.common.email.service.message.NullMessageCreator;
import org.broadleafcommerce.presentation.thymeleaf3.BroadleafThymeleaf3TemplateEngine;
import org.broadleafcommerce.presentation.thymeleaf3.email.BroadleafThymeleaf3MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Properties;

/**
 * Shared email configuration
 *
 * @author Phillip Verheyden (phillipuniverse)
 */
@Configuration
public class CoreEmailConfig {

    /**
     * A dummy mail sender has been set to send emails for testing purposes only
     * To view the emails sent use "DevNull SMTP" (download separately) with the following setting:
     * Port: 30000
     */
    @Bean
    public JavaMailSender blMailSender() throws Exception {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.office365.com");
        sender.setPort(587);
        sender.setProtocol("smtp");
        sender.setUsername("first.cool@hotmail.com");
        sender.setPassword("(1st.Cool)");
        //
        Properties javaMailProps = new Properties();
        javaMailProps.setProperty("mail.smtp.starttls.enable", "true");
        javaMailProps.setProperty("mail.smtp.timeout", "25000");
        javaMailProps.setProperty("mail.smtp.auth", "true");
        javaMailProps.setProperty("mail.smtp.ssl.enable", "true");
        javaMailProps.setProperty("mail.debug", "true");
        sender.setJavaMailProperties(javaMailProps);
        return sender;
    }

    /**
     * Uncomment this bean to send real emails
     */
    @Bean
    @Autowired
    public MessageCreator blMessageCreator(@Qualifier("blEmailTemplateEngine") TemplateEngine tlTemplateEngine, @Qualifier("blMailSender") JavaMailSender mailSender) {
        return new BroadleafThymeleaf3MessageCreator(tlTemplateEngine, mailSender);
    }

    @Bean
    public BroadleafThymeleaf3TemplateEngine blEmailTemplateEngine(@Qualifier("blEmailTemplateResolver") ClassLoaderTemplateResolver templateResolvers) {
        BroadleafThymeleaf3TemplateEngine blEmailTemplateEngine = new BroadleafThymeleaf3TemplateEngine();
        blEmailTemplateEngine.setTemplateResolver(templateResolvers);
        return blEmailTemplateEngine;
    }

    @Bean
    public ClassLoaderTemplateResolver blEmailTemplateResolver() {
        ClassLoaderTemplateResolver blEmailTemplateResolver = new ClassLoaderTemplateResolver();
        blEmailTemplateResolver.setPrefix("emailTemplates/");
        blEmailTemplateResolver.setSuffix(".html");
        blEmailTemplateResolver.setTemplateMode("HTML5");
        blEmailTemplateResolver.setCacheable(false);
        blEmailTemplateResolver.setCharacterEncoding("UTF-8");
        return blEmailTemplateResolver;
    }

    @Bean
    public EmailInfo blEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setFromAddress("support@mycompany.com");
        info.setSendAsyncPriority("2");
        info.setSendEmailReliableAsync("false");
        return info;
    }

    @Bean
    public EmailInfo blRegistrationEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setSubject("You have successfully registered!");
        info.setEmailTemplate("register-email");
        return info;
    }

    @Bean
    public EmailInfo blForgotPasswordEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setSubject("Reset password request");
        info.setEmailTemplate("resetPassword-email");
        return info;
    }

    @Bean
    public EmailInfo blOrderConfirmationEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setSubject("Your order with The Heat Clinic");
        info.setEmailTemplate("orderConfirmation-email");
        return info;
    }

    @Bean
    public EmailInfo blFulfillmentOrderTrackingEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setSubject("Your order with The Heat Clinic Has Shipped");
        info.setEmailTemplate("fulfillmentOrderTracking-email");
        return info;
    }

    @Bean
    public EmailInfo blReturnAuthorizationEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setSubject("Your return with The Heat Clinic");
        info.setEmailTemplate("returnAuthorization-email");
        return info;
    }

    @Bean
    public EmailInfo blReturnConfirmationEmailInfo() {
        EmailInfo info = createUniqueEmailInfo();
        info.setSubject("Your return with The Heat Clinic");
        info.setEmailTemplate("returnConfirmation-email");
        return info;
    }

    protected EmailInfo createUniqueEmailInfo() {
        EmailInfo info = new EmailInfo();
        info.setFromAddress("first.cool@hotmail.com");
        info.setSendAsyncPriority("2");
        info.setSendEmailReliableAsync("false");
        return info;
    }
}
