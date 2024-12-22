package xzcode.nativedemo.request;

import java.io.Serial;
import java.io.Serializable;

public record LoginEntity(
        String acctID,
        String username,
        String password,
        String lcid
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 8219088952080888490L;
}
