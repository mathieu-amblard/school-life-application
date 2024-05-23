package amb.mat.school.life.teacherservice.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * No specific constraints on entities, business rules must be in the domain
 */
@Table("teachers")
public record TeacherEntity(
        @Id Long id,
        String teacherId,
        String username,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
