package amb.mat.school.life.workshop.domain.specification.invariant;

import amb.mat.school.life.workshop.domain.specification.Specification;

public class UuidSpecification implements Specification<String> {

    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Override
    public boolean isSatisfiedBy(String candidate) {
        return candidate != null && candidate.matches(UUID_REGEX);
    }
}
