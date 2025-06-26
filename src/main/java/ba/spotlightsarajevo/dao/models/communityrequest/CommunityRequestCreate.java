package ba.spotlightsarajevo.dao.models.communityrequest;

import ba.spotlightsarajevo.enums.CommunityRequestType;
import ba.spotlightsarajevo.enums.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Properties for a community request create request")
public class CommunityRequestCreate implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Type of the request: adding, editing or reporting an error")
    private String requestType;

    @Schema(description = "Type of the object that is being added or edited")
    private String objectType;

    @Schema(description = "The name of the request")
    private String requestName;

    @Schema(description = "The full description of the request, including its data")
    private String requestDescription;
}
