package amb.mat.school.life.workshop.domain.specification;

import java.util.Arrays;

public class OrSpecification<T> implements Specification<T> {

    private final Specification<T>[] specifications;

    public OrSpecification(Specification<T>... specifications) {
        this.specifications = specifications;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return Arrays.stream(specifications)
                .anyMatch(specification -> specification.isSatisfiedBy(candidate));
    }
}
