package amb.mat.school.life.workshopservice.domain;

public class AndSpecification<T> implements Specification<T> {

    private final Specification<T> one;
    private final Specification<T> other;

    public AndSpecification(Specification<T> one, Specification<T> other) {
        this.one = one;
        this.other = other;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return this.one.isSatisfiedBy(candidate) &&
                this.other.isSatisfiedBy(candidate);
    }
}
