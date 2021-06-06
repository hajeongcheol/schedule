package lecture;

import lecture.config.kafka.KafkaProcessor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler {
    @Autowired
    CourseScheduleRepository courseScheduleRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCourseRegistered_RegisterCourseSchedule(@Payload CourseRegistered courseRegistered) {

        if (!courseRegistered.validate())
            return;

        System.out.println("\n\n##### listener RegisterCourseSchedule : " + courseRegistered.toJson() + "\n\n");

        CourseSchedule courseSchedule = new CourseSchedule();
        courseSchedule.setCourseId(courseRegistered.getId());
        courseSchedule.setCourseName(courseRegistered.getName());
        courseSchedule.setTeacher(courseRegistered.getTeacher());
        courseSchedule.setStudentCount(0);
        courseSchedule.setOpenYn(false);

        courseScheduleRepository.save(courseSchedule);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCourseModified_ModifyCourseSchedule(@Payload CourseModified courseModified) {

        if (!courseModified.validate())
            return;

        System.out.println("\n\n##### listener ModifyCourseSchedule : " + courseModified.toJson() + "\n\n");

        List<CourseSchedule> courseScheduleList = courseScheduleRepository.findByCourseId(courseModified.getId());

        for (CourseSchedule courseSchedule : courseScheduleList) {
            courseSchedule.setCourseName(courseModified.getName());
            courseSchedule.setTeacher(courseModified.getTeacher());

            courseScheduleRepository.save(courseSchedule);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCourseDeleted_DeleteCourseSchedule(@Payload CourseDeleted courseDeleted) {

        if (!courseDeleted.validate())
            return;

        System.out.println("\n\n##### listener DeleteCourseSchedule : " + courseDeleted.toJson() + "\n\n");

        List<CourseSchedule> courseScheduleList = courseScheduleRepository.findByCourseId(courseDeleted.getId());

        for (CourseSchedule courseSchedule : courseScheduleList) {
            courseScheduleRepository.delete(courseSchedule);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverClassRegistered_StudentCount(@Payload ClassRegistered classRegistered) {

        if (!classRegistered.validate())
            return;

        System.out.println("\n\n##### listener StudentCount : " + classRegistered.toJson() + "\n\n");


        List<CourseSchedule> courseScheduleList = courseScheduleRepository
                .findByCourseId(classRegistered.getCourseId());

        for (CourseSchedule courseSchedule : courseScheduleList) {

            if (courseSchedule.getStudentCount() == 0) {
                courseSchedule.setOpenYn(true);
            }

            courseSchedule.setStudentCount(courseSchedule.getStudentCount() + 1);

            System.out.println("\n\n##### listener StudentCount : " + courseSchedule.getStudentCount() + "\n\n");

            courseScheduleRepository.save(courseSchedule);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverClassCanceled_StudentCount(@Payload ClassCanceled classCanceled) {

        if (!classCanceled.validate())
            return;

        System.out.println("\n\n##### listener StudentCount : " + classCanceled.toJson() + "\n\n");

        List<CourseSchedule> courseScheduleList = courseScheduleRepository.findByCourseId(classCanceled.getCourseId());

        for (CourseSchedule courseSchedule : courseScheduleList) {
            if (courseSchedule.getStudentCount() > 0) {
                if (courseSchedule.getStudentCount() == 1) {
                    courseSchedule.setOpenYn(false);
                }
                courseSchedule.setStudentCount(courseSchedule.getStudentCount() - 1);
                courseScheduleRepository.save(courseSchedule);
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
    }

}
