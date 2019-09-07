package transportProtocol;

import java.util.ArrayList;
import java.util.List;

public class Headres {
	private List<Header> headers;
//	private String[] headersKey;
//	private String[] headersValue;
//	private int headersCapacity;

	public Headres() {
		headers = new ArrayList<>();
//		headersCapacity = 10;
//		headersKey = new String[headersCapacity];
//		headersValue = new String[headersCapacity];
	}

	public void addCompleteHeader(String completeHeader) {
		int separator = completeHeader.indexOf(":");
		String headerKey = completeHeader.substring(0, separator);
		String headerValue = completeHeader.substring(separator + 1);
		headers.add(new Header(headerKey, headerValue));
//		addHeader(headerKey, headerValue);
	}
	
	public void addHeader(String headerKey, String headerValue) {
//		resizeHeaders();
//		int headersSize = getHeadersSize();
		int pos;
		if ((pos = getHeaderKeyPosition(headerKey)) < 0) {
			headers.add(new Header(headerKey, headerValue));
//			headersKey[pos] = headerKey;
//			headersValue[pos] = headerValue;
		} else {
			headers.set(pos, new Header(headerKey, headerValue));
//			headersKey[headersSize + 1] = headerKey;
//			headersValue[headersSize + 1] = headerValue;
		}
	}

	public void addHeader(String headerKey, int headerValue) {
		addHeader(headerKey, String.valueOf(headerValue));
	}

	public void addHeader(String headerKey, Long headerValue) {
		addHeader(headerKey, String.valueOf(headerValue));
	}
	
	public String getHeaderValueByKey(String headerKey) {
		int pos = getHeaderKeyPosition(headerKey);
		if (pos < 0) return null;
//		return headersValue[pos];
		return headers.get(pos).getVal();
	}
	
	public void removeHeader(String headerKey) {
		int pos;
		if ((pos = getHeaderKeyPosition(headerKey)) < 0) return;
		headers.remove(pos);
//		for (int i = pos ; i < headersKey.length - 1 ; i++) {
//			headersKey[i] = headersKey[i + 1];
//			headersValue[i] = headersValue[i + 1];
//			if (i == headersKey.length - 1) {
//				headersKey[i] = null;
//				headersValue[i] = null;
//			}
//		}
	}

	public String getCompleteHeadersSet() {
		StringBuilder headersSet = new StringBuilder();
		if (headers.size() == 0) return "";
		for (Header header : headers) headersSet.append(header.getHeaderSet() + System.lineSeparator());
		
//		if (headersKey[0] != null) headersSet.append(headersKey[0] + System.lineSeparator());
//		for (int i = 1 ; i < headersKey.length ; i++) {
//			if (headersKey[i] == null) continue;
//			headersSet.append(headersKey[i] + ":" + headersValue[i] + System.lineSeparator());
//		}
		headersSet.append(System.lineSeparator());
		return headersSet.toString();
	}

	protected void setFirstLine(String line) {
		if (headers.size() == 0) {
			headers.add(0, new Header(null, line));
		} else {
			headers.set(0, new Header(null, line));	
		}
//		headersKey[0] = line;
	}

	protected String getFirstLine() {
		return headers.get(0).getVal();
//		return headersKey[0];
	}

//	private void resizeHeaders() {
//		if (getHeadersSize() == (headersCapacity -1) ) {
//			headersCapacity += 5;
//			String[] tempHeadersKey = new String[headersCapacity];
//			String[] tempHeadersValue = new String[headersCapacity];
//			System.arraycopy(headersKey, 0, tempHeadersKey, 0, headersKey.length);
//			System.arraycopy(headersValue, 0, tempHeadersValue, 0, headersValue.length);
//			headersKey = tempHeadersKey;
//			headersValue = tempHeadersValue;
//		}
//	}

	protected int getHeaderKeyPosition(String headerKey) {
//		for (int i = 1 ; i < headersKey.length ; i ++) {
//			if (headerKey.equals(headersKey[i])) return i;
//		}
//		return -1;
		int position = -1;
		for (Header h : headers) {
			position++;
			if (h.getKey() == null) continue;
			if (h.getKey().equals(headerKey)) return position;
		}
		return -1;
	}

//	private int getHeadersSize() {
//		int count = 0;
//		for (String item : headersKey) {
//			if (item != null) count++;
//		}
//		return count;
//	}
}
