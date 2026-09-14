package com.example.app.repository;

import static st.orm.Operator.EQUALS;

import java.util.Optional;
import com.example.app.model.City;
import com.example.app.model.City_;
import st.orm.repository.EntityRepository;

public interface CityRepository extends EntityRepository<City, Integer> {

    /** The city with this name, if there is one. */
    default Optional<City> findByName(String name) {
        return select()
                .where(City_.name, EQUALS, name)
                .getOptionalResult();
    }
}
