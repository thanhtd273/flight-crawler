package com.thanhtd.flight.crawler.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;

@Configuration
public class ScyllaConfig {

    @Value("${spring.data.cassandra.contact-points}")
    private String CONTACT_POINTS;

    @Value("${spring.data.cassandra.port}")
    private Integer PORT;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String KEYSPACE;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String DC;

    @Value("${spring.data.cassandra.username}")
    private String USER;

    @Value("${spring.data.cassandra.password}")
    private String PASSWORD;

    @Bean
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(CONTACT_POINTS);
        session.setKeyspaceName(KEYSPACE);
        session.setLocalDatacenter(DC);
        session.setUsername(USER);
        session.setPassword(PASSWORD);
        session.setPort(PORT);

        return session;
    }
    @Bean
    public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {

        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
        sessionFactory.setSession(session);
        sessionFactory.setConverter(converter);
        sessionFactory.setSchemaAction(SchemaAction.NONE);

        return sessionFactory;
    }

    @Bean
    public CassandraMappingContext mappingContext() {
        return new CassandraMappingContext();
    }

    @Bean
    public CassandraConverter converter(CqlSession cqlSession, CassandraMappingContext mappingContext) {
        MappingCassandraConverter cassandraConverter = new MappingCassandraConverter(mappingContext);
        cassandraConverter.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));

        return cassandraConverter;
    }

    @Bean
    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);
    }
}
