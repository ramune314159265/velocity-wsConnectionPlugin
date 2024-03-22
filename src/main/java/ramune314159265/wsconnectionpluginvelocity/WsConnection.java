package ramune314159265.wsconnectionpluginvelocity;

import com.google.gson.Gson;
import ramune314159265.wsconnectionpluginvelocity.events.Event;
import ramune314159265.wsconnectionpluginvelocity.events.WsConnectedEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Objects;
import java.util.concurrent.*;

import static ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity.logger;

public class WsConnection {

	public WebSocket ws;

	public void init(String wsUrl) {
		HttpClient client = HttpClient.newHttpClient();
		WebSocket.Builder wsb = client.newWebSocketBuilder();

		WebSocket.Listener listener = new WebSocket.Listener() {
			@Override
			public void onOpen(WebSocket webSocket) {
				logger.info("wsに接続しました");
				Gson gson = new Gson();
				String json = gson.toJson(new WsConnectedEvent());
				webSocket.sendText(json,true);
				WebSocket.Listener.super.onOpen(webSocket);
			}

			@Override
			public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
				logger.info("wsから切断されました");
				if(WsConnectionPluginVelocity.isOpeningWs){
					logger.info("1秒後に再接続します");
					ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
					exec.schedule(() -> {
						WsConnectionPluginVelocity.reconnectWs();
						exec.shutdown();
					}, 1, TimeUnit.SECONDS);
				}
				return null;
			}

			@Override
			public void onError(WebSocket webSocket, Throwable error) {
				logger.error(error.toString());
				logger.warn("1秒後に再接続します");
				ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
				exec.schedule(() -> {
					WsConnectionPluginVelocity.reconnectWs();
					exec.shutdown();
				}, 1, TimeUnit.SECONDS);
			}

			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
				logger.info(data.toString());
				Gson gson = new Gson();
				ReceivedData receivedData = gson.fromJson(data.toString(), ReceivedData.class);
				WsConnectionPluginVelocity.dataReceived(receivedData);
				return WebSocket.Listener.super.onText(webSocket, data, last);
			}
		};

		try {
			CompletableFuture<WebSocket> comp = wsb.buildAsync(URI.create(wsUrl), listener);
			this.ws = comp.join();
			this.ws.request(100000);
		} catch (Exception e) {
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.schedule(() -> {
				WsConnectionPluginVelocity.reconnectWs();
				exec.shutdown();
			}, 1, TimeUnit.SECONDS);
		}
	}

	public void sendEventData(Event data) {
		if (Objects.isNull(this.ws)) {
			logger.warn("wsが接続されていないため送信できません");
			return;
		}

		Gson gson = new Gson();
		String json = gson.toJson(data);

		this.ws.sendText(json, true);
	}

	public void disconnect() {
		if (Objects.isNull(this.ws)) {
			return;
		}
		if(this.ws.isOutputClosed()){
			return;
		}

		try {
			CompletableFuture<WebSocket> end = this.ws.sendClose(WebSocket.NORMAL_CLOSURE, "disconnect() called");
			end.get();
		} catch (InterruptedException | ExecutionException e) {
			logger.error("disconnect error");
		}
	}
}
