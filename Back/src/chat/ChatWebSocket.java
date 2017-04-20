package chat;

import accounts.AccountService;
import accounts.UserDataSet;
import dataset.DialogDataSet;
import dbService.DBService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.List;

/**
 * @author v.chibrikov
 *         <p/>
 *         Пример кода для курса на https://stepic.org/
 *         <p/>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {
    private ChatService chatService;
    private AccountService accountService;
    private DBService dbService;
    private Session session;

    public ChatWebSocket(ChatService chatService, AccountService accountService, DBService dbService) {
        this.chatService = chatService;
        this.accountService = accountService;
        this.dbService = dbService;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        chatService.add(this);
        this.session = session;
        List<DialogDataSet> dialogs = dbService.getAllDialog();
        for (DialogDataSet dialog : dialogs) {
            String sender = dialog.getUser().getLogin();
            String msg = dialog.getMessage();
            sendString(sender, msg);
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        String[] msg = data.split("::");
        UserDataSet user = accountService.getUser(Long.parseLong(msg[0].trim()));
        String sender = user.getLogin();
        dbService.addDialog(new DialogDataSet(msg[1], user));
        chatService.sendMessage(sender, msg[1]);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
    }

    public void sendString(String sender, String msg) {
        try {
            String data = "{ \"sender\": \"" + sender + "\", \"msg\": \"" + msg + "\" }";
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
