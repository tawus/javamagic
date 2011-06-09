package com.googlecode.tawus.gravatar.services;

import java.util.Map;

public interface GravatarURLCreator {
   String createAvatarURL(String email, String type, Map<String, String> parameters);
}
