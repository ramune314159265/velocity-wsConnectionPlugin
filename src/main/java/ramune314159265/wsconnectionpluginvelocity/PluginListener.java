package ramune314159265.wsconnectionpluginvelocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import ramune314159265.wsconnectionpluginvelocity.events.*;

import java.util.Objects;

public class PluginListener {
	@Subscribe()
	public void onServerPostConnectEvent(ServerPreConnectEvent event) {
		String joinedServerId = event.getOriginalServer().getServerInfo().getName();
		String playerId = event.getPlayer().getUsername();

		WsConnectionPluginVelocity.playerConnectingServerMap.put(playerId, joinedServerId);

		Event eventDataToSend;
		if (Objects.isNull(event.getPreviousServer())) {
			eventDataToSend = new PlayerConnectedEvent(joinedServerId, playerId);
		} else {
			String previousJoinedServerId = event.getPreviousServer().getServerInfo().getName();
			eventDataToSend = new PlayerMovedEvent(joinedServerId, previousJoinedServerId, playerId);
		}

		WsConnectionPluginVelocity.wsConnection.sendEventData(eventDataToSend);
	}

	@Subscribe
	public void onDisconnectEvent(DisconnectEvent event) {
		String playerId = event.getPlayer().getUsername();
		String previousJoinedServerId = WsConnectionPluginVelocity.playerConnectingServerMap.get(playerId);
		WsConnectionPluginVelocity.wsConnection.sendEventData(new PlayerDisconnectedEvent(playerId, previousJoinedServerId));
	}

	@Subscribe
	public void onPlayerChattedEvent(PlayerChatEvent event){
		String playerId = event.getPlayer().getUsername();
		String content = event.getMessage();
		WsConnectionPluginVelocity.wsConnection.sendEventData(new PlayerChattedEvent(content,playerId));
	}
}
