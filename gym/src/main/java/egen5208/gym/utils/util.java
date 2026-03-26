package egen5208.gym.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class util {
    public static String getEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return email;
    }
}
