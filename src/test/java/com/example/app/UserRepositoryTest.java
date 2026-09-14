package com.example.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.example.app.model.City;
import com.example.app.model.User;
import com.example.app.repository.CityRepository;
import com.example.app.repository.UserRepository;
import st.orm.template.ORMTemplate;
import st.orm.test.CapturedSql.Operation;
import st.orm.test.SqlCapture;
import st.orm.test.StormTest;

/**
 * Repository tests run on H2, on a database of its own per test class, created from the migration
 * and rolled back after each test. The test asks for what it needs as parameters: an ORMTemplate,
 * and a SqlCapture to assert on the statements Storm generates.
 */
@StormTest(scripts = {"/db/migration/V1__create_schema.sql", "/db/migration/V2__sample_data.sql"})
class UserRepositoryTest {

    @Test
    void usersAreFoundByTheNameOfTheirCity(ORMTemplate orm, SqlCapture capture) {
        UserRepository users = orm.repository(UserRepository.class);

        List<User> found = capture.execute(() -> users.findByCityName("Amsterdam"));

        assertEquals(List.of("ada@example.com"), found.stream().map(User::email).toList());
        // The city is part of the user, so one statement reads both tables.
        assertEquals(1, capture.count(Operation.SELECT));
        assertEquals("Amsterdam", found.getFirst().city().name());
    }

    @Test
    void aUserIsWrittenAndReadBack(ORMTemplate orm) {
        UserRepository users = orm.repository(UserRepository.class);
        CityRepository cities = orm.repository(CityRepository.class);
        City utrecht = cities.insertAndFetch(new City("Utrecht"));

        User inserted = users.insertAndFetch(
                new User("grace@example.com", LocalDate.of(1906, 12, 9), "Oudegracht 3", "3511 AA", utrecht));

        assertTrue(inserted.id() > 0);
        assertEquals(inserted, users.getById(inserted.id()));
    }
}
