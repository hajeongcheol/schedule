package lecture;

public class CourseScheduleModified extends AbstractEvent {

    private Long id;
    private Long courseId;
    private String courseName;
    private String teacher;
    private Boolean openYn;
    private Integer studentCount;

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public CourseScheduleModified() {
        super();
    }

    public Boolean getOpenYn() {
        return openYn;
    }

    public void setOpenYn(Boolean openYn) {
        this.openYn = openYn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
