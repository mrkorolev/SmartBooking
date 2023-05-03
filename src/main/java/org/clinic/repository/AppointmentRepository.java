package org.clinic.repository;

import org.clinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component          // @Repository is not used here, cannot assign @Repository to an interface (@EnableJpaRepositories...)
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByTitle(String title);

//    @Query(value = """
//            SELECT *
//            FROM appointments
//            ORDER BY dt_requested ASC;
//            """, nativeQuery = true)
//    List<Appointment> findAllOrderByDtRequestedDesc();

    @Query(value = """
            SELECT *
            FROM appointments
            WHERE status IN ('PENDING_APPROVAL','PENDING_CANCELLATION')
            ORDER BY dt_requested DESC;
            """, nativeQuery = true)
    List<Appointment> findAppointmentsForAdminsSorted();

    @Query(value = """
            SELECT p.*
            FROM appointments AS p
            JOIN users AS u
                ON p.user_id = u.user_id
            WHERE u.username = ?
            ORDER BY p.dt_requested DESC;
            """, nativeQuery = true)
    List<Appointment> findAppointmentsForUsersSorted(String username);

    @Query(value = """
            SELECT *
            FROM appointments
            ORDER BY dt_generated ASC;
            """, nativeQuery = true)
    List<Appointment> findAllSorted();

    @Query(value = """
            SELECT *
            FROM appointments
            WHERE dt_Requested IS NOT NULL;
            """, nativeQuery = true)
    List<Appointment> nonEmptyList();
}
