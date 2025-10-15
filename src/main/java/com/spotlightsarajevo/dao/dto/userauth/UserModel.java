package com.spotlightsarajevo.dao.dto.userauth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Default, all property, table model for the User Entity")
public class UserModel implements Serializable {
    private static final Long versionUUID = 1L;

    @Schema(description = "Unique identifier of the user")
    private Integer id;

    @Schema(description = "Locale/language preference of the user")
    private String locale;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Flag indicating if the user is premium")
    private Boolean isPremium;

    @Schema(description = "Flag indicating if the user is an admin")
    private Boolean isAdmin;

    @Schema(description = "User preference category 01")
    private Integer category01;

    @Schema(description = "User preference category 02")
    private Integer category02;

    @Schema(description = "User preference category 03")
    private Integer category03;

    @Schema(description = "Timestamp when the user was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated")
    private LocalDateTime updatedAt;
}

