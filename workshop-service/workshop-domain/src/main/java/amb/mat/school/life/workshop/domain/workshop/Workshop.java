package amb.mat.school.life.workshop.domain.workshop;

import amb.mat.school.life.workshop.domain.specification.AndSpecification;
import amb.mat.school.life.workshop.domain.specification.Specification;
import amb.mat.school.life.workshop.domain.specification.invariant.NotNullSpecification;
import amb.mat.school.life.workshop.domain.student.Student;
import amb.mat.school.life.workshop.domain.teacher.Teacher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Business Rules :
 * - The workshop identifier is mandatory.
 * - The title is mandatory.
 * - The date of the workshop is mandatory.
 * - The teacher of the workshop is mandatory.
 * - The students set is mandatory.
 * - The students must comply with the age range.
 * - The when date must be in the future if the workshop is not yet done.
 * - The when date must be in the past if the workshop has been already done.
 * - The number of students must be less than or equal to the max capacity.
 * - A workshop can be done if the number of students is greater than the min capacity.
 */
public class Workshop {

    private static final Specification<Workshop> WORKSHOP_SPECIFICATION = new AndSpecification<>(
            new MandatoryFieldsSpecification(),
            new WhenMustBeInFutureIfNotDoneSpecification(),
            new NumberOfStudentsLowerThanMaxCapacitySpecification(),
            new StudentsComplyWithAgeRangeSpecification()
    );

    private static final Specification<Workshop> CAN_BE_DONE_SPECIFICATION = new AndSpecification<>(
            new WhenMustBeInPastToBeMarkedAsDoneSpecification(),
            new NumberOfStudentsGreaterThanMinCapacitySpecification(),
            new NumberOfStudentsLowerThanMaxCapacitySpecification()
    );

    private final WorkshopId workshopId;
    private final String title;
    private String description;
    private String where;
    private final LocalDateTime when;
    private AgeRange ageRange;
    private Capacity capacity;
    private Teacher teacher;
    private Set<Student> students;
    private boolean done;

    private Workshop(WorkshopBuilder builder) {
        this.workshopId = builder.workshopId;
        this.title = builder.title;
        this.description = builder.description;
        this.where = builder.where;
        this.when = builder.when;
        this.ageRange = builder.ageRange;
        this.capacity = builder.capacity;
        this.teacher = builder.teacher;
        this.students = builder.students;
        this.done = builder.done;
        WORKSHOP_SPECIFICATION.validate(this);
    }

    public static WorkshopBuilder newBuilder(
            WorkshopId workshopId,
            String title,
            LocalDateTime when,
            Teacher teacher
    ) {
        return new WorkshopBuilder(workshopId, title, when, teacher);
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

    public AgeRange ageRange() {
        return ageRange;
    }

    public Capacity capacity() {
        return capacity;
    }

    public Teacher teacher() {
        return teacher;
    }

    public Set<Student> students() {
        return new HashSet<>(students);
    }

    public boolean isDone() {
        return done;
    }

    public boolean canBeDone() {
        return CAN_BE_DONE_SPECIFICATION.isSatisfiedBy(this);
    }

    public void markAsDone() {
        if (canBeDone()) {
            this.done = true;
        }
    }

    public static class WorkshopBuilder {

        private final WorkshopId workshopId;
        private final String title;
        private String description;
        private String where;
        private final LocalDateTime when;
        private AgeRange ageRange = AgeRange.ALL;
        private Capacity capacity = Capacity.UNLIMITED;
        private final Teacher teacher;
        private final Set<Student> students = new HashSet<>();
        private boolean done;

        public WorkshopBuilder(
                WorkshopId workshopId,
                String title,
                LocalDateTime when,
                Teacher teacher
        ) {
            this.workshopId = workshopId;
            this.title = title;
            this.when = when;
            this.teacher = teacher;
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
            this.ageRange = target;
            return this;
        }

        public WorkshopBuilder capacity(Capacity capacity) {
            this.capacity = capacity;
            return this;
        }

        public WorkshopBuilder student(Student student) {
            this.students.add(student);
            return this;
        }

        public WorkshopBuilder done(boolean done) {
            this.done = done;
            return this;
        }

        public Workshop build() {
            return new Workshop(this);
        }
    }

    private static class MandatoryFieldsSpecification implements Specification<Workshop> {

        private static final Specification<Object> MANDATORY_SPECIFICATION = new NotNullSpecification<>();

        @Override
        public boolean isSatisfiedBy(Workshop candidate) {
            return MANDATORY_SPECIFICATION.isSatisfiedBy(candidate.workshopId) &&
                    MANDATORY_SPECIFICATION.isSatisfiedBy(candidate.title) &&
                    MANDATORY_SPECIFICATION.isSatisfiedBy(candidate.when) &&
                    MANDATORY_SPECIFICATION.isSatisfiedBy(candidate.teacher) &&
                    MANDATORY_SPECIFICATION.isSatisfiedBy(candidate.students);
        }
    }

    private static class WhenMustBeInFutureIfNotDoneSpecification implements Specification<Workshop> {

        @Override
        public boolean isSatisfiedBy(Workshop candidate) {
            return candidate.when.isAfter(LocalDateTime.now()) && !candidate.done;
        }
    }

    private static class WhenMustBeInPastToBeMarkedAsDoneSpecification implements Specification<Workshop> {

        @Override
        public boolean isSatisfiedBy(Workshop candidate) {
            return candidate.when.isBefore(LocalDateTime.now()) && !candidate.done;
        }
    }

    private static class NumberOfStudentsGreaterThanMinCapacitySpecification implements Specification<Workshop> {

        @Override
        public boolean isSatisfiedBy(Workshop candidate) {
            return candidate.students.size() >= candidate.capacity.min();
        }
    }

    private static class NumberOfStudentsLowerThanMaxCapacitySpecification implements Specification<Workshop> {

        @Override
        public boolean isSatisfiedBy(Workshop candidate) {
            return candidate.students.size() <= candidate.capacity.max();
        }
    }

    private static class StudentsComplyWithAgeRangeSpecification implements Specification<Workshop> {

        @Override
        public boolean isSatisfiedBy(Workshop candidate) {
            return candidate.students.stream()
                    .allMatch(student -> candidate.ageRange.contains(student.age()));
        }
    }
}
