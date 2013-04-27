package danube.discoverydemo.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xdi2.core.xri3.XDI3Segment;

public class PersonDictionary {

	public static final List<XDI3Segment> DICTIONARY_PERSON_LIST = new ArrayList<XDI3Segment> ();
	public static final Map<XDI3Segment, String> DICTIONARY_PERSON_MAP = new HashMap<XDI3Segment, String> ();

	static {

		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+name<+title>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+name<+suffix>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+first<+name>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+middle<+name>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+last<+name>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+name<+notes>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("<+email>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+secondary<+email>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+mobile<+phone>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("+home<+phone>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("<+fax>"));
		DICTIONARY_PERSON_LIST.add(XDI3Segment.create("<+website>"));

		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+name<+title>"), "Name Title");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+name<+suffix>"), "Name Suffix");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+first<+name>"), "First Name");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+middle<+name>"), "Middle Name");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+last<+name>"), "Last Name");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+name<+notes>"), "Name Notes");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("<+email>"), "E-mail");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+secondary<+email>"), "Secondary E-Mail");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+mobile<+phone>"), "Mobile Phone");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("+home<+phone>"), "Home Phone");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("<+fax>"), "Fax");
		DICTIONARY_PERSON_MAP.put(XDI3Segment.create("<+website>"), "Website");
	}
}
