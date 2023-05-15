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

public class Test {

    public static void main(String[] args) {

        List<CacheTask> tasks = List.of(
                new RemoveXteas(),
                new PackMaps(new File("C:\\Users\\Administrator\\Desktop\\RSPS\\Group\\Group-JS5\\data\\custom\\maps")),
                new PackModels(new File("C:\\Users\\Administrator\\Desktop\\RSPS\\Group\\Group-JS5\\data\\custom\\models")),
                new PackDats(new File("C:\\Users\\Administrator\\Desktop\\RSPS\\Group\\Group-JS5\\data\\custom\\dats"))
        );

        Application application = new Builder().
                taskType(TaskType.BUILD).
                cacheLocation(new File("C:\\Users\\Administrator\\Desktop\\RSPS\\Group\\Group-JS5\\data\\cache\\")).
                extraTasks(tasks)
         .build();

        application.initialize();

    }

}
