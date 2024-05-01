package amb.mat.school.life.studentservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/users")
public interface UserClient {

    @GetExchange("/{username}")
    UserDto get(@PathVariable String username);

    @PutExchange("/{username}")
    void put(@PathVariable String username, @RequestBody UserDto userDto);
}
