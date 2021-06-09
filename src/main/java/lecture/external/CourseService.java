package lecture.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "course", url = "${api.course.url}", fallback = CourseServiceFallback.class)
public interface CourseService {
    @RequestMapping(method = RequestMethod.PUT, path = "/modifyOpenYn/{id}")
    public boolean course(@RequestBody Course course, @PathVariable String id);
}