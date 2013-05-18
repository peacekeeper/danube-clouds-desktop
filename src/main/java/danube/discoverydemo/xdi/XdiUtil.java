package danube.discoverydemo.xdi;

import xdi2.core.Literal;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.impl.BasicLiteral;
import xdi2.core.util.CopyUtil.CopyStrategy;

/**
 * XDI util methods
 */
public class XdiUtil {

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
