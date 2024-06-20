package amb.mat.school.life.teacher.controller.dto;

public record TeacherDto(
        String teacherId,
        String username,
        String emailAddress,
        String lastname,
        String firstname,
        String resume
) {
}
