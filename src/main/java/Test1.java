import com.mark.Application;
import com.mark.Builder;
import com.mark.TaskType;
import com.mark.tasks.CacheTask;
import com.mark.tasks.impl.PackDats;
import com.mark.tasks.impl.PackMaps;
import com.mark.tasks.impl.PackModels;
import com.mark.tasks.impl.RemoveXteas;

import java.io.File;
import java.util.List;

public class Test1 {

    public static void main(String[] args) {

        Application application = new Builder().
                taskType(TaskType.RUN_JS5).
                cacheLocation(new File("C:\\Users\\Administrator\\Desktop\\RSPS\\Group\\Group-JS5\\data\\cache\\"))
         .build();

        application.initialize();

    }

}
