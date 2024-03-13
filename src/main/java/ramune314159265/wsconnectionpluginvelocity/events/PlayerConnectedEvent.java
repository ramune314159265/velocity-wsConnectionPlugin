package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class PlayerConnectedEvent extends Event {
	public String joinedServerId;
	public String playerId;
	public String proxyId;

	public PlayerConnectedEvent(String joinedServerId, String playerId) {
		super();
		this.type = "player_connected";
		this.joinedServerId = joinedServerId;
		this.playerId = playerId;
		this.proxyId = WsConnectionPluginVelocity.serverId;
	}
}
