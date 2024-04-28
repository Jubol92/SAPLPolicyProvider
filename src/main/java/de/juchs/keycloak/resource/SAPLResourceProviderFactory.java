package de.juchs.keycloak.resource;


import com.google.auto.service.AutoService;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;
import javax.ws.rs.ext.Provider;

@AutoService(SAPLResourceProviderFactory.class)
@Provider
public class SAPLResourceProviderFactory implements RealmResourceProviderFactory {

    // Implements a sub path under an existing realm e.g. /realms/<My Realm>/sapl-rest
    public static final String PROVIDER_ID = "sapl-rest";
    @Override
    public RealmResourceProvider create(KeycloakSession keycloakSession) {
        return new SAPLResourceProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
