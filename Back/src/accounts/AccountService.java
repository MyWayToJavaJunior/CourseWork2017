package accounts;

import dbService.DBService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {
    private final DBService dbService;

    public AccountService(DBService dbService) {
        this.dbService = dbService;
    }

    public void addNewUser(UserDataSet userDataSet) {
        dbService.addUser(userDataSet);
    }

    public UserDataSet getUserByLogin(String login) {
        return dbService.getUserByLogin(login);
    }

    public UserDataSet getUser(Long id) { return dbService.getUser(id); }
}
