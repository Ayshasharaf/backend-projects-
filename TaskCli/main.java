package TaskCli;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class main {
    static Path tasksFile = Path.of("tasks.json");

    public static void main(String[] args){
        if (args.length ==0){
            System.out.println("ERROR: No command provided!!");
            return;
        }
        String command = args[0];
        switch (command){
            case "add":
                addTask(args);
                break;
            case "list":
                listTasks(args);
                break;
            case "update":
                updateTask(args);
                break;
            case "delete":
                deleteTask(args);
                break;
            case "mark-in-progress":
                markInProgress(args);
                break;
            case "mark-done":
                markDone(args);
                break;
            default:
                System.out.println("ERROR: Unknown command!"+ command);
        }
    }

    // ============ ADD TASK ============
    public static void addTask(String[] args){
        // Check if description is provided
        if (args.length < 2) {
            System.out.println("ERROR: Please provide a task description");
            return;
        }

        String description = args[1];
        List<TaskCli> tasks = readTasksFromFile();

        // Find highest ID
        int newId = 1;
        for (TaskCli task : tasks) {
            if (task.getId() >= newId) {
                newId = task.getId() + 1;
            }
        }

        // Get current timestamp
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Create and add new task
        TaskCli newTask = new TaskCli(newId, description, "todo", now, now);
        tasks.add(newTask);

        // Write back to file
        writeTasksToFile(tasks);
        System.out.println("Task added successfully (ID: " + newId + ")");
    }

    // ============ UPDATE TASK ============
    public static void updateTask(String[] args){
        if (args.length < 3) {
            System.out.println("ERROR: Please provide task ID and new description");
            return;
        }

        try {
            int targetId = Integer.parseInt(args[1]);
            String newText = args[2];

            List<TaskCli> tasks = readTasksFromFile();

            boolean found = false;
            for (TaskCli task : tasks) {
                if (task.getId() == targetId) {
                    task.setDescription(newText);
                    task.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("ERROR: Task with ID " + targetId + " not found");
                return;
            }

            writeTasksToFile(tasks);
            System.out.println("Task updated successfully");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Task ID must be a number");
        }
    }

    // ============ DELETE TASK ============
    public static void deleteTask(String[] args){
        if (args.length < 2) {
            System.out.println("ERROR: Please provide a task ID");
            return;
        }

        try {
            int targetId = Integer.parseInt(args[1]);
            List<TaskCli> tasks = readTasksFromFile();

            boolean found = false;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId() == targetId) {
                    tasks.remove(i);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("ERROR: Task with ID " + targetId + " not found");
                return;
            }

            writeTasksToFile(tasks);
            System.out.println("Task deleted successfully");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Task ID must be a number");
        }
    }

    // ============ MARK IN PROGRESS ============
    public static void markInProgress(String[] args){
        if (args.length < 2) {
            System.out.println("ERROR: Please provide a task ID");
            return;
        }

        try {
            int targetId = Integer.parseInt(args[1]);
            List<TaskCli> tasks = readTasksFromFile();

            boolean found = false;
            for (TaskCli task : tasks) {
                if (task.getId() == targetId) {
                    task.setStatus("in-progress");
                    task.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("ERROR: Task with ID " + targetId + " not found");
                return;
            }

            writeTasksToFile(tasks);
            System.out.println("Task marked in progress successfully");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Task ID must be a number");
        }
    }

    // ============ MARK DONE ============
    public static void markDone(String[] args){
        if (args.length < 2) {
            System.out.println("ERROR: Please provide a task ID");
            return;
        }

        try {
            int targetId = Integer.parseInt(args[1]);
            List<TaskCli> tasks = readTasksFromFile();

            boolean found = false;
            for (TaskCli task : tasks) {
                if (task.getId() == targetId) {
                    task.setStatus("done");
                    task.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("ERROR: Task with ID " + targetId + " not found");
                return;
            }

            writeTasksToFile(tasks);
            System.out.println("Task marked done successfully");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Task ID must be a number");
        }
    }

    // ============ LIST TASKS ============
    public static void listTasks(String[] args){
        List<TaskCli> tasks = readTasksFromFile();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }

        // Check if filtering by status
        String filterStatus = null;
        if (args.length > 1) {
            filterStatus = args[1];
        }

        // Display tasks
        boolean foundAny = false;
        for (TaskCli task : tasks) {
            if (filterStatus != null) {
                if (filterStatus.equals("done") && !task.getStatus().equals("done")) continue;
                if (filterStatus.equals("todo") && !task.getStatus().equals("todo")) continue;
                if (filterStatus.equals("in-progress") && !task.getStatus().equals("in-progress")) continue;
            }

            System.out.println(task);
            foundAny = true;
        }

        if (!foundAny && filterStatus != null) {
            System.out.println("No tasks found with status: " + filterStatus);
        }
    }

    // ============ WRITE TASKS TO FILE ============
    public static void writeTasksToFile(List<TaskCli> tasks){
        StringBuilder json = new StringBuilder();
        json.append("{\"tasks\": [\n");

        for (int i = 0; i < tasks.size(); i++) {
            TaskCli task = tasks.get(i);
            json.append("  {\n");
            json.append("    \"id\": ").append(task.getId()).append(",\n");
            json.append("    \"description\": \"").append(task.getDescription()).append("\",\n");
            json.append("    \"status\": \"").append(task.getStatus()).append("\",\n");
            json.append("    \"createdAt\": \"").append(task.getCreatedAt()).append("\",\n");
            json.append("    \"updatedAt\": \"").append(task.getUpdatedAt()).append("\"\n");
            json.append("  }");

            if (i < tasks.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("]}");

        try {
            Files.writeString(tasksFile, json.toString());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // ============ READ TASKS FROM FILE ============
    public static List<TaskCli> readTasksFromFile(){
        if (!Files.exists(tasksFile)){
            try {
                String defaultJson = "{\"tasks\": []}";
                Files.writeString(tasksFile, defaultJson);
            }
            catch (IOException e ){
                System.out.println("Error creating file: " + e.getMessage());
            }
            return new ArrayList<>();
        }

        String jsonContents = "";
        try{
            jsonContents = Files.readString(tasksFile);
        }catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }

        List<TaskCli> tasks = new ArrayList<>();

        // Extract the array content between [ and ]
        int startIndex = jsonContents.indexOf("[");
        int endIndex = jsonContents.lastIndexOf("]");

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            return tasks;  // Empty or invalid JSON
        }

        String arrayContent = jsonContents.substring(startIndex + 1, endIndex);

        // Split by }, then process each object
        String[] taskObjects = arrayContent.split("\\}\\s*,\\s*\\{");

        for (String obj : taskObjects) {
            obj = obj.trim();
            if (obj.isEmpty()) continue;

            // Clean up the object string
            obj = obj.replaceAll("^\\{|\\}$", "");  // Remove leading/trailing braces

            int id = extractIntField("{" + obj + "}", "id");
            String description = extractStringField("{" + obj + "}", "description");
            String status = extractStringField("{" + obj + "}", "status");
            String createdAt = extractStringField("{" + obj + "}", "createdAt");
            String updatedAt = extractStringField("{" + obj + "}", "updatedAt");

            TaskCli task = new TaskCli(id, description, status, createdAt, updatedAt);
            tasks.add(task);
        }

        return tasks;
    }

    static int extractIntField(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(json);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }

    static String extractStringField(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*\"([^\"]*)\"";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(json);

        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}