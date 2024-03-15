package ramune314159265.wsconnectionpluginvelocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import ramune314159265.wsconnectionpluginvelocity.events.*;

public class PluginListener {
	@Subscribe()
	public void onServerConnectedEvent(ServerConnectedEvent event) {
		String joinedServerId = event.getServer().getServerInfo().getName();
		String playerId = event.getPlayer().getUsername();

		WsConnectionPluginVelocity.playerConnectingServerMap.put(playerId, joinedServerId);

		Event eventDataToSend;
		if (event.getPreviousServer().isEmpty()) {
			eventDataToSend = new PlayerConnectedEvent(joinedServerId, playerId);
		} else {
			String previousJoinedServerId = event.getPreviousServer().get().getServerInfo().getName();
			eventDataToSend = new PlayerMovedEvent(joinedServerId, previousJoinedServerId, playerId);
		}

		WsConnectionPluginVelocity.wsConnection.sendEventData(eventDataToSend);
	}

	@Subscribe
	public void onDisconnectEvent(DisconnectEvent event) {
		String playerId = event.getPlayer().getUsername();
		if(WsConnectionPluginVelocity.playerConnectingServerMap.get(playerId).isEmpty()){
			return;
		}
		String previousJoinedServerId = WsConnectionPluginVelocity.playerConnectingServerMap.get(playerId);
		WsConnectionPluginVelocity.wsConnection.sendEventData(new PlayerDisconnectedEvent(playerId, previousJoinedServerId));
		WsConnectionPluginVelocity.playerConnectingServerMap.remove(playerId);
	}

	@Subscribe
	public void onPlayerChattedEvent(PlayerChatEvent event){
		String playerId = event.getPlayer().getUsername();
		String content = event.getMessage();
		String serverId = WsConnectionPluginVelocity.playerConnectingServerMap.get(playerId);
		WsConnectionPluginVelocity.wsConnection.sendEventData(new PlayerChattedEvent(content,playerId,serverId));
	}
}
