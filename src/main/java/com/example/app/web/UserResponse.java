package com.example.app.web;

import org.jspecify.annotations.Nullable;
import com.example.app.model.User;

/** What the API returns. A response type of its own keeps the wire format free of the table shape. */
public record UserResponse(
        int id,
        String email,
        @Nullable String birthDate,
        String street,
        String postalCode,
        String city
) {
    public static UserResponse of(User user) {
        return new UserResponse(
                user.id(),
                user.email(),
                user.birthDate() == null ? null : user.birthDate().toString(),
                user.street(),
                user.postalCode(),
                user.city().name());
    }
}
