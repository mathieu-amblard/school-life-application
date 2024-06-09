package amb.mat.school.life.workshopservice.domain;

public class OrSpecification<T> implements Specification<T> {

    private final Specification<T> one;
    private final Specification<T> other;

    public OrSpecification(Specification<T> one, Specification<T> other) {
        this.one = one;
        this.other = other;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return this.one.isSatisfiedBy(candidate) ||
                this.other.isSatisfiedBy(candidate);
    }
}
