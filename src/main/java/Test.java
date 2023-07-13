import com.mark.Application;
import com.mark.Builder;
import com.mark.TaskType;
import com.mark.tasks.CacheTask;
import com.mark.tasks.impl.PackDats;
import com.mark.tasks.impl.PackMaps;
import com.mark.tasks.impl.PackModels;
import com.mark.tasks.impl.RemoveXteas;
import com.mark.tasks.impl.objects.PackItems;
import com.mark.tasks.impl.objects.PackNpcs;
import com.mark.tasks.impl.objects.PackObjects;

import java.io.File;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<CacheTask> tasks = List.of(
                new RemoveXteas(),
                new PackMaps(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\raw-cache\\maps\\")),
                new PackModels(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\raw-cache\\models\\")),
                new PackObjects(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\raw-cache\\definitions\\objects\\")),
                new PackNpcs(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\raw-cache\\definitions\\npcs\\")),
                new PackItems(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\raw-cache\\definitions\\items\\"))
        );

        Application application = new Builder().
                taskType(TaskType.BUILD).
                cacheLocation(new File("F:\\RSPS\\Cadarn\\Cadarn-Assets\\cache\\")).
                extraTasks(tasks)
         .build();

        application.initialize();

    }

}
