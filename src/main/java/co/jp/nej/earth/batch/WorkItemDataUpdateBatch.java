package co.jp.nej.earth.batch;

public class WorkItemDataUpdateBatch implements Runnable {
    private Long id;
    private String tableName;
    private String[] selectColumns;

    /**
     * @param tableName
     * @param selectColumns
     */
    public WorkItemDataUpdateBatch(Long id, String tableName, String[] selectColumns) {
        super();
        this.id = id;
        this.tableName = tableName;
        this.selectColumns = selectColumns;
    }

    public void run() {
    }
}
