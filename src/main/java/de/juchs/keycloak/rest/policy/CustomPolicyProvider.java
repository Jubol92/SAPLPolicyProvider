package de.juchs.keycloak.rest.policy;

import org.jboss.logging.Logger;
import org.keycloak.authorization.policy.evaluation.Evaluation;
import org.keycloak.authorization.policy.provider.PolicyProvider;

import java.util.Map;

public class CustomPolicyProvider implements PolicyProvider {
    private static final Logger logger = Logger.getLogger(CustomPolicyProvider.class);
    @Override
    public void evaluate(Evaluation evaluation) {

        // "Send" the JSON Data to the PDP. Thats the authorization subscription.
        PDP server = new PDP();
        logger.info(server.getLoadedScripts().size() + " SAPL scripts loaded");
        for (Map.Entry<String, String>  entry : server.getLoadedScripts().entrySet()){
            logger.info(entry.getKey() + " " + entry.getValue());
        }

        // Emulation of the send subscription data
        boolean isAuthorized = server.sendToPDP("{\"subject\":\"WILLI\",\"action\":\"read\",\"resource\":\"something\"}");

        /*
           Use the decision in Keycloak. Grant is a positive logic, that means SAPL permit = grant
           and SAPL deny = deny.
        */
        if (isAuthorized) {
            evaluation.grant();
        } else {
            evaluation.deny();
        }
    }

    @Override
    public void close() { }
}
