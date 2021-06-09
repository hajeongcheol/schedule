package lecture;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import lecture.external.Course;
import lecture.external.CourseService;

@Entity
@Table(name = "CourseSchedule_table")
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long courseId;
    private String courseName;
    private String teacher;
    private Integer studentCount;
    private Boolean openYn;

    @Transient
    private Boolean preOpenYn;

    @PostLoad
    public void onPostLoad() {
        this.setPreOpenYn(this.getOpenYn());
    }

    @PostPersist
    public void onPostPersist() {
        CourseScheduleRegistered courseScheduleRegistered = new CourseScheduleRegistered();
        BeanUtils.copyProperties(this, courseScheduleRegistered);
        courseScheduleRegistered.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        System.out.println(
                "\n\n##### CourseSchedule onPreUpdate : " + this.getPreOpenYn() + "/" + this.getOpenYn() + "\n\n");

        if (this.getPreOpenYn().booleanValue() != this.getOpenYn().booleanValue()) {
            Course course = new Course();
            // mappings goes here
            course.setOpenYn(this.getOpenYn());

            if (!ScheduleApplication.applicationContext.getBean(CourseService.class).course(course,
                    this.getCourseId().toString())) {
                throw new RollbackException("Failed during Course Open");
            }
        }
    }

    @PostUpdate
    public void onPostUpdate() {
        CourseScheduleModified courseScheduleModified = new CourseScheduleModified();
        BeanUtils.copyProperties(this, courseScheduleModified);
        courseScheduleModified.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        CourseScheduleDeleted courseScheduleDeleted = new CourseScheduleDeleted();
        BeanUtils.copyProperties(this, courseScheduleDeleted);
        courseScheduleDeleted.publishAfterCommit();
    }

    public Boolean getPreOpenYn() {
        return preOpenYn;
    }

    public void setPreOpenYn(Boolean preOpenYn) {
        this.preOpenYn = preOpenYn;
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

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Boolean getOpenYn() {
        return openYn;
    }

    public void setOpenYn(Boolean openYn) {
        this.openYn = openYn;
    }

}
