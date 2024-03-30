package max.account;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Getter @Setter @Accessors(fluent = true, chain = true)
@AllArgsConstructor @NoArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String email;
    private String password;
    private String hash;

}
