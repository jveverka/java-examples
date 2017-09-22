package itx.examples.guice.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import itx.examples.guice.BasicModule;
import itx.examples.guice.services.SecondaryService;
import itx.examples.guice.services.core.DataService;
import itx.examples.guice.services.core.DataServiceBlueImpl;
import itx.examples.guice.services.core.DataServiceRedImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplicationTest {

    @Test
    public void test() {
        DataService redDataService = new DataServiceRedImpl("R");
        DataService blueDataService = new DataServiceBlueImpl("B");
        Injector injector = Guice.createInjector(new BasicModule(redDataService, blueDataService));
        SecondaryService instance = injector.getInstance(SecondaryService.class);
        String xxx = instance.getData("xxx");
        Assert.assertNotNull(xxx);
        Assert.assertEquals(xxx,"ss=ps=red=Rxxx:ps=blue=Bxxx");
    }

}
