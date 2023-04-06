import org.junit.Before;

import java.io.IOException;

public class Test {
    @Before
    public void setup(){
        String csv = "src/Fleet.csv";
    }

    @org.junit.Test
    public void FilterProcess() throws IOException {
        Search search = new Search();
        search.FilterProcess("src/Fleet.csv");
        if (search.Filtered.size() == 0) throw new AssertionError();
        else{
            System.out.println(search.Filtered);
        }
    }

    @org.junit.Test(expected = IOException.class)
    public void FilterProcess_wrong_csv() throws IOException {
        Search search = new Search();
        search.FilterProcess("src/Flest.csv");
    }

    @org.junit.Test
    public void emailvalid(){
        Personal personal = new Personal();
        personal.ValidEmail("abhishek@gmail.com");
    }



}
