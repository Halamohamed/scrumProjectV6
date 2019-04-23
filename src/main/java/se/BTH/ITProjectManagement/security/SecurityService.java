package se.BTH.ITProjectManagement.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}