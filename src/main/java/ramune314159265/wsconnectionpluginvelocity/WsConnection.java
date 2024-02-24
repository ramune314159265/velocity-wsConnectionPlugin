package ramune314159265.wsconnectionpluginvelocity;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity.logger;

public class WsConnection {

	public WebSocket ws;
	public void init() {
		String wsUrl = "ws://localhost:8000/";

		HttpClient client = HttpClient.newHttpClient();
		WebSocket.Builder wsb = client.newWebSocketBuilder();

		WebSocket.Listener listener = new WebSocket.Listener() {
			@Override
			public void onOpen(WebSocket webSocket) {
				logger.info("ws connected");
			}
			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
				logger.info((String) data);
				return null;
			}
		};

		CompletableFuture<WebSocket> comp = wsb.buildAsync(URI.create(wsUrl), listener);
		try {
			this.ws = comp.get();
		} catch (ExecutionException | InterruptedException e) {
			logger.error(e.toString());
		}
	}
	public void sendJSON(Object data){
		Gson gson = new Gson();
		String json = gson.toJson(data);

		this.ws.sendText(json,true);
	}
	public void disconnect(){
		this.ws.sendClose(0,"disconnect() called");
	}
}
