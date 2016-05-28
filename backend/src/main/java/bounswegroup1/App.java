package bounswegroup1;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.OptionalContainerFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.client.JerseyClientBuilder;

import org.skife.jdbi.v2.DBI;

import bounswegroup1.resource.FingerprintResource;

import bounswegroup1.db.FingerprintDAO;

import bounswegroup1.model.Fingerprint;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.ws.rs.client.Client;

/**
 * Hello world!
 *
 */
public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "We are what we eat";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));

        bootstrap.addBundle(new MigrationsBundle<AppConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfig config) {
                return config.getDatabase();
            }
        });
    }

    @Override
    public void run(AppConfig config, Environment env) {
        configureCors(env);

        // Connect to db
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, config.getDatabase(), "postgresql");

        jdbi.registerContainerFactory(new OptionalContainerFactory());

        // create dao's and clients
        final FingerprintDAO fingerprintDAO = jdbi.onDemand(FingerprintDAO.class);

        final Client httpClient = new JerseyClientBuilder(env).using(config.getHttpClient())
                .build("httpClient");

        // create resources
        final FingerprintResource fingerprintResource = new FingerprintResource(fingerprintDAO);
        
        // register resources
        
        env.jersey().register(MultiPartFeature.class);

        env.jersey().register(fingerprintResource);

    }

    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
                "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

}
