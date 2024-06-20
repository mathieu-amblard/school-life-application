package amb.mat.school.life.workshop.domain.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T candidate);

    default void validate(T candidate) {
        if (!isSatisfiedBy(candidate)) {
            throw new IllegalArgumentException(errorMessage(candidate));
        }
    }

    default String name() {
        return getClass().getName()
                .replace("([a-z])([A-Z]+)", "$1 $2")
                .toLowerCase();
    }

    default String errorMessage(T candidate) {
        return "value %s must comply with %s".formatted(candidate, name());
    }

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
