package com.api.locationwriter.repository.impl;

import com.api.locationwriter.model.Location;
import com.api.locationwriter.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class LocationRepositoryImpl implements LocationRepository
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Using jdbcTemplate instead of hibernate to make it faster
     * @param location
     */
    @Override
    public void createLocation(Location location)
    {
        jdbcTemplate.update("insert into location " +
                "(latitude, longitude, created_on, user_id) VALUES (?, ?, ?, ?) ;",
                  location.getLatitude(), location.getLongitude(), location.getCreatedOn(), location.getUserId());

    }

}
