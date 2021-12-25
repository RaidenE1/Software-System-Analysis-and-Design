package se.buaa.Entity.Data;

public class Achievement {

    /* rap2 scholarInfo 响应数据 */
    private String title;
    private Integer num;
    private String documentId = null;
    private String time = null;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Achievement() {}

    public Achievement(String title, Integer num, String documentId, String time) {
        this.title = title;
        this.num = num;
        this.documentId = documentId;
        this.time = time;
    }

    public Achievement(String title, int citedQuantity) {
        this.title = title;
        this.num = citedQuantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
