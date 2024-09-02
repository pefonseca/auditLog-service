package auditservice.infra.feign;

import auditservice.infra.request.UserRequest;
import auditservice.infra.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "user-client", url = "http://localhost:8081")
public interface UserFeignClient {

    @GetMapping(value = "/api/v1/user/find_by_email", produces = "application/json")
    ResponseEntity<UserResponse> findByEmail(@RequestParam(value = "email") String email);

    @PostMapping(value = "/api/v1/user", produces = "application/json")
    ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request);

}
