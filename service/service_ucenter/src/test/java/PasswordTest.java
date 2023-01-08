import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * PACKAGE_NAME
 *
 * @author xzwnp
 * 2023/1/3
 * 17:30
 */
public class PasswordTest {
	@Test
	public void name() {
		System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
	}
}
