package com.example.app.model;

import st.orm.DbTable;
import st.orm.Entity;
import st.orm.PK;

/**
 * A city. Entities are immutable records: Storm reads them straight out of the result set and
 * writes them back without proxies, a persistence context, or a session to keep open.
 */
@DbTable("city")
public record City(
        @PK Integer id,
        String name
) implements Entity<Integer> {

    public City(String name) {
        this(0, name);
    }
}
