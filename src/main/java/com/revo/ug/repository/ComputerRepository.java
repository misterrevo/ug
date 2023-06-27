package com.revo.ug.repository;

import com.revo.ug.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ComputerRepository extends JpaRepository<Computer, String> {

    List<Computer> findByNameContains(final String name);
    List<Computer> findByPostingDateBetween(final LocalDate start, final LocalDate end);

}
