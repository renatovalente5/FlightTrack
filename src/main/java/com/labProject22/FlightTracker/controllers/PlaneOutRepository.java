package com.labProject22.FlightTracker.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneOutRepository extends JpaRepository<PlaneOut, Long> {
}