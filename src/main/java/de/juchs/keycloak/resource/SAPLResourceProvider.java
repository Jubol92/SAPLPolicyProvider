package de.juchs.keycloak.resource;

import jakarta.ws.rs.core.Response;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.services.resource.RealmResourceProvider;
import org.jboss.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Provider
@RequiredArgsConstructor
public class SAPLResourceProvider implements RealmResourceProvider {
    private static final Logger log = Logger.getLogger(SAPLResourceProvider.class);
    private final KeycloakSession session;

    @POST
    @Path("decision")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response decideToSAPLResponse(AuthorizationRequest request){
        Map<String, List<String>> claims = request.getClaims();

        if(claims != null && claims.containsKey("subject") && claims.containsKey("action") && claims.containsKey("resource")){
            List<String> subjects   = claims.get("subject");
            List<String> actions    = claims.get("action");
            List<String> resources  = claims.get("resources");

            // For now just take the first value. Demo purposes
            String subject  = subjects.get(0);
            String action   = actions.get(0);
            String resource = resources.get(0);

            boolean isValid = evaluateSAPLPolicies(subject, action, resource);
            String decision = isValid ? "permit" : "deny";

            return Response.ok().entity("{\"decision\":  \"" + decision + "\"}").build();
        }
        else{
            return Response.status((Response.Status.BAD_REQUEST)).entity("The required claims were not found").build();
        }
    }

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public jakarta.ws.rs.core.Response hello(){
        return Response.ok(Map.of("hello", session.getContext().getRealm().getName())).build();
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {}

    private boolean evaluateSAPLPolicies(String subject, String action, String resource) {
        return true; // Not important for now
    }
}
