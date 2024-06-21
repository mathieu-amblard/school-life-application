package amb.mat.school.life.workshop.domain.teacher;

import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;

public record Teacher(TeacherId teacherId) {

    private static final Specification<Object> MANDATORY_SPECIFICATION = new NotNullSpecification<>();

    public Teacher {
        MANDATORY_SPECIFICATION.validate(teacherId);
    }
}
