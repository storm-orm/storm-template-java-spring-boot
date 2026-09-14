package com.example.app.repository;

import static st.orm.Operator.EQUALS;

import java.util.List;
import com.example.app.model.User;
import com.example.app.model.User_;
import st.orm.repository.EntityRepository;

/**
 * findById, insert, update, upsert and the rest come with EntityRepository; this interface adds the
 * queries the application needs. Storm registers the implementation itself, so there is no class to
 * write and nothing to register.
 */
public interface UserRepository extends EntityRepository<User, Integer> {

    /**
     * Users living in a city, by the city's name. The metamodel path navigates the foreign key, so
     * Storm joins the city table for the predicate without the query naming the join.
     */
    default List<User> findByCityName(String cityName) {
        return select()
                .where(User_.city.name, EQUALS, cityName)
                .orderBy(User_.email)
                .getResultList();
    }
}
