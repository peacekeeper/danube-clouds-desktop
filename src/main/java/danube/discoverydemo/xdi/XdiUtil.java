package danube.discoverydemo.xdi;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import xdi2.core.Literal;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.impl.BasicLiteral;
import xdi2.core.util.CopyUtil.CopyStrategy;

/**
 * XDI util methods
 */
public class XdiUtil {

	public static String hashAndSaltPass(String pass) {

		if (pass == null) throw new NullPointerException();

		// create hash for password

		String salt = "" + randomChar() + randomChar() + randomChar();
		String hashedAndSaltedPass = DigestUtils.shaHex(salt + pass);

		return salt + hashedAndSaltedPass;
	}

	public static boolean checkPass(String pass, String claimedPass) {

		Matcher matcher = Pattern.compile("^(...)(.+?)$").matcher(pass);
		if (! matcher.matches()) return(false);
		String salt = matcher.group(1);
		String hashedAndSaltedPass = matcher.group(2);
		return DigestUtils.shaHex(salt + claimedPass).equals(hashedAndSaltedPass);
	}

	public static char randomChar() {

		final char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		return chars[new Random().nextInt(chars.length)];
	}

	/*
	 * Helper classes
	 */

	public static class SecretTokenCensoringCopyStrategy extends CopyStrategy {

		@Override
		public Literal replaceLiteral(Literal literal) {

			if (literal.getContextNode().getXri().toString().contains(XDIPolicyConstants.XRI_S_SECRET_TOKEN.toString())) {

				return new BasicLiteral("********");
			} else {

				return literal;
			}
		};
	}

	public static class SecretTokenInsertingCopyStrategy extends CopyStrategy {

		private String secretToken;

		public SecretTokenInsertingCopyStrategy(String secretToken) {

			this.secretToken = secretToken;
		}

		@Override
		public Literal replaceLiteral(Literal literal) {

			if (literal.getContextNode().getXri().toString().contains(XDIPolicyConstants.XRI_S_SECRET_TOKEN.toString())) {

				return new BasicLiteral(this.secretToken);
			} else {

				return literal;
			}
		};
	};
}
