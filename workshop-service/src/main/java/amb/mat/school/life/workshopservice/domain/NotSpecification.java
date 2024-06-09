package amb.mat.school.life.workshopservice.domain;

public class NotSpecification<T> implements Specification<T> {

    private final Specification<T> wrapped;

    public NotSpecification(Specification<T> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return !this.wrapped.isSatisfiedBy(candidate);
    }
}
