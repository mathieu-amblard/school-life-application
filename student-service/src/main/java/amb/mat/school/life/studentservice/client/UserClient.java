package amb.mat.school.life.studentservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/users")
public interface UserClient {

    @PutExchange("/{username}")
    void putUser(@PathVariable String username, @RequestBody UserDto userDto);
}
