package amb.mat.school.life.workshop.domain.teacher;

import amb.mat.school.life.workshop.domain.specification.AndSpecification;
import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;
import amb.mat.school.life.workshop.domain.specification.invariant.UuidSpecification;

import java.util.UUID;

public record TeacherId(String value) {

    private static final Specification<String> UUID_IDENTIFIER_SPECIFICATION = new AndSpecification<>(
            new NotNullSpecification<>(),
            new UuidSpecification()
    );

    public TeacherId {
        UUID_IDENTIFIER_SPECIFICATION.validate(value);
    }

    public TeacherId(UUID uuid) {
        this(uuid.toString());
    }
}
