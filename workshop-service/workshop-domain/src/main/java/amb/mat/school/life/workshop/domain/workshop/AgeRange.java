package amb.mat.school.life.workshop.domain.workshop;

import amb.mat.school.life.workshop.domain.specification.AndSpecification;
import amb.mat.school.life.workshop.domain.specification.Specification;

import java.util.Objects;

public final class AgeRange extends Range {

    public static final AgeRange ALL = new AgeRange(0, Integer.MAX_VALUE);

    private static final Specification<Range> AGE_RANGE_SPECIFICATION = new AndSpecification<>(
            new LowerBoundPositiveSpecification(),
            new Range.LowerBoundLessThanUpperBoundSpecification()
    );

    private final int from;
    private final int to;

    public AgeRange(int from, int to) {
        super(from, to);
        this.from = from;
        this.to = to;
    }

    @Override
    Specification<Range> specification() {
        return AGE_RANGE_SPECIFICATION;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeRange ageRange = (AgeRange) o;
        return from == ageRange.from && to == ageRange.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
