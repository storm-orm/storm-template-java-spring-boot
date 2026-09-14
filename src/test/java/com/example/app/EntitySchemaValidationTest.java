package com.example.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import com.example.app.model.City;
import com.example.app.model.User;
import st.orm.template.ORMTemplate;
import st.orm.test.StormTest;

/**
 * Checks every entity against the schema at the JDBC level: column presence, type compatibility,
 * nullability, primary keys and foreign key consistency. The application runs the same validation
 * at startup; this test puts the failure in the build instead of in the first deployment.
 */
@StormTest(scripts = "/db/migration/V1__create_schema.sql")
class EntitySchemaValidationTest {

    @Test
    void entitiesMatchTheDatabaseSchema(ORMTemplate orm) {
        List<String> errors = orm.validateSchema(List.of(City.class, User.class));
        assertTrue(errors.isEmpty(), () -> "Schema validation errors: " + errors);
    }
}
