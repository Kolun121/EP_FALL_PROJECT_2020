package mospolytech.engineering2020.fall.epprojectfall.service;

import mospolytech.engineering2020.fall.epprojectfall.domain.User;

public interface UserService extends CrudService<User, Long> {
    User findByUsername(String username);
}
