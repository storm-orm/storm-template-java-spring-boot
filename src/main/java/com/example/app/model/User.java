package com.example.app.model;

import java.time.LocalDate;
import org.jspecify.annotations.Nullable;
import st.orm.DbTable;
import st.orm.Entity;
import st.orm.FK;
import st.orm.PK;

/**
 * A user of the application, living in a city.
 *
 * <p>The foreign key is the {@link City} itself, so reading a user reads its city in the same
 * statement. Declare it as {@code Ref<City>} instead when most reads do not need the city: the
 * column stays in the row, queries can still navigate and filter through it, and {@code fetch()}
 * resolves it when needed.</p>
 *
 * <p>The table is named app_user because {@code user} is a reserved word on several databases.</p>
 */
@DbTable("app_user")
public record User(
        @PK Integer id,
        String email,
        @Nullable LocalDate birthDate,
        String street,
        String postalCode,
        @FK City city
) implements Entity<Integer> {

    public User(String email, @Nullable LocalDate birthDate, String street, String postalCode, City city) {
        this(0, email, birthDate, street, postalCode, city);
    }
}
