package ramune314159265.wsconnectionpluginvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ListenerBoundEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import ramune314159265.wsconnectionpluginvelocity.events.ServerStartedEvent;
import ramune314159265.wsconnectionpluginvelocity.events.ServerStoppedEvent;

@Plugin(
		id = "wsconnectionpluginvelocity",
		name = "WsConnectionPluginVelocity",
		version = BuildConstants.VERSION,
		description = "EventDataToWsConnection for Velocity",
		authors = {"Ramune"}
)
public class WsConnectionPluginVelocity {

	public static ProxyServer server;
	public static Logger logger;
	public static WsConnection wsConnection;

	@Inject
	public WsConnectionPluginVelocity(ProxyServer server, Logger logger) {
		WsConnectionPluginVelocity.logger = logger;
		WsConnectionPluginVelocity.server = server;
	}

	@Subscribe
	public void onProxyInitialized(ProxyInitializeEvent event) {
		logger.info("connecting...");

		WsConnectionPluginVelocity.wsConnection = new WsConnection();
		WsConnectionPluginVelocity.wsConnection.init();

		server.getEventManager().register(this, new PluginListener());
	}

    @Subscribe
    public void onListenerBounded(ListenerBoundEvent event){
        WsConnectionPluginVelocity.wsConnection.sendEventData(new ServerStartedEvent());
    }

	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event) {
        WsConnectionPluginVelocity.wsConnection.sendEventData(new ServerStoppedEvent());

		logger.info("disconnecting...");
		WsConnectionPluginVelocity.wsConnection.disconnect();
	}
}
