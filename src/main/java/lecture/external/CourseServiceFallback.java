package lecture.external;

import org.springframework.stereotype.Component;

@Component
public class CourseServiceFallback implements CourseService {
    @Override
    public boolean course(Course course, String id) {
        //do nothing if you want to forgive it

        System.out.println("Circuit breaker has been opened. Fallback returned instead.");
        return false;
    }
}