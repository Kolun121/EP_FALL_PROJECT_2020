package mospolytech.engineering2020.fall.epprojectfall.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}