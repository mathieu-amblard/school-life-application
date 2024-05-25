package amb.mat.school.life.teacherservice.domain.teacher.command;

@FunctionalInterface
public interface CommandSupplier<T, R extends Command> {

    /**
     * Get the command.
     *
     * @return the command result
     */
    default R getCommand() {
        return getCommand(null);
    }

    /**
     * Get the command.
     *
     * @param extraArg extra argument used to provide the command
     * @return the command result
     */
    R getCommand(T extraArg);
}
