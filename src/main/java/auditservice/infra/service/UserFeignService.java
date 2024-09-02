package auditservice.infra.service;

import auditservice.infra.request.UserRequest;
import auditservice.infra.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserFeignService {

    UserResponse create(UserRequest request);
    UserResponse findByEmail(String email);

}
