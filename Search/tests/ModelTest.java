import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
/**
 * JUnit Class to test the A1main.
 * @author Dhananjay Saikumar
 */
public class ModelTest{


	@Before // executed before each of the other tests in this class
	public void setup(){
		//System.out.println("    ");
		//System.out.println("    ");
		//System.out.println("Setting up environment");
	}

	//Checks for invalid input (no config given)
	@Test public void invalidinputlength() {
		System.out.println("Checking invalid input: Java A1main BFS");
		String console = A1main.main(new String[] {"BFS"});
		assertEquals("usage: java A1main <DFS|BFS|AStar|BestF|...> <ConfID>", console);
		//System.out.println(console);
		System.out.println("pass");
		System.out.println(" ");
	}
	//Checks for invalid input (invalid agent given)
	@Test public void invalidinputagent() {
		System.out.println("Checking invalid input: Java A1main xyz CONF11");
		String console = A1main.main(new String[] {"xyz", "CONF0"});
		assertEquals("usage: java A1main <DFS|BFS|AStar|BestF|...> <ConfID>", console);
		//System.out.println(console);
		System.out.println("pass");
		System.out.println(" ");
	}

	//Checks for invalid input (invalid confid given)
	@Test public void invalidinputconf() {
		System.out.println("Checking invalid input: Java A1main BFS CONF99");
		String console = A1main.main(new String[] {"BFS", "CONF99"});
		assertEquals("Invalid ConfID", console);
		//System.out.println(console);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BFS CONF25
	@Test public void BFS_CONF25() {
		System.out.println("Testing output for: Java A1main BFS CONF25");
		String console = A1main.main(new String[] {"BFS", "CONF25"});
		String expected = "[(0,0)]" + "\n[(1,0)]"
		+ "\n[(1,1)]" + "\n[(1,2),(2,1)]" + "\n[(2,1),(1,3)]" + "\n[(1,3),(2,2),(2,0)]"
		+ "\n(0,0)(1,0)(1,1)(1,2)(1,3)" + "\n4.0" + "\n6";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: DFS CONF25
	@Test public void DFS_CONF25() {
		System.out.println("Testing output for: Java A1main DFS CONF25");
		String console = A1main.main(new String[] {"DFS", "CONF25"});
		String expected = "[(0,0)]" + "\n[(1,0)]"
		+ "\n[(1,1)]" + "\n[(1,2),(2,1)]" + "\n[(1,3),(2,1)]"
		+ "\n(0,0)(1,0)(1,1)(1,2)(1,3)" + "\n4.0" + "\n5";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BestF CONF25
	@Test public void BestF_CONF25() {
		System.out.println("Testing output for: Java A1main BestF CONF25");
		String console = A1main.main(new String[] {"BestF", "CONF25"});
		String expected = "[(0,0):4.0]" + "\n[(1,0):3.0]"
		+ "\n[(1,1):2.0]" + "\n[(1,2):1.0,(2,1):3.0]" + "\n[(1,3):0.0,(2,1):3.0]"
		+ "\n(0,0)(1,0)(1,1)(1,2)(1,3)" + "\n4.0" + "\n5";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: AStar CONF25
	@Test public void AStar_CONF25() {
		System.out.println("Testing output for: Java A1main AStar CONF25");
		String console = A1main.main(new String[] {"AStar", "CONF25"});
		String expected = "[(0,0):4.0]" + "\n[(1,0):4.0]"
		+ "\n[(1,1):4.0]" + "\n[(1,2):4.0,(2,1):6.0]" + "\n[(1,3):4.0,(2,1):6.0]"
		+ "\n(0,0)(1,0)(1,1)(1,2)(1,3)" + "\n4.0" + "\n5";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BFS CONF26
	@Test public void BFS_CONF26() {
		System.out.println("Testing output for: Java A1main BFS CONF26");
		String console = A1main.main(new String[] {"BFS", "CONF26"});
		String expected = "[(0,0)]" + "\n[(1,0)]"
		+ "\n[(1,1)]" + "\n[(1,2),(2,1)]" + "\n[(2,1),(1,3)]" + "\n[(1,3),(2,2),(2,0)]"
		+ "\n[(2,2),(2,0),(1,4),(2,3)]" + "\n[(2,0),(1,4),(2,3),(3,2)]"
		+ "\n(0,0)(1,0)(1,1)(2,1)(2,0)" + "\n4.0" + "\n8";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: DFS CONF26
	@Test public void DFS_CONF26() {
		System.out.println("Testing output for: Java A1main DFS CONF26");
		String console = A1main.main(new String[] {"DFS", "CONF26"});
		String expected = "[(0,0)]\n"+
		"[(1,0)]\n"+
		"[(1,1)]\n"+
		"[(1,2),(2,1)]\n"+
		"[(1,3),(2,1)]\n"+
		"[(1,4),(2,3),(2,1)]\n"+
		"[(1,5),(2,3),(2,1)]\n"+
		"[(2,5),(2,3),(2,1)]\n"+
		"[(2,4),(2,3),(2,1)]\n"+
		"[(3,4),(2,3),(2,1)]\n"+
		"[(3,5),(3,3),(2,3),(2,1)]\n"+
		"[(4,5),(3,3),(2,3),(2,1)]\n"+
		"[(4,4),(3,3),(2,3),(2,1)]\n"+
		"[(5,4),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(5,5),(5,3),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(5,3),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(5,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(5,1),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(5,0),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(4,0),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(4,1),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(3,1),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(3,2),(3,0),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(2,2),(3,0),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(3,0),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"[(2,0),(4,2),(4,3),(3,3),(2,3),(2,1)]\n"+
		"(0,0)(1,0)(1,1)(1,2)(1,3)(1,4)(1,5)(2,5)(2,4)(3,4)(3,5)(4,5)(4,4)(5,4)(5,3)(5,2)(5,1)(5,0)(4,0)(4,1)(3,1)(3,0)(2,0)\n"+
		"22.0\n"+
		"26";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BestF CONF26
	@Test public void BestF_CONF26() {
		System.out.println("Testing output for: Java A1main BestF CONF26");
		String console = A1main.main(new String[] {"BestF", "CONF26"});
		String expected =
		"[(0,0):4.0]\n"+
		"[(1,0):3.0]\n"+
		"[(1,1):2.0]\n"+
		"[(2,1):1.0,(1,2):3.0]\n"+
		"[(2,0):0.0,(2,2):2.0,(1,2):3.0]\n"+
		"(0,0)(1,0)(1,1)(2,1)(2,0)\n"+
		"4.0\n"+
		"5";

		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: AStar CONF26
	@Test public void AStar_CONF26() {
		System.out.println("Testing output for: Java A1main AStar CONF26");
		String console = A1main.main(new String[] {"AStar", "CONF26"});
		String expected =
		"[(0,0):4.0]\n"+
		"[(1,0):4.0]\n"+
		"[(1,1):4.0]\n"+
		"[(2,1):4.0,(1,2):6.0]\n"+
		"[(2,0):4.0,(1,2):6.0,(2,2):6.0]\n"+
		"(0,0)(1,0)(1,1)(2,1)(2,0)\n"+
		"4.0\n"+
		"5";

		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BFS CONF27
	@Test public void BFS_CONF27() {
		System.out.println("Testing output for: Java A1main BFS CONF27");
		String console = A1main.main(new String[] {"BFS", "CONF27"});
		String expected =
		"[(3,0)]\n"+
		"[(3,1),(2,0)]\n"+
		"[(2,0),(3,2),(4,1)]\n"+
		"[(3,2),(4,1),(2,1)]\n"+
		"[(4,1),(2,1),(3,3),(2,2)]\n"+
		"[(2,1),(3,3),(2,2),(4,2),(4,0)]\n"+
		"[(3,3),(2,2),(4,2),(4,0),(1,1)]\n"+
		"[(2,2),(4,2),(4,0),(1,1),(3,4),(4,3)]\n"+
		"[(4,2),(4,0),(1,1),(3,4),(4,3),(2,3)]\n"+
		"(3,0)(3,1)(4,1)(4,2)\n"+
		"3.0\n"+
		"9"+
		"";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: DFS CONF27
	@Test public void DFS_CONF27() {
		System.out.println("Testing output for: Java A1main DFS CONF27");
		String console = A1main.main(new String[] {"DFS", "CONF27"});
		String expected =
		"[(3,0)]\n"+
		"[(3,1),(2,0)]\n"+
		"[(3,2),(4,1),(2,0)]\n"+
		"[(3,3),(2,2),(4,1),(2,0)]\n"+
		"[(3,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(3,5),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(4,5),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(4,4),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(5,4),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(5,5),(5,3),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(5,3),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(5,2),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(5,1),(4,2),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(5,0),(4,2),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(4,0),(4,2),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"[(4,2),(2,4),(4,3),(2,2),(4,1),(2,0)]\n"+
		"(3,0)(3,1)(3,2)(3,3)(3,4)(3,5)(4,5)(4,4)(5,4)(5,3)(5,2)(4,2)\n"+
		"11.0\n"+
		"16";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BestF CONF27
	@Test public void BestF_CONF27() {
		System.out.println("Testing output for: Java A1main BestF CONF27");
		String console = A1main.main(new String[] {"BestF", "CONF27"});
		String expected =
		"[(3,0):3.0]\n"+
		"[(3,1):2.0,(2,0):4.0]\n"+
		"[(4,1):1.0,(3,2):3.0,(2,0):4.0]\n"+
		"[(4,2):0.0,(4,0):2.0,(3,2):3.0,(2,0):4.0]\n"+
		"(3,0)(3,1)(4,1)(4,2)\n"+
		"3.0\n"+
		"4";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: AStar CONF27
	@Test public void AStar_CONF27() {
		System.out.println("Testing output for: Java A1main AStar CONF27");
		String console = A1main.main(new String[] {"AStar", "CONF27"});
		String expected =
		"[(3,0):3.0]\n"+
		"[(3,1):3.0,(2,0):5.0]\n"+
		"[(4,1):3.0,(2,0):5.0,(3,2):5.0]\n"+
		"[(4,2):3.0,(4,0):5.0,(3,2):5.0,(2,0):5.0]\n"+
		"(3,0)(3,1)(4,1)(4,2)\n"+
		"3.0\n"+
		"4";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BFS CONF28
	@Test public void BFS_CONF28() {
		System.out.println("Testing output for: Java A1main BFS CONF28");
		String console = A1main.main(new String[] {"BFS", "CONF28"});
		String expected = "[(2,1)]\n"+
		"[(2,0),(1,1)]\n"+
		"[(1,1),(3,0)]\n"+
		"[(3,0),(1,2),(1,0)]\n"+
		"[(1,2),(1,0),(3,1)]\n"+
		"[(1,0),(3,1),(1,3),(0,2)]\n"+
		"[(3,1),(1,3),(0,2),(0,0)]\n"+
		"(2,1)(2,0)(3,0)(3,1)\n"+
		"3.0\n"+
		"7";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: DFS CONF28
	@Test public void DFS_CONF28() {
		System.out.println("Testing output for: Java A1main DFS CONF28");
		String console = A1main.main(new String[] {"DFS", "CONF28"});
		String expected = "[(2,1)]\n"+
		"[(2,0),(1,1)]\n"+
		"[(3,0),(1,1)]\n"+
		"[(3,1),(1,1)]\n"+
		"(2,1)(2,0)(3,0)(3,1)\n"+
		"3.0\n"+
		"4";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BestF CONF28
	@Test public void BestF_CONF28() {
		System.out.println("Testing output for: Java A1main BestF CONF28");
		String console = A1main.main(new String[] {"BestF", "CONF28"});
		String expected = "[(2,1):3.0]\n"+
		"[(2,0):2.0,(1,1):4.0]\n"+
		"[(3,0):1.0,(1,1):4.0]\n"+
		"[(3,1):0.0,(1,1):4.0]\n"+
		"(2,1)(2,0)(3,0)(3,1)\n"+
		"3.0\n"+
		"4";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: AStar CONF28
	@Test public void AStar_CONF28() {
		System.out.println("Testing output for: Java A1main AStar CONF28");
		String console = A1main.main(new String[] {"AStar", "CONF28"});
		String expected = "[(2,1):3.0]\n"+
		"[(2,0):3.0,(1,1):5.0]\n"+
		"[(3,0):3.0,(1,1):5.0]\n"+
		"[(3,1):3.0,(1,1):5.0]\n"+
		"(2,1)(2,0)(3,0)(3,1)\n"+
		"3.0\n"+
		"4";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BFS CONF29
	@Test public void BFS_CONF29() {
		System.out.println("Testing output for: Java A1main BFS CONF29");
		String console = A1main.main(new String[] {"BFS", "CONF29"});
		String expected = "[(5,1)]\n"+
		"[(5,2),(5,0)]\n"+
		"[(5,0),(5,3),(4,2)]\n"+
		"[(5,3),(4,2),(4,0)]\n"+
		"[(4,2),(4,0),(5,4)]\n"+
		"[(4,0),(5,4),(4,3),(4,1)]\n"+
		"[(5,4),(4,3),(4,1)]\n"+
		"[(4,3),(4,1),(5,5),(4,4)]\n"+
		"[(4,1),(5,5),(4,4),(3,3)]\n"+
		"[(5,5),(4,4),(3,3),(3,1)]\n"+
		"[(4,4),(3,3),(3,1)]\n"+
		"(5,1)(5,2)(5,3)(5,4)(4,4)\n"+
		"4.0\n"+
		"11";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: DFS CONF28
	@Test public void DFS_CONF29() {
		System.out.println("Testing output for: Java A1main DFS CONF29");
		String console = A1main.main(new String[] {"DFS", "CONF29"});
		String expected = "[(5,1)]\n"+
		"[(5,2),(5,0)]\n"+
		"[(5,3),(4,2),(5,0)]\n"+
		"[(5,4),(4,2),(5,0)]\n"+
		"[(5,5),(4,4),(4,2),(5,0)]\n"+
		"[(4,4),(4,2),(5,0)]\n"+
		"(5,1)(5,2)(5,3)(5,4)(4,4)\n"+
		"4.0\n"+
		"6";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: BestF CONF28
	@Test public void BestF_CONF29() {
		System.out.println("Testing output for: Java A1main BestF CONF29");
		String console = A1main.main(new String[] {"BestF", "CONF29"});
		String expected = "[(5,1):4.0]\n"+
		"[(5,2):3.0,(5,0):5.0]\n"+
		"[(5,3):2.0,(4,2):2.0,(5,0):5.0]\n"+
		"[(5,4):1.0,(4,2):2.0,(5,0):5.0]\n"+
		"[(4,4):0.0,(4,2):2.0,(5,5):2.0,(5,0):5.0]\n"+
		"(5,1)(5,2)(5,3)(5,4)(4,4)\n"+
		"4.0\n"+
		"5";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}

	//COMPARISON TEST: AStar CONF28
	@Test public void AStar_CONF29() {
		System.out.println("Testing output for: Java A1main AStar CONF29");
		String console = A1main.main(new String[] {"AStar", "CONF29"});
		String expected = "[(5,1):4.0]\n"+
		"[(5,2):4.0,(5,0):6.0]\n"+
		"[(5,3):4.0,(4,2):4.0,(5,0):6.0]\n"+
		"[(4,2):4.0,(5,4):4.0,(5,0):6.0]\n"+
		"[(5,4):4.0,(4,3):4.0,(4,1):6.0,(5,0):6.0]\n"+
		"[(4,3):4.0,(4,4):4.0,(5,5):6.0,(4,1):6.0,(5,0):6.0]\n"+
		"[(4,4):4.0,(4,4):4.0,(5,0):6.0,(4,1):6.0,(5,5):6.0,(3,3):6.0]\n"+
		"(5,1)(5,2)(5,3)(5,4)(4,4)\n"+
		"4.0\n"+
		"7";
		assertEquals(expected, console);
		//System.out.println(console);
		//System.out.println(expected);
		System.out.println("pass");
		System.out.println(" ");
	}
}