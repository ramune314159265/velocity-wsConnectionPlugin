package ramune314159265.wsconnectionpluginvelocity;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;
import ramune314159265.wsconnectionpluginvelocity.events.ServerStartedEvent;
import ramune314159265.wsconnectionpluginvelocity.events.ServerStoppedEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

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
	public static Path configFolder;
	public static WsConnection wsConnection;
	public static boolean isOpeningWs;
	public static HashMap<String, String> playerConnectingServerMap;
	public static HashSet<Number> receivedTimestamps;

	public static String wsUrl;
	public static String serverId;

	public static void dataReceived(ReceivedData data){
		if(WsConnectionPluginVelocity.receivedTimestamps.contains(data.timestamp)){
			return;
		}
		if(Objects.equals(data.type, "send_chat")){
			server.sendMessage(MiniMessage.miniMessage().deserialize(data.content));
		}
		WsConnectionPluginVelocity.receivedTimestamps.add(data.timestamp);
	}
	@Inject
	public WsConnectionPluginVelocity(ProxyServer server, Logger logger, @DataDirectory Path configFolder) {
		WsConnectionPluginVelocity.logger = logger;
		WsConnectionPluginVelocity.server = server;
		WsConnectionPluginVelocity.configFolder = configFolder;
		WsConnectionPluginVelocity.playerConnectingServerMap = new HashMap<>();
		WsConnectionPluginVelocity.receivedTimestamps = new HashSet<>();
		WsConnectionPluginVelocity.isOpeningWs = false;

		this.loadConf();
	}

	public static void reconnectWs(){
		WsConnectionPluginVelocity.isOpeningWs = false;
		WsConnectionPluginVelocity.wsConnection.disconnect();
		WsConnectionPluginVelocity.wsConnection = new WsConnection();
		WsConnectionPluginVelocity.wsConnection.init(WsConnectionPluginVelocity.wsUrl);
		WsConnectionPluginVelocity.isOpeningWs = true;
	}

	public void loadConf() {
		File folder = configFolder.toFile();
		File configFile = new File(folder, "conf.toml");
		if (!configFile.getParentFile().exists()) {
			configFile.getParentFile().mkdirs();
		}

		if (!configFile.exists()) {
			try (InputStream input = WsConnectionPluginVelocity.class.getResourceAsStream("/" + configFile.getName())) {
				if (input != null) {
					Files.copy(input, configFile.toPath());
				} else {
					configFile.createNewFile();
				}
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}
		Toml configToml = new Toml().read(configFile);

		WsConnectionPluginVelocity.wsUrl = configToml.getString("wsUrl");
		WsConnectionPluginVelocity.serverId = configToml.getString("serverId");
	}

	@Subscribe
	public void onProxyInitialized(ProxyInitializeEvent event) {
		logger.info("wsに接続中...");
		WsConnectionPluginVelocity.isOpeningWs = true;
		WsConnectionPluginVelocity.wsConnection = new WsConnection();
		WsConnectionPluginVelocity.wsConnection.init(WsConnectionPluginVelocity.wsUrl);

		server.getEventManager().register(this, new PluginListener());

		WsConnectionPluginVelocity.wsConnection.sendEventData(new ServerStartedEvent());
	}

	@Subscribe
	public void onProxyReloaded(ProxyReloadEvent event) {
		this.loadConf();
		logger.info("wsに再接続中...");
		this.reconnectWs();
	}

	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event) {
		WsConnectionPluginVelocity.wsConnection.sendEventData(new ServerStoppedEvent());

		logger.info("wsを切断中...");
		WsConnectionPluginVelocity.isOpeningWs = false;
		WsConnectionPluginVelocity.wsConnection.disconnect();
	}
}
