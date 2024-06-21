package amb.mat.school.life.workshop.domain.workshop;

import amb.mat.school.life.workshop.domain.specification.AndSpecification;
import amb.mat.school.life.workshop.domain.specification.Specification;

import java.util.Objects;

public class Capacity extends Range {

    public static final Capacity UNLIMITED = new Capacity(0, Integer.MAX_VALUE);

    private static final Specification<Range> CAPACITY_SPECIFICATION = new AndSpecification<>(
            new LowerBoundPositiveSpecification(),
            new Range.LowerBoundLessThanUpperBoundSpecification()
    );

    private final int min;
    private final int max;

    public Capacity(int min, int max) {
        super(min, max);
        this.min = min;
        this.max = max;
    }

    @Override
    Specification<Range> specification() {
        return CAPACITY_SPECIFICATION;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capacity capacity = (Capacity) o;
        return min == capacity.min && max == capacity.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
