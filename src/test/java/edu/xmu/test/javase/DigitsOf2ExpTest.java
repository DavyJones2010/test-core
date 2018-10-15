package edu.xmu.test.javase;

import org.junit.Test;

public class DigitsOf2ExpTest {
	/**
	 * 将target值拆解为2^N集合
	 */
	@Test
	public void getExpOf2() {
		long target = 1073741824L;
		String line = Long.toBinaryString(target);
		System.out.println(line);
		int len = line.length();
		for (int i = 0; i < len; i++) {
			if (line.charAt(i) == '1') {
				System.out.println("2的" + (len - i - 1) + "次方");
			}
		}
	}

	/**
	 * 判断target值是否是2^N
	 */
	@Test
	public void isExpOf2() {
		long target = 4503599627370490L;
		System.out.println(Long.bitCount(target) == 1);
	}

	/**
	 * 得到2^N的值
	 */
	@Test
	public void getExpValueOf2() {
		int i = 61;
		// 2^33 = 8589934592
		// 2^34 = 17179869184
		// 2^35 = 34359738368
		// 2^36 = 68719476736
		System.out.printf("%d", (long) Math.pow(2, i));
	}
	
//	insert into bmw_certify_0785(id, gmt_create, gmt_modified, member_id, customer_id, certify_from, certify_status, certify_time, certify_expired_time, certify_product, certify_level, ent_register_no, ent_corp_name, ent_corp_card_no, ent_bus_licence, ent_org_code, ent_bus_scope, ent_validity, ent_address, ent_contact, ent_pic, extra_info, operation, version, certify_mode, certificate_picture, ent_certificate_picture, ent_legal_person_certificate_picture) values(19, '2016-03-17 16:37:32', '2016-03-17 16:37:32', 3696230161, 173533, 1, 1, '2016-03-17 16:37:31', null, 0, null, null, null, null, null, null, null, null, 'CN^^^广西壮族自治区^^^北海市^^^null', null, null, 0, '1:5', 0, null, null, null, null);

}
