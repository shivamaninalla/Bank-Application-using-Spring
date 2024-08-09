// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.dto;

public class JWTAuthResponse {
   private String accessToken;
   private String tokenType = "Bearer";

   public JWTAuthResponse() {
   }

   public JWTAuthResponse(String accessToken, String tokenType) {
      this.accessToken = accessToken;
      this.tokenType = tokenType;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public String getTokenType() {
      return this.tokenType;
   }

   public void setTokenType(String tokenType) {
      this.tokenType = tokenType;
   }
}
