package umbrella.task.config;

public class TaskConfig {
    public static int THREAD_COUNT=5;

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("TaskConfig-->");
        stringBuilder.append("THREAD_COUNT:");
        stringBuilder.append(THREAD_COUNT);
        return stringBuilder.toString();
    }
}
