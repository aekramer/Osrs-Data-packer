import com.mark.Application;
import com.mark.Builder;
import com.mark.TaskType;

import java.io.File;

public class Test1 {

    public static void main(String[] args) {

        Application application = new Builder().
                taskType(TaskType.RUN_JS5).
                cacheLocation(new File("C:\\Users\\Administrator\\Desktop\\RSPS\\Group\\Group-JS5\\data\\cache\\"))
         .build();

        application.initialize();

    }

}
