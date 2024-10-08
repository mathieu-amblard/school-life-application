package amb.mat.school.life.workshop.domain.workshop;

import amb.mat.school.life.workshop.domain.specification.AndSpecification;
import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;
import amb.mat.school.life.workshop.domain.specification.invariant.UuidSpecification;

import java.util.UUID;

public record WorkshopId(String value) {

    private static final Specification<String> UUID_IDENTIFIER_SPECIFICATION = new AndSpecification<>(
            new NotNullSpecification<>(),
            new UuidSpecification()
    );

    public WorkshopId {
        UUID_IDENTIFIER_SPECIFICATION.validate(value);
    }

    public WorkshopId(UUID uuid) {
        this(uuid.toString());
    }
}

