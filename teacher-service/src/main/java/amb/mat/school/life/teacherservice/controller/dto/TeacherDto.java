package amb.mat.school.life.teacherservice.controller.dto;

public record TeacherDto(
        String teacherId,
        String username,
        String emailAddress,
        String lastname,
        String firstname,
        String resume
) {
}
