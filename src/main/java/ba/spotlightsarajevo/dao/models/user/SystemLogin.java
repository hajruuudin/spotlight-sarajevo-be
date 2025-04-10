package ba.spotlightsarajevo.dao.models.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for a system login request")
public class SystemLogin implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Email of the system user")
    private String email;

    @Schema(description = "Un-hashed password")
    private String password;
}
