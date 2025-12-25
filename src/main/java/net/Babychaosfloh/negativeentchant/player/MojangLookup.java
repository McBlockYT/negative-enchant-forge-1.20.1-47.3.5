package net.Babychaosfloh.negativeentchant.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class MojangLookup {

    /** Class to hold both UUID and texture */
    public static class PlayerData {
        public final String uuid;
        public final TextureData texture;

        public PlayerData(String uuid, TextureData texture) {
            this.uuid = uuid;
            this.texture = texture;
        }
    }

    /** Class for Base64 texture data */
    public static class TextureData {
        public final String value;
        public final String signature;

        public TextureData(String value, String signature) {
            this.value = value;
            this.signature = signature;
        }

        /** Decode Base64 to JSON string */
        public String toJsonString() {
            byte[] decoded = Base64.getDecoder().decode(value);
            return new String(decoded, StandardCharsets.UTF_8);
        }
    }

    /** Synchronous method to get both UUID and texture */
    public static PlayerData getPlayerData(String username) throws Exception {
        String uuid = getUUID(username);
        if (uuid == null) {
            System.out.println("Player not found: " + username);
            throw new Exception("Player not found: " + username);
        }

        TextureData textureData = getPlayerTexture(uuid);
        if (textureData == null) {
            System.out.println("Could not get texture for: " + username);
            throw new Exception("Could not get texture for: " + username);
        }

        return new PlayerData(uuid, textureData);
    }

    /** Get UUID from Mojang API */
    private static String getUUID(String username) throws Exception {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + username;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        if (conn.getResponseCode() != 200) return null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            String rawId = json.get("id").getAsString();
            return formatUuid(rawId);
        }
    }

    /** Get texture property from Mojang session API */
    private static TextureData getPlayerTexture(String uuid) throws Exception {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        if (conn.getResponseCode() != 200) return null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray properties = json.getAsJsonArray("properties");

            for (int i = 0; i < properties.size(); i++) {
                JsonObject prop = properties.get(i).getAsJsonObject();
                if ("textures".equals(prop.get("name").getAsString())) {
                    String value = prop.get("value").getAsString();
                    String signature = prop.has("signature") ? prop.get("signature").getAsString() : null;
                    return new TextureData(value, signature);
                }
            }
        }
        return null;
    }

    /** Format Mojang raw UUID (without dashes) into standard UUID format */
    private static String formatUuid(String raw) {
        return UUID.fromString(
                raw.replaceFirst(
                        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                        "$1-$2-$3-$4-$5"
                )
        ).toString();
    }
}
