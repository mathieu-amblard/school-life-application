package amb.mat.school.life.workshop.domain.student;

import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;

public record Student(StudentId studentId, int age) {

    private static final Specification<Object> MANDATORY_SPECIFICATION = new NotNullSpecification<>();

    public Student {
        MANDATORY_SPECIFICATION.validate(studentId);
    }
}
