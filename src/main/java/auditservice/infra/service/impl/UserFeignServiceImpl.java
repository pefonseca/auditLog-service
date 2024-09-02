package auditservice.infra.service.impl;

import auditservice.infra.feign.UserFeignClient;
import auditservice.infra.request.UserRequest;
import auditservice.infra.response.UserResponse;
import auditservice.infra.service.UserFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignServiceImpl implements UserFeignService {

    private final UserFeignClient userFeignClient;

    /**
     * @param request
     * @return
     */
    @Override
    public UserResponse create(UserRequest request) {
        var userResponse = new UserResponse();

        var userResponseExchange = userFeignClient.create(request);

        if(userResponseExchange.getStatusCode() == HttpStatus.CREATED) {
            userResponse = userResponseExchange.getBody();
        }

        return userResponse;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public UserResponse findByEmail(String email) {
        var userResponse = new UserResponse();

        var userResponseExchange = userFeignClient.findByEmail(email);

        if(userResponseExchange.getStatusCode() == HttpStatus.OK) {
            userResponse = userResponseExchange.getBody();
        }

        return userResponse;
    }
}
