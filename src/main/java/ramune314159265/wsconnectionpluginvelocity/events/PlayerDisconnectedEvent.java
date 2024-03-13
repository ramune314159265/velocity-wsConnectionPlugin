package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class PlayerDisconnectedEvent extends Event {
	public String previousJoinedServerId;
	public String playerId;
	public String proxyId;

	public PlayerDisconnectedEvent(String playerId, String previousJoinedServerId) {
		super();
		this.type = "player_disconnected";
		this.previousJoinedServerId = previousJoinedServerId;
		this.playerId = playerId;
		this.proxyId = WsConnectionPluginVelocity.serverId;
	}
}
