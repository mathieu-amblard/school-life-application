package amb.mat.school.life.workshop.domain.workshop;

import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;

import java.time.LocalDateTime;

/**
 * Business Rules :
 * - The workshop identifier is mandatory.
 * - The title is mandatory.
 * - The date of the workshop is mandatory.
 */
public class Workshop {

    private static final Specification<Object> MANDATORY_SPECIFICATION = new NotNullSpecification<>();

    private final WorkshopId workshopId;
    private final String title;
    private String description;
    private String where;
    private final LocalDateTime when;
    private AgeRange target;
    private Capacity capacity;

    private Workshop(WorkshopBuilder builder) {
        MANDATORY_SPECIFICATION.validate(builder.workshopId);
        MANDATORY_SPECIFICATION.isSatisfiedBy(builder.title);
        MANDATORY_SPECIFICATION.isSatisfiedBy(builder.when);
        this.workshopId = builder.workshopId;
        this.title = builder.title;
        this.description = builder.description;
        this.where = builder.where;
        this.when = builder.when;
        this.target = builder.target;
        this.capacity = builder.capacity;
    }

    public static WorkshopBuilder newBuilder(WorkshopId workshopId, String title, LocalDateTime when) {
        return new WorkshopBuilder(workshopId, title, when);
    }

    public WorkshopId workshopId() {
        return workshopId;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public String where() {
        return where;
    }

    public LocalDateTime when() {
        return when;
    }

    public AgeRange target() {
        return target;
    }

    public Capacity capacity() {
        return capacity;
    }

    public static class WorkshopBuilder {

        private final WorkshopId workshopId;
        private final String title;
        private String description;
        private String where;
        private final LocalDateTime when;
        private AgeRange target = AgeRange.ALL;
        private Capacity capacity = Capacity.ALL;

        public WorkshopBuilder(WorkshopId workshopId, String title, LocalDateTime when) {
            this.workshopId = workshopId;
            this.title = title;
            this.when = when;
        }

        public WorkshopBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkshopBuilder where(String where) {
            this.where = where;
            return this;
        }

        public WorkshopBuilder target(AgeRange target) {
            this.target = target;
            return this;
        }

        public WorkshopBuilder capacity(Capacity capacity) {
            this.capacity = capacity;
            return this;
        }

        public Workshop build() {
            return new Workshop(this);
        }
    }
}
