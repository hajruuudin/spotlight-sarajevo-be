package com.spotlightsarajevo.utils.helpers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.spotlightsarajevo.service.implementation.AuthServiceImpl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class UtilsFunctions {
    private static final GsonFactory jsonFactory = new GsonFactory();
    private static final NetHttpTransport transport = new NetHttpTransport();

    /**
     * Method to verify google id token received from the frontend upon Google authentication.<br/>
     * Works with both login and registration. Used in {@link AuthServiceImpl}
     *
     * @param idTokenString The string of the token received from the frontend.
     */
    public static GoogleIdToken verifyGoogleToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(System.getenv("GOOGLE_CLIENT_ID")))
                .build();

        return verifier.verify(idTokenString);
    }
}
