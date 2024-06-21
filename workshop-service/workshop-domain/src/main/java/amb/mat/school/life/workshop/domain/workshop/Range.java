package amb.mat.school.life.workshop.domain.workshop;

import amb.mat.school.life.workshop.domain.specification.Specification;

public abstract class Range {

    private final int lowerBound;
    private final int upperBound;

    protected Range(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        specification().validate(this);
    }

    abstract Specification<Range> specification();

    public boolean contains(int value) {
        return lowerBound <= value && value <= upperBound;
    }

    static class LowerBoundLessThanUpperBoundSpecification implements Specification<Range> {

        @Override
        public boolean isSatisfiedBy(Range candidate) {
            return candidate.lowerBound < candidate.upperBound;
        }
    }

    static class LowerBoundPositiveSpecification implements Specification<Range> {

        @Override
        public boolean isSatisfiedBy(Range candidate) {
            return candidate.lowerBound > 0;
        }
    }
}
