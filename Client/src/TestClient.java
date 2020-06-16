import Collection.User;

public class TestClient {
    public static void main(String[] args) {
        User user = new User();
        String password = "123";
        user.setPassword(password);
        System.out.println(user.MD5Hash());
    }
}
//123:
//202CB962AC59075B964B07152D234B70
//1234:
//81DC9BDB52D04DC20036DBD8313ED055