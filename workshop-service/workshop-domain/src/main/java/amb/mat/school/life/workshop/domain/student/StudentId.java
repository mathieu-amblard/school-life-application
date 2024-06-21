package amb.mat.school.life.workshop.domain.student;

import amb.mat.school.life.workshop.domain.specification.AndSpecification;
import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;
import amb.mat.school.life.workshop.domain.specification.invariant.UuidSpecification;

import java.util.UUID;

public record StudentId(String value) {

    private static final Specification<String> UUID_IDENTIFIER_SPECIFICATION = new AndSpecification<>(
            new NotNullSpecification<>(),
            new UuidSpecification()
    );

    public StudentId {
        UUID_IDENTIFIER_SPECIFICATION.validate(value);
    }

    public StudentId(UUID uuid) {
        this(uuid.toString());
    }
}
