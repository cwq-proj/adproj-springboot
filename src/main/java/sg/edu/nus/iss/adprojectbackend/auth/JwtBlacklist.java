package sg.edu.nus.iss.adprojectbackend.auth;

import java.util.HashSet;
import java.util.Set;

public class JwtBlacklist {
    private static Set<String> blacklist = new HashSet<>();

    public static void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
