package co.com.pruebatecnica.adapter.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DBSecret {

    private final String url;
    private final String username;
    private final String password;
}
