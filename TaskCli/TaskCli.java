package TaskCli;

public class TaskCli{
    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;


    public TaskCli(int id, String description , String status, String createdAt, String updatedAt){
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    //setters


    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "ID: " + id + " | Description: " + description + " | Status: " + status +
                " | Created: " + createdAt + " | Updated: " + updatedAt;
    }
}
