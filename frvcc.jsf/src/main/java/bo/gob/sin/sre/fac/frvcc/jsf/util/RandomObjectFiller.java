package bo.gob.sin.sre.fac.frvcc.jsf.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomObjectFiller {

	private Random random = new Random();
	private String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
	private String numericString = "0123456789";

	public <T> T createAndFill(Class<T> clazz) throws Exception {
		T instance = clazz.newInstance();
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.getType().isPrimitive()) {
				field.setAccessible(true);
				Object value = getRandomValueForField(field);
				field.set(instance, value);
			}
		}
		return instance;
	}

	private Object getRandomValueForField(Field field) throws Exception {
		Class<?> type = field.getType();

		// Note that we must handle the different types here! This is just an
		// example, so this list is not complete! Adapt this to your needs!
		if (type.isEnum()) {
			Object[] enumValues = type.getEnumConstants();
			return enumValues[random.nextInt(enumValues.length)];
		} else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
			return random.nextInt();
		} else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
			return random.nextLong();
		} else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
			return random.nextDouble();
		} else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
			return random.nextFloat();
		} else if (type.equals(BigDecimal.class)) {
			return new BigDecimal(BigInteger.valueOf(new Random().nextInt(100001)), 2);
		} else if (type.equals(String.class)) {
			return getAlphaNumericString(6, numericString);
		} else if (type.equals(BigInteger.class)) {
			return BigInteger.valueOf(random.nextInt());
		} else if (type.equals(Date.class)) {
			return Date.from(Instant.now());
		} else if (type.equals(UUID.class)) {
			return UUID.randomUUID();
		}

		return createAndFill(type);
	}

	// function to generate a random string of length n
	static String getAlphaNumericString(int n, String alphaNumericString) {

		// chose a Character random from this String
		// String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" +
		// "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (alphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(alphaNumericString.charAt(index));
		}

		return sb.toString();
	}
}