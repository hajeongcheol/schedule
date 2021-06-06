package lecture;

public class CourseClosed extends AbstractEvent {

    private Long id;
    private Integer courseId;
    private Boolean openYn;

    public CourseClosed(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public Boolean getOpenYn() {
        return openYn;
    }

    public void setOpenYn(Boolean openYn) {
        this.openYn = openYn;
    }
}
