package servlets;

import accounts.AccountService;
import chat.ChatService;
import chat.ChatWebSocket;
import dbService.DBService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {
    public static final String URL_PATH = "/chat";
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    private final ChatService chatService;
    private final AccountService accountService;
    private final DBService dbService;

    public WebSocketChatServlet(AccountService accountService, DBService dbService) {
        this.chatService = new ChatService();
        this.accountService = accountService;
        this.dbService = dbService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService, accountService, dbService));
    }
}
