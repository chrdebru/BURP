
package burp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;

public class TestRMLIOTarget {
	private static String base = "./src/test/burp/rml-io/";

//	@Test public void RMLTTC0001a() throws IOException { testForOK("RMLTTC0001a"); }
//	@Test public void RMLTTC0001b() throws IOException { testForOK("RMLTTC0001b"); }
//	@Test public void RMLTTC0001c() throws IOException { testForOK("RMLTTC0001c"); }
//	@Test public void RMLTTC0001d() throws IOException { testForOK("RMLTTC0001d"); }
//	@Test public void RMLTTC0001e() throws IOException { testForOK("RMLTTC0001e"); }
//	@Test public void RMLTTC0001f() throws IOException { testForOK("RMLTTC0001f"); }
//	@Test public void RMLTTC0002a() throws IOException { testForOK("RMLTTC0002a"); }
//	@Test public void RMLTTC0002b() throws IOException { testForOK("RMLTTC0002b"); }
//	@Test public void RMLTTC0002c() throws IOException { testForOK("RMLTTC0002c"); }
//	@Test public void RMLTTC0002d() throws IOException { testForOK("RMLTTC0002d"); }
//	@Test public void RMLTTC0002e() throws IOException { testForOK("RMLTTC0002e"); }
//	@Test public void RMLTTC0002f() throws IOException { testForOK("RMLTTC0002f"); }
//	@Test public void RMLTTC0002g() throws IOException { testForOK("RMLTTC0002g"); }
//	@Test public void RMLTTC0002h() throws IOException { testForOK("RMLTTC0002h"); }
//	@Test public void RMLTTC0002i() throws IOException { testForOK("RMLTTC0002i"); }
//	@Test public void RMLTTC0002j() throws IOException { testForOK("RMLTTC0002j"); }
//	@Test public void RMLTTC0002k() throws IOException { testForOK("RMLTTC0002k"); }
//	@Test public void RMLTTC0002l() throws IOException { testForOK("RMLTTC0002l"); }
//	@Test public void RMLTTC0002m() throws IOException { testForOK("RMLTTC0002m"); }
//	@Test public void RMLTTC0002n() throws IOException { testForOK("RMLTTC0002n"); }
//	@Test public void RMLTTC0002o() throws IOException { testForOK("RMLTTC0002o"); }
//	@Test public void RMLTTC0002p() throws IOException { testForOK("RMLTTC0002p"); }
//	@Test public void RMLTTC0002q() throws IOException { testForOK("RMLTTC0002q"); }
//	@Test public void RMLTTC0002r() throws IOException { testForOK("RMLTTC0002r"); }
//	@Test public void RMLTTC0003a() throws IOException { testForOK("RMLTTC0003a"); }
//	@Test public void RMLTTC0004a() throws IOException { testForOK("RMLTTC0004a"); }
//	@Test public void RMLTTC0004b() throws IOException { testForOK("RMLTTC0004b"); }
//	@Test public void RMLTTC0004c() throws IOException { testForOK("RMLTTC0004c"); }
//	@Test public void RMLTTC0004d() throws IOException { testForOK("RMLTTC0004d"); }
//	@Test public void RMLTTC0004e() throws IOException { testForOK("RMLTTC0004e"); }
//	@Test public void RMLTTC0004f() throws IOException { testForOK("RMLTTC0004f"); }
//	@Test public void RMLTTC0004g() throws IOException { testForOK("RMLTTC0004g"); }
//	@Test public void RMLTTC0005a() throws IOException { testForOK("RMLTTC0005a"); }
//	@Test public void RMLTTC0005b() throws IOException { testForOK("RMLTTC0005b"); }
//	@Test public void RMLTTC0006a() throws IOException { testForOK("RMLTTC0006a"); }
//	@Test public void RMLTTC0006b() throws IOException { testForOK("RMLTTC0006b"); }
//	@Test public void RMLTTC0006c() throws IOException { testForOK("RMLTTC0006c"); }
//	@Test public void RMLTTC0006d() throws IOException { testForOK("RMLTTC0006d"); }
//	@Test public void RMLTTC0006e() throws IOException { testForOK("RMLTTC0006e"); }
//	@Test public void RMLTTC0007a() throws IOException { testForOK("RMLTTC0007a"); }
//	@Test public void RMLTTC0007b() throws IOException { testForOK("RMLTTC0007b"); }
//	@Test public void RMLTTC0007c() throws IOException { testForOK("RMLTTC0007c"); }
//	@Test public void RMLTTC0007d() throws IOException { testForOK("RMLTTC0007d"); }
	
	public void testForOK(String f) throws IOException {
		System.out.println(String.format("Now processing %s", f));
		String m = new File(base + f, "mapping.ttl").getAbsolutePath().toString();
		String r = Files.createTempFile(null, ".nq").toString();
		System.out.println(String.format("Writing output to %s", r));

		System.out.println("This test should generate a graph.");
		String o = new File(base + f, "default.nq").getAbsolutePath().toString();

		int exit = Main.doMain(new String[] { "-m", m, "-o", r, "-b", "http://example.com/base/" });

		Model expected = RDFDataMgr.loadModel(o);
		Model actual = RDFDataMgr.loadModel(r);

		if (!expected.isIsomorphicWith(actual)) {
			expected.write(System.out, "Turtle");
			System.out.println("---");
			actual.write(System.out, "Turtle");
		}

		assertEquals(0, exit);

		System.out.println(expected.isIsomorphicWith(actual) ? "OK" : "NOK");

		assertTrue(expected.isIsomorphicWith(actual));
	}

	public void testForNotOK(String f) throws IOException {
		System.out.println(String.format("Now processing %s", f));
		String m = new File(base + f, "mapping.ttl").getAbsolutePath().toString();
		String r = Files.createTempFile(null, ".nq").toString();
		System.out.println(String.format("Writing output to %s", r));

		System.out.println("This test should NOT generate a graph.");
		int exit = Main.doMain(new String[] { "-m", m, "-o", r });
		System.out.println(Files.size(Paths.get(r)) == 0 ? "OK" : "NOK");
		Model actual = RDFDataMgr.loadModel(r);
		actual.write(System.out, "NQ");

		assertTrue(exit > 0);
		assertTrue(Files.size(Paths.get(r)) == 0);

		System.out.println();
	}

}