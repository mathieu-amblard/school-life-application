package amb.mat.school.life.workshop.domain.workshop;

public record Capacity(int min, int max) {

    public static Capacity ALL = new Capacity(0, Integer.MAX_VALUE);

}
