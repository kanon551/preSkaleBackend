package com.library.birds.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Response <response,obj>{

    private response message;
    private obj object;
}
