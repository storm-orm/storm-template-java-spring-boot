package com.example.app.web;

import org.jspecify.annotations.Nullable;

/** What the API accepts. */
public record CreateUserRequest(
        String email,
        @Nullable String birthDate,
        String street,
        String postalCode,
        String city
) {
}
