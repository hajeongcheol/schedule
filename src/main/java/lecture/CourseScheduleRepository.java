package lecture;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="courseSchedules", path="courseSchedules")
public interface CourseScheduleRepository extends PagingAndSortingRepository<CourseSchedule, Long>{

    List<CourseSchedule> findByCourseId(Long courseId);
}
