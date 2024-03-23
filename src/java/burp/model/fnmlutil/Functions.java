package burp.model.fnmlutil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;

import burp.model.RMLFunction;
import burp.model.Return;

public class Functions {
	
	public static Map<String, RMLFunction> functions = prepareFunctions();

	static public List<Return> execute(String function, Map<String, Object> map) {
		
		RMLFunction f = functions.get(function);
		if(f != null)
			return f.apply(map);
		
		throw new RuntimeException(String.format("Function %s not yet supported.", function));
	}
	
	private static Map<String, RMLFunction> prepareFunctions() {
		Map<String, RMLFunction> functions = new HashMap<String, RMLFunction>();
		
		// Hello World Function
		
		functions.put("http://example.com/base/HelloWorld", new RMLFunction() {
			@Override
			public List<Return> apply(Map<String, Object> map) {
				List<Return> l = new ArrayList<Return>();
				l.add(new Return("Hello World!"));
				return l;
			}
		});
		
		// GREL functions
		
		// https://openrefine.org/docs/manual/grelfunctions
		// Escapes s in the given escaping mode. The mode can be one of: "html", "xml", "csv", "url", "javascript". 
		// Note that quotes are required around your mode. See the recipes for examples of escaping and unescaping.
		functions.put("http://users.ugent.be/~bjdmeest/function/grel.ttl#escape", new RMLFunction() {
			@Override
			public List<Return> apply(Map<String, Object> map) {
				List<Return> l = new ArrayList<Return>();
				String s = map.get("http://users.ugent.be/~bjdmeest/function/grel.ttl#valueParam").toString();
				String p = map.get("http://users.ugent.be/~bjdmeest/function/grel.ttl#modeParam").toString();
				
				String out = null;
				if("html".equals(p.toLowerCase()))
					out = StringEscapeUtils.escapeHtml4(s);
				else if("xml".equals(p.toLowerCase()))
					out = StringEscapeUtils.escapeXml11(s);
				else if("csv".equals(p.toLowerCase()))
					out = StringEscapeUtils.escapeCsv(s);
				else if("javascript".equals(p.toLowerCase()))
					out = StringEscapeUtils.escapeEcmaScript(s);
				else if("url".equals(p.toLowerCase()))
					out = URLEncoder.encode(s, StandardCharsets.UTF_8);
				else
					throw new RuntimeException(String.format("Mode %s not supported in GREL's escape function.", p));
			
				Return r = new Return(out);
				r.put("http://users.ugent.be/~bjdmeest/function/grel.ttl#stringOut", out);
				l.add(r);
				
				return l;
			}
		});
		
		functions.put("http://users.ugent.be/~bjdmeest/function/grel.ttl#string_replace", new RMLFunction() {
			@Override
			public List<Return> apply(Map<String, Object> map) {
				List<Return> l = new ArrayList<Return>();
				String s = map.get("http://users.ugent.be/~bjdmeest/function/grel.ttl#valueParam").toString();
				String f = map.get("http://users.ugent.be/~bjdmeest/function/grel.ttl#param_find").toString();
				String r = map.get("http://users.ugent.be/~bjdmeest/function/grel.ttl#param_replace").toString();
				
				
				String out = s.replaceAll(f, r);
				Return re = new Return(out);
				re.put("http://users.ugent.be/~bjdmeest/function/grel.ttl#stringOut", out);
				l.add(re);
				
				return l;
			}
		});
		
		functions.put("http://users.ugent.be/~bjdmeest/function/grel.ttl#toUpperCase", new RMLFunction() {
			@Override
			public List<Return> apply(Map<String, Object> map) {
				List<Return> l = new ArrayList<Return>();
				
				String s = map.get("http://users.ugent.be/~bjdmeest/function/grel.ttl#valueParam").toString();

				String out = s.toUpperCase();
				Return re = new Return(out);
				re.put("http://users.ugent.be/~bjdmeest/function/grel.ttl#stringOut", out);
				l.add(re);
				
				return l;
			}
		});
		
		
		// ID-Lab functions
		
		functions.put("http://example.com/idlab/function/toUpperCaseURL", new RMLFunction() {
			@Override
			public List<Return> apply(Map<String, Object> map) {
				List<Return> l = new ArrayList<Return>();
				String str = map.get("http://example.com/idlab/function/str").toString().toUpperCase();
				
				String out = null;
				if(str.startsWith("HTTP://"))
					out = str;
				else
					out = "http://" + str;
				
				Return re = new Return(out);
				l.add(re);
				
				return l;
			}
		});
		
		System.out.println("The following functions were loaded:");
		functions.keySet().forEach(s -> System.out.println("- %s".formatted(s)));
		
		return functions;
	}
	
}
