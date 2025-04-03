package ba.spotlightsarajevo.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication API")
@RequestMapping(value = "/auth")
@AllArgsConstructor
@RestController
public class AuthRestController {
    /*
    *   Mandatory Routes: /login /signup, both with google and regular sign up
    *   Possible Routes: /reset-password
    */
}
