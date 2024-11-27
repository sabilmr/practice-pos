package project.practicepos.auth.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReq {
    private String username;
    private String password;
    private List<String> roles;
}
