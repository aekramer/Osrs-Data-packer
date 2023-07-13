import com.mark.Application;
import com.mark.Builder;
import com.mark.TaskType;

import java.io.File;

public class Test1 {

    public static void main(String[] args) {

        Application application = new Builder().
                taskType(TaskType.RUN_JS5).
                cacheLocation(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\cache\\"))
         .build();

        application.initialize();

    }

}
