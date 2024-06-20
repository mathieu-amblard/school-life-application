package amb.mat.school.life.workshop.domain.specification;

import java.util.Arrays;

public class AndSpecification<T> implements Specification<T> {

    private final Specification<T>[] specifications;

    public AndSpecification(Specification<T>... specifications) {
        this.specifications = specifications;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return Arrays.stream(specifications)
                .allMatch(specification -> specification.isSatisfiedBy(candidate));
    }
}
