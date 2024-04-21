package amb.mat.school.life.studentservice.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * No specific constraints on entities, business rules must be in the domain
 */
@Table("students")
public record StudentEntity(
        @Id Long id,
        String studentId,
        String username,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
