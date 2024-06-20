package amb.mat.school.life.workshop.domain.specification.invariant;

import amb.mat.school.life.workshop.domain.specification.Specification;

public class NotNullSpecification<T> implements Specification<T> {

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return candidate != null;
    }
}
