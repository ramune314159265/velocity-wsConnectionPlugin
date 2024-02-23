package ramune314159265.wsconnectionpluginvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "wsconnectionpluginvelocity",
        name = "WsConnectionPluginVelocity",
        version = BuildConstants.VERSION,
        description = "EventDataToWsConnection for Velocity",
        authors = {"Ramune"}
)
public class WsConnectionPluginVelocity {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
