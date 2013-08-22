package commons.utils.string;

public class StringTools {
	public static void toString(Integer i) {
	}

	public static String HexToStr(int i) {
		String s = "0123456789abcdef";
		StringBuffer sb = new StringBuffer();
		for (int j = 0; i >= 16; j++) {
			int a = i % 16;
			i /= 16;
			sb.append(s.charAt(a));
		}
		sb.append(s.charAt(i));
		return sb.reverse().toString();
	}

	private static final char[] hexes = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String hex2Str(int iVal) {
		StringBuffer sb = new StringBuffer(8);
		boolean started = false;
		for (int i = 24; i >= 0; i -= 8) {
			byte b = (byte) (iVal >> i);
			int val = b >> 4;
			if (val > 0 || started) {
				started = true;
				sb.append(hexes[b >> 4]);
			}
			val = b & 15;
			if (val > 0 || started) {
				started = true;
				sb.append(hexes[b & 15]);
			}
		}
		return sb.toString();
	}

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };

	private static String toUnsignedString(int i, int shift) {
		char[] buf = new char[32];
		int charPos = 32;
		int radix = 1 << shift;
		int mask = radix - 1;
		do {
			buf[--charPos] = digits[i & mask];
			i >>>= shift;
		} while (i != 0);
		return new String(buf, charPos, (32 - charPos));
	}

	public static void main(String[] args) {
		String s1 = StringTools.HexToStr(320);
		System.out.println(s1 + "--" + Integer.toHexString(320));
		StringBuffer sb = new StringBuffer();
		sb.append("12345");
		System.out.println(sb.reverse().toString());
	}
}
