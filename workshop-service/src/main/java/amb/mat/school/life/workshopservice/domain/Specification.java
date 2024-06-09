package amb.mat.school.life.workshopservice.domain;

public interface Specification<T> {

    boolean isSatisfiedBy(T candidate);

    default Specification<T> and(Specification<T> otherSpecification) {
        return new AndSpecification<>(this, otherSpecification);
    }

    default Specification<T> or(Specification<T> otherSpecification) {
        return new OrSpecification<>(this, otherSpecification);
    }

    default Specification<T> not() {
        return new NotSpecification<>(this);
    }
}
