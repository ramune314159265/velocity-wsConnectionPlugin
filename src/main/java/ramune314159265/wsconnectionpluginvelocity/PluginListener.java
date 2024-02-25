package ramune314159265.wsconnectionpluginvelocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import ramune314159265.wsconnectionpluginvelocity.events.Event;
import ramune314159265.wsconnectionpluginvelocity.events.PlayerConnectedEvent;
import ramune314159265.wsconnectionpluginvelocity.events.PlayerMovedEvent;

import java.util.Objects;

public class PluginListener {
	@Subscribe()
	public void onServerPostConnectEvent(ServerPreConnectEvent event) {
		String joinedServerId = event.getOriginalServer().getServerInfo().getName();
		String playerId = event.getPlayer().getUsername();
		Event eventDataToSend;

		if (Objects.isNull(event.getPreviousServer())) {
			eventDataToSend = new PlayerConnectedEvent(joinedServerId, playerId);
		} else {
			String previousJoinedServerId = event.getPreviousServer().getServerInfo().getName();
			eventDataToSend = new PlayerMovedEvent(joinedServerId, previousJoinedServerId, playerId);
		}

		WsConnectionPluginVelocity.wsConnection.sendEventData(eventDataToSend);
	}
}
