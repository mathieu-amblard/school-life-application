package amb.mat.school.life.workshop.domain.workshop;

public record AgeRange(int from, int to) {

    public static AgeRange ALL = new AgeRange(0, Integer.MAX_VALUE);
}
