package amb.mat.school.life.teacher.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
        String resume
) {
}
