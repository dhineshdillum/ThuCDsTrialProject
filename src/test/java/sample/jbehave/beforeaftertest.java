package sample.jbehave;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.junit.*;

/**
 * Created by dhinesh.dillum on 09/12/16.
 */
public class beforeaftertest {

    @Before
    public void before(){
        System.out.println("before = " );
    }

    @BeforeClass
    public static void BeforeClass(){
        System.out.println("BeforeClass = " );
    }

    @BeforeScenario
    public void BeforeScenario(){
        System.out.println("BeforeScenario = " );
    }


    @After
    public void After(){
        System.out.println("After = " );
    }

    @AfterClass
    public static void AfterClass(){
        System.out.println("AfterClass = " );
    }

    @AfterScenario
    public void AfterScenario(){
        System.out.println("AfterScenario = " );
    }

    @Test
    public void test(){
        System.out.println("Test = " );
    }


}
