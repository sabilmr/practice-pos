package project.practicepos.category.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Response<T> {
    private int statusCode;
    private Object message;
    private T data;
    private int total;
}
