package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class ServerStartedEvent extends Event {
	public String serverId;

	public ServerStartedEvent() {
		this.type = "server_started";
		this.serverId = WsConnectionPluginVelocity.serverId;
	}
}