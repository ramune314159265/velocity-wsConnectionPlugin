package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class PlayerMovedEvent extends Event {
	public String joinedServerId;
	public String previousJoinedServerId;
	public String playerId;
	public String proxyId;

	public PlayerMovedEvent(String joinedServerId, String previousJoinedServerId, String playerId) {
		super();
		this.type = "player_moved";
		this.joinedServerId = joinedServerId;
		this.previousJoinedServerId = previousJoinedServerId;
		this.playerId = playerId;
		this.proxyId = WsConnectionPluginVelocity.serverId;
	}
}
